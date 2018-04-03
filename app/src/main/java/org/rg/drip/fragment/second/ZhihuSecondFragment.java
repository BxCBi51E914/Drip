package org.rg.drip.fragment.second;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return 0;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
		    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zhihu_fragment_second, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (findChildFragment(ViewPagerFragment.class) == null) {
            loadRootFragment(R.id.fl_second_container, ViewPagerFragment.newInstance());
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 这里可以不用懒加载,因为Adapter的场景下,Adapter内的子Fragment只有在父Fragment是show状态时,才会被Attach,Create
    }
}
