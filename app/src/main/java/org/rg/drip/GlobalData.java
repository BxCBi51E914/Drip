package org.rg.drip;

import org.rg.drip.utils.ConfigUtil;

/**
 * Created by TankGq
 * on 2018/4/10.
 */
public class GlobalData {

	public static Object nullObj = new Object();
	
	public static String config = ConfigUtil.getDefaultConfig();
	
	public static final Boolean isDebug = true;
}
