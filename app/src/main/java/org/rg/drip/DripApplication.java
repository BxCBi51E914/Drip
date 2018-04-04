package org.rg.drip;

import android.app.Application;
import android.content.Context;

import org.rg.drip.data.model.local.UserL;
import org.rg.drip.utils.BmobUtil;
import org.rg.drip.utils.FragmentationUtil;
import org.rg.drip.utils.LoggerUtil;
import org.rg.drip.utils.RealmUtil;
import org.rg.drip.utils.StethoUtil;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class DripApplication extends Application {

	private static DripApplication mInstance;

	public static DripApplication getInstance() {
		return mInstance;
	}

	/**
	 * 初始化工具
	 */
	private void initTools(Context context) {
		LoggerUtil.init();
		BmobUtil.initialize(context);
		RealmUtil.initialize(context);
		StethoUtil.initialize(context);
		FragmentationUtil.initialize();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		initTools(DripApplication.this);
	}
}
