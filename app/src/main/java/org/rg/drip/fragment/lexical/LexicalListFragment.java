package org.rg.drip.fragment.lexical;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.InputType;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import org.rg.drip.GlobalData;
import org.rg.drip.R;
import org.rg.drip.adapter.CustomPagerFragmentAdapter;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.constant.LexicalConstant;
import org.rg.drip.constant.LexicalItemConstant;
import org.rg.drip.constant.UIConstant;
import org.rg.drip.data.model.cache.Lexical;
import org.rg.drip.data.model.cache.LexicalItem;
import org.rg.drip.utils.CheckUtil;
import org.rg.drip.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class LexicalListFragment extends BaseSubFragment implements QMUITabSegment.OnTabSelectedListener {

	@BindView(R.id.top_bar) Toolbar mTopBar;
	@BindView(R.id.tab_segment) QMUITabSegment mTabSegment;
	@BindView(R.id.content_view_pager) ViewPager mViewPager;

	private CustomPagerFragmentAdapter mAdapter;
	private List<String> mLexicalNameList;
	private List<Lexical> mLexicalList;

	public static LexicalListFragment newInstance() {
		LexicalListFragment fragment = new LexicalListFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.lexical_fragment_main_pager;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		mTopBar.setTitle(R.string.lexical);
		mLexicalNameList = new ArrayList<>();
		mLexicalList = new ArrayList<>();
		int size = GlobalData.lexicalDic.size();
		Lexical lexical;
		if(size == 0) {
			lexical = new Lexical();
			lexical.setUserId(LexicalConstant.DEFAULT_USER_ID);
			lexical.setName(LexicalConstant.DEFAULT_NAME);
			lexical.setId(LexicalConstant.DEFAULT_LEXICAL);
			addLexicalToGlobalData(lexical, 0);
			mLexicalNameList.add(lexical.getName());
			mLexicalList.add(lexical);
		} else {
			int lexicalId;
			for(int idx = 0; idx < size; ++ idx) {
				lexicalId = GlobalData.lexicalDic.keyAt(idx);
				lexical = new Lexical();
				lexical.setId(GlobalData.lexicalDic.get(lexicalId).getId());
				lexical.setName(GlobalData.lexicalDic.get(lexicalId).getName());
				lexical.setUserId(GlobalData.lexicalDic.get(lexicalId).getUserId());
				mLexicalNameList.add(lexical.getName());
				mLexicalList.add(lexical);
			}
		}
		mAdapter = new CustomPagerFragmentAdapter(getChildFragmentManager(), mLexicalNameList);
		mViewPager.setAdapter(mAdapter);
		mViewPager.setCurrentItem(0);
		refreshSegmentTab();
		int space = QMUIDisplayHelper.dp2px(getContext(), 16);
		mTabSegment.setHasIndicator(true);
		mTabSegment.setIndicatorWidthAdjustContent(false);
		mTabSegment.setMode(QMUITabSegment.MODE_SCROLLABLE);
		mTabSegment.setItemSpaceInScrollMode(space);
		mTabSegment.setupWithViewPager(mViewPager, false);
		mTabSegment.setPadding(space, 0, space, 0);
		mTabSegment.addOnTabSelectedListener(this);
	}

	private void addLexicalToGlobalData(Lexical lexical, int index) {
		if(null != GlobalData.lexicalItemDic.get(lexical.getId())) {
			return;
		}
		List<LexicalItem> tmp = new ArrayList<>();
		LexicalItem lexicalItem = new LexicalItem();
		lexicalItem.setId(LexicalItemConstant.DEFAULT_ID);
		tmp.add(lexicalItem);
		GlobalData.lexicalDic.put(lexical.getId(), lexical);
		GlobalData.lexicalPositionInvDic.put(lexical.getId(), index);
		GlobalData.lexicalPositionDic.put(index, lexical.getId());
		GlobalData.lexicalItemDic.put(lexical.getId(), tmp);
	}

	private void refreshSegmentTab() {
		mTabSegment.reset();
		int size = mLexicalList.size();
		for(int idx = 0; idx < size; ++ idx) {
			mTabSegment.addTab(new QMUITabSegment.Tab(adjust(mLexicalNameList.get(idx), 16)));
		}
		mTabSegment.addTab(new QMUITabSegment.Tab(adjust("+", UIConstant.CUSTOM_TAB_MIN_SIZE)));
		mTabSegment.notifyDataChanged();
		mTabSegment.selectTab(0);
	}

	private void addOneTab(int index, String tabName) {
		tabName = adjust(tabName, UIConstant.CUSTOM_TAB_MIN_SIZE);
		Lexical lexical = new Lexical();
		lexical.setUserId((int) (Math.random() * 1000000 + 1));
		lexical.setName(tabName);
		lexical.setId((int) (Math.random() * 1000 + 1));
		addLexicalToGlobalData(lexical, index);
		mLexicalNameList.add(lexical.getName());
		mLexicalList.add(lexical);
		mAdapter.setData(mLexicalNameList);
		int size = mLexicalList.size();
		mTabSegment.updateTabText(size - 1, tabName);
		mTabSegment.addTab(new QMUITabSegment.Tab(adjust("+", UIConstant.CUSTOM_TAB_MIN_SIZE)));
		mTabSegment.notifyDataChanged();
		mTabSegment.selectTab(size - 1);
	}

	private void deleteOneTab(int index) {
		mLexicalNameList.remove(index);
		mLexicalList.remove(index);
		mAdapter.setData(mLexicalNameList);
		refreshSegmentTab();
		int lexicalId = GlobalData.lexicalPositionDic.get(index);
		GlobalData.lexicalDic.delete(lexicalId);
		GlobalData.lexicalItemDic.delete(lexicalId);
		GlobalData.lexicalPositionDic.delete(index);
		GlobalData.lexicalPositionInvDic.delete(lexicalId);
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
	public void onTabSelected(final int index) {
		final int curIndex = mViewPager.getCurrentItem();
		if(index == mLexicalList.size()) {
			final QMUIDialog.EditTextDialogBuilder tabNameDialog = new QMUIDialog.EditTextDialogBuilder(getContext());
			tabNameDialog.setPlaceholder(R.string.tip_Lexical_item)
			             .setTitle(R.string.act_add_Lexical_item)
			             .setInputType(InputType.TYPE_CLASS_TEXT)
			             .addAction(R.string.chk_no, (dialog, idx) -> {
				             mTabSegment.selectTab(curIndex);
				             dialog.dismiss();
			             })
			             .addAction(R.string.chk_yes, (dialog, idx) -> {
				             String text = tabNameDialog.getEditText()
				                                        .getText()
				                                        .toString();
				             if(text.length() != 0 && CheckUtil.checkName(text)) {
					             addOneTab(index, text);
					             ToastUtil.showCustumToast(getContext(), getString(R.string.tip_create_succeed));
				             } else {
					             ToastUtil.showCustumToast(getContext(), getString(R.string.tip_create_failed));
					             mTabSegment.selectTab(curIndex);
				             }
				             dialog.dismiss();
			             })
			             .create()
			             .show();
		}
	}

	@Override
	public void onTabUnselected(int index) {

	}

	@Override
	public void onTabReselected(int index) {

	}

	@Override
	public void onDoubleTap(final int index) {
		final QMUIDialog.CheckableDialogBuilder checkDeleteDialog = new QMUIDialog.CheckableDialogBuilder(getContext());
		checkDeleteDialog.setTitle(R.string.act_delete_Lexical)
		                 .addAction(R.string.chk_no, (dialog, idx) -> dialog.dismiss())
		                 .addAction(R.string.chk_yes, (dialog, idx) -> {
			                 dialog.dismiss();
			                 if(mLexicalList.size() == 1) {
				                 ToastUtil.showCustumToast(getContext(), getString(R.string.tip_delete_failed));
				                 return;
			                 }
			                 deleteOneTab(index);
			                 ToastUtil.showCustumToast(getContext(), getString(R.string.tip_delete_succeed));
		                 })
		                 .create()
		                 .show();
	}
}
