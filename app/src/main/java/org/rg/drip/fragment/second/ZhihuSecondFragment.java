package org.rg.drip.fragment.second;

import android.os.Bundle;

import org.rg.drip.R;
import org.rg.drip.base.BaseMainFragment;
import org.rg.drip.fragment.second.child.ViewPagerFragment;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class ZhihuSecondFragment extends BaseMainFragment {

    public static ZhihuSecondFragment newInstance() {

        Bundle args = new Bundle();

        ZhihuSecondFragment fragment = new ZhihuSecondFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.zhihu_fragment_second;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

	@Override
	protected void initViewOnActivityCreated() {
		if (findChildFragment(ViewPagerFragment.class) == null) {
			loadRootFragment(R.id.fl_second_container, ViewPagerFragment.newInstance());
		}
	}

	@Override
	protected void initViewOnLazyInitView() {
		// 这里可以不用懒加载,因为Adapter的场景下,Adapter内的子Fragment只有在父Fragment是show状态时,才会被Attach,Create
	}
}
