package org.rg.drip.constant;

import org.rg.drip.data.model.cache.Word;

/**
 * Created by eee
 * on 2018/3/16.
 */
public class WordConstant {
	
	public static final String FIELD_ID = "id";
	public static final String FIELD_WORD = "word";
	public static final String FIELD_PHONETIC = "phonetic";
	public static final String FIELD_EXPLAIN = "explain";
	
	public static final int STATE_UNKNOWN = 0x00;         // 未知
	public static final int STATE_UNFAMILIAR = 0x01;      // 不熟悉
	public static final int STATE_GENERAL = 0x2;          // 一般
	public static final int STATE_FAMILIAR = 0x03;        // 熟悉
	public static final int STATE_MASTER = 0x05;          // 掌握

	public static final Word empty = new Word();
}
