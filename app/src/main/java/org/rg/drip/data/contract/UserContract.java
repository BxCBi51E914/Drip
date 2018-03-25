package org.rg.drip.data.contract;

import org.rg.drip.data.model.cache.User;

import io.reactivex.Flowable;

/**
 * Created by TankGq
 * on 2018/3/22.
 */
public interface UserContract {

	interface Local {
	}

	interface Remote {
		
		/**
		 * 获得当前登录的用户
		 */
		User getCurrentUser();

		/**
		 * 检查用户名是否存在
		 */
		Flowable<Boolean> checkUsernameExist(String username);
		
		/**
		 * 检查名称是否已经存在了
		 */
		Flowable<Boolean> checkNameExist(String name);

		/**
		 * 登录
		 */
		Flowable<Boolean> signIn(String username, String password);

		/**
		 * 退出登录
		 */
		void signOut();

		/**
		 * 使用邮箱登录
		 */
		Flowable<Boolean> signInWithEmail(String email, String password);

		/**
		 * 注册
		 */
		Flowable<Boolean> signUp(User user);

		/**
		 * 修改用户密码
		 */
		Flowable<Boolean> changePassword(String oldPassword, String newPassword);

		/**
		 * 发送通过邮箱重置密码请求
		 */
		Flowable<Boolean> changePasswordByEmail(String email);

		/**
		 * 更新用户邮箱
		 */
		Flowable<Boolean> changeEmail(String email);

		/**
		 * 发送验证邮件进行邮箱验证
		 */
		Flowable<Boolean> verifyEmail();

		/**
		 * 检查用户邮箱是否验证过
		 */
		Flowable<Boolean> checkEmailVerified();
	}

	interface Repository {
		
		/**
		 * 获得当前登录的用户
		 */
		User getCurrentUser();
		
		/**
		 * 检查用户名是否存在
		 */
		Flowable<Boolean> checkUsernameExist(String username);
		
		/**
		 * 检查名称是否已经存在了
		 */
		Flowable<Boolean> checkNameExist(String name);
		
		/**
		 * 登录
		 */
		Flowable<Boolean> signIn(String username, String password);
		
		/**
		 * 退出登录
		 */
		void signOut();
		
		/**
		 * 使用邮箱登录
		 */
		Flowable<Boolean> signInWithEmail(String email, String password);
		
		/**
		 * 注册
		 */
		Flowable<Boolean> signUp(User user);
		
		/**
		 * 修改用户密码
		 */
		Flowable<Boolean> changePassword(String oldPassword, String newPassword);
		
		/**
		 * 发送通过邮箱重置密码请求
		 */
		Flowable<Boolean> changePasswordByEmail(String email);
		
		/**
		 * 更新用户邮箱
		 */
		Flowable<Boolean> changeEmail(String email);
		
		/**
		 * 发送验证邮件进行邮箱验证
		 */
		Flowable<Boolean> verifyEmail();
		
		/**
		 * 检查用户邮箱是否验证过
		 */
		Flowable<Boolean> checkEmailVerified();
	}
}
