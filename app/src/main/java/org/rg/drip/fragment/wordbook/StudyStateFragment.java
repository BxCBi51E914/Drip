package org.rg.drip.fragment.wordbook;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import org.rg.drip.R;
import org.rg.drip.adapter.StudyStateFragmentAdapter;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.contract.StudyStateConstact;

import butterknife.BindView;

/**
 * Author : eee
 * Time : 03/04/2018
 */
public class StudyStateFragment extends BaseSubFragment implements StudyStateConstact.View {

	@BindView(R.id.tab) TabLayout mTabLayout;
	@BindView(R.id.viewPager) ViewPager mViewPager;

	public static StudyStateFragment newInstance() {
		StudyStateFragment fragment = new StudyStateFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.wordbook_fragment_study_state;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		mTabLayout.addTab(mTabLayout.newTab());
		mTabLayout.addTab(mTabLayout.newTab());
		mTabLayout.addTab(mTabLayout.newTab());

		mViewPager.setAdapter(new StudyStateFragmentAdapter(getChildFragmentManager(),
		                                                    getString(R.string.tab_complete_progress),
		                                                    getString(R.string.tab_learning_progress),
		                                                    getString(R.string.tab_word_count)));
		mTabLayout.setupWithViewPager(mViewPager);
	}
	
	@Override
	public void setPresenter(StudyStateConstact.Presenter presenter) {
	
	}
}
