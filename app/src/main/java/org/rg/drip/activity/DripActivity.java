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
import org.rg.drip.utils.ToastUtil;
import org.rg.drip.view.bottom_bar.BottomBar;
import org.rg.drip.view.bottom_bar.BottomBarTab;

import butterknife.BindView;
import hugo.weaving.DebugLog;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class DripActivity extends BaseActivity implements BaseMainFragment.OnBackToFirstListener {
	
	private SupportFragment[] mFragments = new SupportFragment[4];
	
	@BindView(R.id.bottomBar) BottomBar mBottomBar;
	
	@Override
	protected int getContentViewLayoutID() {
		return R.layout.activity_drip_main;
	}
	
	@DebugLog
	@Override
	protected void initView(Bundle savedInstanceState) {
		SupportFragment firstFragment = findFragment(ZhihuFirstFragment.class);
		if(firstFragment == null) {
			mFragments[FragmentConstant.WORD_BOOK] = ZhihuFirstFragment.newInstance();
			mFragments[FragmentConstant.READING] = ZhihuSecondFragment.newInstance();
			mFragments[FragmentConstant.THIRD] = ZhihuThirdFragment.newInstance();
			mFragments[FragmentConstant.SETTING] = UserMainFragment.newInstance();
			
			loadMultipleRootFragment(R.id.fl_container,
			                         FragmentConstant.WORD_BOOK,
			                         mFragments[FragmentConstant.WORD_BOOK],
			                         mFragments[FragmentConstant.READING],
			                         mFragments[FragmentConstant.THIRD],
			                         mFragments[FragmentConstant.SETTING]);
		} else {
			// 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
			
			// 这里我们需要拿到mFragments的引用
			mFragments[FragmentConstant.WORD_BOOK] = firstFragment;
			mFragments[FragmentConstant.READING] = findFragment(ZhihuSecondFragment.class);
			mFragments[FragmentConstant.THIRD] = findFragment(ZhihuThirdFragment.class);
			mFragments[FragmentConstant.SETTING] = findFragment(UserMainFragment.class);
		}
		
		mBottomBar.addItem(new BottomBarTab(this, R.drawable.ic_home_white_24dp))
		          .addItem(new BottomBarTab(this, R.drawable.ic_discover_white_24dp))
		          .addItem(new BottomBarTab(this, R.drawable.ic_message_white_24dp))
		          .addItem(new BottomBarTab(this, R.drawable.ic_account_circle_white_24dp));
		
		mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
			@Override
			public void onTabSelected(int position, int prePosition) {
				showHideFragment(mFragments[position], mFragments[prePosition]);
			}
			
			@Override
			public void onTabUnselected(int position) {
			
			}
			
			@Override
			public void onTabReselected(int position) {
				final SupportFragment currentFragment = mFragments[position];
				int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();
				
				// 如果不在该类别Fragment的主页,则回到主页;
				if(count > 1) {
					if(currentFragment instanceof ZhihuFirstFragment) {
						currentFragment.popToChild(FirstHomeFragment.class, false);
					} else if(currentFragment instanceof ZhihuSecondFragment) {
						currentFragment.popToChild(ViewPagerFragment.class, false);
					} else if(currentFragment instanceof ZhihuThirdFragment) {
						currentFragment.popToChild(ShopFragment.class, false);
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
		ToastUtil.showCustumToast(DripActivity.this, "DripActivity.onCreate");
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
