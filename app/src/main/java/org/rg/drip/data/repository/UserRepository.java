package org.rg.drip.data.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.rg.drip.data.model.cache.User;
import org.rg.drip.data.contract.UserContract;
import org.rg.drip.utils.CheckUtil;

import io.reactivex.Flowable;

/**
 * Author : Tank
 * Time : 22/03/2018
 */
public class UserRepository implements UserContract.Repository {

	@NonNull
	private static UserContract.Remote mUserRemoteSource;
	@NonNull
	private static UserContract.Local mUserLocalSource;
	@Nullable
	private static UserContract.Repository mInstance = null;

	private User mLoggedInUser;

	private UserRepository(@NonNull UserContract.Remote userRemoteSource,
	                       @NonNull UserContract.Local userLocalSource) {
		mUserRemoteSource = CheckUtil.checkNotNull(userRemoteSource);
		mUserLocalSource = CheckUtil.checkNotNull(userLocalSource);
		mLoggedInUser = mUserRemoteSource.getCurrentUser();
	}

	public static UserContract.Repository getInstance(@NonNull UserContract.Remote userRemoteSource,
	                                         @NonNull UserContract.Local userLocalSource) {
		if(mInstance == null) {
			mInstance = new UserRepository(userRemoteSource, userLocalSource);
		}
		return mInstance;
	}

	public static void destoryInstance() {
		mInstance = null;
	}
	
	@Override
	@Nullable
	public User getCurrentUser() {
		if(mLoggedInUser == null) {
			mLoggedInUser = mUserRemoteSource.getCurrentUser();
		}
		
		return mLoggedInUser;
	}
	
	@Override
	public Flowable<Boolean> checkUsernameExist(String username) {
		return mUserRemoteSource.checkUsernameExist(username);
	}
	
	@Override
	public Flowable<Boolean> checkNameExist(String name) {
		return mUserRemoteSource.checkNameExist(name);
	}
	
	@Override
	public Flowable<Boolean> signIn(String username, String password) {
		return mUserRemoteSource.signIn(username, password);
	}
	
	@Override
	public void signOut() {
		mLoggedInUser = null;
		mUserRemoteSource.signOut();
	}
	
	@Override
	public Flowable<Boolean> signInWithEmail(String email, String password) {
		return mUserRemoteSource.signInWithEmail(email, password);
	}
	
	@Override
	public Flowable<Boolean> signUp(User user) {
		return mUserRemoteSource.signUp(user);
	}
	
	@Override
	public Flowable<Boolean> changePassword(String oldPassword, String newPassword) {
		return mUserRemoteSource.changePassword(oldPassword, newPassword);
	}
	
	@Override
	public Flowable<Boolean> changePasswordByEmail(String email) {
		return mUserRemoteSource.changePasswordByEmail(email);
	}
	
	@Override
	public Flowable<Boolean> changeEmail(String email) {
		return mUserRemoteSource.changeEmail(email);
	}
	
	@Override
	public Flowable<Boolean> verifyEmail() {
		return mUserRemoteSource.verifyEmail();
	}
	
	@Override
	public Flowable<Boolean> checkEmailVerified() {
		return mUserRemoteSource.checkEmailVerified();
	}
	
	@Override
	public Flowable<Boolean> saveUserConfig(String config) {
		// 可能需要更新
		mLoggedInUser = null;
		return mUserRemoteSource.saveUserConfig(config);
	}
}
