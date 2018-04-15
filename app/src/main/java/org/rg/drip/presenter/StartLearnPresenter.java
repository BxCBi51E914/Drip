package org.rg.drip.presenter;

import android.support.annotation.NonNull;

import org.reactivestreams.Publisher;
import org.rg.drip.constant.UIConstant;
import org.rg.drip.constant.WordConstant;
import org.rg.drip.contract.StartLearnContract;
import org.rg.drip.data.contract.WordContract;
import org.rg.drip.data.contract.WordbookContract;
import org.rg.drip.data.model.cache.Word;
import org.rg.drip.data.model.cache.WordLink;
import org.rg.drip.data.model.cache.Wordbook;
import org.rg.drip.data.model.remote.WordR;
import org.rg.drip.utils.BmobUtil;
import org.rg.drip.utils.LoggerUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Author : TankGq
 * Time : 2018/4/14
 */
public class StartLearnPresenter implements StartLearnContract.Presenter {

	@NonNull private final WordbookContract.Repository mWordbookRepository;
	@NonNull private final WordContract.Repository mWordRepository;
	@NonNull private final StartLearnContract.View mView;

	@NonNull private CompositeDisposable mCompositeDisposable;

	private List<Word> mData;

	public StartLearnPresenter(@NonNull WordbookContract.Repository wordbookRepository,
	                           @NonNull WordContract.Repository wordRepository,
	                           @NonNull StartLearnContract.View startLearnView) {
		mWordbookRepository = wordbookRepository;
		mWordRepository = wordRepository;
		mView = startLearnView;

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
