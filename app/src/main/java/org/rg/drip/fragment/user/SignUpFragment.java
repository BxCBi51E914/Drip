package org.rg.drip.fragment.user;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.EditText;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.rg.drip.R;
import org.rg.drip.activity.DripActivity;
import org.rg.drip.activity.SignUpActivity;
import org.rg.drip.base.BaseSubFragment;
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
 * on 2018/4/3.
 */
public class SignUpFragment extends BaseSubFragment implements SignUpContract.View {
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
	
	private SignUpContract.Presenter mPresenter;
	
	@Override
	protected int getContentViewLayoutID() {
		return R.layout.activity_sign_up;
	}
	
	@Override
	protected void initView(Bundle savedInstanceState) {
		mPresenter = new SignUpPresenter(RepositoryUtil.getUserRepository(), this);
		
	}
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ShowEnterAnimation();
	}
	
	public static SignUpFragment newInstance() {
		SignUpFragment fragment = new SignUpFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}
	
	/**
	 * 先播放按钮的移动的动画
	 */
	private void ShowEnterAnimation() {
		Transition transition = TransitionInflater
				.from(getContext())
				.inflateTransition(R.transition.fabtransition);
//		setSharedElementEnterTransition(transition);
//		setSharedElementReturnTransition(transition);
		
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
	
	/**
	 * 与 animateRevealShow 相反的动画
	 */
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
				super.onAnimationEnd(animation);
				mSignUpCardView.setVisibility(View.INVISIBLE);
				mSignInFab.setImageResource(R.drawable.ic_clear);
				pop();
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
		animateRevealClose();
		return true;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mPresenter.subscribe();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mPresenter.unSubscribe();
	}
	
	@Override
	public void setPresenter(SignUpContract.Presenter presenter) {
		mPresenter = CheckUtil.checkNotNull(presenter);
	}
	
	@Override
	public void showTip(int stringId) {
		ToastUtil.showCustumToast(getContext(), getString(stringId));
	}
	
	@Override
	public void signUpOk() {
		showTip(R.string.tip_sign_up_succeed);
		jumpToMainActivityWhenSignUpOk();
	}
	
	@Override
	public void showLoadingTipDialog(boolean bShow) {
		if(bShow) {
			LoadingTipDialogUtil.show(getContext());
		} else {
			LoadingTipDialogUtil.dismiss();
		}
	}
	
	/**
	 * 注册成功后回到主界面
	 */
	public void jumpToMainActivityWhenSignUpOk() {
		onBackPressedSupport();
	}
}
