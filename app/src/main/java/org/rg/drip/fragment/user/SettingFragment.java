package org.rg.drip.fragment.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseIntArray;
import android.view.View;

import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import org.rg.drip.R;
import org.rg.drip.base.BaseMainFragment;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.constant.ConfigConstant;
import org.rg.drip.contract.SettingContract;
import org.rg.drip.presenter.SettingPresenter;
import org.rg.drip.utils.CheckUtil;
import org.rg.drip.utils.ConfigUtil;
import org.rg.drip.utils.RepositoryUtil;
import org.rg.drip.utils.ToastUtil;

import butterknife.BindView;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class SettingFragment extends BaseSubFragment implements View.OnClickListener, SettingContract.View {
	
	@BindView(R.id.toolbarSettings) Toolbar mToolbar;
	@BindView(R.id.groupListView) QMUIGroupListView mGroupListView;
	
	private QMUIBottomSheet mLanguageBottomSheet;
	QMUICommonListItemView mLanguageItem;
	
	private SettingContract.Presenter mPresenter;
	
	private SparseIntArray mViewIdDic;
	
	public static SettingFragment newInstance() {
		SettingFragment fragment = new SettingFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	protected int getContentViewLayoutID() {
		return R.layout.user_fragment_settings;
	}
	
	@Override
	protected void initView(Bundle savedInstanceState) {
		mPresenter = new SettingPresenter(RepositoryUtil.getUserRepository(), this);
		mViewIdDic = new SparseIntArray();
		initToolbarNav(mToolbar);
		mLanguageItem = mGroupListView.createItemView(getString(R.string.language));
		mLanguageItem.setDetailText("(" + getString(getLanguageStringId()) + ")");
		mLanguageItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
		mLanguageItem.setId(View.generateViewId());
		mViewIdDic.put(R.string.language, mLanguageItem.getId());

		QMUIGroupListView.newSection(getContext())
		                 .addItemView(mLanguageItem, this)
		                 .addTo(mGroupListView);
	}
	
	@Override
	public boolean onBackPressedSupport() {
		pop();
		return true;
	}
	
	private int getLanguageStringId() {
		String language = ConfigUtil.getLanguage();
		switch(language) {
			case ConfigConstant.LANGUAGE_CHINESE:
				return R.string.language_zh;
			case ConfigConstant.LANGUAGE_ENGLISH:
				return R.string.language_en;
		}
		return R.string.error;
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == mViewIdDic.get(R.string.language)) {
			if(mLanguageBottomSheet == null) {
				QMUIBottomSheet.BottomListSheetBuilder sheet = new QMUIBottomSheet.BottomListSheetBuilder(getContext());
				mLanguageBottomSheet = sheet.addItem(getString(R.string.language_auto))
				                            .addItem(getString(R.string.language_en))
				                            .addItem(getString(R.string.language_zh))
				                            .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
					                            dialog.dismiss();
					                            switch(position) {
						                            // R.string.language_auto
						                            case 0:
							                            ConfigUtil.setLanguage(ConfigConstant.LANGUAGE_AUTO);
							                            break;
						                            // R.string.language_en
						                            case 1:
							                            ConfigUtil.setLanguage(ConfigConstant.LANGUAGE_ENGLISH);
							                            break;
						                            // R.string.language_zh
						                            case 2:
							                            ConfigUtil.setLanguage(ConfigConstant.LANGUAGE_CHINESE);
							                            break;
						                            default:
							                            return;
					                            }
					                            
					                            mPresenter.saveUserConfig();
				                            })
				                            .build();
			}
			mLanguageBottomSheet.show();
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mPresenter.subscribe();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mPresenter.unSubscribe();
	}
	
	@Override
	public void showTip(int stringId) {
		ToastUtil.showCustumToast(getContext(), getString(stringId));
	}
	
	@Override
	public void updateLanguageItem() {
//		String oldText = mLanguageItem.getDetailText().toString();
//		String newText = "(" + getString(getLanguageStringId()) + ")";
	}
	
	@Override
	public void setPresenter(SettingContract.Presenter presenter) {
		mPresenter = CheckUtil.checkNotNull(presenter);
	}
	
}
