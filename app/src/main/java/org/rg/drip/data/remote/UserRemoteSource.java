package org.rg.drip.data.remote;

import android.support.annotation.Nullable;

import org.rg.drip.constant.UserConstant;
import org.rg.drip.data.contract.UserContract;
import org.rg.drip.data.model.cache.User;
import org.rg.drip.data.model.remote.UserR;
import org.rg.drip.utils.BmobUtil;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * Author : Tank
 * Time : 22/03/2018
 */
public class UserRemoteSource implements UserContract.Remote {

	private static UserRemoteSource mInstance = null;

	// 当前登录的用户的缓存
	private UserR mUser = null;

	private UserRemoteSource() {
		// 获取缓存的当前登录用户
		mUser = BmobUser.getCurrentUser(UserR.class);
	}

	public static UserRemoteSource getInstance() {
		if(mInstance == null) {
			mInstance = new UserRemoteSource();
			mInstance.mUser = null;
		}

		return mInstance;
	}

	/**
	 * 存储当前登录的用户
	 */
	private void storeLoggedUser(UserR user) {
		mUser = user;
	}

	@Nullable
	@Override
	public User getCurrentUser() {
		if(mUser == null) {
			mUser = BmobUser.getCurrentUser(UserR.class);
		}

		if(null != mUser) {
			return mUser.convertToCache();
		}

		return null;
	}

	@Override
	public Flowable<Boolean> checkUsernameExist(String username) {
		return Flowable.create(emitter -> {
			new BmobQuery<UserR>().addWhereEqualTo(UserConstant.USERNAME, username)
			                      .count(UserR.class, new CountListener() {
				                      @Override
				                      public void done(Integer integer, BmobException e) {
					                      if(e != null) {
						                      BmobUtil.logBmobErrorInfo(e);
						                      emitter.onNext(false);
						                      emitter.onError(e);
						                      return;
					                      }
					                      emitter.onNext(integer > 0);
					                      emitter.onComplete();
				                      }
			                      });
		}, BackpressureStrategy.BUFFER);
	}

	@Override
	public Flowable<Boolean> checkNameExist(String name) {
		return Flowable.create(emitter -> {
			new BmobQuery<UserR>().addWhereEqualTo(UserConstant.NAME, name)
			                      .count(UserR.class, new CountListener() {
				                      @Override
				                      public void done(Integer integer, BmobException e) {
					                      if(e != null) {
						                      BmobUtil.logBmobErrorInfo(e);
						                      emitter.onNext(false);
						                      emitter.onError(e);
						                      return;
					                      }
					                      emitter.onNext(integer > 0);
					                      emitter.onComplete();
				                      }
			                      });
		}, BackpressureStrategy.BUFFER);
	}

	@Override
	public Flowable<Boolean> signIn(String username, String password) {
		UserR userR = new UserR();
		userR.setUsername(username);
		userR.setPassword(password);
		return Flowable.create(emitter -> {
			BmobUser.loginByAccount(username, password, new LogInListener<UserR>() {
				@Override
				public void done(UserR userR, BmobException e) {
					if(e != null) {
						BmobUtil.logBmobErrorInfo(e);
						emitter.onNext(false);
						emitter.onError(e);
						return;
					}
					if(userR != null) {
						storeLoggedUser(userR);
						emitter.onNext(true);
					} else {
						emitter.onNext(false);
					}
					emitter.onComplete();
				}
			});
		}, BackpressureStrategy.BUFFER);
	}

	@Override
	public Flowable<Boolean> signInWithEmail(String email, String password) {
		return Flowable.create(emitter -> {
			BmobUser.loginByAccount(email, password, new LogInListener<UserR>() {
				@Override
				public void done(UserR userR, BmobException e) {
					if(e != null) {
						BmobUtil.logBmobErrorInfo(e);
						emitter.onNext(false);
						emitter.onError(e);
						return;
					}
					if(userR != null) {
						storeLoggedUser(userR);
						emitter.onNext(true);
					} else {
						emitter.onNext(false);
					}
					emitter.onComplete();
				}
			});
		}, BackpressureStrategy.BUFFER);
	}

	@Override
	public Flowable<Boolean> signUp(User user) {
		return Flowable.create(emitter -> {
			UserR userR = new UserR();
			userR.setId(user.getId());
			userR.setUsername(user.getUsername());
			userR.setNickname(user.getNickname());
			userR.setEmail(user.getEmail());
			userR.setEmailVerified(user.getEmailVerified());
			userR.setPassword(user.getPassword());
			userR.signUp(new SaveListener<UserR>() {
				@Override
				public void done(UserR userR, BmobException e) {
					if(e != null) {
						BmobUtil.logBmobErrorInfo(e);
						emitter.onNext(false);
						emitter.onError(e);
						return;
					}
					if(userR != null) {
						storeLoggedUser(userR);
						emitter.onNext(true);
					} else {
						emitter.onNext(false);
					}
				}
			});
		}, BackpressureStrategy.BUFFER);
	}

	@Override
	public void signOut() {
		BmobUser.logOut();
		mUser = null;
	}

	@Override
	public Flowable<Boolean> changePassword(String oldPassword, String newPassword) {
		if(mUser == null) {
			return Flowable.just(false);
		}
		return Flowable.create(emitter -> {
			BmobUser.updateCurrentUserPassword(oldPassword, newPassword, new UpdateListener() {
				@Override
				public void done(BmobException e) {
					if(e != null) {
						BmobUtil.logBmobErrorInfo(e);
						emitter.onNext(false);
						emitter.onError(e);
						return;
					}
					// 修改完密码后退出登录, 清除缓存
					signOut();
					emitter.onNext(true);
					emitter.onComplete();
				}
			});
		}, BackpressureStrategy.BUFFER);
	}

	@Override
	public Flowable<Boolean> changePasswordByEmail(String email) {
		return Flowable.create(emitter -> {
			BmobUser.resetPasswordByEmail(email, new UpdateListener() {
				@Override
				public void done(BmobException e) {
					if(e != null) {
						BmobUtil.logBmobErrorInfo(e);
						emitter.onNext(false);
						emitter.onError(e);
						return;
					}
					emitter.onNext(true);
					emitter.onComplete();
				}
			});
		}, BackpressureStrategy.BUFFER);
	}

	@Override
	public Flowable<Boolean> changeEmail(String email) {
		if(mUser == null) {
			return Flowable.just(false);
		}
		return Flowable.create(emitter -> {
			UserR user = new UserR();
			// 修改邮箱后, emailVerified 这个字段会自动置为 false
			user.setEmail(email);
			user.update(mUser.getObjectId(), new UpdateListener() {
				@Override
				public void done(BmobException e) {
					if(e != null) {
						BmobUtil.logBmobErrorInfo(e);
						emitter.onNext(false);
						emitter.onError(e);
						return;
					}
					// 修改完邮箱后退出登录, 清除缓存
					signOut();
					emitter.onNext(true);
					emitter.onComplete();
				}
			});
		}, BackpressureStrategy.BUFFER);
	}

	@Override
	public Flowable<Boolean> verifyEmail() {
		if(mUser == null || mUser.getEmailVerified()) {
			return Flowable.just(false);
		}
		// 邮箱验证有效期一个星期
		return Flowable.create(emitter -> {
			BmobUser.requestEmailVerify(mUser.getEmail(), new UpdateListener() {
				@Override
				public void done(BmobException e) {
					if(e != null) {
						BmobUtil.logBmobErrorInfo(e);
						emitter.onNext(false);
						emitter.onError(e);
						return;
					}
					emitter.onNext(true);
					emitter.onComplete();
				}
			});
		}, BackpressureStrategy.BUFFER);
	}

	@Override
	public Flowable<Boolean> checkEmailVerified() {
		if(mUser == null) {
			return Flowable.just(false);
		}
		return Flowable.create(emitter -> {
			new BmobQuery<UserR>().getObject(mUser.getObjectId(), new QueryListener<UserR>() {
				@Override
				public void done(UserR userR, BmobException e) {
					if(e != null) {
						BmobUtil.logBmobErrorInfo(e);
						emitter.onNext(false);
						emitter.onError(e);
						return;
					}
					emitter.onNext(userR.getEmailVerified());
					emitter.onComplete();
				}
			});
		}, BackpressureStrategy.BUFFER);
	}
	
	@Override
	public Flowable<Boolean> saveUserConfig(String config) {
		if(mUser == null) {
			return Flowable.just(false);
		}
		return Flowable.create(emitter -> {
			UserR user = new UserR();
			user.setConfig(config);
			user.update(mUser.getObjectId(), new UpdateListener() {
				@Override
				public void done(BmobException e) {
					if(e != null) {
						BmobUtil.logBmobErrorInfo(e);
						emitter.onNext(false);
						emitter.onError(e);
						return;
					}
					emitter.onNext(true);
					emitter.onComplete();
				}
			});
		}, BackpressureStrategy.BUFFER);
	}
}
