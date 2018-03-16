package org.rg.drip;

import android.content.Context;
import android.os.Bundle;
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
import org.rg.drip.data.model.bmob.Word;
import org.rg.drip.data.source.contract.WordContract;
import org.rg.drip.utils.BmobUtil;
import org.rg.drip.utils.LoggerUtil;

import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import hugo.weaving.DebugLog;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

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
		ActionBarDrawerToggle
				toggle =
				new ActionBarDrawerToggle(this,
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
		
		
		Observable<List<Word>>
				observable =
				Observable.create(new ObservableOnSubscribe<List<Word>>() {
					@Override
					public void subscribe(final ObservableEmitter<List<Word>> emitter) throws
					                                                                   Exception {
						BmobQuery<Word> query = new BmobQuery<Word>();
						query.addWhereEndsWith(WordConstant.WORD, "word")
						     .findObjects(new FindListener<Word>() {
							     @Override
							     public void done(List<Word> list, BmobException e) {
								     if (e == null) {
								        emitter.onNext(list);
								        emitter.onComplete();
								        return;
								     }
								     emitter.onError(e);
							     }
						     });
					}
				});
		Word word = new Word();
		word.setId(1);
		word.setWord("a");
		word.save(new SaveListener<String>() {
			@Override
			public void done(String s, BmobException e) {
			
			}
		});
	}
	
	/**
	 * 初始化工具
	 */
	private void initTools(Context context) {
		LoggerUtil.init();
		BmobUtil.initialize(context);
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
