package org.rg.drip.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

import org.rg.drip.R;
import org.rg.drip.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by TankGq
 * on 2018/3/27.
 */
public class SignUpActivity extends BaseActivity {
	
	@BindView(R.id.fab_sign_in) FloatingActionButton mSignInFab;
	
	@BindView(R.id.cv_sign_up) CardView mSignUpCardView;
	
	@BindView(R.id.bt_go) Button mGoBtn;
	
	@Override
	protected int getContentViewLayoutID() {
		return R.layout.activity_sign_up;
	}
	
	@Override
	protected void initView(Bundle savedInstanceState) {
		ShowEnterAnimation();
		mSignInFab.setOnClickListener(v -> animateRevealClose());
		mGoBtn.setOnClickListener(v -> {
//			SignInActivity.this.onBackPressed();
			Explode explode = new Explode();
			explode.setDuration(500);
			
			getWindow().setExitTransition(explode);
			getWindow().setEnterTransition(explode);
			
			startActivity(new Intent(SignUpActivity.this, DripActivity.class),
			              ActivityOptionsCompat.makeSceneTransitionAnimation(SignUpActivity.this)
			                                   .toBundle());
		});
	}
	
	private void ShowEnterAnimation() {
		Transition transition = TransitionInflater.from(this)
		                                          .inflateTransition(R.transition.fabtransition);
		getWindow().setSharedElementEnterTransition(transition);
		
		transition.addListener(new Transition.TransitionListener() {
			@Override
			public void onTransitionStart(Transition transition) {
				mSignUpCardView.setVisibility(View.GONE);
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
		Animator mAnimator = ViewAnimationUtils.createCircularReveal(mSignUpCardView,
		                                                             mSignUpCardView.getWidth() / 2,
		                                                             0,
		                                                             mSignInFab.getWidth() / 2,
		                                                             mSignUpCardView.getHeight());
		mAnimator.setDuration(600);
		mAnimator.setInterpolator(new AccelerateInterpolator());
		mAnimator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
			}
			
			@Override
			public void onAnimationStart(Animator animation) {
				mSignUpCardView.setVisibility(View.VISIBLE);
				super.onAnimationStart(animation);
			}
		});
		mAnimator.start();
	}
	
	public void animateRevealClose() {
		Animator mAnimator = ViewAnimationUtils.createCircularReveal(mSignUpCardView,
		                                                             mSignUpCardView.getWidth() / 2,
		                                                             0,
		                                                             mSignUpCardView.getHeight(),
		                                                             mSignInFab.getWidth() / 2);
		mAnimator.setDuration(600);
		mAnimator.setInterpolator(new AccelerateInterpolator());
		mAnimator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				mSignUpCardView.setVisibility(View.INVISIBLE);
				super.onAnimationEnd(animation);
				mSignInFab.setImageResource(R.drawable.ic_clear);
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
