package org.rg.drip.ui.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.rg.drip.R;
import org.rg.drip.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by TankGq
 * on 2018/3/27.
 */
public class SignInActivity extends BaseActivity {
	
	@BindView(R.id.fab) FloatingActionButton mSignUpBtn;
	
	@BindView(R.id.et_username) EditText mUsernameET;
	
	@BindView(R.id.et_password) EditText mPasswordET;
	
	@BindView(R.id.go_bt) Button mGo;
	
	@BindView(R.id.forget_password_bt) TextView mForgetPasswordBtn;
	
	@Override
	protected int getContentViewLayoutID() {
		return R.layout.activity_sign_in;
	}
	
	@Override
	protected void initView(Bundle savedInstanceState) {
		mGo.setOnClickListener(v -> {
			SignInActivity.this.onBackPressed();
			Explode explode = new Explode();
			explode.setDuration(500);

			getWindow().setExitTransition(explode);
			getWindow().setEnterTransition(explode);

			startActivity(new Intent(SignInActivity.this, DripActivity.class),
			              ActivityOptionsCompat.makeSceneTransitionAnimation(SignInActivity.this)
			                                   .toBundle());
			
		});
		mSignUpBtn.setOnClickListener(v -> {
			getWindow().setExitTransition(null);
			getWindow().setEnterTransition(null);
			startActivity(new Intent(SignInActivity.this, SignUpActivity.class),
			              ActivityOptions.makeSceneTransitionAnimation(SignInActivity.this,
			                                                           mSignUpBtn,
			                                                           mSignUpBtn.getTransitionName())
			                             .toBundle());
		});
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		mSignUpBtn.setVisibility(View.GONE);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mSignUpBtn.setVisibility(View.VISIBLE);
	}
}
