package org.rg.drip.utils;

import android.content.Context;

import org.rg.drip.constant.BmobConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by TankGq
 * on 2018/3/16.
 */

public class BmobUtil {

	private final static HashMap<Integer, String> ERROR_INFO = new HashMap<Integer, String>() {{
		put(9001, "Application Id 为空，请初始化.");
		put(9002, "解析返回数据出错");
		put(9003, "上传文件出错");
		put(9004, "文件上传失败");
		put(9005, "批量操作只支持最多50条");
		put(9006, "objectId为空");
		put(9007, "文件大小超过10M");
		put(9008, "上传文件不存在");
		put(9009, "没有缓存数据");
		put(9010, "网络超时");
		put(9011, "BmobUser类不支持批量操作");
		put(9012, "上下文为空");
		put(9013, "BmobObject（数据表名称）格式不正确");
		put(9014, "第三方账号授权失败");
		put(9015, "其他错误均返回此code");
		put(9016, "无网络连接，请检查您的手机网络.");
		put(9017, "与第三方登录有关的错误，具体请看对应的错误描述");
		put(9018, "参数不能为空");
		put(9019, "格式不正确：手机号码、邮箱地址、验证码");
		put(9020, "保存CDN信息失败");
		put(9021, "文件上传缺少wakelock权限");
		put(9022, "文件上传失败，请重新上传");
		put(9023, "请调用Bmob类的initialize方法去初始化SDK");
	}};


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

	public static void logBmobErrorInfo(BmobException e) {
		if(e == null) return;
		LoggerUtil.e("[BmobError] errorCode : " + e.getErrorCode()
		             + ", errorMessage : " + e.getLocalizedMessage());
	}
	
	/**
	 * BmobBatch 执行的条数或者查询得到的条数有限制，所以得等长分隔下, 注意这边并不是拷贝出一份新的数据
	 */
	public static <T> List<List<T>> split(List<T> data, int maxSize) {
		List<List<T>> result = new ArrayList<>();
		int size = data.size();
		int batchCount = getBatchCount(size, maxSize);
		
		for(int idx = 0; idx < batchCount; ++ idx) {
			result.add(data.subList(idx * maxSize, (idx + 1) * maxSize));
		}
		return result;
	}
	
	/**
	 * BmobBatch 执行的条数有限制，所以得等长分隔下, 注意这边并不是拷贝出一份新的数据
	 */
	public static <T> List<List<T>> batchSplit(List<T> data) {
		return split(data, BmobConstant.BATCH_MAX_COUNT);
	}
	
	/**
	 * 查询得到的条数有限制，所以得等长分隔下, 注意这边并不是拷贝出一份新的数据
	 */
	public static <T> List<List<T>> querySplit(List<T> data) {
		return split(data, BmobConstant.LIMIT_MAX_COUNT);
	}
	
	/**
	 * 获得需要多少批次
	 */
	public static int getBatchCount(int dataSize, int maxSize) {
		maxSize = Math.min(maxSize, BmobConstant.BATCH_MAX_COUNT);
		return (dataSize + maxSize - 1) / maxSize;
	}
	
	/**
	 * 获得需要查询多少次
	 */
	public static int getQueryCount(int totalCount, int maxSize) {
		maxSize = Math.min(maxSize, BmobConstant.LIMIT_MAX_COUNT);
		return (totalCount + maxSize - 1) / maxSize;
	}
}
