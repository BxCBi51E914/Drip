package org.rg.drip.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.rg.drip.R;
import org.rg.drip.base.BaseActivity;
import org.rg.drip.contract.user.SignInContract;
import org.rg.drip.presenter.SignInPresenter;
import org.rg.drip.utils.CheckUtil;
import org.rg.drip.utils.RepositoryUtil;
import org.rg.drip.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

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

	@OnClick({ R.id.fab_sign_up, R.id.bt_go, R.id.bt_forget_password })
	void Click(View v) {
		ToastUtil.showCustumToast(this, "OnClick()");
		switch(v.getId()) {
			case R.id.fab_sign_up:
				getWindow().setExitTransition(null);
				getWindow().setEnterTransition(null);
				startActivity(new Intent(SignInActivity.this, SignUpActivity.class),
				              ActivityOptions.makeSceneTransitionAnimation(SignInActivity.this,
				                                                           mSignUpFab,
				                                                           mSignUpFab.getTransitionName())
				                             .toBundle());
				break;

			case R.id.bt_go:
				mPresenter.signIn(mUsernameEt.getText().toString(),
				                  mPasswordEt.getText().toString());
				break;

			case R.id.bt_forget_password:
				QMUIDialog.EditTextDialogBuilder builder =
						new QMUIDialog.EditTextDialogBuilder(this);
				builder.setPlaceholder(R.string.tip_input_email)
				       .setTitle(R.string.tip_send_email_to_reset)
				       .setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
				       .addAction(R.string.check_no, ((dialog, index) -> dialog.dismiss()))
				       .addAction(R.string.check_yes,
				                  (dialog, index) -> mPresenter.forgetPassword(builder.getEditText()
				                                                                      .getText()
				                                                                      .toString()))
				       .create()
				       .show();
				break;
		}
	}

	private SignInContract.Presenter mPresenter;

	private QMUITipDialog mLoadingTipDialog;

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.activity_sign_in;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		mPresenter = new SignInPresenter(RepositoryUtil.getUserRepository(), this);
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
		showTip(R.string.tip_sign_in_succeed);
		startActivity(new Intent(SignInActivity.this, DripActivity.class));
	}

	@Override
	public void showLoadingTipDialog(boolean bShow) {
		if(bShow) {
			if(mLoadingTipDialog == null) {
				mLoadingTipDialog = new QMUITipDialog.CustomBuilder(this)
						                    .setContent(R.layout.tip_loading)
						                    .create();
			}
			mLoadingTipDialog.show();
		} else {
			if(mLoadingTipDialog != null && mLoadingTipDialog.isShowing()) {
				mLoadingTipDialog.dismiss();
			}
		}
	}
}
