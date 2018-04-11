package org.rg.drip.constant;

/**
 * Created by TankGq
 * on 2018/3/16.
 */
public class BmobConstant {

	public static final String CHANNEL = "Drip";

	public static final String APPLICATION_ID = "3487d09e65db16628574b3621cee833a";

	public static final int CONNECT_TIMEOUT = 30;

	public static final int UPLOAD_BLOK_SIZE = 1024 * 1024;

	public static final int FILE_EXPIRATION = 2500;
	
	/**
	 * 批量操作最多能执行的条数
	 */
	public static final int BATCH_MAX_COUNT = 50;
	
	/**
	 * 每次查询能获得的数据的最大个数
	 */
	public static final int LIMIT_MAX_COUNT = 500;
}
