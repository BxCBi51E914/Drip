package org.rg.drip.utils;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Author : Tank
 * Time : 15/03/2018
 */

public class LoggerUtil {

	private static final Boolean mIsDebug = true;
	private static final String TAG = "DRIP_LOG";

	public static void init() {
		FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
		                                                    .showThreadInfo(true)  //（可选）是否显示线程信息。 默认值为true
		                                                    .methodCount(2)         // （可选）要显示的方法行数。 默认2
		                                                    .methodOffset(7)        // （可选）隐藏内部方法调用到偏移量。 默认5
//		                                                    .logStrategy(new LogcatLogStrategy() {
//			                                                    @Override
//			                                                    public void log(int priority,
//			                                                                    String tag,
//			                                                                    String message) {
//			                                                    	if(priority >= Logger.DEBUG) {
//					                                                    super.log(priority,
//					                                                              tag,
//					                                                              message);
//				                                                    }
//			                                                    }
//		                                                    }) //（可选）更改要打印的日志策略。 默认全部等级都输出
		                                                    .tag(TAG)   //（可选）每个日志的全局标记。 默认PRETTY_LOGGER
		                                                    .build();
		AndroidLogAdapter androidLogAdapter = new AndroidLogAdapter(formatStrategy) {
			@Override
			public boolean isLoggable(int priority, String tag) {
				return mIsDebug;
			}
		};

		Logger.addLogAdapter(androidLogAdapter);
	}

	/**
	 * VERBOSE 级别，可添加占位符
	 *
	 * @param message 消息
	 * @param args    消息中的占位符对应的参数
	 */
	public static void v(String message, Object... args) {
		Logger.v(message, args);
	}

	/**
	 * DEBUG 级别，可添加占位符
	 *
	 * @param message 消息
	 * @param args    消息中的占位符对应的参数
	 */
	public static void d(String message, Object... args) {
		Logger.d(message, args);
	}

	/**
	 * DEBUG 级别，可添加占位符
	 *
	 * @param object 要打印的对象
	 */
	public static void d(Object object) {
		Logger.d(object);
	}

	/**
	 * INFO 级别，可添加占位符
	 *
	 * @param message 消息
	 * @param args    消息中的占位符对应的参数
	 */
	public static void i(String message, Object... args) {
		Logger.i(message, args);
	}

	/**
	 * WARN 级别，可添加占位符
	 *
	 * @param message 消息
	 * @param args    消息中的占位符对应的参数
	 */
	public static void w(String message, Object... args) {
		Logger.w(message, args);
	}

	/**
	 * ERROR 级别，可添加占位符
	 *
	 * @param message 消息
	 * @param args    消息中的占位符对应的参数
	 */
	public static void e(String message, Object... args) {
		Logger.e(message, args);
	}

	/**
	 * ERROR 级别，可添加占位符
	 *
	 * @param throwable 要抛出的异常
	 * @param message   消息
	 * @param args      消息中的占位符对应的参数
	 */
	public static void e(Throwable throwable, String message, Object... args) {
		Logger.e(throwable, message, args);
	}

	/**
	 * ASSERT 级别，可添加占位符
	 *
	 * @param message 消息
	 * @param args    消息中的占位符对应的参数
	 */
	public static void wtf(String message, Object... args) {
		Logger.wtf(message, args);
	}

	/**
	 * 输出 xml
	 *
	 * @param xml xml 字符串
	 */
	public static void xml(String xml) {
		Logger.xml(xml);
	}

	/**
	 * 输出 json
	 *
	 * @param json json 字符串
	 */
	public static void json(String json) {
		Logger.json(json);
	}
}
