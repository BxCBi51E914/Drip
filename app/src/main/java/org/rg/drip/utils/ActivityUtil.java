package org.rg.drip.utils;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by TankGq
 * on 2018/3/15.
 */

public class ActivityUtil {

	public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
	                                          @NonNull Fragment fragment,
	                                          @IdRes int frameId) {
		CheckUtil.checkNotNull(fragmentManager);
		CheckUtil.checkNotNull(fragment);

		fragmentManager.beginTransaction()
		               .add(frameId, fragment)
		               .commit();
	}
}
