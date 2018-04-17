package org.rg.drip.fragment.lexical;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;

import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.rg.drip.GlobalData;
import org.rg.drip.R;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.constant.MessageEventConstant;
import org.rg.drip.constant.UIConstant;
import org.rg.drip.data.model.cache.LexicalItem;
import org.rg.drip.event.MessageEvent;
import org.rg.drip.utils.InputMethodUtil;
import org.rg.drip.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class CreateLexicalItemFragment extends BaseSubFragment {

	@BindView(R.id.et_key) MaterialEditText mKeyEt;
	@BindView(R.id.et_value) MaterialEditText mValueEt;
	@BindView(R.id.bt_go) Button mGoBt;

	@OnClick(R.id.bt_go)
	void onClick(View v) {
		if(v.getId() != R.id.bt_go) {
			return;
		}
		String key = mKeyEt.getText().toString();
		if(key.isEmpty()) {
			ToastUtil.showCustumToast(getContext(), getString(R.string.tip_create_failed));
			return;
		}
		String value = mValueEt.getText().toString();
		if(value.isEmpty()) {
			ToastUtil.showCustumToast(getContext(), getString(R.string.tip_create_failed));
			return;
		}
		LexicalItem lexicalItem = new LexicalItem();
		lexicalItem.setId((int) (Math.random() * 1000 + 1));
		lexicalItem.setLexicalId(GlobalData.currentLexicalId);
		lexicalItem.setKey(key);
		lexicalItem.setValue(value);
		List<LexicalItem> lexicalItems = GlobalData.getCurrentLexicalItemList();
		int size = lexicalItems.size();
		lexicalItems.add(size - 1, lexicalItem);
		EventBus.getDefault().post(MessageEventConstant.UPDATE_LEXICAL_DATA_EVENT);
//		EventBus.getDefault().post(MessageEventConstant.HIDE_CREATE_LEXICAL_ITEM_EVENT);
		ToastUtil.showCustumToast(getContext(), getString(R.string.tip_create_succeed));
		InputMethodUtil.hide(v);
		this.onBackPressedSupport();
	}

	public static CreateLexicalItemFragment newInstance() {
		CreateLexicalItemFragment fragment = new CreateLexicalItemFragment();
		Bundle bundle = new Bundle();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.lexical_fragment_create_lexical_item;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
	}

	@Override
	protected boolean registerEventBus() {
		return true;
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void refreshData(MessageEvent event) {
		if(event.getCode() != MessageEventConstant.CREATE_LEXICAL_ITEM_REFRESH) {
			return;
		}
		mKeyEt.setText("");
		mValueEt.setText("");
	}

	@Override
	public boolean onBackPressedSupport() {
		pop();
//		EventBus.getDefault()
//		        .post(MessageEventConstant.HIDE_CREATE_LEXICAL_ITEM_EVENT);
		return true;
	}

//	@Subscribe(threadMode = ThreadMode.MAIN)
//	public void fadeOut(MessageEvent event) {
//		if(event.getCode() != MessageEventConstant.HIDE_CREATE_LEXICAL_ITEM) {
//			return;
//		}
//		QMUIViewHelper.fadeOut(this.getView(), UIConstant.FADE_ANIMATOR_DURATION, null, true);
//	}
}
