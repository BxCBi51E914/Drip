package org.rg.drip.fragment.wordbook;

import android.os.Bundle;

import org.rg.drip.R;
import org.rg.drip.base.BaseSubFragment;

/**
 * Author : TankGq
 * Time : 2018/4/5
 */
public class CompleteProgressFragment extends BaseSubFragment {

	public static CompleteProgressFragment newInstance() {
		CompleteProgressFragment fragment = new CompleteProgressFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.tab_wordbook_fragment_complete_progress;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {

	}
}
