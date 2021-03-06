package org.rg.drip.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.rg.drip.R;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by eee
 * on 2018/3/20.
 */
public class DefaultFragment extends SupportFragment {
    private static final String ARG_TYPE = "arg_type";

    private String mTitle;

    public static DefaultFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(ARG_TYPE, title);
        DefaultFragment fragment = new DefaultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getString(ARG_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
		    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.default_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_key);
        tvTitle.setText(mTitle);
    }
}
