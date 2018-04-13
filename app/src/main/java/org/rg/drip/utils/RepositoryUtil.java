package org.rg.drip.utils;

import android.support.annotation.NonNull;

import org.rg.drip.data.contract.UserContract;
import org.rg.drip.data.contract.WordBookContract;
import org.rg.drip.data.local.UserLocalSource;
import org.rg.drip.data.local.WordbookLocalSource;
import org.rg.drip.data.remote.UserRemoteSource;
import org.rg.drip.data.remote.WordbookRemoteSource;
import org.rg.drip.data.repository.UserRepository;
import org.rg.drip.data.repository.WordbookRepository;

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

	@NonNull
	public static WordBookContract.Repository getWordbookRepository() {
		return WordbookRepository.getInstance(WordbookRemoteSource.getInstance(),
		                                      WordbookLocalSource.getInstance());
	}
}
