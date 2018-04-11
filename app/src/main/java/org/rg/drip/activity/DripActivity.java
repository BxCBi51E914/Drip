package org.rg.drip.activity;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import org.apache.commons.logging.Log;
import org.reactivestreams.Publisher;
import org.rg.drip.R;
import org.rg.drip.base.BaseActivity;
import org.rg.drip.base.BaseMainFragment;
import org.rg.drip.constant.UIConstant;
import org.rg.drip.constant.WordConstant;
import org.rg.drip.data.model.cache.Word;
import org.rg.drip.data.model.cache.Wordbook;
import org.rg.drip.data.model.remote.WordR;
import org.rg.drip.data.model.remote.WordbookR;
import org.rg.drip.event.TabSelectedEvent;
import org.rg.drip.fragment.first.ZhihuFirstFragment;
import org.rg.drip.fragment.first.child.FirstHomeFragment;
import org.rg.drip.fragment.second.ZhihuSecondFragment;
import org.rg.drip.fragment.second.child.ViewPagerFragment;
import org.rg.drip.fragment.user.MeFragment;
import org.rg.drip.fragment.user.UserMainFragment;
import org.rg.drip.fragment.wordbook.StudyActionFragment;
import org.rg.drip.fragment.wordbook.WordBookMainFragment;
import org.rg.drip.utils.BmobUtil;
import org.rg.drip.utils.LoadingTipDialogUtil;
import org.rg.drip.utils.LoggerUtil;
import org.rg.drip.widget.bottombar.BottomBar;
import org.rg.drip.widget.bottombar.BottomBarTab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import hugo.weaving.DebugLog;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class DripActivity extends BaseActivity implements BaseMainFragment.OnBackToFirstListener {
	
	private List<SupportFragment> mMainFragments = new ArrayList<>(4);
	
	@BindView(R.id.bottomBar) BottomBar mBottomBar;
	
	@Override
	protected int getContentViewLayoutID() {
		return R.layout.activity_drip_main;
	}
	
	@DebugLog
	@Override
	protected void initView(Bundle savedInstanceState) {
		SupportFragment firstFragment = findFragment(WordBookMainFragment.class);
		if(firstFragment == null) {
			mMainFragments.add(UIConstant.WORDBOOK, WordBookMainFragment.newInstance());
			mMainFragments.add(UIConstant.READING, ZhihuSecondFragment.newInstance());
			mMainFragments.add(UIConstant.THIRD, ZhihuFirstFragment.newInstance());
			mMainFragments.add(UIConstant.SETTING, UserMainFragment.newInstance());
			
			loadMultipleRootFragment(R.id.fl_container,
			                         UIConstant.WORDBOOK,
			                         mMainFragments.get(UIConstant.WORDBOOK),
			                         mMainFragments.get(UIConstant.READING),
			                         mMainFragments.get(UIConstant.THIRD),
			                         mMainFragments.get(UIConstant.SETTING));
		} else {
			mMainFragments.add(UIConstant.WORDBOOK, firstFragment);
			mMainFragments.add(UIConstant.READING, findFragment(ZhihuSecondFragment.class));
			mMainFragments.add(UIConstant.THIRD, findFragment(ZhihuFirstFragment.class));
			mMainFragments.add(UIConstant.SETTING, findFragment(UserMainFragment.class));
		}
		
		mBottomBar
				.addItem(new BottomBarTab(this, R.drawable.ic_folder_open))
				.addItem(new BottomBarTab(this, R.drawable.ic_discover_white_24dp))
				.addItem(new BottomBarTab(this, R.drawable.ic_message_white_24dp))
				.addItem(new BottomBarTab(this, R.drawable.ic_account_circle_white_24dp));
		
		mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
			@Override
			public void onTabSelected(int position, int prePosition) {
				// position 与 UIConstant.WORDBOOK 之类一一对应
				SupportFragment fragment = mMainFragments.get(prePosition);
				if(fragment instanceof BaseMainFragment) {
					((BaseMainFragment) fragment).cancelAnimateCircularReveal(fragment.getView());
				}
				showHideFragment(mMainFragments.get(position), mMainFragments.get(prePosition));
				fragment = mMainFragments.get(position);
				if(fragment instanceof BaseMainFragment) {
					int height = mBottomBar.getHeight();
					int width = mBottomBar.getWidth();
					int count = mBottomBar.getCount();
					int y = (int) mBottomBar.getY();
					int perWidth = width / count;
					int w = (Math.max((count - position - 1) << 1 | 1, position << 1 | 1) >> 1)
					        * perWidth + (perWidth >> 1);
					((BaseMainFragment) fragment).animateCircularReveal(fragment.getView(),
					                                                    perWidth * position + (
							                                                    perWidth
							                                                    >> 1),
					                                                    y + (height >> 1),
					                                                    height >> 1,
					                                                    (float) Math.sqrt(w * w
					                                                                      + y * y));
				}
			}
			
			@Override
			public void onTabUnselected(int position) {
			
			}
			
			@Override
			public void onTabReselected(int position) {
				final SupportFragment currentFragment = mMainFragments.get(position);
				int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();
				
				// 如果不在该类别Fragment的主页,则回到主页;
				if(count > 1) {
					if(currentFragment instanceof WordBookMainFragment) {
						currentFragment.popToChild(StudyActionFragment.class, false);
					} else if(currentFragment instanceof ZhihuSecondFragment) {
						currentFragment.popToChild(ViewPagerFragment.class, false);
					} else if(currentFragment instanceof ZhihuFirstFragment) {
						currentFragment.popToChild(FirstHomeFragment.class, false);
					} else if(currentFragment instanceof UserMainFragment) {
						currentFragment.popToChild(MeFragment.class, false);
					}
					return;
				}
				
				// 这里推荐使用EventBus来实现 -> 解耦
				if(count == 1) {
					// 在FirstPagerFragment中接收, 因为是嵌套的孙子Fragment 所以用EventBus比较方便
					// 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
					EventBusActivityScope
							.getDefault(DripActivity.this)
							.post(new TabSelectedEvent(position));
				}
			}
		});
		//		UserRemoteSource urs = UserRemoteSource.getInstance();
//		User user = new User();
//		user.setId(1);
//		user.setUsername("asdasdasd");
//		user.setPassword("aaaaaaaaaa1qwc");
//		user.setEmail("2384167200@qq.com");
//		user.setNickname("2384");
//		urs.signUp(user)
//	       .subscribeOn(Schedulers.io())
//	       .observeOn(AndroidSchedulers.mainThread())
//	       .subscribe(
//	       		result -> {
//			        ToastUtil.showColorfulToast(DripActivity.this, "登录成功");
//		        },
//	            throwable -> {
//		            ToastUtil.showColorfulToast(DripActivity.this, "失败");
//	            }
//	       );

//		WordbookR wordbook = new WordbookR();
//		wordbook.setId(0);
//		wordbook.setUserId(0);
//		wordbook.setOption("123");
//		WordLinkR wordLink = new WordLinkR();
//		wordLink.setId(0);
//		wordLink.setState((byte) 0);
//		wordLink.setWord("word");
//		wordLink.setWordBookId(0);
//		new BmobBatch().insertBatch(Collections.singletonList(wordbook))
//		               .insertBatch(Collections.singletonList(wordLink))
//		               .doBatch(new QueryListListener<BatchResult>() {
//			               @Override
//			               public void done(List<BatchResult> list, BmobException e) {
//				               if(e != null) {
//					               BmobUtil.logBmobErrorInfo(e);
//					               return;
//				               }
//				               for(BatchResult result : list) {
//					               if(result.isSuccess()) {
//						               LoggerUtil.d("true");
//					               } else {
//						               BmobUtil.logBmobErrorInfo(result.getError());
//					               }
//				               }
//			               }
//		               });
		// result (true)

//		WordbookR wordbook = new WordbookR();
//		wordbook.setId(0);
//		wordbook.setUserId(0);
//		wordbook.save(new SaveListener<String>() {
//			@Override
//			public void done(String s, BmobException e) {
//				if(e != null) {
//					BmobUtil.logBmobErrorInfo(e);
//					return;
//				}
//				LoggerUtil.d("s: " + s + ", ObjectId: " + wordbook.getObjectId());
//			}
//		});
		// result (s: abe987dbc3, ObjectId: abe987dbc3)

//		List<BmobObject> wordbooks = new ArrayList<>();
//		for(int i = 0; i < 51; ++ i) {
//			wordbooks.add(new Wordbook(0, 0).convertToRemote());
//		}
//		new BmobBatch().insertBatch(wordbooks).doBatch(new QueryListListener<BatchResult>() {
//			@Override
//			public void done(List<BatchResult> list, BmobException e) {
//				if(e != null) {
//					BmobUtil.logBmobErrorInfo(e);
//					return;
//				}
//				int count = 0;
//				for(BatchResult result : list) {
//					if(result.isSuccess()) {
//						++ count;
//					} else {
//						BmobUtil.logBmobErrorInfo(result.getError());
//					}
//				}
//				LoggerUtil.d("count: " + count);
//			}
//		});
		// result (Error)
		
//		int count = 2000;
//		int limit = 500;
//		int queryCount = BmobUtil.getQueryCount(count, limit);
//		Flowable<List<Word>> flowable = getWords(0, limit);
//		for(int idx = 1; idx < queryCount; ++ idx) {
//			flowable = flowable.concatWith(getWords(limit * idx, limit));
//		}
//		List<Word> result = new ArrayList<>();
//		LoadingTipDialogUtil.show(DripActivity.this);
//		flowable
//				.subscribeOn(Schedulers.io())
//				.observeOn(AndroidSchedulers.mainThread())
//				.subscribe(words -> {
////					for(int idx = 0; idx < words.size(); ++ idx) {
////						LoggerUtil.d(words.get(idx).getWord());
////					}
//					LoggerUtil.d(words.get(0).getWord());
//					result.addAll(words);
//				}, throwable -> {
//				}, () -> {
//					LoadingTipDialogUtil.dismiss();
//					LoggerUtil.d(result.size());
//				});
	}
	
//	private Flowable<List<Word>> getWords(final int skip, final int limit) {
//		return Flowable.create(emitter -> {
//			new BmobQuery<WordR>()
//					.setSkip(skip)
//					.setLimit(limit)
//                                  .findObjects(new FindListener<WordR>() {
//	                                  @Override
//	                                  public void done(List<WordR> list, BmobException e) {
//
//		                                  if(e != null) {
//			                                  BmobUtil.logBmobErrorInfo(e);
//			                                  emitter.onError(e);
//			                                  return;
//		                                  }
//		                                  LoggerUtil.d("info (skip: "
//		                                               + skip
//		                                               + ", limit: "
//		                                               + limit
//		                                               + ", size:"
//		                                               + list.size()
//		                                               + ")");
//		                                  int size = list.size();
//		                                  if(size == 0) {
//			                                  return;
//		                                  }
//		                                  List<Word> data = new ArrayList<>(size);
//		                                  for(int idx = 0; idx < size; ++ idx) {
//			                                  data.add(list.get(idx).convertToCache());
//		                                  }
//		                                  emitter.onNext(data);
//		                                  emitter.onComplete();
//	                                  }
//                                  });
//		}, BackpressureStrategy.BUFFER);
//	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	public void onBackPressedSupport() {
		if(getSupportFragmentManager().getBackStackEntryCount() > 1) {
			pop();
		} else {
			ActivityCompat.finishAfterTransition(this);
		}
	}
	
	@Override
	public void onBackToFirstFragment() {
		mBottomBar.setCurrentItem(0);
	}
}
