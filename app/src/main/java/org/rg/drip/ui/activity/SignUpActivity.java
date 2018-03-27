package org.rg.drip.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

import org.rg.drip.R;
import org.rg.drip.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by TankGq
 * on 2018/3/27.
 */
public class SignUpActivity extends BaseActivity {
	
	@BindView(R.id.fab) FloatingActionButton mCloseBtn;
	
	@BindView(R.id.cv_add) CardView mCardView;
	
	@Override
	protected int getContentViewLayoutID() {
		return R.layout.activity_sign_up;
	}
	
	@Override
	protected void initView(Bundle savedInstanceState) {
		ShowEnterAnimation();
		mCloseBtn.setOnClickListener(v -> animateRevealClose());
	}
	
	private void ShowEnterAnimation() {
		Transition transition = TransitionInflater.from(this)
		                                          .inflateTransition(R.transition.fabtransition);
		getWindow().setSharedElementEnterTransition(transition);
		
		transition.addListener(new Transition.TransitionListener() {
			@Override
			public void onTransitionStart(Transition transition) {
				mCardView.setVisibility(View.GONE);
			}
			
			@Override
			public void onTransitionEnd(Transition transition) {
				transition.removeListener(this);
				animateRevealShow();
			}
			
			@Override
			public void onTransitionCancel(Transition transition) {
			
			}
			
			@Override
			public void onTransitionPause(Transition transition) {
			
			}
			
			@Override
			public void onTransitionResume(Transition transition) {
			
			}
			
			
		});
	}
	
	public void animateRevealShow() {
		Animator mAnimator = ViewAnimationUtils.createCircularReveal(mCardView,
		                                                             mCardView.getWidth() / 2,
		                                                             0,
		                                                             mCloseBtn.getWidth() / 2,
		                                                             mCardView.getHeight());
		mAnimator.setDuration(500);
		mAnimator.setInterpolator(new AccelerateInterpolator());
		mAnimator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
			}
			
			@Override
			public void onAnimationStart(Animator animation) {
				mCardView.setVisibility(View.VISIBLE);
				super.onAnimationStart(animation);
			}
		});
		mAnimator.start();
	}
	
	public void animateRevealClose() {
		Animator mAnimator = ViewAnimationUtils.createCircularReveal(mCardView,
		                                                             mCardView.getWidth() / 2,
		                                                             0,
		                                                             mCardView.getHeight(),
		                                                             mCloseBtn.getWidth() / 2);
		mAnimator.setDuration(500);
		mAnimator.setInterpolator(new AccelerateInterpolator());
		mAnimator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				mCardView.setVisibility(View.INVISIBLE);
				super.onAnimationEnd(animation);
				mCloseBtn.setImageResource(R.drawable.ic_clear);
				SignUpActivity.super.onBackPressedSupport();
			}
			
			@Override
			public void onAnimationStart(Animator animation) {
				super.onAnimationStart(animation);
			}
		});
		mAnimator.start();
	}
	
	@Override
	public void onBackPressedSupport() {
		animateRevealClose();
	}
}
