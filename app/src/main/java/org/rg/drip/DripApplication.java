package org.rg.drip;

import android.app.Application;
import android.content.Context;

import org.rg.drip.data.model.local.UserL;
import org.rg.drip.utils.BmobUtil;
import org.rg.drip.utils.LoggerUtil;
import org.rg.drip.utils.RealmUtil;
import org.rg.drip.utils.StethoUtil;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class DripApplication extends Application {
	
	/**
	 * 初始化工具
	 */
	private void initTools(Context context) {
		LoggerUtil.init();
		BmobUtil.initialize(context);
		RealmUtil.initialize(context);
		StethoUtil.initialize(context);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		initTools(DripApplication.this);
	}
}
