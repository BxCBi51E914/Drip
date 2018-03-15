package org.rg.drip.utils;

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
}
