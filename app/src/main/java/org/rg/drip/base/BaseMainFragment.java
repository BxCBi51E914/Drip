package org.rg.drip.base;

import android.content.Context;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public abstract class BaseMainFragment extends BaseFragment {
	
	protected OnBackToFirstListener _mBackToFirstListener;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if(context instanceof OnBackToFirstListener) {
			_mBackToFirstListener = (OnBackToFirstListener) context;
		} else {
			throw new RuntimeException(context.toString()
			                           + " must implement OnBackToFirstListener");
		}
	}

	/**
	 * 标记当前 fragment 是不是第一个 fragment
	 */
	protected boolean isFirstFragment() {
		return false;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		_mBackToFirstListener = null;
	}

	/**
	 * 处理回退事件
	 */
	@Override
	public boolean onBackPressedSupport() {
		if(getChildFragmentManager().getBackStackEntryCount() > 1) {
			popChild();
		} else {
			// 如果当前 fragment 第一个Fragment 则退出app
			if(isFirstFragment()) {
				_mActivity.finish();
			} else {
				// 如果不是,则回到第一个Fragment
				_mBackToFirstListener.onBackToFirstFragment();
			}
		}
		return true;
	}

	public interface OnBackToFirstListener {
		void onBackToFirstFragment();
	}
}
