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

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.rg.drip.R;
import org.rg.drip.activity.SignInActivity;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.constant.UIConstant;
import org.rg.drip.contract.AvatarContract;
import org.rg.drip.data.model.cache.User;
import org.rg.drip.presenter.AvatarPresenter;
import org.rg.drip.utils.CheckUtil;
import org.rg.drip.utils.LoadingTipDialogUtil;
import org.rg.drip.utils.LoggerUtil;
import org.rg.drip.utils.RepositoryUtil;
import org.rg.drip.utils.ToastUtil;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class AvatarFragment extends BaseSubFragment implements AvatarContract.View {

	@BindView(R.id.rb_sign_in_or_sign_up) QMUIRoundButton mSignOrSignUpBt;

	@BindView(R.id.tv_nickname) TextView mNickNameTv;

	@OnClick({ R.id.rb_sign_in_or_sign_up, R.id.tv_nickname })
	void onClick(View v) {
		switch(v.getId()) {
			case R.id.rb_sign_in_or_sign_up:
				showSignInFragment();
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

	private AvatarContract.Presenter mPresenter;

	/**
	 * 点击用户昵称显示的下拉菜单
	 */
	private QMUIListPopup mListPopup;

	public static AvatarFragment newInstance() {
		AvatarFragment fragment = new AvatarFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.user_fragment_avatar;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		LoggerUtil.d("qwe");
		mPresenter = new AvatarPresenter(RepositoryUtil.getUserRepository(), this);
		mPresenter.updateSignInUser();
		List<String> popupItemData = Arrays.asList(getString(R.string.act_sign_out),
		                                           getString(R.string.act_change_password),
		                                           getString(R.string.act_change_email));
		mListPopup = new QMUIListPopup(getContext(),
		                               QMUIPopup.DIRECTION_BOTTOM,
		                               new ArrayAdapter<>(getActivity(),
		                                                  R.layout.popup_list_item,
		                                                  popupItemData));
		mListPopup.create(QMUIDisplayHelper.dp2px(getContext(), UIConstant.AVATAR_POPUP_WIDTH),
		                  QMUIDisplayHelper.dp2px(getContext(), UIConstant.AVATAR_POPUP_HEIGHT),
		                  (parent, view, position, id) -> {
			                  switch(position) {
				                  // R.string.act_sign_out
				                  case 0:
					                  showConfirmSignOutDialog();
					                  break;

				                  // R.string.act_change_password
				                  case 1:
					                  showConfirmChangePasswordDialog();
					                  break;

				                  // R.string.act_change_password
				                  case 2:
					                  showChangeEmailDialog();
					                  break;
			                  }
			                  mListPopup.dismiss();
		                  });
	}

	@Override
	public void onResume() {
		super.onResume();
		mPresenter.subscribe();
		mPresenter.updateSignInUser();
	}

	@Override
	public void onPause() {
		super.onPause();
		mPresenter.unSubscribe();
	}

	@Override
	public void showTip(int stringId) {
		ToastUtil.showCustumToast(getContext(), getString(stringId));
	}

	@Override
	public void showLoadingTipDialog(boolean bShow) {
		if(bShow) {
			LoadingTipDialogUtil.show(getContext());
		} else {
			LoadingTipDialogUtil.dismiss();
		}
	}

	@Override
	public void loadUser(User user) {
		if(user == null) {
			mNickNameTv.setVisibility(View.INVISIBLE);
			mSignOrSignUpBt.setVisibility(View.VISIBLE);
			return;
		}

		mNickNameTv.setVisibility(View.VISIBLE);
		mSignOrSignUpBt.setVisibility(View.INVISIBLE);
		String nickname = user.getNickname();
		if(null == user.getEmailVerified() || ! user.getEmailVerified()) {
			nickname += getString(R.string.brackets_unverified);
		}
		mNickNameTv.setText(nickname);
	}

	@Override
	public void showConfirmSignOutDialog() {
		new QMUIDialog.MessageDialogBuilder(getActivity())
				.setTitle(getString(R.string.act_sign_out))
				.setMessage(getString(R.string.ask_sign_out))
				.addAction(getString(R.string.chk_no), (dialog, index) -> dialog.dismiss())
				.addAction(getString(R.string.chk_out), (dialog, index) -> {
					mPresenter.signOut();
					mPresenter.updateSignInUser();
					dialog.dismiss();
				})
				.create()
				.show();
	}

	@Override
	public void showChangeEmailDialog() {
		new QMUIDialog.EditTextDialogBuilder(getContext())
				.setPlaceholder(R.string.tip_input_new_email)
				.setTitle(R.string.act_change_email)
				.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
				.addAction(R.string.chk_no, ((dialog, index) -> dialog.dismiss()))
				.addAction(R.string.chk_send, (dialog, index) -> {
				})
				.create()
				.show();
	}

	@Override
	public void showConfirmChangePasswordDialog() {
		new QMUIDialog.MessageDialogBuilder(getActivity())
				.setTitle(getString(R.string.act_change_password))
				.setMessage(getString(R.string.ask_change_password))
				.addAction(getString(R.string.chk_no), (dialog, index) -> dialog.dismiss())
				.addAction(getString(R.string.chk_yes), (dialog, index) -> dialog.dismiss())
				.create()
				.show();
	}

	@Override
	public void showSignInFragment() {
//		BaseFragment parentFragment = (BaseFragment) getParentFragment();
//		if(parentFragment == null) {
//			LoggerUtil.e("The parent of AvatarFragment is null");
//			showTip(R.string.tip_unknown_error);
//			return;
//		}
//
//		parentFragment.loadRootFragment(R.id.fragment_user_container_whole,
//		                                SignInFragment.newInstance());
		startActivity(new Intent(getActivity(), SignInActivity.class));
	}

	@Override
	public void setPresenter(AvatarContract.Presenter presenter) {
		mPresenter = CheckUtil.checkNotNull(presenter);
	}
	
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void Test() {
		LoggerUtil.d("asd");
	}
}
