package org.rg.drip.contract.user;

import android.support.annotation.StringRes;

import org.rg.drip.base.BasePresenter;
import org.rg.drip.base.BaseView;

/**
 * Author : TankGq
 * Time : 28/03/2018
 */
public class SignInContract {

	public interface View extends BaseView<Presenter> {

		/**
		 * 显示登录错误提示
		 */
		void showTip(@StringRes int stringId);

		/**
		 * 显示登录成功
		 */
		void signInOk();
	}

	public interface Presenter extends BasePresenter {

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
