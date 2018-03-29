package org.rg.drip.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogBuilder;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.rg.drip.R;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.activity.SignInActivity;
import org.rg.drip.data.model.cache.User;
import org.rg.drip.utils.RepositoryUtil;
import org.rg.drip.utils.ToastUtil;

import butterknife.BindView;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class AvatarFragment extends BaseSubFragment {
	
	@BindView(R.id.rb_sign_in_or_sign_up) QMUIRoundButton mSignOrSignUpBt;
	
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
			startActivity(new Intent(getActivity(), SignInActivity.class));
			ToastUtil.showCustumToast(getContext(), "mSignOrSignUpBt.OnClick()");
		});
		
		mNickNameTv.setOnClickListener(v -> {
			new QMUIDialog.MessageDialogBuilder(getActivity()).setTitle("退出登录")
			                                                  .setMessage("确认退出登录?")
			                                                  .addAction("取消",
			                                                             (dialog, index) -> dialog.dismiss())
			                                                  .addAction("确定", (dialog, index) -> {
				                                                  RepositoryUtil.getUserRepository()
				                                                                .signOut();
				                                                  updateUserSignState();
				                                                  dialog.dismiss();
			                                                  })
			                                                  .create()
			                                                  .show();
		});
		updateUserSignState();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		updateUserSignState();
	}
	
	public void updateUserSignState() {
		User user = RepositoryUtil.getUserRepository().getCurrentUser();
		if(user == null) {
			mNickNameTv.setVisibility(View.INVISIBLE);
			mSignOrSignUpBt.setVisibility(View.VISIBLE);
		} else {
			mNickNameTv.setVisibility(View.VISIBLE);
			mSignOrSignUpBt.setVisibility(View.INVISIBLE);
			mNickNameTv.setText(user.getNickname());
		}
	}
}
