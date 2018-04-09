package org.rg.drip.fragment.wordbook;

import android.os.Bundle;

import org.rg.drip.R;
import org.rg.drip.base.BaseSubFragment;

/**
 * Author : TankGq
 * Time : 2018/4/9
 */
public class StartLearningFragment extends BaseSubFragment {

	public static StartLearningFragment newInstance() {
		StartLearningFragment fragment = new StartLearningFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.wordbook_fragment_start_learn;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
	}

	@Override
	public boolean onBackPressedSupport() {
		this.popToChild(StudyActionFragment.class, false);
		return true;
	}
}
