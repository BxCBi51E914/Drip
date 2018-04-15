package org.rg.drip.fragment.wordbook;

import android.os.Bundle;
import android.view.View;

import com.huxq17.swipecardsview.SwipeCardsView;
import com.loopeer.cardstack.AllMoveDownAnimatorAdapter;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.UpDownStackAnimatorAdapter;
import com.qmuiteam.qmui.util.QMUIViewHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.rg.drip.R;
import org.rg.drip.adapter.BrowseInCardStackCardAdapter;
import org.rg.drip.adapter.StartLearnSwipeCardAdapter;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.constant.MessageEventConstant;
import org.rg.drip.constant.UIConstant;
import org.rg.drip.contract.BrowseInCardContract;
import org.rg.drip.contract.StartLearnContract;
import org.rg.drip.data.model.cache.Word;
import org.rg.drip.data.model.cache.Wordbook;
import org.rg.drip.event.MessageEvent;
import org.rg.drip.presenter.BrowseInCardPresenter;
import org.rg.drip.presenter.StartLearnPresenter;
import org.rg.drip.utils.ConfigUtil;
import org.rg.drip.utils.LoadingTipDialogUtil;
import org.rg.drip.utils.LoggerUtil;
import org.rg.drip.utils.RepositoryUtil;
import org.rg.drip.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Author : TankGq
 * Time : 2018/4/9
 */
public class BrowseInCardFragment extends BaseSubFragment implements BrowseInCardContract.View,
                                                                     CardStackView.ItemExpendListener,
                                                                     View.OnScrollChangeListener {

	@BindView(R.id.card_stack_view) CardStackView mCardStackView;
	private BrowseInCardStackCardAdapter mAdapter;

	private BrowseInCardContract.Presenter mPresenter;

	private int mCurrentIndex;
	private int mDataSize;

	public static BrowseInCardFragment newInstance() {
		BrowseInCardFragment fragment = new BrowseInCardFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.wordbook_fragment_browse_in_card;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		mPresenter = new BrowseInCardPresenter(RepositoryUtil.getWordbookRepository(),
		                                       RepositoryUtil.getWordRepository(),
		                                       this);
		ConfigUtil.setCurrentWordBook(new Wordbook(- 1, 0));
		mAdapter = new BrowseInCardStackCardAdapter(getContext());
		mCardStackView.setAnimatorAdapter(new AllMoveDownAnimatorAdapter(mCardStackView));
		mCardStackView.setItemExpendListener(this);
		mCardStackView.setAdapter(mAdapter);
		mCurrentIndex = 0;
		mCardStackView.setOnScrollChangeListener(this);
	}

	@Override
	public boolean onBackPressedSupport() {
		EventBus.getDefault().post(MessageEventConstant.HIDE_BROWSE_IN_CARD_EVENT);
		return true;
	}

	@Override
	protected boolean registerEventBus() {
		return true;
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void fadeOut(MessageEvent event) {
		if(event.getCode() != MessageEventConstant.HIDE_BROWSE_IN_CARD) {
			return;
		}
		QMUIViewHelper.fadeOut(this.getView(),
		                       UIConstant.FADE_ANIMATOR_DURATION,
		                       null,
		                       true);
	}

//	@Override
//	public void onShow(int index) {
//		mCurrentIndex = index;
//		if(mDataSize - mCurrentIndex < UIConstant.LOAD_DATA_CARD_NUMBER) {
//			mPresenter.updateCardsData();
//		}
//	}

	@Override
	public void setPresenter(BrowseInCardContract.Presenter presenter) {
		mPresenter = presenter;
	}

	@Override
	public void onResume() {
		super.onResume();
		mPresenter.subscribe();
		mPresenter.updateCardsData();
	}

	@Override
	public void onPause() {
		super.onPause();
		mPresenter.unSubscribe();
	}

	@Override
	public void updateSwipeCardsView(List<Word> data) {
		mDataSize = data.size();
		mAdapter.updateData(data);
	}

	@Override
	public void showLoadingTipDialog(boolean bShow) {
		// 初次加载才显示
		if(mDataSize != 0) {
			return;
		}
		if(bShow) {
			LoadingTipDialogUtil.show(getContext());
		} else {
			LoadingTipDialogUtil.dismiss();
		}
	}

	@Override
	public void onItemExpend(boolean expend) {

	}

	@Override
	public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
		LoggerUtil.d("scrollX: %d, scrollY: %d, oldScrollX: %d, oldScrollY: %d",
		             scrollX,
		             scrollY,
		             oldScrollX,
		             oldScrollY);
	}
}
