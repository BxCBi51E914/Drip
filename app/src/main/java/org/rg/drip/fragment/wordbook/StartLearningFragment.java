package org.rg.drip.fragment.wordbook;

import android.os.Bundle;
import android.view.View;

import com.huxq17.swipecardsview.SwipeCardsView;

import org.rg.drip.R;
import org.rg.drip.adapter.StartLearnSwipeCardAdapter;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.data.model.cache.Word;
import org.rg.drip.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author : TankGq
 * Time : 2018/4/9
 */
public class StartLearningFragment extends BaseSubFragment {
	
	@BindView(R.id.swipe_cards_view) SwipeCardsView mSwipeCardsView;
	private StartLearnSwipeCardAdapter mAdapter;

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
		mSwipeCardsView.retainLastCard(true);
		mSwipeCardsView.enableSwipe(true);
		List<Word> words = new ArrayList<>();
		mSwipeCardsView.setCardsSlideListener(new SwipeCardsView.CardsSlideListener() {
			@Override
			public void onShow(int index) {
				ToastUtil.showCustumToast(getContext(), "Cur card index: " + index);
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
		});
		mAdapter = new StartLearnSwipeCardAdapter(getContext(), null);
		mSwipeCardsView.setAdapter(mAdapter);
	}

	@Override
	public boolean onBackPressedSupport() {
		this.popToChild(StudyActionFragment.class, false);
		return true;
	}
}
