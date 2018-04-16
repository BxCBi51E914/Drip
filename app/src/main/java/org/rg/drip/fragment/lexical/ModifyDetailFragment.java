package org.rg.drip.fragment.lexical;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.rg.drip.R;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.fragment.CycleFragment;


/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class ModifyDetailFragment extends BaseSubFragment {
    private static final String ARG_TITLE = "arg_title";

    private Toolbar mToolbar;
    private EditText mEtModiyTitle;
    private Button mBtnModify, mBtnNext;

    private String mTitle;

    public static ModifyDetailFragment newInstance(String title) {
        Bundle args = new Bundle();
        ModifyDetailFragment fragment = new ModifyDetailFragment();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mTitle = args.getString(ARG_TITLE);
        }
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
        View view = inflater.inflate(R.layout.fragment_modify_detail, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mEtModiyTitle = (EditText) view.findViewById(R.id.et_modify_title);
        mBtnModify = (Button) view.findViewById(R.id.btn_modify);
        mBtnNext = (Button) view.findViewById(R.id.btn_next);

        mToolbar.setTitle(R.string.start_result_test);
        initToolbarNav(mToolbar);

        mEtModiyTitle.setText(mTitle);

        // 显示 软键盘
//        showSoftInput(mEtModiyTitle);

        mBtnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(LexicalItemDetailFragment.KEY_RESULT_TITLE, mEtModiyTitle.getText().toString());
                setFragmentResult(RESULT_OK, bundle);

                Toast.makeText(_mActivity, R.string.modify_success, Toast.LENGTH_SHORT).show();
            }
        });
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(CycleFragment.newInstance(1));
            }
        });
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        hideSoftInput();
    }

    @Override
    public boolean onBackPressedSupport() {
        pop();
        return true;
    }
}
