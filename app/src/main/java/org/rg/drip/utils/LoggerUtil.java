package org.rg.drip.utils;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Author : Tank
 * Time : 15/03/2018
 */

public class LoggerUtil {

	private static final Boolean mIsDebug = false;

	public static void init() {
		FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
		                                                    .showThreadInfo(false)  //（可选）是否显示线程信息。 默认值为true
		                                                    .methodCount(2)         // （可选）要显示的方法行数。 默认2
		                                                    .methodOffset(7)        // （可选）隐藏内部方法调用到偏移量。 默认5
		                                                    // .logStrategy(customLog) //（可选）更改要打印的日志策略。 默认LogCat
		                                                    .tag("Logger")   //（可选）每个日志的全局标记。 默认PRETTY_LOGGER
		                                                    .build();
		AndroidLogAdapter androidLogAdapter = new AndroidLogAdapter(formatStrategy) {
			@Override
			public boolean isLoggable(int priority, String tag) {
				return mIsDebug;
			}
		};

		Logger.addLogAdapter(androidLogAdapter);
	}
}
