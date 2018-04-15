package org.rg.drip.utils;

import android.support.annotation.NonNull;

import org.rg.drip.constant.WordConstant;
import org.rg.drip.data.contract.UserContract;
import org.rg.drip.data.contract.WordContract;
import org.rg.drip.data.contract.WordbookContract;
import org.rg.drip.data.local.UserLocalSource;
import org.rg.drip.data.local.WordLocalSource;
import org.rg.drip.data.local.WordbookLocalSource;
import org.rg.drip.data.remote.UserRemoteSource;
import org.rg.drip.data.remote.WordRemoteSource;
import org.rg.drip.data.remote.WordbookRemoteSource;
import org.rg.drip.data.repository.UserRepository;
import org.rg.drip.data.repository.WordRepository;
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
	public static WordbookContract.Repository getWordbookRepository() {
		return WordbookRepository.getInstance(WordbookRemoteSource.getInstance(),
		                                      WordbookLocalSource.getInstance());
	}

	@NonNull
	public static WordContract.Repository getWordRepository() {
		return WordRepository.getInstance(WordRemoteSource.getInstance(),
		                                  WordLocalSource.getInstance());
	}
}
