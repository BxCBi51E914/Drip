package org.rg.drip.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.rg.drip.GlobalData;
import org.rg.drip.fragment.lexical.LexicalItemListFragment;

import java.util.List;

/**
 * Author : eee
 * Time : 16/6/5
 */
public class CustomPagerFragmentAdapter extends FragmentStatePagerAdapter {
	private List<String> mTitles;
	
	public CustomPagerFragmentAdapter(FragmentManager fm, List<String> titles) {
		super(fm);
		mTitles = titles;
	}
	
	@Override
	public Fragment getItem(int position) {
		return LexicalItemListFragment.newInstance(GlobalData.lexicalPositionDic.get(position));
	}
	
	public void setData(List<String> data) {
		this.mTitles = data;
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getItemPosition(@NonNull Object object) {
//		http://stackoverflow.com/questions/10396321/remove-fragment-page-from-viewpager-in-android
//		viewpager在加载当前页的时候已经将pager页左右页的内容加载进内存里了，这样才保证了viewpager左右滑动的时候的流畅性；
//		为了解决彻底删除fragment，我们要做的是：
//		1.将FragmentPagerAdapter 替换成FragmentStatePagerAdapter，因为前者只要加载过，fragment中的视图就一直在内存中，在这个过程中无论你怎么刷新，清除都是无用的，直至程序退出； 后者 可以满足我们的需求。
//		2.我们可以重写Adapter的方法--getItemPosition()，让其返回PagerAdapter.POSITION_NONE即可；
		return POSITION_NONE;
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
