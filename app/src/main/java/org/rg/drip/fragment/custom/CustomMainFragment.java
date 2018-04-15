package org.rg.drip.fragment.custom;

import android.os.Bundle;

import org.rg.drip.R;
import org.rg.drip.base.BaseMainFragment;
import org.rg.drip.fragment.custom.child.ViewPagerFragment;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class CustomMainFragment extends BaseMainFragment
		implements BaseMainFragment.OnBackToFirstListener {

	public static CustomMainFragment newInstance() {

		Bundle args = new Bundle();

		CustomMainFragment fragment = new CustomMainFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.fragment_custom_main;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {

	}

	@Override
	protected void initViewOnActivityCreated() {
		if(findChildFragment(ViewPagerFragment.class) == null) {
			loadRootFragment(R.id.fragment_custom_container, ViewPagerFragment.newInstance());
		}
	}

	@Override
	protected void initViewOnLazyInitView() {
		// 这里可以不用懒加载,因为Adapter的场景下,Adapter内的子Fragment只有在父Fragment是show状态时,才会被Attach,Create
	}

	@Override
	public void onBackToFirstFragment() {
		_mBackToFirstListener.onBackToFirstFragment();
	}
}
