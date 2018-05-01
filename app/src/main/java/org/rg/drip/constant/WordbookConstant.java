package org.rg.drip.constant;

import org.rg.drip.data.model.cache.WordLink;
import org.rg.drip.data.model.cache.Wordbook;

import java.util.HashMap;

/**
 * Created by eee
 * on 2018/4/10.
 */
public class WordbookConstant {
	
	public static final String FIELD_ID = "id";
	public static final String FIELD_USER_ID = "userId";
	
	/**
	 * wordbook 的 id 为 DEFAULT_WORDBOOK_USER_ID 表示该单词本是默认的单词本
	 */
	public static final int DEFAULT_WORDBOOK_USER_ID = 0;
	
	/**
	 * 大于该值为私人的单词本, 小于该值为默认的单词本或者是从默认的单词本中拷贝出来的, 等于该值表示出错了
	 */
	public static final int WORDBOOK_ID_SPLIT = 0;

	public static final HashMap<String, WordLink> emptyMap = new HashMap<>();

	/**
	 * app 自带的单词本, 简称默认单词本,
	 * id 小于 WORDBOOK_ID_SPLIT, userId 固定为 0
	 */
	public static final String TYPE_DEFAULT = "default";
	/**
	 * 用户从默认的单词本里面拷贝出来的单词本,
	 * id 小于 WORDBOOK_ID_SPLIT, userId 为对应创建的用户的 id
	 */
	public static final String TYPE_USER_COPY = "copy";
	/**
	 * 用户自己创建的单词本,
	 * id 大于 WORDBOOK_ID_SPLIT, userId 为对应创建的用户的 id
	 */
	public static final String TYPE_USER_CREATE = "create";
}
