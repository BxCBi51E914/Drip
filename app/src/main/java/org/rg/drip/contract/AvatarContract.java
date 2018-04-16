package org.rg.drip.contract;

import android.support.annotation.StringRes;

import org.rg.drip.base.BasePresenter;
import org.rg.drip.base.BaseView;
import org.rg.drip.data.model.cache.User;

/**
 * Created by TankGq
 * on 2018/3/30.
 */
public interface AvatarContract {
	
	interface View extends BaseView<Presenter> {
		
		/**
		 * 显示提示
		 */
		void showTip(@StringRes int stringId);
		
		/**
		 * 显示或者隐藏加载对话框
		 */
		void showLoadingTipDialog(boolean bShow);
		
		/**
		 * 加载登录的用户
		 */
		void loadUser(User user);
		
		/**
		 * 显示确认退出登录对话框
		 */
		void showConfirmSignOutDialog();
		
		/**
		 * 显示验证邮箱对话框
		 */
		void showVerifyEmailDialog();
		
		/**
		 * 显示修改邮箱对话框
		 */
		void showChangeEmailDialog();
		
		/**
		 * 显示修改邮箱对话框
		 */
		void showConfirmChangePasswordDialog();
		
		/**
		 * 显示登录界面
		 */
		void showSignInFragment();
	}
	
	interface Presenter extends BasePresenter {
		
		/**
		 * 退出登录
		 */
		void signOut();
		
		/**
		 * 验证邮箱
		 */
		void verifyEmail();
		
		/**
		 * 修改邮箱
		 */
		void changeEmail(String email);
		
		/**
		 * 获得已登录的用户
		 */
		void updateSignInUser();
	}
}
