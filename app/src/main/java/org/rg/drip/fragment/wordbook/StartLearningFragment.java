package org.rg.drip.fragment.wordbook;

import android.os.Bundle;
import android.view.View;

import com.huxq17.swipecardsview.SwipeCardsView;
import com.qmuiteam.qmui.util.QMUIViewHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.rg.drip.R;
import org.rg.drip.activity.SignInActivity;
import org.rg.drip.adapter.StartLearnSwipeCardAdapter;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.constant.MessageEventConstant;
import org.rg.drip.constant.UIConstant;
import org.rg.drip.contract.StartLearnContract;
import org.rg.drip.data.model.cache.Word;
import org.rg.drip.data.model.cache.Wordbook;
import org.rg.drip.event.MessageEvent;
import org.rg.drip.presenter.StartLearnPresenter;
import org.rg.drip.utils.ConfigUtil;
import org.rg.drip.utils.LoadingTipDialogUtil;
import org.rg.drip.utils.RepositoryUtil;
import org.rg.drip.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author : eee
 * Time : 2018/4/9
 */
public class StartLearningFragment extends BaseSubFragment implements SwipeCardsView.CardsSlideListener,
                                                                      StartLearnContract.View {

	@BindView(R.id.swipe_cards_View) SwipeCardsView mSwipeCardsView;
	private StartLearnSwipeCardAdapter mAdapter;

	private StartLearnContract.Presenter mPresenter;

	private int mCurrentIndex;
	private int mDataSize;

	public static StartLearningFragment newInstance() {
		StartLearningFragment fragment = new StartLearningFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.wordbook_fragment_start_learn;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		mPresenter = new StartLearnPresenter(RepositoryUtil.getWordbookRepository(),
		                                     RepositoryUtil.getWordRepository(),
		                                     this);
		ConfigUtil.setCurrentWordBook(new Wordbook(-1, 0));
		mSwipeCardsView.retainLastCard(true);
		mSwipeCardsView.enableSwipe(true);
		mCurrentIndex = 0;
		mSwipeCardsView.setCardsSlideListener(this);
//		mAdapter = new StartLearnSwipeCardAdapter(getContext(), null);
//		mSwipeCardsView.setAdapter(mAdapter);
	}

	@Override
	public boolean onBackPressedSupport() {
		EventBus.getDefault().post(MessageEventConstant.BACK_TO_WORDBOOK_MAIN_EVENT);
		return true;
	}

	@Override
	protected boolean registerEventBus() {
		return true;
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void fadeOut(MessageEvent event) {
		if(event.getCode() != MessageEventConstant.HIDE_START_LEARN) {
			return;
		}
		QMUIViewHelper.fadeOut(this.getView(),
		                       UIConstant.FADE_ANIMATOR_DURATION,
		                       null,
		                       true);
	}

	@Override
	public void onShow(int index) {
		mCurrentIndex = index;
		if(mDataSize - mCurrentIndex < UIConstant.LOAD_DATA_CARD_NUMBER) {
			mPresenter.updateCardsData();
		}
	}

	@Override
	public void onCardVanish(int index, SwipeCardsView.SlideType type) {
		switch(type) {
			case LEFT:
				ToastUtil.showCustumToast(getContext(), "Left");
				break;
			case RIGHT:
				ToastUtil.showCustumToast(getContext(), "Right");
				break;
			case NONE:
				ToastUtil.showCustumToast(getContext(), "None");
				break;
		}
	}

	@Override
	public void onItemClick(View cardImageView, int index) {

	}

	@Override
	public void setPresenter(StartLearnContract.Presenter presenter) {
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
		if(mAdapter == null) {
			mAdapter = new StartLearnSwipeCardAdapter(getContext(), data);
			mSwipeCardsView.setAdapter(mAdapter);
			return;
		}
		mAdapter.setData(data);
		mSwipeCardsView.notifyDatasetChanged(mCurrentIndex);
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
}
