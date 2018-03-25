package org.rg.drip.ui.fragment.user;

import android.os.Bundle;

import org.rg.drip.R;
import org.rg.drip.base.BaseSubFragment;

import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Author : TankGq
 * Time : 25/03/2018
 */
public class SignUpFragment extends BaseSubFragment {

	public static SignUpFragment newInstance() {
		SignUpFragment fragment = new SignUpFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.tab_user_fragment_sign_up;
	}

	@Override
	protected void initView() {
	}
}
