package org.rg.drip.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import org.greenrobot.eventbus.EventBus;
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
		EventBus.getDefault().register(this);
		if(getContentViewLayoutID() != 0) {
			setContentView(getContentViewLayoutID());
			initView(savedInstanceState);
		}
	}

	/**
	 * 设置状态栏颜色
	 */
	protected void initSystemBarTint() {
//		Window window = getWindow();
//		// 沉浸式状态栏
//		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//		if(translucentStatusBar()) {
//			window.getDecorView()
//			      .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
//			                             View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//			window.setStatusBarColor(Color.TRANSPARENT);
//		} else {
//			window.setStatusBarColor(setStatusBarColor());
//		}
		QMUIStatusBarHelper.translucent(this);
		getWindow().setStatusBarColor(setStatusBarColor());
	}

	/**
	 * 子类可以重写改变状态栏颜色
	 */
	@ColorInt
	protected int setStatusBarColor() {
		return getColor(R.color.colorPrimary);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
