package org.rg.drip.fragment.wordbook;

import android.os.Bundle;

import org.rg.drip.R;
import org.rg.drip.base.BaseMainFragment;
import org.rg.drip.fragment.user.AvatarFragment;

/**
 * Author : TankGq
 * Time : 03/04/2018
 */
public class WordBookMainFragment extends BaseMainFragment
		implements BaseMainFragment.OnBackToFirstListener {

	public static WordBookMainFragment newInstance() {
		WordBookMainFragment fragment = new WordBookMainFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.tab_wordbook_fragment_main;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {

	}

	@Override
	public void onBackToFirstFragment() {
		_mBackToFirstListener.onBackToFirstFragment();
	}

	@Override
	protected void initViewOnActivityCreated() {
		if(findChildFragment(AvatarFragment.class) == null) {
			loadFragment();
		}
	}

	private void loadFragment() {
		loadRootFragment(R.id.fragment_wordbook_container_upper, StudyStateFragment.newInstance());
		loadRootFragment(R.id.fragment_wordbook_container_lower, StudyActionFragment.newInstance());
	}

	@Override
	protected void initViewOnLazyInitView() {
	}
	
	
}
