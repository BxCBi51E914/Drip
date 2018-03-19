package org.rg.drip.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import org.rg.drip.R;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Activity基类
 * <p>
 * Author : Tank
 * Time : 08/03/2018
 */

public abstract class BaseActivity extends SupportActivity {

	/**
	 * 获取布局ID
	 *
	 * @return 布局id
	 */
	protected abstract int getContentViewLayoutID();

	/**
	 * 界面初始化前期准备
	 */
	protected void beforeInit() {
	}

	/**
	 * 初始化布局以及View控件
	 */
	protected abstract void initView(Bundle savedInstanceState);

	@Override
	public void setContentView(@LayoutRes int layoutResID) {
		super.setContentView(layoutResID);
		ButterKnife.bind(this);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initSystemBarTint();
		beforeInit();
		if(getContentViewLayoutID() != 0) {
			setContentView(getContentViewLayoutID());
			initView(savedInstanceState);
		}
	}

	/**
	 * 子类可以重写决定是否使用透明状态栏
	 */
	protected boolean translucentStatusBar() {
		return true;
	}

	/**
	 * 设置状态栏颜色
	 */
	protected void initSystemBarTint() {
		Window window = getWindow();
		if(translucentStatusBar()) {
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.getDecorView()
			      .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
			                             View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.TRANSPARENT);
			return;
		}
		// 沉浸式状态栏
		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		window.setStatusBarColor(setStatusBarColor());
	}

	/**
	 * 子类可以重写改变状态栏颜色
	 */
	protected int setStatusBarColor() {
		return R.color.blue;
	}
}
