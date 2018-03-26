package org.rg.drip.ui.fragment.user;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import org.rg.drip.R;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.utils.ToastUtil;

import butterknife.BindView;

/**
 * Author : TankGq
 * Time : 25/03/2018
 */
public class SignUpFragment extends BaseSubFragment {

	@BindView(R.id.fab)
	FloatingActionButton mFab;

	@BindView(R.id.cv_add)
	CardView mCardView;

	public static SignUpFragment newInstance() {
		SignUpFragment fragment = new SignUpFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.tab_user_fragment_sign_up;
	}

	@Override
	protected void initView() {
		mFab.setOnClickListener(v -> animateRevealClose());
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
	                         @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {

		View view = super.onCreateView(inflater, container, savedInstanceState);
		showEnterAnimation();
		return view;
	}

	private void showEnterAnimation() {
		Transition
				transition =
				TransitionInflater.from(this.getContext())
				                  .inflateTransition(R.transition.fabtransition);
		this.setSharedElementEnterTransition(transition);

		transition.addListener(new Transition.TransitionListener() {
			@Override
			public void onTransitionStart(Transition transition) {

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

	private void animateRevealShow() {
		Animator mAnimator = ViewAnimationUtils.createCircularReveal(mCardView,
		                                                             mCardView.getWidth() >> 1,
		                                                             0,
		                                                             mFab.getWidth() >> 1,
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

	private void animateRevealClose() {
		Animator mAnimator = ViewAnimationUtils.createCircularReveal(mCardView,
		                                                             mCardView.getWidth() >> 1,
		                                                             0,
		                                                             mCardView.getHeight(),
		                                                             mFab.getWidth() >> 1);
		mAnimator.setDuration(500);
		mAnimator.setInterpolator(new AccelerateInterpolator());
		mAnimator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				mCardView.setVisibility(View.INVISIBLE);
				super.onAnimationEnd(animation);
				mFab.setImageResource(R.drawable.ic_add);
				animateRevealClose();
//				SignUpFragment.super.onBackPressedSupport();
			}

			@Override
			public void onAnimationStart(Animator animation) {
				super.onAnimationStart(animation);
			}
		});
		mAnimator.start();
	}

	@Override
	public boolean onBackPressedSupport() {
		ToastUtil.showCustumToast(getContext(), "OnBackPressed");
		animateRevealClose();
		return super.onBackPressedSupport();
	}
}
