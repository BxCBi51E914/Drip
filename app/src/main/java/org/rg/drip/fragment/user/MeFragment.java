package org.rg.drip.fragment.user;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.rg.drip.R;
import org.rg.drip.base.BaseSubFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class MeFragment extends BaseSubFragment {
	
	@BindView(R.id.tv_btn_settings) TextView mTvBtnSettings;
	@OnClick(R.id.tv_btn_settings)
	void onClick(View v) {
		start(SettingsFragment.newInstance());
	}
	
	public static MeFragment newInstance() {
		Bundle args = new Bundle();
		MeFragment fragment = new MeFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	protected int getContentViewLayoutID() {
		return R.layout.user_fragment_me;
	}
	
	@Override
	protected void initView(Bundle savedInstanceState) {
	}
}
