package org.rg.drip.utils;

import com.alibaba.fastjson.JSONObject;

import org.rg.drip.GlobalData;
import org.rg.drip.constant.ConfigConstant;

/**
 * Created by TankGq
 * on 2018/4/10.
 */
public class ConfigUtil {
	
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
	
	/**
	 * 设置当前语言
	 */
	public static String setCurrentWordBook(int currentWordBookId) {
		if(currentWordBookId < 0) {
			return GlobalData.config;
		}
		
		return saveConfig(ConfigConstant.CURRENT_WORDBOOK, currentWordBookId);
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
		
		return saveConfig(ConfigConstant.LANGUAGE, language);
	}
}
