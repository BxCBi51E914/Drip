package org.rg.drip.utils;

import android.support.annotation.NonNull;

import org.rg.drip.data.contract.UserContract;
import org.rg.drip.data.local.UserLocalSource;
import org.rg.drip.data.remote.UserRemoteSource;
import org.rg.drip.data.repository.UserRepository;

/**
 * Author : TankGq
 * Time : 29/03/2018
 */
public class RepositoryUtil {

	@NonNull
	public static UserContract.Repository getUserRepository() {
		return UserRepository.getInstance(UserRemoteSource.getInstance(),
		                                  UserLocalSource.getInstance());
	}
}
