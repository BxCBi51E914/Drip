package org.rg.drip.fragment.wordbook;

import android.os.Bundle;

import org.rg.drip.R;
import org.rg.drip.base.BaseSubFragment;

/**
 * Author : TankGq
 * Time : 03/04/2018
 */
public class StudyActionFragment extends BaseSubFragment {

	public static StudyActionFragment newInstance() {
		StudyActionFragment fragment = new StudyActionFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.wordbook_fragment_study_action;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {

	}
}
