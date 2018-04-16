package org.rg.drip.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.rg.drip.R;
import org.rg.drip.base.BaseActivity;
import org.rg.drip.base.BaseFragment;
import org.rg.drip.base.BaseMainFragment;
import org.rg.drip.constant.MessageEventConstant;
import org.rg.drip.constant.UIConstant;
import org.rg.drip.event.MessageEvent;
import org.rg.drip.event.TabSelectedEvent;
import org.rg.drip.fragment.reading.ReadingMainFragment;
import org.rg.drip.fragment.reading.ReadingHomeFragment;
import org.rg.drip.fragment.lexical.LexicalMainFragment;
import org.rg.drip.fragment.lexical.LexicalListFragment;
import org.rg.drip.fragment.user.MeFragment;
import org.rg.drip.fragment.user.UserMainFragment;
import org.rg.drip.fragment.wordbook.StudyActionFragment;
import org.rg.drip.fragment.wordbook.WordbookMainFragment;
import org.rg.drip.utils.RealmUtil;
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
		BaseFragment firstFragment = findFragment(WordbookMainFragment.class);
		if(firstFragment == null) {
			mMainFragments.add(UIConstant.WORDBOOK, WordbookMainFragment.newInstance());
			mMainFragments.add(UIConstant.CUSTOM, LexicalMainFragment.newInstance());
			mMainFragments.add(UIConstant.READING, ReadingMainFragment.newInstance());
			mMainFragments.add(UIConstant.SETTING, UserMainFragment.newInstance());
			
			loadMultipleRootFragment(R.id.fl_container,
			                         UIConstant.WORDBOOK,
			                         mMainFragments.get(UIConstant.WORDBOOK),
			                         mMainFragments.get(UIConstant.CUSTOM),
			                         mMainFragments.get(UIConstant.READING),
			                         mMainFragments.get(UIConstant.SETTING));
		} else {
			mMainFragments.add(UIConstant.WORDBOOK, firstFragment);
			mMainFragments.add(UIConstant.CUSTOM, findFragment(ReadingMainFragment.class));
			mMainFragments.add(UIConstant.READING, findFragment(LexicalMainFragment.class));
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
					int w = (Math.max((count - position - 1) << 1 | 1, position << 1 | 1) >> 1) * perWidth +
					        (perWidth >> 1);
					((BaseMainFragment) fragment).animateCircularReveal(fragment.getView(),
					                                                    perWidth * position + (perWidth >> 1),
					                                                    y + (height >> 1),
					                                                    height >> 1,
					                                                    (float) Math.sqrt(w * w + y * y));
				}
			}
			
			@Override
			public void onTabUnselected(int position) {
			
			}
			
			@Override
			public void onTabReselected(int position) {
				final SupportFragment currentFragment = mMainFragments.get(position);
				int count = currentFragment.getChildFragmentManager()
				                           .getBackStackEntryCount();
				
				// 如果不在该类别Fragment的主页,则回到主页;
				if(count > 1) {
					if(currentFragment instanceof WordbookMainFragment) {
						currentFragment.popToChild(StudyActionFragment.class, false);
					} else if(currentFragment instanceof LexicalMainFragment) {
						currentFragment.popToChild(LexicalListFragment.class, false);
					} else if(currentFragment instanceof ReadingMainFragment) {
						currentFragment.popToChild(ReadingHomeFragment.class, false);
					} else if(currentFragment instanceof UserMainFragment) {
						currentFragment.popToChild(MeFragment.class, false);
					}
					return;
				}
				
				// 这里推荐使用EventBus来实现 -> 解耦
				if(count == 1) {
					// 在FirstPagerFragment中接收, 因为是嵌套的孙子Fragment 所以用EventBus比较方便
					// 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
//					EventBusActivityScope.getDefault(DripActivity.this)
//					                     .post(new TabSelectedEvent(position));
//					EventBus.getDefault().post();
				}
			}
		});
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
	
	@Override
	protected boolean registerEventBus() {
		return true;
	}
	
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void restartThis(MessageEvent event) {
		if(event.getCode() != MessageEventConstant.RESTART_ACTIVITY) {
			return;
		}
		finish();
		startActivity(new Intent(this, DripActivity.class));
	}
}
