package org.rg.drip.utils;

import com.alibaba.fastjson.JSONObject;

import org.greenrobot.eventbus.EventBus;
import org.rg.drip.GlobalData;
import org.rg.drip.constant.ConfigConstant;
import org.rg.drip.constant.MessageEventConstant;
import org.rg.drip.data.model.cache.Wordbook;
import org.rg.drip.event.MessageEvent;

/**
 * Created by TankGq
 * on 2018/4/10.
 */
public class ConfigUtil {

	/**
	 * 加载设置
	 */
	public static void applyConfig() {
		JSONObject json = JSONObject.parseObject(GlobalData.config);
		if(json.containsKey(ConfigConstant.LANGUAGE)) {
			applyLanguageConfig((String) json.get(ConfigConstant.LANGUAGE));
		}
	}

	/**
	 * 获取默认设置
	 */
	public static String getDefaultConfig() {
		JSONObject json = new JSONObject();
		json.put(ConfigConstant.CURRENT_WORDBOOK, ConfigConstant.CURRENT_WORDBOOK_DEFAULT);
		json.put(ConfigConstant.LANGUAGE, ConfigConstant.LANGUAGE_AUTO);
		return json.toJSONString();
	}

	private static String saveConfig(String key, Object value) {
		JSONObject json = JSONObject.parseObject(GlobalData.config);
		json.put(key, value);
		GlobalData.config = json.toJSONString();
		return GlobalData.config;
	}

	private static String getConfigValue(String key) {
		JSONObject json = JSONObject.parseObject(GlobalData.config);
		if(json.containsKey(key)) {
			return (String) json.get(key);
		}
		return "";
	}

	/**
	 * 设置当前选择的单词
	 */
	public static String setCurrentWordBook(Wordbook currentWordbook) {
		if(! CheckUtil.checkWordbook(currentWordbook)) {
			return GlobalData.config;
		}

		return saveConfig(ConfigConstant.CURRENT_WORDBOOK, currentWordbook.getCode());
	}

	public static Wordbook getCurrentWordBook() {
		return Wordbook.ofCode(getConfigValue(ConfigConstant.CURRENT_WORDBOOK));
	}

	public static String getLanguage() {
		String language = getConfigValue(ConfigConstant.LANGUAGE);
		if(language.equals(ConfigConstant.LANGUAGE_AUTO)) {
			language = LocaleUtil.getSystemLanguage();
		}
		switch(language) {
			case ConfigConstant.LANGUAGE_CHINESE:
			case ConfigConstant.LANGUAGE_ENGLISH:
				return language;
		}
		return "";
	}

	/**
	 * 修改语言的设置
	 */
	public static String setLanguage(String language) {
		switch(language) {
			case ConfigConstant.LANGUAGE_AUTO:
			case ConfigConstant.LANGUAGE_CHINESE:
			case ConfigConstant.LANGUAGE_ENGLISH:
				break;
			default:
				return GlobalData.config;
		}

		applyLanguageConfig(language);
		return saveConfig(ConfigConstant.LANGUAGE, language);
	}

	/**
	 * 应用语言的设置
	 */
	public static void applyLanguageConfig(String language) {
		switch(language) {
			case ConfigConstant.LANGUAGE_AUTO:
			case ConfigConstant.LANGUAGE_CHINESE:
			case ConfigConstant.LANGUAGE_ENGLISH:
				LocaleUtil.setLanguage(language);
				break;
		}
	}
}
