package org.rg.drip.utils;

import android.content.Context;
import android.util.DebugUtils;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.rg.drip.R;

/**
 * Created by eee
 * on 2018/4/3.
 */
public class LoadingTipDialogUtil {

	private static QMUITipDialog mLoadingTipDialog;
	public static int mContextHashCode = 0;

	public static void show(Context context) {
		if(context == null) {
			LoggerUtil.e("Context is null");
			return;
		}
		int contextHashCode = context.hashCode();
		if(mLoadingTipDialog == null || contextHashCode != mContextHashCode) {
			if(mLoadingTipDialog != null) dismiss();
			mLoadingTipDialog = new QMUITipDialog.CustomBuilder(context)
					                    .setContent(R.layout.tip_loading)
					                    .create();
		}
		mLoadingTipDialog.show();
	}

	public static void dismiss() {
		if(null == mLoadingTipDialog || ! mLoadingTipDialog.isShowing()) {
			return;
		}
		mLoadingTipDialog.dismiss();
	}
}
