package org.rg.drip.utils;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import org.greenrobot.eventbus.EventBus;
import org.rg.drip.DripApplication;
import org.rg.drip.constant.ConfigConstant;
import org.rg.drip.constant.MessageEventConstant;
import org.rg.drip.event.MessageEvent;

import java.util.Locale;

/**
 * Created by TankGq
 * on 2018/4/13.
 */
public class LocaleUtil {
	
	/**
	 * 获得系统的语言
	 */
	public static String getSystemLanguage() {
		Locale locale;
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			locale = LocaleList.getDefault()
			                   .get(0);
		} else {
			locale = Locale.getDefault();
		}
		
		switch(locale.getLanguage()) {
			case ConfigConstant.LOCALE_CHINESE_LANGUAGE:
				return ConfigConstant.LANGUAGE_CHINESE;
			case ConfigConstant.LOCALE_ENGLISH_LANGUAGE:
				return ConfigConstant.LANGUAGE_ENGLISH;
		}
		return locale.getLanguage();
	}
	
	/**
	 * 获得 APP 当前的语言
	 */
	public static String getCurrentLanguage() {
		Configuration config = DripApplication.getInstance()
		                                      .getResources()
		                                      .getConfiguration();
		Locale locale;
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			locale = config.getLocales()
			               .get(0);
		} else {
			locale = config.locale;
		}
		
		switch(locale.getLanguage()) {
			case ConfigConstant.LOCALE_CHINESE_LANGUAGE:
				return ConfigConstant.LANGUAGE_CHINESE;
			case ConfigConstant.LOCALE_ENGLISH_LANGUAGE:
				return ConfigConstant.LANGUAGE_ENGLISH;
		}
		return locale.getLanguage();
	}
	
	public static boolean isDifferent(String language) {
		switch(language) {
			case ConfigConstant.LANGUAGE_CHINESE:
			case ConfigConstant.LANGUAGE_ENGLISH:
				return ! getCurrentLanguage().equals(language);
				
			case ConfigConstant.LANGUAGE_AUTO:
				return ! getCurrentLanguage().equals(getSystemLanguage());
		}
		
		return true;
	}
	
	/**
	 * 修改 APP 当前的语言
	 */
	public static void setLanguage(Locale locale) {
		Resources resources = DripApplication.getInstance()
		                                     .getResources();
		Configuration configuration = resources.getConfiguration();
		configuration.setLocale(locale);
		DisplayMetrics displayMetrics = resources.getDisplayMetrics();
		resources.updateConfiguration(configuration, displayMetrics);
		configuration.setLayoutDirection(locale);
	}
	
	public static void setLanguage(String language) {
		if(! isDifferent(language)) {
			return;
		}
		if(language.equals(ConfigConstant.LANGUAGE_AUTO)) {
			language = getSystemLanguage();
		}
		switch(language) {
			case ConfigConstant.LANGUAGE_CHINESE:
				setLanguage(ConfigConstant.LOCALE_CHINESE);
				break;
			case ConfigConstant.LANGUAGE_ENGLISH:
				setLanguage(ConfigConstant.LOCALE_ENGLISH);
				break;
			default:
				return;
		}
		EventBus.getDefault().post(new MessageEvent(MessageEventConstant.RESTART_ACTIVITY));
	}
}
