package org.rg.drip.utils;

import org.rg.drip.constant.WordbookConstant;
import org.rg.drip.data.model.cache.Wordbook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by eee
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
	
	private static final String
			NAME_REGEX
			= "^[a-zA-Z0-9_\\u4e00-\\u9fa5]+[a-zA-Z0-9_\\u4e00-\\u9fa5 -]*[a-zA-Z0-9_\\u4e00-\\u9fa5-]+$";
	private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);
	
	public static boolean checkName(String name) {
		return ! (null == name || name.length() == 0) &&
		       NAME_PATTERN.matcher(name)
		                   .matches();
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
		return ! (email == null || email.length() == 0) &&
		       EMAIL_PATTERN.matcher(email)
		                    .matches();
	}
	
	public static boolean checkWordbook(Wordbook wordbook) {
		if(wordbook == null) return false;
		if(wordbook.getId() == WordbookConstant.WORDBOOK_ID_SPLIT ||
		   (wordbook.getId() < WordbookConstant.WORDBOOK_ID_SPLIT &&
		    wordbook.getUserId() != WordbookConstant.DEFAULT_WORDBOOK_USER_ID)) {
			LoggerUtil.e("[Wordbook] id: " + wordbook.getId() + ", userId: " + wordbook.getUserId());
			return false;
		}
		
		return true;
	}
	
	public static boolean checkWordbook(Wordbook wordbook, String type) {
		if(! checkWordbook(wordbook)) {
			return false;
		}
		switch(type) {
			case WordbookConstant.TYPE_DEFAULT:
				return (wordbook.getId() < WordbookConstant.WORDBOOK_ID_SPLIT &&
				        wordbook.getUserId() == WordbookConstant.DEFAULT_WORDBOOK_USER_ID);
			case WordbookConstant.TYPE_USER_COPY:
				return (wordbook.getId() < WordbookConstant.WORDBOOK_ID_SPLIT &&
				        wordbook.getUserId() != WordbookConstant.DEFAULT_WORDBOOK_USER_ID);
			case WordbookConstant.TYPE_USER_CREATE:
				return (wordbook.getId() > WordbookConstant.WORDBOOK_ID_SPLIT &&
				        wordbook.getUserId() != WordbookConstant.DEFAULT_WORDBOOK_USER_ID);
		}
		
		return false;
	}
}
