package org.rg.drip;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import org.rg.drip.data.contract.UserContract;
import org.rg.drip.data.model.cache.User;
import org.rg.drip.utils.BmobUtil;
import org.rg.drip.utils.ConfigUtil;
import org.rg.drip.utils.FragmentationUtil;
import org.rg.drip.utils.LoggerUtil;
import org.rg.drip.utils.RealmUtil;
import org.rg.drip.utils.RepositoryUtil;
import org.rg.drip.utils.StethoUtil;
import org.rg.drip.utils.TypefaceUtil;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class DripApplication extends Application {

	private static DripApplication mInstance;

	@NonNull
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
		TypefaceUtil.initialize(context);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		initTools(DripApplication.this);
		loadConfig();
	}
	
	/**
	 * 加载用户设置, 如果之前用户就登录了的话
	 */
	private void loadConfig() {
		UserContract.Repository userRepository = RepositoryUtil.getUserRepository();
		User user = userRepository.getCurrentUser();
		if(user == null) {
			return;
		}
		if(null == user.getConfig() || user.getConfig().isEmpty()) {
			userRepository.saveUserConfig(ConfigUtil.getDefaultConfig());
		} else {
			GlobalData.config = user.getConfig();
		}
		ConfigUtil.applyConfig();
	}
}
