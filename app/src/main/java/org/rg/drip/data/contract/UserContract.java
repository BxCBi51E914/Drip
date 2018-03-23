package org.rg.drip.data.contract;

import org.rg.drip.entity.User;

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
		 * 检查用户名是否存在
		 */
		Flowable<Boolean> checkUsernameExist(String username);

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
		 * 检查用户名是否存在
		 */
		Flowable<Boolean> checkUsernameExist(String username);

		/**
		 * 通过用户名查找用户
		 */
		Flowable<User> getUserByUsername(String username);

		/**
		 * 检查密码是否合理
		 */
		Boolean checkPasswordFormat(String password);

		/**
		 * 检查密码是否正确
		 */
		Flowable<Boolean> chechkPassword(String username, String password);

		/**
		 * 获取当前登录的用户
		 */
		Flowable<User> getLoggedInUser();

		/**
		 * 保存登录用户的信息
		 */
		Flowable<Boolean> saveUser(User user);

		/**
		 * 创建用户
		 */
		Flowable<Boolean> CreateUser(User user);

		/**
		 * 修改用户密码
		 */
		Flowable<Boolean> changeUserPassword(String password);

		/**
		 * 设置用户邮箱验证成功
		 */
		Flowable<Boolean> verifyEmail();

		/**
		 * 检查用户邮箱是否验证过
		 */
		Flowable<Boolean> checkEmailVarified();
	}
}
