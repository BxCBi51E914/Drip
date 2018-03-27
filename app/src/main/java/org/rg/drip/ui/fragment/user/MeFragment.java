package org.rg.drip.ui.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.rg.drip.R;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class MeFragment extends SupportFragment {
    private TextView mTvBtnSettings;

    public static MeFragment newInstance() {

        Bundle args = new Bundle();

        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
		    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me_main, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTvBtnSettings = (TextView) view.findViewById(R.id.tv_btn_settings);
        mTvBtnSettings.setOnClickListener(v -> start(SettingsFragment.newInstance()));
    }

    @Override
    public boolean onBackPressedSupport() {
        // 这里实际项目中推荐使用 EventBus接耦
        ((TabUserMainFragment)getParentFragment()).onBackToFirstFragment();
        return true;
    }
}
