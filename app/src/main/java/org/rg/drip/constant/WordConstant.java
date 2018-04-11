package org.rg.drip.constant;

/**
 * Created by TankGq
 * on 2018/3/16.
 */
public class WordConstant {
	
	public static final String ID = "id";
	public static final String WORD = "word";
	public static final String PHONETIC = "phonetic";
	public static final String EXPLAIN = "explain";
	
	public static final int STATE_UNKNOWN = 0x00;         // 未知
	public static final int STATE_UNFAMILIAR = 0x01;      // 不熟悉
	public static final int STATE_GENERAL = 0x2;          // 一般
	public static final int STATE_FAMILIAR = 0x03;        // 熟悉
	public static final int STATE_MASTER = 0x05;          // 掌握
}
