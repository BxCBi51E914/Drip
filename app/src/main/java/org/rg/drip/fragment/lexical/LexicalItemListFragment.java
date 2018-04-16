package org.rg.drip.fragment.lexical;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.rg.drip.GlobalData;
import org.rg.drip.R;
import org.rg.drip.adapter.CustomLexicalItemAdapter;
import org.rg.drip.base.BaseFragment;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.constant.LexicalConstant;
import org.rg.drip.constant.LexicalItemConstant;
import org.rg.drip.constant.MessageEventConstant;
import org.rg.drip.constant.UIConstant;
import org.rg.drip.data.model.cache.LexicalItem;
import org.rg.drip.event.MessageEvent;
import org.rg.drip.event.TabSelectedEvent;
import org.rg.drip.listener.OnItemClickListener;
import org.rg.drip.utils.LoggerUtil;
import org.rg.drip.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class LexicalItemListFragment extends BaseSubFragment implements SwipeRefreshLayout.OnRefreshListener,
                                                                        OnItemClickListener {
	
	@BindView(R.id.recycle_view) RecyclerView mRecycleView;
	@BindView(R.id.refresh_layout) SwipeRefreshLayout mRefreshLayout;
	private CustomLexicalItemAdapter mAdapter;
	private int mLexicalId;
	private List<LexicalItem> mLexicalItems;
	private boolean mAtTop = true;
	private int mScrollTotal;
	private String[] mKeys;
	private String[] mValues;
	
	public static LexicalItemListFragment newInstance() {
		LexicalItemListFragment fragment = new LexicalItemListFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	protected int getContentViewLayoutID() {
		return R.layout.lexical_fragment_lexical;
	}
	
	@Override
	protected void initView(Bundle savedInstanceState) {
		mLexicalId = GlobalData.currentLexicalId;
		LoggerUtil.d("GlobalData.currentLexicalId: %d", mLexicalId);
		
		mKeys = getResources().getStringArray(R.array.tutorial_title);
		mValues = getResources().getStringArray(R.array.tutorial_content);
		
		mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
		mRefreshLayout.setOnRefreshListener(this);
		
		mAdapter = new CustomLexicalItemAdapter(_mActivity);
		LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
		mRecycleView.setLayoutManager(manager);
		mRecycleView.setAdapter(mAdapter);
		
		mAdapter.setOnItemClickListener((position, view1, vh) -> {
			// 这里的DetailFragment在flow包里
			// 这里是父Fragment启动,要注意 栈层级
			((BaseFragment) getParentFragment()).start(LexicalItemDetailFragment.newInstance(mAdapter.getItem(position)
			                                                                                         .getKey()));
		});
		
		mLexicalItems = GlobalData.lexicalItemDic.get(mLexicalId);
		if(mLexicalId == LexicalConstant.DEFAULT_LEXICAL) {
			int size = mKeys.length;
			LexicalItem lexicalItem;
			for(int idx = 0; idx < size; ++ idx) {
				lexicalItem = new LexicalItem();
				lexicalItem.setId((int) (Math.random() * 1000 + 1));
				lexicalItem.setLexicalId(mLexicalId);
				lexicalItem.setKey(mKeys[idx]);
				lexicalItem.setValue(mValues[idx]);
				mLexicalItems.add(0, lexicalItem);
			}
		}
		mAdapter.setData(mLexicalItems);
		mAdapter.setOnItemClickListener(this);
		
		mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				mScrollTotal += dy;
				mAtTop = mScrollTotal <= 0;
			}
		});
	}
	
	@Override
	protected boolean registerEventBus() {
		return true;
	}
	
	@Override
	public void onRefresh() {
		mRefreshLayout.postDelayed(() -> mRefreshLayout.setRefreshing(false), 2000);
	}
	
	private void scrollToTop() {
		mRecycleView.smoothScrollToPosition(0);
	}
	
	/**
	 * 选择tab事件
	 */
	@Subscribe
	public void onTabSelectedEvent(TabSelectedEvent event) {
		if(event.position != UIConstant.READING) return;
		
		if(mAtTop) {
			mRefreshLayout.setRefreshing(true);
			onRefresh();
		} else {
			scrollToTop();
		}
	}
	
	
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		EventBusActivityScope.getDefault(_mActivity)
		                     .unregister(this);
	}
	
	@Override
	public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
		LexicalItem lexicalItem = mLexicalItems.get(position);
		if(lexicalItem.getId() == LexicalItemConstant.DEFAULT_ID) {
			EventBus.getDefault().post(MessageEventConstant.SHOW_CREATE_LEXICAL_ITEM_EVENT);
		} else {
			ToastUtil.showCustumToast(getContext(), lexicalItem.getKey());
		}
	}
}
