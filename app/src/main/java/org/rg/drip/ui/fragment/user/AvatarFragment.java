package org.rg.drip.ui.fragment.user;

import android.content.Intent;
import android.os.Bundle;

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

	@BindView(R.id.signInOrSignUpBtn)
	QMUIRoundButton mSignOrSignUpBtn;

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
		mSignOrSignUpBtn.setOnClickListener(v -> startActivity(new Intent(getActivity(),
		                                                                  SignInActivity.class)));
	}

	@Override
	public void onResume() {
		super.onResume();
		ToastUtil.showCustumToast(getContext(), "AvatarFragment.onResume()");
	}
}
