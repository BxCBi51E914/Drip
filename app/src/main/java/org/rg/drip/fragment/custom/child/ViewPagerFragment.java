package org.rg.drip.fragment.custom.child;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.InputType;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import org.rg.drip.R;
import org.rg.drip.adapter.CustomPagerFragmentAdapter;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.constant.UIConstant;
import org.rg.drip.utils.CheckUtil;
import org.rg.drip.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class ViewPagerFragment extends BaseSubFragment implements QMUITabSegment.OnTabClickListener {

	@BindView(R.id.top_bar) Toolbar mTopBar;
	@BindView(R.id.tab_segment) QMUITabSegment mTabSegment;
	@BindView(R.id.content_view_pager) ViewPager mViewPager;

	private CustomPagerFragmentAdapter mAdapter;
	private List<String> mTabNameList;

	public static ViewPagerFragment newInstance() {
		ViewPagerFragment fragment = new ViewPagerFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.custom_fragment_main_pager;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		mTopBar.setTitle(R.string.custom_learn);
		mTabNameList = new ArrayList<>();
		mTabNameList.add("Example");
		mAdapter = new CustomPagerFragmentAdapter(getChildFragmentManager(), mTabNameList);
		mViewPager.setAdapter(mAdapter);
		mViewPager.setCurrentItem(0);
		int size = mTabNameList.size();
		for(int idx = 0; idx < size; ++ idx) {
			mTabSegment.addTab(new QMUITabSegment.Tab(adjust(mTabNameList.get(idx), 16)));
		}
		mTabSegment.addTab(new QMUITabSegment.Tab("  +  "));
		int space = QMUIDisplayHelper.dp2px(getContext(), 16);
		mTabSegment.setHasIndicator(true);
		mTabSegment.setIndicatorWidthAdjustContent(false);
		mTabSegment.setMode(QMUITabSegment.MODE_SCROLLABLE);
		mTabSegment.setItemSpaceInScrollMode(space);
		mTabSegment.setupWithViewPager(mViewPager, false);
		mTabSegment.setPadding(space, 0, space, 0);
		mTabSegment.setOnTabClickListener(this);
	}

	public void addOneTab(String tabName) {
		tabName = adjust(tabName, UIConstant.CUSTOM_TAB_MIN_SIZE);
		mTabNameList.add(tabName);
		mAdapter.setData(mTabNameList);
		int size = mTabNameList.size();
		mTabSegment.updateTabText(size - 1, tabName);
		mTabSegment.addTab(new QMUITabSegment.Tab(adjust("+",  UIConstant.CUSTOM_TAB_MIN_SIZE)));
		mTabSegment.notifyDataChanged();
	}

	public String adjust(String text, int minSize) {
		int length = text.length();
		if(length > minSize) {
			return text;
		}
		length = (minSize - length) >> 1;
		StringBuilder textBuilder = new StringBuilder(text);
		for(int idx = 0; idx < length; ++ idx) {
			textBuilder = new StringBuilder(' ' + textBuilder.toString() + ' ');
		}
		text = textBuilder.toString();
		return text;
	}

	@Override
	public void onTabClick(int index) {
		if(index == mTabNameList.size()) {
			final QMUIDialog.EditTextDialogBuilder tabNameDialog = new QMUIDialog.EditTextDialogBuilder(getContext());
			tabNameDialog.setPlaceholder(R.string.tip_Lexical_item)
			             .setTitle(R.string.act_add_Lexical_item)
			             .setInputType(InputType.TYPE_CLASS_TEXT)
			             .addAction(R.string.chk_no, (dialog, idx) -> dialog.dismiss())
			             .addAction(R.string.chk_yes, (dialog, idx) -> {
				             String text = tabNameDialog.getEditText().getText().toString();
				             if(text.length() != 0 && CheckUtil.checkName(text)) {
					             addOneTab(text);
					             ToastUtil.showCustumToast(getContext(), getString(R.string.tip_create_succeed));
				             } else {
					             ToastUtil.showCustumToast(getContext(), getString(R.string.tip_create_failed));
				             }
				             dialog.dismiss();
			             })
			             .create()
			             .show();
		}
	}
}
