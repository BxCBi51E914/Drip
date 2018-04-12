package org.rg.drip.utils;

import android.content.Context;

import org.rg.drip.constant.RealmConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Author : Tank
 * Time : 17/03/2018
 */
public class RealmUtil {

	public static String mRealmName = RealmConstant.DEFAULT_REAML_NAME;
	public static RealmConfiguration mConfig = null;

	private static Realm mRealm = null;

	public static void initialize(Context context) {
		Realm.init(context);
	}

	public static Realm getInstance(String realmName) {
		if(null == mConfig || ! Objects.equals(mRealmName, realmName)) {
			mRealmName = realmName;
			mConfig = new RealmConfiguration.Builder()
					          .name(mRealmName)
//					          .encryptionKey(REALM_KEY)
					          .build();
		}

		if(null == mRealm || mRealm.isClosed()) {
			LoggerUtil.d("Open realm at : " + TimeUtil.getCurrentTime());
			mRealm = Realm.getInstance(mConfig);
		}

		return mRealm;
	}

	public static Realm getInstance() {
		return getInstance(RealmConstant.DEFAULT_REAML_NAME);
	}

	public static void closeRealm() {
		if(mRealm == null) {
			return;
		}
		if(! mRealm.isClosed()) {
			LoggerUtil.d("Close realm at : " + TimeUtil.getCurrentTime());
			mRealm.close();
		}
		mRealm = null;
	}

	public static void logErrorInfo(Throwable e) {
		if(e == null) return;
		LoggerUtil.e("[RealmError] " + e.getLocalizedMessage());
	}

	/**
	 * 等长分隔下, 注意这边并不是拷贝出一份新的数据
	 */
	public static <T> List<List<T>> split(List<T> data, int maxSize) {
		List<List<T>> result = new ArrayList<>();
		int size = data.size();
		int batchCount = getBatchCount(size, maxSize);

		for(int idx = 0; idx < batchCount; ++ idx) {
			result.add(data.subList(idx * maxSize, (idx + 1) * maxSize));
		}
		return result;
	}

	/**
	 * 获得需要多少批次
	 */
	public static int getBatchCount(int dataSize, int maxSize) {
		return (dataSize + maxSize - 1) / maxSize;
	}
}
