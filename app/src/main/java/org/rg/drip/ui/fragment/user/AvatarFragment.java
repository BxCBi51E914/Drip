package org.rg.drip.ui.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.rg.drip.R;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.ui.activity.SignInActivity;
import org.rg.drip.utils.ToastUtil;

import butterknife.BindView;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class AvatarFragment extends BaseSubFragment {

	@BindView(R.id.rb_sign_in_or_sign_up)
	QMUIRoundButton mSignOrSignUpBt;
	
	@BindView(R.id.tv_nickname) TextView mNickNameTv;

	public static AvatarFragment newInstance() {
		AvatarFragment fragment = new AvatarFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.tab_user_fragment_avatar;
	}

	@Override
	protected void initView() {
		mSignOrSignUpBt.setOnClickListener(v -> {
			startActivity(new Intent(getActivity(),
			                         SignInActivity.class));
			ToastUtil.showCustumToast(getContext(), "mSignOrSignUpBt.OnClick()");
		});
		
		mNickNameTv.setOnClickListener(v -> {
			ToastUtil.showCustumToast(getContext(), "mNickNameTv.OnClick()");
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		if(Math.random() > 0.5) {
			mNickNameTv.setVisibility(View.INVISIBLE);
			mSignOrSignUpBt.setVisibility(View.VISIBLE);
		} else {
			mNickNameTv.setVisibility(View.VISIBLE);
			mSignOrSignUpBt.setVisibility(View.INVISIBLE);
		}
	}
}
