package org.rg.drip.activity;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import org.reactivestreams.Publisher;
import org.rg.drip.R;
import org.rg.drip.base.BaseActivity;
import org.rg.drip.base.BaseMainFragment;
import org.rg.drip.constant.UIConstant;
import org.rg.drip.data.model.cache.Word;
import org.rg.drip.data.model.remote.WordR;
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
import org.rg.drip.utils.RealmUtil;
import org.rg.drip.utils.TimeUtil;
import org.rg.drip.widget.bottombar.BottomBar;
import org.rg.drip.widget.bottombar.BottomBarTab;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import hugo.weaving.DebugLog;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
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
		
		mBottomBar.addItem(new BottomBarTab(this, R.drawable.ic_folder_open))
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
					EventBusActivityScope.getDefault(DripActivity.this)
					                     .post(new TabSelectedEvent(position));
				}
			}
		});
		
		final int limit = 1;
		final long delayTime = 1;
		final int zipCount = 10, concatCount = 100;
		final List<Word> data = new ArrayList<>();
		Flowable<List<Word>> concatFlowable = Flowable.empty();
		for(int i = 0; i < concatCount; ++ i) {
			Flowable<List<Word>> zipFlowable = Flowable.just(new ArrayList<>());
			for(int j = 0; j < zipCount; ++ j) {
				final int skip = (i * zipCount + j) * limit;
				Flowable<List<Word>> flowable = getWords(skip, limit).retryWhen(attempts -> attempts
						.flatMap(throwable -> {
							LoggerUtil.d("error: " + throwable.getMessage());
							if(throwable.getMessage().contains("Qps beyond the limit")) {
								LoggerUtil.d("retry: skip = " + skip);
								return Flowable.just(1).delay(1, TimeUnit.SECONDS);
							}
							return Flowable.error(throwable);
						}));
				zipFlowable = zipFlowable.zipWith(flowable, (words, words2) -> {
					words.addAll(words2);
					return words;
				});
			}
			zipFlowable = zipFlowable.zipWith(Flowable.just(1).delay(delayTime, TimeUnit.SECONDS),
			                                  (words, integer) -> words);
			concatFlowable = concatFlowable.concatWith(zipFlowable);
		}
		
		final long start = System.currentTimeMillis();
		Disposable disposable = concatFlowable.observeOn(Schedulers.io())
		                                      .subscribeOn(AndroidSchedulers.mainThread())
		                                      .subscribe(words -> {
			                                      int size = words.size();
			                                      StringBuilder content = new StringBuilder();
			                                      for(int idx = 0; idx < size; ++ idx) {
				                                      content.append(words.get(idx).getId())
				                                             .append(", ");
			                                      }
			                                      LoggerUtil.d(content.toString());
			                                      data.addAll(words);
		                                      });
	}
	
	private Flowable<List<Word>> getWords(final int skip, final int limit) {
		return Flowable.create(emitter -> {
			new BmobQuery<WordR>().setSkip(skip)
			                      .setLimit(limit)
			                      .findObjects(new FindListener<WordR>() {
				                      @Override
				                      public void done(List<WordR> list, BmobException e) {
					                      if(e != null) {
//						                      BmobUtil.logErrorInfo(e);
						                      emitter.onError(e);
						                      return;
					                      }
					                      int size = list.size();
					                      if(size == 0) {
						                      return;
					                      }
					                      List<Word> data = new ArrayList<>(size);
					                      for(int idx = 0; idx < size; ++ idx) {
						                      data.add(list.get(idx).convertToCache());
					                      }
					                      emitter.onNext(data);
					                      emitter.onComplete();
				                      }
			                      });
		}, BackpressureStrategy.BUFFER);
	}
	
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
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		RealmUtil.closeRealm();
	}
}
