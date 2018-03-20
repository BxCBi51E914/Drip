package org.rg.drip;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.orhanobut.logger.Logger;

import org.rg.drip.base.BaseActivity;
import org.rg.drip.base.BaseMainFragment;
import org.rg.drip.data.model.realm.WordL;
import org.rg.drip.event.TabSelectedEvent;
import org.rg.drip.ui.fragment.first.ZhihuFirstFragment;
import org.rg.drip.ui.fragment.first.child.FirstHomeFragment;
import org.rg.drip.ui.fragment.fourth.ZhihuFourthFragment;
import org.rg.drip.ui.fragment.fourth.child.MeFragment;
import org.rg.drip.ui.fragment.second.ZhihuSecondFragment;
import org.rg.drip.ui.fragment.second.child.ViewPagerFragment;
import org.rg.drip.ui.fragment.third.ZhihuThirdFragment;
import org.rg.drip.ui.fragment.third.child.ShopFragment;
import org.rg.drip.ui.view.BottomBar;
import org.rg.drip.ui.view.BottomBarTab;
import org.rg.drip.utils.BmobUtil;
import org.rg.drip.utils.LoggerUtil;
import org.rg.drip.utils.RealmUtil;

import butterknife.BindView;
import hugo.weaving.DebugLog;
import io.realm.Realm;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

public class DripActivity extends BaseActivity implements BaseMainFragment.OnBackToFirstListener {
	public static final int FIRST = 0;
	public static final int SECOND = 1;
	public static final int THIRD = 2;
	public static final int FOURTH = 3;
	
	private SupportFragment[] mFragments = new SupportFragment[4];
	
	private BottomBar mBottomBar;
	
	@Override
	protected int getContentViewLayoutID() {
		return R.layout.zhihu_activity_main;
	}
	
	@DebugLog
	@Override
	protected void initView(Bundle savedInstanceState) {
		SupportFragment firstFragment = findFragment(ZhihuFirstFragment.class);
		if (firstFragment == null) {
			mFragments[FIRST] = ZhihuFirstFragment.newInstance();
			mFragments[SECOND] = ZhihuSecondFragment.newInstance();
			mFragments[THIRD] = ZhihuThirdFragment.newInstance();
			mFragments[FOURTH] = ZhihuFourthFragment.newInstance();
			
			loadMultipleRootFragment(R.id.fl_container, FIRST,
			                         mFragments[FIRST],
			                         mFragments[SECOND],
			                         mFragments[THIRD],
			                         mFragments[FOURTH]);
		} else {
			// 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
			
			// 这里我们需要拿到mFragments的引用
			mFragments[FIRST] = firstFragment;
			mFragments[SECOND] = findFragment(ZhihuSecondFragment.class);
			mFragments[THIRD] = findFragment(ZhihuThirdFragment.class);
			mFragments[FOURTH] = findFragment(ZhihuFourthFragment.class);
		}
		
		mBottomBar = (BottomBar) findViewById(R.id.bottomBar);
		
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
				if (count > 1) {
					if (currentFragment instanceof ZhihuFirstFragment) {
						currentFragment.popToChild(FirstHomeFragment.class, false);
					} else if (currentFragment instanceof ZhihuSecondFragment) {
						currentFragment.popToChild(ViewPagerFragment.class, false);
					} else if (currentFragment instanceof ZhihuThirdFragment) {
						currentFragment.popToChild(ShopFragment.class, false);
					} else if (currentFragment instanceof ZhihuFourthFragment) {
						currentFragment.popToChild(MeFragment.class, false);
					}
					return;
				}
				
				
				// 这里推荐使用EventBus来实现 -> 解耦
				if (count == 1) {
					// 在FirstPagerFragment中接收, 因为是嵌套的孙子Fragment 所以用EventBus比较方便
					// 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
					EventBusActivityScope.getDefault(DripActivity.this).post(new TabSelectedEvent(position));
				}
			}
		});
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		Observable<List<WordL>>
//				observable =
//				Observable.create(new ObservableOnSubscribe<List<WordL>>() {
//					@Override
//					public void subscribe(final ObservableEmitter<List<WordL>> emitter) throws
//					                                                                   Exception {
//						BmobQuery<WordL> query = new BmobQuery<WordL>();
//						query.addWhereEndsWith(WordConstant.WORD, "word")
//						     .findObjects(new FindListener<WordL>() {
//							     @Override
//							     public void done(List<WordL> list, BmobException e) {
//								     if (e == null) {
//								        emitter.onNext(list);
//								        emitter.onComplete();
//								        return;
//								     }
//								     emitter.onError(e);
//							     }
//						     });
//					}
//				});
//		Realm realm = RealmUtil.getInstance();
//		realm.beginTransaction();
//		WordL word = realm.createObject(WordL.class);
//		LoggerUtil.d(realm.getPath());
//		word.setId(1);
//		word.setWord("a");
//		realm.commitTransaction();
	}
	
	@Override
	public void onBackPressedSupport() {
		if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
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
