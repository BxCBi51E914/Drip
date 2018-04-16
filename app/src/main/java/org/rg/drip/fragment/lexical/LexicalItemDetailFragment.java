package org.rg.drip.fragment.lexical;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.rg.drip.R;
import org.rg.drip.base.BaseSubFragment;

import butterknife.BindView;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class LexicalItemDetailFragment extends BaseSubFragment {
	
	public static final String TAG = LexicalItemDetailFragment.class.getSimpleName();
	private static final String ARG_TITLE = "arg_title";
	
	static final String KEY_RESULT_TITLE = "title";
	
	@BindView(R.id.toolbar) Toolbar mToolbar;
	@BindView(R.id.tv_content) TextView mTvContent;
	private String mTitle;
	
	public static LexicalItemDetailFragment newInstance(String title) {
		LexicalItemDetailFragment fragment = new LexicalItemDetailFragment();
		Bundle bundle = new Bundle();
		bundle.putString(ARG_TITLE, title);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle bundle = getArguments();
		if(bundle != null) {
			mTitle = bundle.getString(ARG_TITLE);
		}
	}
	
	@Override
	protected int getContentViewLayoutID() {
		return R.layout.lexical_fragment_lexical_item_detail;
	}
	
	@Override
	protected void initView(Bundle savedInstanceState) {
		mToolbar.setTitle(mTitle);
		mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
		mToolbar.setNavigationOnClickListener(v -> this.onBackPressedSupport());
	}
	
	/**
	 * 比较复杂的Fragment页面会在第一次start时,导致动画卡顿
	 * Fragmentation提供了onEnterAnimationEnd()方法,该方法会在 入栈动画 结束时回调
	 * 所以在onCreateView进行一些简单的View初始化(比如 toolbar设置标题,返回按钮; 显示加载数据的进度条等),
	 * 然后在onEnterAnimationEnd()方法里进行 复杂的耗时的初始化 (比如FragmentPagerAdapter的初始化 加载数据等)
	 */
	@Override
	public void onEnterAnimationEnd(Bundle savedInstanceState) {
		super.onEnterAnimationEnd(savedInstanceState);
		initDelayView();
	}
	
	private void initDelayView() {
		mTvContent.setText(R.string.large_text);
	}
	
	@Override
	public boolean onBackPressedSupport() {
		pop();
		return true;
	}
}
