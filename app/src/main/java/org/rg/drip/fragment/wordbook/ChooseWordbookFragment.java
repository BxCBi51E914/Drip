package org.rg.drip.fragment.wordbook;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.QMUILoadingView;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import org.rg.drip.R;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.utils.ToastUtil;

import butterknife.BindView;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class ChooseWordbookFragment extends BaseSubFragment {

	@BindView(R.id.toolbarSettings) Toolbar mToolbar;
	@BindView(R.id.groupListView) QMUIGroupListView mGroupListView;

	public static ChooseWordbookFragment newInstance() {
		ChooseWordbookFragment fragment = new ChooseWordbookFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.wordbook_fragment_choose_wordbook;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		initToolbarNav(mToolbar);
		SparseIntArray itemIdDic = new SparseIntArray();
		QMUICommonListItemView
				itemWithDetail =
				mGroupListView.createItemView(getString(R.string.language));
		itemWithDetail.setDetailText("(" + getString(R.string.language_en) + ")");
		itemWithDetail.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
		itemIdDic.put(R.string.language, View.generateViewId());
		itemWithDetail.setId(itemIdDic.get(R.string.language));

		QMUICommonListItemView normalItem = mGroupListView.createItemView("Item 1");
		normalItem.setOrientation(QMUICommonListItemView.VERTICAL);

		QMUICommonListItemView itemWithDetailBelow = mGroupListView.createItemView("Item 3");
		itemWithDetailBelow.setOrientation(QMUICommonListItemView.VERTICAL);
		itemWithDetailBelow.setDetailText("在标题下方的详细信息");

		QMUICommonListItemView itemWithChevron = mGroupListView.createItemView("Item 4");
		itemWithChevron.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

		QMUICommonListItemView itemWithSwitch = mGroupListView.createItemView("Item 5");
		itemWithSwitch.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
		itemWithSwitch.getSwitch()
		              .setOnCheckedChangeListener((buttonView, isChecked) -> Toast.makeText(
				              getActivity(),
				              "checked = " + isChecked,
				              Toast.LENGTH_SHORT).show());

		QMUICommonListItemView itemWithCustom = mGroupListView.createItemView("Item 6");
		itemWithCustom.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
		QMUILoadingView loadingView = new QMUILoadingView(getActivity());
		itemWithCustom.addAccessoryCustomView(loadingView);

		View.OnClickListener onClickListener = v -> {
			if(v.getId() == itemIdDic.get(R.string.language)) {
				new QMUIBottomSheet.BottomListSheetBuilder(getActivity())
						.addItem(getString(R.string.language_auto))
						.addItem(getString(R.string.language_en))
						.addItem(getString(R.string.language_zh))
						.setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
							ToastUtil.showCustumToast(getContext(), tag);
							dialog.dismiss();
						})
						.build()
						.show();
			}
			if(v instanceof QMUICommonListItemView) {
				String text = ((QMUICommonListItemView) v).getText().toString();
				ToastUtil.showCustumToast(getContext(), text + " is Clicked");
			}
		};

		QMUIGroupListView.newSection(getContext())
		                 .addItemView(itemWithDetail, onClickListener)
		                 .addTo(mGroupListView);

		QMUIGroupListView.newSection(getContext())
		                 .setTitle("Section 1: 默认提供的样式")
		                 .setDescription("Section 1 的描述")
		                 .addItemView(normalItem, onClickListener)
		                 .addItemView(itemWithDetailBelow, onClickListener)
		                 .addItemView(itemWithChevron, onClickListener)
		                 .addItemView(itemWithSwitch, onClickListener)
		                 .addTo(mGroupListView);

		QMUIGroupListView.newSection(getContext())
		                 .setTitle("Section 2: 自定义右侧 View")
		                 .addItemView(itemWithCustom, onClickListener)
		                 .addTo(mGroupListView);
	}

	@Override
	public boolean onBackPressedSupport() {
		pop();
		return true;
	}
}
