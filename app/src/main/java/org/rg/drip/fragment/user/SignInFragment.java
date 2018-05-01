package org.rg.drip.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.text.InputType;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import org.rg.drip.R;
import org.rg.drip.activity.DripActivity;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.contract.SignInContract;
import org.rg.drip.presenter.SignInPresenter;
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
public class SignInFragment extends BaseSubFragment implements SignInContract.View {
	private SignInContract.Presenter mPresenter;
	
	@BindView(R.id.fab_sign_up) FloatingActionButton mSignUpFab;
	@BindView(R.id.et_username) EditText mUsernameEt;
	@BindView(R.id.et_password) EditText mPasswordEt;
	@BindView(R.id.bt_go) Button mGoBt;
	@BindView(R.id.bt_forget_password) TextView mForgetPasswordBt;
	
	@OnClick({ R.id.fab_sign_up, R.id.bt_go, R.id.bt_forget_password })
	void Click(View v) {
		switch(v.getId()) {
			case R.id.fab_sign_up:
				SignUpFragment signUpFragment = findFragment(SignUpFragment.class);
				if(signUpFragment == null) {
					signUpFragment = SignUpFragment.newInstance();
				}
				Transition transition = TransitionInflater
						.from(getContext())
						.inflateTransition(R.transition.fabtransition);
				signUpFragment.setSharedElementEnterTransition(transition);
				signUpFragment.setSharedElementReturnTransition(transition);
				extraTransaction()
						.addSharedElement(mSignUpFab, mSignUpFab.getTransitionName())
						.replace(signUpFragment);
				break;
			
			case R.id.bt_go:
				mPresenter.signIn(mUsernameEt.getText().toString(),
				                  mPasswordEt.getText().toString());
				break;
			
			case R.id.bt_forget_password:
				showForgetPasswordDialog();
				break;
		}
	}
	
	@Override
	protected int getContentViewLayoutID() {
		return R.layout.activity_sign_in;
	}
	
	public static SignInFragment newInstance() {
		SignInFragment fragment = new SignInFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	protected void initView(Bundle savedInstanceState) {
		mPresenter = new SignInPresenter(RepositoryUtil.getUserRepository(), this);
		ViewCompat.setTransitionName(mSignUpFab, mSignUpFab.getTransitionName());
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
	public void setPresenter(SignInContract.Presenter presenter) {
		mPresenter = CheckUtil.checkNotNull(presenter);
	}
	
	@Override
	public void showTip(@StringRes int stringId) {
		ToastUtil.showCustumToast(getContext(), getString(stringId));
	}
	
	@Override
	public void signInOk() {
		showTip(R.string.tip_sign_in_succeed);
		startActivity(new Intent(getContext(), DripActivity.class));
	}
	
	@Override
	public void showLoadingTipDialog(boolean bShow) {
		if(bShow) {
			LoadingTipDialogUtil.show(getContext());
		} else {
			LoadingTipDialogUtil.dismiss();
		}
	}
	
	@Override
	public void showForgetPasswordDialog() {
		QMUIDialog.EditTextDialogBuilder
				builder
				= new QMUIDialog.EditTextDialogBuilder(getContext());
		builder
				.setPlaceholder(R.string.tip_input_email)
				.setTitle(R.string.tip_send_email_to_reset)
				.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
				.addAction(R.string.chk_no, ((dialog, index) -> dialog.dismiss()))
				.addAction(R.string.chk_yes,
				           (dialog, index) -> mPresenter.forgetPassword(builder
						                                                        .getEditText()
						                                                        .getText()
						                                                        .toString()))
				.create()
				.show();
	}
}
