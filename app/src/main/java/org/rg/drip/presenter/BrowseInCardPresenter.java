package org.rg.drip.presenter;

import android.support.annotation.NonNull;

import org.rg.drip.constant.UIConstant;
import org.rg.drip.constant.WordConstant;
import org.rg.drip.contract.BrowseInCardContract;
import org.rg.drip.contract.StartLearnContract;
import org.rg.drip.data.contract.WordContract;
import org.rg.drip.data.contract.WordbookContract;
import org.rg.drip.data.model.cache.Word;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author : eee
 * Time : 2018/4/14
 */
public class BrowseInCardPresenter implements BrowseInCardContract.Presenter {

	@NonNull private final WordbookContract.Repository mWordbookRepository;
	@NonNull private final WordContract.Repository mWordRepository;
	@NonNull private final BrowseInCardContract.View mView;

	@NonNull private CompositeDisposable mCompositeDisposable;

	private List<Word> mData;

	public BrowseInCardPresenter(@NonNull WordbookContract.Repository wordbookRepository,
	                             @NonNull WordContract.Repository wordRepository,
	                             @NonNull BrowseInCardContract.View browseInCardView) {
		mWordbookRepository = wordbookRepository;
		mWordRepository = wordRepository;
		mView = browseInCardView;

		mCompositeDisposable = new CompositeDisposable();
		mView.setPresenter(this);
	}

	@Override
	public void subscribe() {
		mData = new ArrayList<>();
	}

	@Override
	public void unSubscribe() {
		mCompositeDisposable.clear();
		mData = null;
	}

	@Override
	public void updateCardsData() {
		mView.showLoadingTipDialog(true);
		mCompositeDisposable.add(mWordbookRepository.getCurrentWordBook()
		                                            .subscribeOn(Schedulers.io())
		                                            .observeOn(Schedulers.io())
		                                            .flatMap(wordbook -> mWordbookRepository.getWordsState(wordbook,
		                                                                                                   WordConstant.STATE_UNKNOWN,
		                                                                                                   mData.size(),
		                                                                                                   UIConstant.LOAD_DATA_CARD_NUMBER))
		                                            .flatMap(mWordRepository::getWords)
		                                            .observeOn(AndroidSchedulers.mainThread())
		                                            .subscribe(
				                                            words -> {
				                                            	mView.showLoadingTipDialog(false);
					                                            mData.addAll(words);
					                                            mView.updateSwipeCardsView(mData);
				                                            }
		                                            ));
	}
}
