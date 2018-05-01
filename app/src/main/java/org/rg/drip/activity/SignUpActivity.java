package org.rg.drip.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.rg.drip.R;
import org.rg.drip.base.BaseActivity;
import org.rg.drip.constant.UIConstant;
import org.rg.drip.contract.SignUpContract;
import org.rg.drip.presenter.SignUpPresenter;
import org.rg.drip.utils.CheckUtil;
import org.rg.drip.utils.LoadingTipDialogUtil;
import org.rg.drip.utils.RepositoryUtil;
import org.rg.drip.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by eee
 * on 2018/3/27.
 */
public class SignUpActivity extends BaseActivity implements SignUpContract.View {

	private SignUpContract.Presenter mPresenter;

	@BindView(R.id.fab_sign_in) FloatingActionButton mSignInFab;

	@BindView(R.id.cv_sign_up) CardView mSignUpCardView;

	@BindView(R.id.bt_go) Button mGoBtn;

	@BindView(R.id.et_username) EditText mUsernameEt;
	@BindView(R.id.et_password) EditText mPasswordEt;
	@BindView(R.id.et_repeat_password) EditText mRepeatPasswordEt;
	@BindView(R.id.et_nickname) EditText mNicknameEt;
	@BindView(R.id.et_email) EditText mEmailEt;

	@OnClick({ R.id.fab_sign_in, R.id.bt_go })
	void OnClick(View v) {
		switch(v.getId()) {
			case R.id.fab_sign_in:
				animateRevealClose();
				break;

			case R.id.bt_go:
				mPresenter.signUp(mUsernameEt.getText().toString(),
				                  mPasswordEt.getText().toString(),
				                  mRepeatPasswordEt.getText().toString(),
				                  mNicknameEt.getText().toString(),
				                  mEmailEt.getText().toString());
				break;
		}
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.activity_sign_up;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		mPresenter = new SignUpPresenter(RepositoryUtil.getUserRepository(), this);
		ShowEnterAnimation();
	}

	/**
	 * 先播放按钮的移动的动画
	 */
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

	/**
	 * 播放以关闭按钮为圆心逐渐显示这个界面的动画
	 */
	public void animateRevealShow() {
		Animator mAnimator = ViewAnimationUtils.createCircularReveal(mSignUpCardView,
		                                                             mSignUpCardView.getWidth() >>
		                                                             1,
		                                                             0,
		                                                             mSignInFab.getWidth() >> 1,
		                                                             (float) Math.sqrt(
				                                                             mSignUpCardView.getHeight() *
				                                                             mSignUpCardView.getHeight() +
				                                                             mSignUpCardView.getWidth() *
				                                                             mSignUpCardView.getHeight()));
		mAnimator.setDuration(UIConstant.ANIMATOR_DURATION);
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

	/**
	 * 与 animateRevealShow 相反的动画
	 */
	public void animateRevealClose() {
		Animator mAnimator = ViewAnimationUtils.createCircularReveal(mSignUpCardView,
		                                                             mSignUpCardView.getWidth() >>
		                                                             1,
		                                                             0,
		                                                             mSignUpCardView.getHeight(),
		                                                             mSignInFab.getWidth() >> 1);
		mAnimator.setDuration(UIConstant.ANIMATOR_DURATION);
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

	@Override
	protected void onResume() {
		super.onResume();
		mPresenter.subscribe();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mPresenter.unSubscribe();
	}

	@Override
	public void setPresenter(SignUpContract.Presenter presenter) {
		mPresenter = CheckUtil.checkNotNull(presenter);
	}

	@Override
	public void showTip(int stringId) {
		ToastUtil.showCustumToast(SignUpActivity.this, getString(stringId));
	}

	@Override
	public void signUpOk() {
		showTip(R.string.tip_sign_up_succeed);
		jumpToMainActivityWhenSignUpOk();
	}

	@Override
	public void showLoadingTipDialog(boolean bShow) {
		if(bShow) {
			LoadingTipDialogUtil.show(SignUpActivity.this);
		} else {
			LoadingTipDialogUtil.dismiss();
		}
	}

	/**
	 * 注册成功后回到主界面
	 */
	public void jumpToMainActivityWhenSignUpOk() {
		Explode explode = new Explode();
		explode.setDuration(600);

		getWindow().setExitTransition(explode);
		getWindow().setEnterTransition(explode);

		startActivity(new Intent(SignUpActivity.this, DripActivity.class),
		              ActivityOptionsCompat.makeSceneTransitionAnimation(SignUpActivity.this)
		                                   .toBundle());
	}
}
