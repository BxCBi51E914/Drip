package org.rg.drip.activity;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import org.rg.drip.R;
import org.rg.drip.base.BaseActivity;
import org.rg.drip.base.BaseMainFragment;
import org.rg.drip.constant.FragmentConstant;
import org.rg.drip.event.TabSelectedEvent;
import org.rg.drip.fragment.first.ZhihuFirstFragment;
import org.rg.drip.fragment.first.child.FirstHomeFragment;
import org.rg.drip.fragment.second.ZhihuSecondFragment;
import org.rg.drip.fragment.second.child.ViewPagerFragment;
import org.rg.drip.fragment.third.ZhihuThirdFragment;
import org.rg.drip.fragment.third.child.ShopFragment;
import org.rg.drip.fragment.user.MeFragment;
import org.rg.drip.fragment.user.UserMainFragment;
import org.rg.drip.fragment.wordbook.StudyActionFragment;
import org.rg.drip.fragment.wordbook.WordBookMainFragment;
import org.rg.drip.utils.ToastUtil;
import org.rg.drip.widget.bottombar.BottomBar;
import org.rg.drip.widget.bottombar.BottomBarTab;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import hugo.weaving.DebugLog;
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
			mMainFragments.add(FragmentConstant.WORDBOOK, WordBookMainFragment.newInstance());
			mMainFragments.add(FragmentConstant.READING, ZhihuSecondFragment.newInstance());
			mMainFragments.add(FragmentConstant.THIRD, ZhihuFirstFragment.newInstance());
			mMainFragments.add(FragmentConstant.SETTING, UserMainFragment.newInstance());

			loadMultipleRootFragment(R.id.fl_container,
			                         FragmentConstant.WORDBOOK,
			                         mMainFragments.get(FragmentConstant.WORDBOOK),
			                         mMainFragments.get(FragmentConstant.READING),
			                         mMainFragments.get(FragmentConstant.THIRD),
			                         mMainFragments.get(FragmentConstant.SETTING));
		} else {
			mMainFragments.add(FragmentConstant.WORDBOOK, firstFragment);
			mMainFragments.add(FragmentConstant.READING, findFragment(ZhihuSecondFragment.class));
			mMainFragments.add(FragmentConstant.THIRD, findFragment(ZhihuFirstFragment.class));
			mMainFragments.add(FragmentConstant.SETTING, findFragment(UserMainFragment.class));
		}

		mBottomBar.addItem(new BottomBarTab(this, R.drawable.ic_folder_open))
		          .addItem(new BottomBarTab(this, R.drawable.ic_discover_white_24dp))
		          .addItem(new BottomBarTab(this, R.drawable.ic_message_white_24dp))
		          .addItem(new BottomBarTab(this, R.drawable.ic_account_circle_white_24dp));

		mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
			@Override
			public void onTabSelected(int position, int prePosition) {
				// position 与 FragmentConstant.WORDBOOK 之类一一对应
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
					                                                    perWidth * position +
					                                                    (perWidth >> 1),
					                                                    y + (height >> 1),
					                                                    height >> 1,
					                                                    (float) Math.sqrt(w * w +
					                                                                      y * y));
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
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
}
