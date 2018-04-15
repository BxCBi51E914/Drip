package org.rg.drip.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;

/**
 * Author : TankGq
 * Time : 2018/4/15
 */
public class TypefaceUtil {

	public static Typeface TYPE_UNICODE_IPI_AC;

	public static void initialize(@NonNull Context context) {
		TYPE_UNICODE_IPI_AC = Typeface.createFromAsset(context.getAssets(), "UnicodeIPI_ac.ttf");
	}
}
