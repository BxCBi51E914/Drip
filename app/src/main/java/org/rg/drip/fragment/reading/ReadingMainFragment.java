package org.rg.drip.fragment.reading;

import android.os.Bundle;
import android.support.annotation.Nullable;

import org.rg.drip.R;
import org.rg.drip.base.BaseMainFragment;

/**
 * Created by eee
 * on 2018/3/20.
 */
public class ReadingMainFragment extends BaseMainFragment
		implements BaseMainFragment.OnBackToFirstListener {

	public static ReadingMainFragment newInstance() {
		ReadingMainFragment fragment = new ReadingMainFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.reading_fragment_main;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
	}

	@Override
	public void onLazyInitView(@Nullable Bundle savedInstanceState) {
		super.onLazyInitView(savedInstanceState);

		if(findChildFragment(ReadingHomeFragment.class) == null) {
			loadRootFragment(R.id.fl_first_container, ReadingHomeFragment.newInstance());
		}
	}

	@Override
	public void onBackToFirstFragment() {
		if(this.getChildFragmentManager().getBackStackEntryCount() > 1) {
			this.popToChild(ReadingHomeFragment.class, false);
		}
	}
}
