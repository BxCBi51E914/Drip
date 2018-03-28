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
	
	@BindView(R.id.fab_sign_up) FloatingActionButton mSignUpFab;
	
	@BindView(R.id.et_username) EditText mUsernameEt;
	
	@BindView(R.id.et_password) EditText mPasswordEt;
	
	@BindView(R.id.bt_go) Button mGoBt;
	
	@BindView(R.id.bt_forget_password) TextView mForgetPasswordBt;
	
	@Override
	protected int getContentViewLayoutID() {
		return R.layout.activity_sign_in;
	}
	
	@Override
	protected void initView(Bundle savedInstanceState) {
		mGoBt.setOnClickListener(v -> {
//			SignInActivity.this.onBackPressed();
			Explode explode = new Explode();
			explode.setDuration(500);

			getWindow().setExitTransition(explode);
			getWindow().setEnterTransition(explode);

			startActivity(new Intent(SignInActivity.this, DripActivity.class),
			              ActivityOptionsCompat.makeSceneTransitionAnimation(SignInActivity.this)
			                                   .toBundle());
			
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
	}
}
