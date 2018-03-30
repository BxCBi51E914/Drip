package org.rg.drip.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.popup.QMUIListPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.rg.drip.R;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.activity.SignInActivity;
import org.rg.drip.data.model.cache.User;
import org.rg.drip.utils.RepositoryUtil;

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
		List<String> popupItemData = Arrays.asList(getString(R.string.act_sign_out),
		                                           getString(R.string.act_change_password),
		                                           getString(R.string.act_change_email));
		mListPopup = new QMUIListPopup(getContext(),
		                               QMUIPopup.DIRECTION_BOTTOM,
		                               new ArrayAdapter<>(getActivity(),
		                                                  R.layout.popup_list_item,
		                                                  popupItemData));
		mListPopup.create(QMUIDisplayHelper.dp2px(getContext(), 192),
		                  QMUIDisplayHelper.dp2px(getContext(), 192),
		                  (parent, view, position, id) -> {
			                  switch(position) {
				                  // R.string.act_sign_out
				                  case 0:
					                  new QMUIDialog.MessageDialogBuilder(getActivity())
							                  .setTitle(getString(R.string.act_sign_out))
							                  .setMessage(getString(R.string.ask_sign_out))
							                  .addAction(getString(R.string.chk_no),
							                             (dialog, index) -> dialog.dismiss())
							                  .addAction(getString(R.string.chk_out),
							                             (dialog, index) -> {
								                             RepositoryUtil
										                             .getUserRepository()
										                             .signOut();
								                             updateUserSignState();
								                             dialog.dismiss();
							                             })
							                  .create()
							                  .show();
					                  break;
				
				                  // R.string.act_change_password
				                  case 1:
					                  QMUIDialog.EditTextDialogBuilder
							                  builder
							                  = new QMUIDialog.EditTextDialogBuilder(getContext());
					                  builder
							                  .setPlaceholder(R.string.tip_input_new_email)
							                  .setTitle(R.string.act_change_email)
							                  .setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
							                  .addAction(R.string.chk_no,
							                             ((dialog, index) -> dialog.dismiss()))
							                  .addAction(R.string.chk_send, (dialog, index) -> {
							                  })
							                  .create()
							                  .show();
					                  break;
				
				                  // R.string.act_change_password
				                  case 2:
					                  new QMUIDialog.MessageDialogBuilder(getActivity())
							                  .setTitle(getString(R.string.act_change_password))
							                  .setMessage(getString(R.string.ask_change_password))
							                  .addAction(getString(R.string.chk_no),
							                             (dialog, index) -> dialog.dismiss())
							                  .addAction(getString(R.string.chk_yes),
							                             (dialog, index) -> dialog.dismiss())
							                  .create()
							                  .show();
					                  break;
			                  }
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
			if(null == user.getEmailVerified() || ! user.getEmailVerified()) {
				nickname += getString(R.string.brackets_unverified);
			}
			mNickNameTv.setText(nickname);
		}
	}
}
