package org.rg.drip.utils;

import android.content.Context;

import org.rg.drip.constant.RealmConstant;

import java.util.ArrayList;
import java.util.HashMap;
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

	public static void initialize(Context context) {
		Realm.init(context);
	}

	private static HashMap<String, Realm> mRealmDic = new HashMap<>();

	public static Realm getInstance() {
		if(null == mConfig) {
//			mRealmName = realmName;
			mConfig = new RealmConfiguration.Builder()
					          .name(mRealmName)
//					          .encryptionKey(REALM_KEY)
					          .build();
		}
		String threadName = Thread.currentThread().getName();
		if(! mRealmDic.containsKey(threadName) || mRealmDic.get(threadName).isClosed()) {
			LoggerUtil.d("Open realm at : " + TimeUtil.getCurrentTime());
			mRealmDic.put(threadName, Realm.getInstance(mConfig));
		}

		return mRealmDic.get(threadName);
	}

//	public static Realm getInstance() {
//		return getInstance(RealmConstant.DEFAULT_REAML_NAME);
//	}

	public static void closeRealm() {
		for(String threadName : mRealmDic.keySet()) {
			mRealmDic.get(threadName).close();
		}
		mRealmDic = new HashMap<>();
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
			result.add(data.subList(idx * maxSize, Math.min((idx + 1) * maxSize, size)));
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
