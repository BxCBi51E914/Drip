package org.rg.drip.constant;

/**
 * Created by TankGq
 * on 2018/4/10.
 */
public class WordbookConstant {

	public static final String ID = "id";
	public static final String USER_ID = "userId";
	public static final String MD5 = "md5";
	
	/**
	 * wordbook 的 id 为 DEFAULT_WORDBOOK_USER_ID 表示不自带的单词本
	 */
	public static final int DEFAULT_WORDBOOK_USER_ID = 0;

	/**
	 * 大于该值为私人的单词本, 小于该值为默认的单词本或者是从默认的单词本中拷贝出来的, 等于该值表示出错了
	 */
	public static final int WORDBOOK_ID_SPLIT = 0;

}
