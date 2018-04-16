package org.rg.drip.utils;

import android.provider.Settings;

import org.rg.drip.BuildConfig;
import org.rg.drip.GlobalData;

import me.yokeyword.fragmentation.Fragmentation;

/**
 * Created by TankGq
 * on 2018/4/4.
 */
public class FragmentationUtil {
	
	public static void initialize() {
		Fragmentation.builder()
		             // 设置栈视图模式为(默认)悬浮球模式, SHAKE: 摇一摇唤出, NONE: 隐藏, 仅在Debug环境生效
		             .stackViewMode(Fragmentation.BUBBLE)
		             .debug(GlobalData.isDebug) // 实际场景建议.debug(BuildConfig.DEBUG)
		             /**
		              * 可以获取到{@link me.yokeyword.fragmentation.exception.AfterSaveStateTransactionWarning}
		              * 在遇到After onSaveInstanceState时，不会抛出异常，会回调到下面的ExceptionHandler
		              */
		             .handleException(e -> {
			             // 以Bugtags为例子: 把捕获到的 Exception 传到 Bugtags 后台。
			             // Bugtags.sendException(e);
		             })
		             .install();
	}
}
