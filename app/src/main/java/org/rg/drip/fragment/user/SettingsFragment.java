package org.rg.drip.fragment.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import org.rg.drip.R;
import org.rg.drip.base.BaseSubFragment;

import butterknife.BindView;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class SettingsFragment extends BaseSubFragment {
	
	@BindView(R.id.toolbarSettings) Toolbar mToolbar;
	
	public static SettingsFragment newInstance() {
		SettingsFragment fragment = new SettingsFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	protected int getContentViewLayoutID() {
		return R.layout.tab_user_fragment_settings;
	}
	
	@Override
	protected void initView(Bundle savedInstanceState) {
		initToolbarNav(mToolbar);
	}
	
	@Override
	public boolean onBackPressedSupport() {
		pop();
		return true;
	}
}
