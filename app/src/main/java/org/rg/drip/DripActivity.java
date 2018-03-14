package org.rg.drip;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import org.rg.drip.base.BaseActivity;
import org.rg.drip.utils.ToastUtil;

public class DripActivity extends BaseActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.activity_drip;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
		                                                         toolbar,
		                                                         R.string.navigation_drawer_open,
		                                                         R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = findViewById(R.id.sidebar_view);
		navigationView.setNavigationItemSelectedListener(this);
		ToastUtil.showColorfulToast(DripActivity.this, "Test");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
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
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.nav_camera) {
			// Handle the camera action
		} else if (id == R.id.nav_gallery) {
			ToastUtil.showColorfulToast(DripActivity.this, "nav_gallery");
		} else if (id == R.id.nav_slideshow) {
			ToastUtil.showColorfulToast(DripActivity.this, "nav_slideshow");
		} else if (id == R.id.nav_manage) {
			ToastUtil.showColorfulToast(DripActivity.this, "nav_manage");
		} else if (id == R.id.nav_share) {
			ToastUtil.showColorfulToast(DripActivity.this, "nav_share");
		} else if (id == R.id.nav_send) {
			ToastUtil.showColorfulToast(DripActivity.this, "nav_send");
		}

		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
