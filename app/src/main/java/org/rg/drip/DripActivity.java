package org.rg.drip;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.orhanobut.logger.Logger;

import org.rg.drip.base.BaseActivity;
import org.rg.drip.constant.WordConstant;
import org.rg.drip.data.model.Word;
import org.rg.drip.data.model.bmob.WordR;
import org.rg.drip.data.model.realm.WordL;
import org.rg.drip.utils.BmobUtil;
import org.rg.drip.utils.LoggerUtil;
import org.rg.drip.utils.RealmUtil;

import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import hugo.weaving.DebugLog;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.android.schedulers.AndroidSchedulers;

public class DripActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
	
	@BindView(R.id.toolbar) Toolbar mToolbar;
	
	@BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
	
	@BindView(R.id.sidebar_view) NavigationView mNavigationView;
	
	@Override
	protected int getContentViewLayoutID() {
		return R.layout.activity_drip;
	}
	
	@DebugLog
	@Override
	protected void initView(Bundle savedInstanceState) {
		Logger.d("initView");
		setSupportActionBar(mToolbar);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
		                                                         mDrawerLayout,
		                                                         mToolbar,
		                                                         R.string.navigation_drawer_open,
		                                                         R.string.navigation_drawer_close);
		mDrawerLayout.addDrawerListener(toggle);
		toggle.syncState();
		mNavigationView.setNavigationItemSelectedListener(this);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initTools(DripActivity.this);


//		Observable<List<WordR>>
//				observable =
//				Observable.create(new ObservableOnSubscribe<List<WordR>>() {
//					@Override
//					public void subscribe(final ObservableEmitter<List<WordR>> emitter) throws
//					                                                                   Exception {
//						BmobQuery<WordR> query = new BmobQuery<WordR>();
//						query.addWhereEndsWith(WordConstant.WORD, "word")
//						     .findObjects(new FindListener<WordR>() {
//							     @Override
//							     public void done(List<WordR> list, BmobException e) {
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
		Realm realm = RealmUtil.getInstance();
		realm.where(WordL.class)
		     .equalTo(WordConstant.ID, 1)
		     .findFirstAsync()
		     .asFlowable()
		     .filter(wordL -> wordL.isLoaded())
		     .observeOn(AndroidSchedulers.mainThread());
		realm.close();
	}
	
	/**
	 * 初始化工具
	 */
	private void initTools(Context context) {
		LoggerUtil.init();
		BmobUtil.initialize(context);
		RealmUtil.initialize(context);
	}
	
	@Override
	public void onBackPressedSupport() {
		if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
			mDrawerLayout.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		int id = item.getItemId();
		
		if (id == R.id.nav_camera) {
		} else if (id == R.id.nav_gallery) {
		} else if (id == R.id.nav_slideshow) {
		} else if (id == R.id.nav_manage) {
		} else if (id == R.id.nav_share) {
		} else if (id == R.id.nav_send) {
		}
		mDrawerLayout.closeDrawer(GravityCompat.START);
		return true;
	}
}
