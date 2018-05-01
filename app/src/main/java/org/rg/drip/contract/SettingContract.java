package org.rg.drip.contract;

import android.support.annotation.StringRes;

import org.rg.drip.base.BasePresenter;
import org.rg.drip.base.BaseView;

/**
 * Created by eee
 * on 2018/4/13.
 */
public interface SettingContract {
	
	interface View extends BaseView<Presenter> {
		
		/**
		 * 显示提示
		 */
		void showTip(@StringRes int stringId);
		
		/**
		 * 更新语言的显示
		 */
		void updateLanguageItem();
	}
	
	interface Presenter extends BasePresenter {
		
		/**
		 * 保存用户的设置
		 */
		void saveUserConfig();
	}
}
