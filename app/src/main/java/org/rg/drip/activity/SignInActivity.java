package org.rg.drip.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.rg.drip.R;
import org.rg.drip.base.BaseActivity;
import org.rg.drip.contract.user.SignInContract;
import org.rg.drip.data.model.cache.User;
import org.rg.drip.presenter.SignInPresenter;
import org.rg.drip.utils.CheckUtil;
import org.rg.drip.utils.RepositoryUtil;
import org.rg.drip.utils.ToastUtil;

import butterknife.BindView;

/**
 * Created by TankGq
 * on 2018/3/27.
 */
public class SignInActivity extends BaseActivity implements SignInContract.View {

	@BindView(R.id.fab_sign_up) FloatingActionButton mSignUpFab;

	@BindView(R.id.et_username) EditText mUsernameEt;

	@BindView(R.id.et_password) EditText mPasswordEt;

	@BindView(R.id.bt_go) Button mGoBt;

	@BindView(R.id.bt_forget_password) TextView mForgetPasswordBt;

	private SignInContract.Presenter mPresenter;

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.activity_sign_in;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		mPresenter = new SignInPresenter(RepositoryUtil.getUserRepository(), this);
		mGoBt.setOnClickListener(v -> {
			mPresenter.signIn(mUsernameEt.getText().toString(),
			                  mPasswordEt.getText().toString());
		});
		mSignUpFab.setOnClickListener(v -> {
			getWindow().setExitTransition(null);
			getWindow().setEnterTransition(null);
			startActivity(new Intent(SignInActivity.this, SignUpActivity.class),
			              ActivityOptions.makeSceneTransitionAnimation(SignInActivity.this,
			                                                           mSignUpFab,
			                                                           mSignUpFab.getTransitionName())
			                             .toBundle());
		});
		mForgetPasswordBt.setOnClickListener(v -> mPresenter.forgetPassword(mUsernameEt.getText()
		                                                                               .toString()));
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		mSignUpFab.setVisibility(View.GONE);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSignUpFab.setVisibility(View.VISIBLE);
		mPresenter.subscribe();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mPresenter.unSubscribe();

	}

	@Override
	public void setPresenter(SignInContract.Presenter presenter) {
		mPresenter = CheckUtil.checkNotNull(presenter);
	}

	@Override
	public void showTip(@StringRes int stringId) {
		ToastUtil.showCustumToast(SignInActivity.this, getString(stringId));
	}

	@Override
	public void signInOk() {
		showTip(R.string.tip_msg_sign_in_succeed);
		startActivity(new Intent(SignInActivity.this, DripActivity.class));
	}
}
