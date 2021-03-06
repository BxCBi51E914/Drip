package org.rg.drip.contract;

import android.support.annotation.StringRes;

import org.rg.drip.base.BasePresenter;
import org.rg.drip.base.BaseView;

/**
 * Author : eee
 * Time : 28/03/2018
 */
public interface SignUpContract {

	interface View extends BaseView<Presenter> {

		/**
		 * 显示提示
		 */
		void showTip(@StringRes int stringId);

		/**
		 * 显示登录成功
		 */
		void signUpOk();

		/**
		 * 显示或者隐藏加载对话框
		 */
		void showLoadingTipDialog(boolean bShow);
	}

	interface Presenter extends BasePresenter {

		/**
		 * 注册
		 */
		void signUp(String username,
		            String password,
		            String repeatPassword,
		            String nickname,
		            String email);
	}
}
