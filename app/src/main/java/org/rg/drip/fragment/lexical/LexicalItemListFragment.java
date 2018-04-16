package org.rg.drip.fragment.lexical;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.rg.drip.GlobalData;
import org.rg.drip.R;
import org.rg.drip.adapter.LexicalItemAdapter;
import org.rg.drip.base.BaseFragment;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.constant.LexicalConstant;
import org.rg.drip.constant.LexicalItemConstant;
import org.rg.drip.constant.MessageEventConstant;
import org.rg.drip.data.model.cache.LexicalItem;
import org.rg.drip.event.MessageEvent;
import org.rg.drip.listener.OnItemClickListener;
import org.rg.drip.listener.OnItemLongClickListener;
import org.rg.drip.utils.LoggerUtil;
import org.rg.drip.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class LexicalItemListFragment extends BaseSubFragment implements OnItemClickListener, OnItemLongClickListener {

	private final static String TAG_LEXICAL_ID = "lexical_id";

	@BindView(R.id.recycle_view) RecyclerView mRecycleView;
	private LexicalItemAdapter mAdapter;
	private int mLexicalId;
	private List<LexicalItem> mLexicalItems;
	private String[] mKeys;
	private String[] mValues;

	public static LexicalItemListFragment newInstance(int lexicalId) {
		LexicalItemListFragment fragment = new LexicalItemListFragment();
		Bundle args = new Bundle();
		args.putInt(TAG_LEXICAL_ID, lexicalId);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.lexical_fragment_lexical;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		mLexicalId = getArguments().getInt(TAG_LEXICAL_ID);
		LoggerUtil.d("mLexicalId = %d", mLexicalId);
		mKeys = getResources().getStringArray(R.array.tutorial_title);
		mValues = getResources().getStringArray(R.array.tutorial_content);

//		mAdapter.setOnItemClickListener((position, view1, vh) -> {
//			// 这里的DetailFragment在flow包里
//			// 这里是父Fragment启动,要注意 栈层级
//			((BaseFragment) getParentFragment()).start(LexicalItemDetailFragment.newInstance(mAdapter.getItem(position)
//			                                                                                         .getKey()));
//		});

		mLexicalItems = GlobalData.lexicalItemDic.get(mLexicalId);
		if(mLexicalId == LexicalConstant.DEFAULT_LEXICAL && mLexicalItems.size() == 1) {
			int size = mKeys.length;
			LexicalItem lexicalItem;
			for(int idx = 0; idx < size; ++ idx) {
				lexicalItem = new LexicalItem();
				lexicalItem.setId((int) (Math.random() * 1000 + 1));
				lexicalItem.setLexicalId(mLexicalId);
				lexicalItem.setKey(mKeys[idx]);
				lexicalItem.setValue(mValues[idx]);
				mLexicalItems.add(idx, lexicalItem);
			}
		}
		mAdapter = new LexicalItemAdapter(_mActivity);
		LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
		mAdapter.setData(mLexicalItems);
		mAdapter.setOnItemClickListener(this);
		mAdapter.setOnLongClickListener(this);
//		mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//			@Override
//			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//				super.onScrolled(recyclerView, dx, dy);
//				mScrollTotal += dy;
//				mAtTop = mScrollTotal <= 0;
//			}
//		});
		mRecycleView.setLayoutManager(manager);
		mRecycleView.setAdapter(mAdapter);
	}

	@Override
	protected boolean registerEventBus() {
		return true;
	}

	@Override
	protected boolean unregisterWhenOnPause() {
		return false;
	}

//	@Override
//	public void onRefresh() {
//		mRefreshLayout.postDelayed(() -> mRefreshLayout.setRefreshing(false), 2000);
//	}
//
//	private void scrollToTop() {
//		mRecycleView.smoothScrollToPosition(0);
//	}

	/**
	 * 选择tab事件
	 */
//	@Subscribe
//	public void onTabSelectedEvent(TabSelectedEvent event) {
//		if(event.position != UIConstant.READING) return;
//
//		if(mAtTop) {
//			mRefreshLayout.setRefreshing(true);
//			onRefresh();
//		} else {
//			scrollToTop();
//		}
//	}
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onLexicalDataUpdate(MessageEvent event) {
		if(event.getCode() != MessageEventConstant.UPDATE_LEXICAL_DATA) {
			return;
		}
		if(GlobalData.currentLexicalId != mLexicalId) {
			return;
		}
		int index = mLexicalItems.size() - 2;
		mAdapter.addData(index, mLexicalItems.get(index));
		mAdapter.notifyItemInserted(index);
	}

	@Override
	public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
		mAdapter.setData(mLexicalItems);
		mAdapter.notifyDataSetChanged();
		LexicalItem lexicalItem = mLexicalItems.get(position);
		GlobalData.currentLexicalId = mLexicalId;
		GlobalData.currentLexicalItemId = lexicalItem.getId();
		if(lexicalItem.getId() == LexicalItemConstant.DEFAULT_ID) {
			EventBus.getDefault().post(MessageEventConstant.SHOW_CREATE_LEXICAL_ITEM_EVENT);
		} else {
			((BaseFragment) getParentFragment()).start(LexicalItemDetailFragment.newInstance());
		}
	}

	private void deleteOneItem(int position) {
		mLexicalItems.remove(position);
		mAdapter.removeData(position);
	}

	@Override
	public boolean onItemLongClick(final int position, View view, RecyclerView.ViewHolder vh) {
		final QMUIDialog.CheckableDialogBuilder checkDeleteDialog = new QMUIDialog.CheckableDialogBuilder(getContext());
		checkDeleteDialog.setTitle(R.string.act_delete_Lexical_item)
		                 .addAction(R.string.chk_no, (dialog, idx) -> dialog.dismiss())
		                 .addAction(R.string.chk_yes, (dialog, idx) -> {
			                 dialog.dismiss();
			                 if(mLexicalItems.size() == position + 1) {
				                 ToastUtil.showCustumToast(getContext(), getString(R.string.tip_delete_failed));
				                 return;
			                 }
			                 deleteOneItem(position);
			                 ToastUtil.showCustumToast(getContext(), getString(R.string.tip_delete_succeed));
		                 })
		                 .create()
		                 .show();
		return true;
	}
}
