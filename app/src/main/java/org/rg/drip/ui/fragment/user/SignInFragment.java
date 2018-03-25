package org.rg.drip.ui.fragment.user;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.rg.drip.R;
import org.rg.drip.base.BaseMainFragment;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.utils.LoggerUtil;
import org.rg.drip.utils.ToastUtil;

import butterknife.BindView;

/**
 * Author : TankGq
 * Time : 25/03/2018
 */
public class SignInFragment extends BaseSubFragment {

	@BindView(R.id.fab)
	FloatingActionButton signUpBtn;

	@BindView(R.id.et_username)
	EditText usernameET;

	@BindView(R.id.et_password)
	EditText passwordET;

	@BindView(R.id.go_bt)
	Button goBtn;

	@BindView(R.id.forget_password_bt)
	TextView forgetPasswordBtn;

	public static SignInFragment newInstance() {
		SignInFragment fragment = new SignInFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.tab_user_fragment_sign_in;
	}

	@Override
	protected void initView() {
		signUpBtn.setOnClickListener(
				v -> {
					BaseSubFragment signUpFragment = findFragment(SignUpFragment.class);
					if(signUpFragment == null) {
						ToastUtil.showCustumToastLong(SignInFragment.this.getContext(),
						                              "new");
						signUpFragment = SignUpFragment.newInstance();
					}
					start(signUpFragment);
				}
		);

		goBtn.setOnClickListener(v -> ToastUtil.showCustumToastLong(SignInFragment.this.getContext(),
		                                                            "Login"));
	}
}
