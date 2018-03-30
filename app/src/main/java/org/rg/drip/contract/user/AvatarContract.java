package org.rg.drip.contract.user;

import android.support.annotation.StringRes;

import org.rg.drip.base.BasePresenter;
import org.rg.drip.base.BaseView;

/**
 * Created by TankGq
 * on 2018/3/30.
 */
public interface AvatarContract {
	
	interface View extends BaseView<Presenter> {
		
		/**
		 * 显示提示
		 */
		void showTip(@StringRes int stringId);
		
		/**
		 * 显示或者隐藏加载对话框
		 */
		void showLoadingTipDialog(boolean bShow);
	}
	
	interface Presenter extends BasePresenter {
	
	}
}
