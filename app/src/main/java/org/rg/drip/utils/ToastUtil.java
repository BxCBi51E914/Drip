package org.rg.drip.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.rg.drip.R;

import java.util.logging.Logger;

import hugo.weaving.DebugLog;

/**
 * Created by TankGq
 * on 2018/3/14.
 */
public class ToastUtil {
	private static ToastUtil mInstance = null;

	public static int mContextHashCode = 0;
	private static Toast mToast = null;

	private ToastUtil() {
	}

	/**
	 * 获取 Toast 对象
	 *
	 * @param context 上下文
	 * @return ToastUtil 对象
	 */
	public static ToastUtil makeToast(Context context) {
		int contextHashCode = context.hashCode();
		if(mToast == null || contextHashCode != mContextHashCode) {
			mToast = Toast.makeText(context, "ToastUtil", Toast.LENGTH_SHORT);
			mContextHashCode = contextHashCode;
		}
		if(mInstance == null) {
			mInstance = new ToastUtil();
		}
		return mInstance;
	}

	/**
	 * 设置 Toast 的布局
	 *
	 * @param view 视图
	 * @return ToastUtil 对象
	 */
	public ToastUtil setView(View view) {
		mToast.setView(view);
		return mInstance;
	}

	/**
	 * 通过 id 设置吐司显示的信息
	 *
	 * @param messageId 信息 id
	 * @return ToastUtil 对象
	 */
	public ToastUtil setMessage(@StringRes int messageId) {
		mToast.setText(messageId);
		return mInstance;
	}

	/**
	 * 设置吐司显示的信息
	 *
	 * @param msg 信息
	 * @return ToastUtil 对象
	 */
	public ToastUtil setMessage(String msg) {
		mToast.setText(msg);
		return mInstance;
	}

	/**
	 * 设置吐司显示的持续时间
	 *
	 * @param duration 持续时间
	 * @return ToastUtil 对象
	 */
	public ToastUtil setDuration(int duration) {
		mToast.setDuration(duration);
		return mInstance;
	}

	/**
	 * 设置吐司显示信息的字体颜色
	 *
	 * @param color 颜色
	 * @return ToastUtil 对象
	 */
	public ToastUtil setMessageColor(@ColorRes int color) {
		View view = mToast.getView();
		if(view == null) {
			return mInstance;
		}
		TextView message = view.findViewById(android.R.id.message);
		message.setTextColor(color);
		return mInstance;
	}

	/**
	 * 设置吐司显示信息的背景颜色
	 *
	 * @param color 颜色
	 * @return ToastUtil 对象
	 */
	public ToastUtil setBackgroundColor(@ColorInt int color) {
		View view = mToast.getView();
		if(view == null) {
			return mInstance;
		}
		TextView message = view.findViewById(android.R.id.message);
		message.setBackgroundColor(color);
		return mInstance;
	}

	/**
	 * 设置吐司显示信息的背景
	 *
	 * @param resId 背景的 id
	 * @return ToastUtil 对象
	 */
	public ToastUtil setBackground(@DrawableRes int resId) {
		View view = mToast.getView();
		if(view == null) {
			return mInstance;
		}
		TextView message = view.findViewById(android.R.id.message);
		message.setBackgroundResource(resId);
		return mInstance;
	}

	/**
	 * 显示吐司
	 */
	public void show() {
		mToast.show();
	}

	/**
	 * 显示自定义的 Toast
	 *
	 * @param context 上下文
	 * @param message 消息
	 * @return Toast 对象
	 */
	@DebugLog
	public static Toast showColorfulToast(Context context, String message) {
		makeToast(context).setView(LayoutInflater.from(context)
		                                         .inflate(R.layout.toast_layout, null))
		                  .setDuration(Toast.LENGTH_SHORT)
		                  .setMessage(message)
		                  .setMessageColor(R.color.colorAccent)
		                  .setBackground(R.drawable.toast_radius)
		                  .show();
		return mToast;
	}
}