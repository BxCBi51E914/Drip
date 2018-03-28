package org.rg.drip.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by TankGq
 * on 2018/3/15.
 */

public class CheckUtil {

	public static <T> T checkNotNull(T reference) {
		if(reference == null) {
			LoggerUtil.d("NullPointer");
			throw new NullPointerException();
		} else {
			return reference;
		}
	}

	public static int checkElementIndex(int index, int size) {
		if(index >= 0 && index < size) {
			return index;
		} else {
			LoggerUtil.d("IndexOutOfBounds, index = " + index + ", size = " + size);
			throw new IndexOutOfBoundsException();
		}
	}

	private static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

	public static boolean checkEmail(String email) {
		return ! (email == null || email.length() == 0) && EMAIL_PATTERN.matcher(email).matches();
	}
}
