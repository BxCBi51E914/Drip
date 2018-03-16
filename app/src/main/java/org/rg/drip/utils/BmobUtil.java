package org.rg.drip.utils;

import android.content.Context;

import org.rg.drip.constant.BmobConstant;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * Created by TankGq
 * on 2018/3/16.
 */

public class BmobUtil {

	public static void initialize(Context context) {
		Bmob.initialize(new BmobConfig.Builder(context)
				                .setApplicationId(BmobConstant.APPLICATION_ID)
				                //请求超时时间（单位为秒）：默认15s
				                .setConnectTimeout(BmobConstant.CONNECT_TIMEOUT)
				                //文件分片上传时每片的大小（单位字节），默认512*1024
				                .setUploadBlockSize(BmobConstant.UPLOAD_BLOK_SIZE)
				                //文件的过期时间(单位为秒)：默认1800s
				                .setFileExpiration(BmobConstant.FILE_EXPIRATION)
				                .build());
//		Bmob.initialize(context, BmobConstant.APPLICATION_ID, BmobConstant.CHANNEL);
	}
}
