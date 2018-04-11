package org.rg.drip.data.local;

import org.rg.drip.constant.WordbookConstant;
import org.rg.drip.data.contract.WordBookContract;
import org.rg.drip.data.model.cache.WordLink;
import org.rg.drip.data.model.cache.Wordbook;
import org.rg.drip.data.model.local.WordLinkL;
import org.rg.drip.data.model.local.WordbookL;
import org.rg.drip.utils.CheckUtil;
import org.rg.drip.utils.RealmUtil;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * Author : TankGq
 * Time : 2018/4/12
 */
public class WordbookLocalSource implements WordBookContract.Local {

	private static WordbookLocalSource mInstance = null;

	public static WordbookLocalSource getInstance() {
		if(mInstance == null) {
			mInstance = new WordbookLocalSource();
		}

		return mInstance;
	}

	@Override
	public Flowable<Wordbook> getWordbook(final Wordbook wordbook) {
		if(! CheckUtil.checkWordbook(wordbook)) {
			return Flowable.just(WordbookConstant.nullptr);
		}
		return RealmUtil.getInstance()
		                .where(WordbookL.class)
		                .equalTo(WordbookConstant.FIELD_ID, wordbook.getId())
		                .and()
		                .equalTo(WordbookConstant.FIELD_USER_ID, wordbook.getUserId())
		                .findFirstAsync()
		                .asFlowable()
		                .filter(WordbookL::isLoaded)
		                .map(wordbookL -> ((WordbookL) wordbookL).convertToCache());
	}

	@Override
	public Flowable<Boolean> checkWordbookExist(final Wordbook wordbook) {
		if(! CheckUtil.checkWordbook(wordbook)) {
			return Flowable.just(false);
		}
		return Flowable.create(emitter -> {
			long count = RealmUtil.getInstance()
			                      .where(WordbookL.class)
			                      .equalTo(WordbookConstant.FIELD_ID, wordbook.getId())
			                      .and()
			                      .equalTo(WordbookConstant.FIELD_USER_ID, wordbook.getUserId())
			                      .count();
			emitter.onNext(count == 1);
			emitter.onComplete();
		}, BackpressureStrategy.LATEST);
	}

	@Override
	public Flowable<Boolean> storeWordbook(final Wordbook wordbook) {
		if(! CheckUtil.checkWordbook(wordbook)) {
			return Flowable.just(false);
		}

		return Flowable.create(emitter -> {
			RealmUtil.getInstance().executeTransactionAsync(realm -> {
				WordbookL wordbookL = realm.createObject(WordbookL.class);
				wordbookL.setId(wordbook.getId());
				wordbookL.setUserId(wordbook.getUserId());
				wordbookL.generateCode();
			}, () -> {
				emitter.onNext(true);
				emitter.onComplete();
			}, error -> {
				RealmUtil.logErrorInfo(error);
				emitter.onNext(false);
				emitter.onComplete();
			});
		}, BackpressureStrategy.BUFFER);
	}

	@Override
	public Flowable<Boolean> insertWords(final Wordbook wordbook, final List<WordLink> wordLinks) {
		if(! CheckUtil.checkWordbook(wordbook)) {
			return Flowable.just(false);
		}
		return Flowable.create(emitter -> {
			RealmUtil.getInstance().executeTransactionAsync(realm -> {
				String wordbookCode = wordbook.getCode();
				int size = wordLinks.size();
				WordLinkL wordLink;
				WordLink tmp;
				for(int idx = 0; idx < size; ++ idx) {
					tmp = wordLinks.get(idx);
					wordLink = realm.createObject(WordLinkL.class);
					wordLink.setId(tmp.getId());
					wordLink.setState(tmp.getState());
					wordLink.setWord(tmp.getWord());
					wordLink.setWordBookCode(tmp.getWordBookCode());
				}
			}, () -> {
				// 只会全部成功或者全部失败
				emitter.onNext(true);
				emitter.onComplete();
			}, error -> {
				RealmUtil.logErrorInfo(error);
				emitter.onNext(false);
				emitter.onComplete();
			});
		}, BackpressureStrategy.BUFFER);
	}

	@Override
	public Flowable<Integer> deleteWords(Wordbook wordbook, List<WordLink> wordLinks) {
		return null;
	}

	@Override
	public Flowable<List<WordLink>> getWordsState(Wordbook wordbook,
	                                              int state,
	                                              int skip,
	                                              int limit) {
		return null;
	}

	@Override
	public Flowable<Integer> changeWordsState(Wordbook wordbook,
	                                          List<WordLink> wordLinks,
	                                          int state) {
		return null;
	}

	@Override
	public Flowable<List<WordLink>> getWordsInWordBook(Wordbook wordbook, int count, int skip) {
		return null;
	}

	@Override
	public Flowable<Integer> getWorkBookCount(Wordbook wordbook) {
		return null;
	}

	@Override
	public Flowable<Integer> getStateCount(Wordbook wordbook, int state) {
		return null;
	}
}
