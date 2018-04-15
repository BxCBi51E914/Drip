package org.rg.drip.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.rg.drip.constant.MessageEventConstant;
import org.rg.drip.event.MessageEvent;
import org.rg.drip.fragment.custom.child.childpager.FirstPagerFragment;
import org.rg.drip.fragment.custom.child.childpager.OtherPagerFragment;

import java.util.List;

/**
 * Author : TankGq
 * Time : 16/6/5
 */
public class CustomPagerFragmentAdapter extends FragmentPagerAdapter {
	private List<String> mTitles;

	public CustomPagerFragmentAdapter(FragmentManager fm, List<String> titles) {
		super(fm);
		mTitles = titles;
	}

	@Override
	public Fragment getItem(int position) {
		if(position == 0) {
			return FirstPagerFragment.newInstance();
		} else {
			return OtherPagerFragment.newInstance(mTitles.get(position));
		}
	}

	public void setData(List<String> data) {
		this.mTitles = data;
		this.notifyDataSetChanged();
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
