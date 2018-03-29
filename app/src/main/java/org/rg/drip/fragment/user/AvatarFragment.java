package org.rg.drip.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogBuilder;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.popup.QMUIListPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.rg.drip.R;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.activity.SignInActivity;
import org.rg.drip.data.model.cache.User;
import org.rg.drip.utils.RepositoryUtil;
import org.rg.drip.utils.ToastUtil;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class AvatarFragment extends BaseSubFragment {

	@BindView(R.id.rb_sign_in_or_sign_up) QMUIRoundButton mSignOrSignUpBt;

	@BindView(R.id.tv_nickname) TextView mNickNameTv;

	@OnClick({ R.id.rb_sign_in_or_sign_up, R.id.tv_nickname })
	void onClick(View v) {
		switch(v.getId()) {
			case R.id.rb_sign_in_or_sign_up:
				startActivity(new Intent(getActivity(), SignInActivity.class));
				break;

			case R.id.tv_nickname:
				if(mListPopup != null) {
					if(mListPopup.isShowing()) {
						mListPopup.dismiss();
					} else {
						mListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
						mListPopup.setPreferredDirection(QMUIPopup.DIRECTION_BOTTOM);
						mListPopup.show(v);
					}
				}
//				new QMUIDialog.MessageDialogBuilder(getActivity()).setTitle("退出登录")
//				                                                  .setMessage(
//						                                                  "确认退出登录?")
//				                                                  .addAction(
//						                                                  "取消",
//						                                                  (dialog, index) -> dialog.dismiss())
//				                                                  .addAction(
//						                                                  "确定",
//						                                                  (dialog, index) -> {
//							                                                  RepositoryUtil
//									                                                  .getUserRepository()
//									                                                  .signOut();
//							                                                  updateUserSignState();
//							                                                  dialog.dismiss();
//						                                                  })
//				                                                  .create()
//				                                                  .show();
				break;
		}
	}

	private QMUIListPopup mListPopup;

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
		updateUserSignState();
		mListPopup = new QMUIListPopup(getContext(),
		                               QMUIPopup.DIRECTION_BOTTOM,
		                               new ArrayAdapter<>(getActivity(),
		                                                  R.layout.popup_list_item,
		                                                  Arrays.asList("退出登录", "修改密码", "修改邮箱")));
		mListPopup.create(QMUIDisplayHelper.dp2px(getContext(), 120),
		                  QMUIDisplayHelper.dp2px(getContext(), 200),
		                  (parent, view, position, id) -> {
			                  ToastUtil.showCustumToast(getContext(),
			                                            view.toString() +
			                                            " position=" +
			                                            position +
			                                            ", id=" +
			                                            id);
			                  mListPopup.dismiss();
		                  });
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
			String nickname = user.getNickname();
			if(! user.getEmailVerified()) {
				nickname += getString(R.string.brackets_unverified);
			}
			mNickNameTv.setText(nickname);
		}
	}
}
