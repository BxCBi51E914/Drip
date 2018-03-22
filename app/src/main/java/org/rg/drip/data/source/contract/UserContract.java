package org.rg.drip.data.source.contract;

import org.rg.drip.data.model.User;

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
		 * 通过用户名查找用户
		 */
		Flowable<User> getUserByUsername(String username);

		/**
		 * 检查用户名是否存在
		 */
		Flowable<Boolean> checkUsernameExist(String username);

		/**
		 * 检查密码是否正确
		 */
		Flowable<Boolean> checkPassword(String username, String password);

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
