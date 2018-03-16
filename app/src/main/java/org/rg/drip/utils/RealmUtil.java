package org.rg.drip.utils;

import android.content.Context;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Author : Tank
 * Time : 17/03/2018
 */
public class RealmUtil {
	private static final String DEFAULT_REAML_NAME = "drip.realm";
	// 需要 64 字节, 即 REALM_KEY.length = 64
//	private static final byte[] REALM_KEY = { 0x44, 0x72, 0x69, 0x70 };

	public static String mRealmName = DEFAULT_REAML_NAME;
	public static RealmConfiguration mConfig = null;

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

		return Realm.getInstance(mConfig);
	}

	public static Realm getInstance() {
		return getInstance(DEFAULT_REAML_NAME);
	}
}
