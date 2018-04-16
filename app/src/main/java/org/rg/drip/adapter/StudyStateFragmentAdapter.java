package org.rg.drip.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.rg.drip.fragment.DefaultFragment;
import org.rg.drip.fragment.wordbook.CompleteProgressFragment;
import org.rg.drip.fragment.wordbook.LearningProgressFragment;
import org.rg.drip.fragment.wordbook.WordCountFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author : TankGq
 * Time : 2018/4/5
 */
public class StudyStateFragmentAdapter extends FragmentPagerAdapter {

	private List<String> mTitles;

	public StudyStateFragmentAdapter(FragmentManager fm, String... titles) {
		super(fm);
		mTitles = new ArrayList<>();
		Collections.addAll(mTitles, titles);
	}

	@Override
	public Fragment getItem(int position) {
		switch(position) {
			case 0:
				return CompleteProgressFragment.newInstance();
			case 1:
				return LearningProgressFragment.newInstance();
			case 2:
				return WordCountFragment.newInstance();
			default:
				return DefaultFragment.newInstance(mTitles.get(position));
		}
	}

	@Override
	public int getCount() {
		return mTitles.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mTitles.get(position);
	}
}
