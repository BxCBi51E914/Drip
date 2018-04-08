package org.rg.drip.fragment.first;

import android.os.Bundle;
import android.support.annotation.Nullable;

import org.rg.drip.R;
import org.rg.drip.base.BaseMainFragment;
import org.rg.drip.fragment.first.child.FirstHomeFragment;
import org.rg.drip.fragment.second.ZhihuSecondFragment;
import org.rg.drip.fragment.second.child.ViewPagerFragment;
import org.rg.drip.fragment.user.MeFragment;
import org.rg.drip.fragment.user.UserMainFragment;
import org.rg.drip.fragment.wordbook.StudyActionFragment;
import org.rg.drip.fragment.wordbook.WordBookMainFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class ZhihuFirstFragment extends BaseMainFragment
		implements BaseMainFragment.OnBackToFirstListener {

	public static ZhihuFirstFragment newInstance() {
		ZhihuFirstFragment fragment = new ZhihuFirstFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.zhihu_fragment_first;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
	}

	@Override
	public void onLazyInitView(@Nullable Bundle savedInstanceState) {
		super.onLazyInitView(savedInstanceState);

		if(findChildFragment(FirstHomeFragment.class) == null) {
			loadRootFragment(R.id.fl_first_container, FirstHomeFragment.newInstance());
		}
	}

	@Override
	public void onBackToFirstFragment() {
		if(this.getChildFragmentManager().getBackStackEntryCount() > 1) {
			this.popToChild(FirstHomeFragment.class, false);
		}
	}
}
