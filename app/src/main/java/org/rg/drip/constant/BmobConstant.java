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
	public static final int BATCH_MAX_COUNT = 64;
	
	/**
	 * 每次查询能获得的数据的最大个数
	 */
	public static final int LIMIT_MAX_COUNT = 500;

	/**
	 * Bmob 的 QPS 限制导致同个 IP 1s 内只接收 10 条请求,
	 * 这边要考虑的是发送后, 服务器收到消息, 而不是 1s 内发 10 条请求
	 * 这边保守点限制了 8 条
	 */
	public static final int QPS_MAX_COUNT = 8;

	/**
	 * 并发的请求最短需要执行的时间, 单位秒
	 */
	public static final int MIN_TIME_ONCE = 1;

	/**
	 * 当因为 QPS 的限制导致请求失败的时候, 等待该时间后再次重新发起请求, 单位秒
	 */
	public static final int QPS_WAIT_TIME = 2;
}
