package org.rg.drip.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.rg.drip.fragment.second.child.childpager.FirstPagerFragment;
import org.rg.drip.fragment.second.child.childpager.OtherPagerFragment;
import org.rg.drip.fragment.wordbook.CompleteProgressFragment;

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

		if(position == 0) {
			return CompleteProgressFragment.newInstance();
		} else {
			return OtherPagerFragment.newInstance(mTitles.get(position));
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
