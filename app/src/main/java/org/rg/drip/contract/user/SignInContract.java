package org.rg.drip.contract.user;

import android.support.annotation.StringRes;

import org.rg.drip.base.BasePresenter;
import org.rg.drip.base.BaseView;

/**
 * Author : TankGq
 * Time : 28/03/2018
 */
public interface SignInContract {

	interface View extends BaseView<Presenter> {

		/**
		 * 显示提示
		 */
		void showTip(@StringRes int stringId);

		/**
		 * 显示登录成功
		 */
		void signInOk();

		/**
		 * 显示或者隐藏加载对话框
		 */
		void showLoadingTipDialog(boolean bShow);
	}

	interface Presenter extends BasePresenter {

		/**
		 * 登录
		 */
		void signIn(String usernameOrEmail, String password);

		/**
		 * 忘记密码
		 */
		void forgetPassword(String email);
	}
}
