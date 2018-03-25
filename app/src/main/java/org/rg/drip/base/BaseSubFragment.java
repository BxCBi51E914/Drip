package org.rg.drip.base;

import android.support.v7.widget.Toolbar;

import org.rg.drip.R;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public abstract class BaseSubFragment extends BaseFragment {

	protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressed());
    }
}
