package org.rg.drip.fragment.wordbook;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.CompoundButton;

import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.rg.drip.R;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.constant.MessageEventConstant;
import org.rg.drip.constant.UIConstant;
import org.rg.drip.data.model.cache.Wordbook;
import org.rg.drip.event.MessageEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class ChooseWordbookFragment extends BaseSubFragment implements CompoundButton.OnCheckedChangeListener {

	@BindView(R.id.groupListView) QMUIGroupListView mGroupListView;
	private QMUIGroupListView.Section mDefaultWordbookList;
	private QMUIGroupListView.Section mMyWordbookList;

	private List<QMUICommonListItemView> mItemViewList;
	private SparseArray<Wordbook> mWordbookDic;

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
		mItemViewList = new ArrayList<>();
		mWordbookDic = new SparseArray<>();
		mMyWordbookList = QMUIGroupListView.newSection(getContext());
		mMyWordbookList.setTitle(getString(R.string.tip_my_wordbook));
		mDefaultWordbookList = QMUIGroupListView.newSection(getContext());
		mDefaultWordbookList.setTitle(getString(R.string.tip_default_wordbook));

		// 自己的单词本
		List<Wordbook> wordbooks = new ArrayList<>();
		Wordbook wordbook = new Wordbook();
		wordbook.setId(1);
		wordbook.setUserId(1);
		wordbook.setName("我的单词本1");
		wordbooks.add(wordbook);
		wordbook = new Wordbook();
		wordbook.setId(2);
		wordbook.setUserId(1);
		wordbook.setName("我的单词本2");
		wordbooks.add(wordbook);
		wordbook = new Wordbook();
		wordbook.setId(3);
		wordbook.setUserId(1);
		wordbook.setName("我的单词本3");
		wordbooks.add(wordbook);
		QMUICommonListItemView itemView;
		int size = wordbooks.size();
		for(int idx = 0; idx < size; ++ idx) {
			itemView = mGroupListView.createItemView(wordbooks.get(idx).getName());
			itemView.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
			itemView.getSwitch().setOnCheckedChangeListener(this);
			itemView.getSwitch().setChecked(false);
			itemView.getSwitch().setId(View.generateViewId());
			mItemViewList.add(itemView);
			mWordbookDic.put(itemView.getSwitch().getId(), wordbooks.get(idx));
			mMyWordbookList.addItemView(itemView, null);
		}
		mMyWordbookList.addTo(mGroupListView);

		// 默认的单词本
		wordbook = new Wordbook();
		wordbook.setId(- 1);
		wordbook.setUserId(0);
		wordbook.setName("默认单词本1");
		wordbooks = new ArrayList<>();
		wordbooks.add(wordbook);
		wordbook = new Wordbook();
		wordbook.setId(- 2);
		wordbook.setUserId(0);
		wordbook.setName("默认单词本2");
		wordbooks.add(wordbook);
		wordbook = new Wordbook();
		wordbook.setId(- 3);
		wordbook.setUserId(0);
		wordbook.setName("默认单词本3");
		wordbooks.add(wordbook);

		size = wordbooks.size();
		for(int idx = 0; idx < size; ++ idx) {
			itemView = mGroupListView.createItemView(wordbooks.get(idx).getName());
			itemView.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
			itemView.getSwitch().setOnCheckedChangeListener(this);
			itemView.getSwitch().setChecked(false);
			itemView.getSwitch().setId(View.generateViewId());
			mItemViewList.add(itemView);
			mWordbookDic.put(itemView.getSwitch().getId(), wordbooks.get(idx));
			mDefaultWordbookList.addItemView(itemView, null);
		}
		mDefaultWordbookList.addTo(mGroupListView);
	}

	@Override
	protected boolean registerEventBus() {
		return true;
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void fadeOut(MessageEvent event) {
		if(event.getCode() != MessageEventConstant.HIDE_CHOOSE_WORDBOOK) {
			return;
		}
		QMUIViewHelper.fadeOut(this.getView(),
		                       UIConstant.FADE_ANIMATOR_DURATION,
		                       null,
		                       true);
	}

	@Override
	public boolean onBackPressedSupport() {
		EventBus.getDefault().post(MessageEventConstant.HIDE_CHOOSE_WORDBOOK_EVENT);
		EventBus.getDefault().post(MessageEventConstant.BACK_TO_WORDBOOK_MAIN_EVENT);
		return true;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		EventBus.getDefault().post(MessageEventConstant.BACK_TO_WORDBOOK_MAIN_EVENT);
		EventBus.getDefault().post(MessageEventConstant.HIDE_CHOOSE_WORDBOOK_EVENT);
		if(! isChecked) {
			EventBus.getDefault().post(MessageEventConstant.UPDATE_SELECT_WORDBOOK_NAME_EVENT);
			return;
		}
		int size = mItemViewList.size();
		for(int idx = 0; idx < size; ++ idx) {
			mItemViewList.get(idx).getSwitch().setChecked(false);
		}
		buttonView.setChecked(true);
		// 这边本来不应这样写的, 先这样临时写下
		EventBus.getDefault()
		        .post(new MessageEvent(MessageEventConstant.UPDATE_SELECT_WORDBOOK_NAME,
		                               mWordbookDic.get(buttonView.getId()).getName()));
	}
}
