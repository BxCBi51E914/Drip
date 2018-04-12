package org.rg.drip.constant;

/**
 * Author : TankGq
 * Time : 2018/4/13
 */
public class RealmConstant {

	public static final String DEFAULT_REAML_NAME = "drip.realm";
	// 需要 64 字节, 即 REALM_KEY.length = 64
//	public static final byte[] REALM_KEY = { 0x44, 0x72, 0x69, 0x70 };

	/**
	 * 查询的时候经常需要合并查询, 这边大概限制下每次查询的最大个数, 通常用于 ContainIn
	 */
	public static final int COMBINE_MAX_COUNT = 64;

	/**
	 * 大概限制下并行查询的最大个数
	 */
	public static final int PARALLEL_MAX_COUNT = 16;
}
