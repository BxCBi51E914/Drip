package org.rg.drip.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

import org.rg.drip.R;
import org.rg.drip.activity.SignUpActivity;

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
	
	private void animateCircularReveal(View vStart, View vEnd, View vTarget) {
		Animator mAnimator = ViewAnimationUtils.createCircularReveal(vTarget,
		                                                             vStart.getWidth() >> 1,
		                                                             vEnd.getHeight() >> 1,
		                                                             vStart.getWidth() >> 1,
		                                                             vEnd.getHeight());
		boolean forward = vStart.getWidth() < vEnd.getHeight();
		mAnimator.setDuration(600);
		mAnimator.setInterpolator(new AccelerateInterpolator());
		mAnimator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				vTarget.setVisibility(forward ? View.VISIBLE : View.GONE);
				super.onAnimationEnd(animation);
			}
			
			@Override
			public void onAnimationStart(Animator animation) {
				super.onAnimationStart(animation);
				vTarget.setVisibility(forward ? View.GONE : View.VISIBLE);
			}
		});
		mAnimator.start();
	}
}
