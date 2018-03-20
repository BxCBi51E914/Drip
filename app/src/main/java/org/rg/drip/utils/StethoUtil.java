package org.rg.drip.utils;

import android.content.Context;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.regex.Pattern;

import static cn.bmob.v3.Bmob.getCacheDir;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class StethoUtil {
	
	public static void initialize(Context context) {
		Stetho.initialize(Stetho.newInitializerBuilder(context)
		                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
		                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(context)
		                                                                            .build())
		                        .build());
		RealmInspectorModulesProvider.builder(context)
		                             .withFolder(getCacheDir())
//		                             .withEncryptionKey("encrypted.realm", key)
		                             .withMetaTables()
		                             .withDescendingOrder()
		                             .withLimit(1000)
		                             .databaseNamePattern(Pattern.compile(".+\\.realm"))
		                             .build();
	}
}
