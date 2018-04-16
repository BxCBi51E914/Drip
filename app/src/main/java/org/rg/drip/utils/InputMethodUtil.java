package org.rg.drip.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.rg.drip.DripApplication;

/**
 * Author : TankGq
 * Time : 2018/4/17
 */
public class InputMethodUtil {

	private static InputMethodManager imm = (InputMethodManager) DripApplication.getInstance()
	                                                                            .getSystemService(Context.INPUT_METHOD_SERVICE);

	public static void hide(View view) {
		imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public static void show(View view) {
		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	public static boolean isOpen() {
		return imm.isActive();
	}

	public static void toggle() {
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
