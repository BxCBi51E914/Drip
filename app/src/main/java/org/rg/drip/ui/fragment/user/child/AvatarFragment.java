package org.rg.drip.ui.fragment.user.child;

import android.os.Bundle;

import org.rg.drip.R;
import org.rg.drip.base.BaseSubFragment;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class AvatarFragment extends BaseSubFragment {

    public static AvatarFragment newInstance() {
        AvatarFragment fragment = new AvatarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.tab_user_fragment_avatar;
    }

    @Override
    protected void initView() {

    }
}
