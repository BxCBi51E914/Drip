package org.rg.drip.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import org.rg.drip.R;
import org.rg.drip.base.BaseMainFragment;

import butterknife.BindView;

/**
 * Created by eee
 * on 2018/3/20.
 */
public class UserMainFragment extends BaseMainFragment
		implements BaseMainFragment.OnBackToFirstListener {
	@BindView(R.id.toolbar) Toolbar mToolbar;

	public static UserMainFragment newInstance() {
		UserMainFragment fragment = new UserMainFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.user_fragment_main;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
	}

	@Override
	public void onLazyInitView(@Nullable Bundle savedInstanceState) {
		super.onLazyInitView(savedInstanceState);

		if(findChildFragment(AvatarFragment.class) == null) {
			loadFragment();
		}

		mToolbar.setTitle(R.string.me);
	}

	private void loadFragment() {
		loadRootFragment(R.id.fragment_user_container_upper, AvatarFragment.newInstance());
		loadRootFragment(R.id.fragment_user_container_lower, MeFragment.newInstance());
	}


	@Override
	public void onBackToFirstFragment() {
		_mBackToFirstListener.onBackToFirstFragment();
	}
}
