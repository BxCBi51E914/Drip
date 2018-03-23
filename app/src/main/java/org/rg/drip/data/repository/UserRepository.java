package org.rg.drip.data.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.rg.drip.entity.User;
import org.rg.drip.data.contract.UserContract;
import org.rg.drip.data.local.UserLocalSource;
import org.rg.drip.data.remote.UserRemoteSource;
import org.rg.drip.utils.CheckUtil;

import io.reactivex.Flowable;

/**
 * Author : Tank
 * Time : 22/03/2018
 */

public class UserRepository implements UserContract.Repository {

	@NonNull
	private static UserRemoteSource mUserRemoteSource;
	@NonNull
	private static UserLocalSource mUserLocalSource;
	@Nullable
	private static UserRepository mInstance = null;

	private User mLoggedInUser = null;

	private UserRepository(@NonNull UserRemoteSource userRemoteSource,
	                       @NonNull UserLocalSource userLocalSource) {
		mUserRemoteSource = CheckUtil.checkNotNull(userRemoteSource);
		mUserLocalSource = CheckUtil.checkNotNull(userLocalSource);
	}

	public static UserRepository getInstance(@NonNull UserRemoteSource userRemoteSource,
	                                         @NonNull UserLocalSource userLocalSource) {
		if(mInstance == null) {
			mInstance = new UserRepository(userRemoteSource, userLocalSource);
		}
		return mInstance;
	}

	public static void destoryInstance() {
		mInstance = null;
	}

	@Override
	public Flowable<Boolean> checkUsernameExist(String username) {
		return null;
	}

	@Override
	public Flowable<User> getUserByUsername(String username) {
		return null;
	}

	@Override
	public Boolean checkPasswordFormat(String password) {
		return null;
	}

	@Override
	public Flowable<Boolean> chechkPassword(String username, String password) {
		return null;
	}

	@Override
	public Flowable<User> getLoggedInUser() {
		return null;
	}

	@Override
	public Flowable<Boolean> saveUser(User user) {
		return null;
	}

	@Override
	public Flowable<Boolean> CreateUser(User user) {
		return null;
	}

	@Override
	public Flowable<Boolean> changeUserPassword(String password) {
		return null;
	}

	@Override
	public Flowable<Boolean> verifyEmail() {
		return null;
	}

	@Override
	public Flowable<Boolean> checkEmailVarified() {
		return null;
	}
}
