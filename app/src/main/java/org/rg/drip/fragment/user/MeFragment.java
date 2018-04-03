package org.rg.drip.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.rg.drip.R;
import org.rg.drip.base.BaseFragment;
import org.rg.drip.base.BaseMainFragment;
import org.rg.drip.base.BaseSubFragment;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

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
		return R.layout.fragment_me_main;
	}
	
	@Override
	protected void initView(Bundle savedInstanceState) {
	}
}
