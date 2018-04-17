package org.rg.drip.data.local;

import org.rg.drip.constant.RealmConstant;
import org.rg.drip.constant.WordConstant;
import org.rg.drip.data.contract.WordContract;
import org.rg.drip.data.model.cache.Word;
import org.rg.drip.data.model.cache.WordLink;
import org.rg.drip.data.model.local.WordL;
import org.rg.drip.utils.LoggerUtil;
import org.rg.drip.utils.RealmUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Author : Tank
 * Time : 08/03/2018
 */

public class WordLocalSource implements WordContract.Local {

	private static WordLocalSource mInstance = null;

	public static WordLocalSource getInstance() {
		if(mInstance == null) {
			mInstance = new WordLocalSource();
		}

		return mInstance;
	}

	private Flowable<List<Word>> getWordsCombineUnit(final List<WordLink> wordLinks) {
		final int size = wordLinks.size();
		if(size > RealmConstant.COMBINE_MAX_COUNT) {
			LoggerUtil.e(size + " > RealmConstant.COMBINE_MAX_COUNT");
			return Flowable.empty();
		}
		String[] words = new String[size];
		for(int idx = 0; idx < size; ++ idx) {
			words[idx] = wordLinks.get(idx).getWord();
		}

		return Flowable.create(emitter -> {
			RealmResults<WordL> wordLs = RealmUtil.getInstance().where(WordL.class)
			                                      .in(WordConstant.FIELD_WORD, words)
			                                      .findAll();

			int length = wordLs.size();
			// 如果有的单词没查到, 那就简单一点处理, 直接当成全部没查到
			if(length != size) {
				emitter.onComplete();
				return;
			}
			List<Word> result = new ArrayList<>();
			for(int idx = 0; idx < length; ++ idx) {
				if(wordLs.get(idx) == null) {
					continue;
				}
				result.add(wordLs.get(idx).convertToCache());
			}
			emitter.onNext(result);
			emitter.onComplete();
		}, BackpressureStrategy.BUFFER);
	}

	private Flowable<List<Word>> getWordsParallelUnit(final List<WordLink> wordLinks) {
		int size = wordLinks.size();
		if(size > RealmConstant.COMBINE_MAX_COUNT * RealmConstant.PARALLEL_MAX_COUNT) {
			LoggerUtil.e(size + " > RealmConstant.COMBINE_MAX_COUNT * RealmConstant.PARALLEL_MAX_COUNT");
			return Flowable.empty();
		}
		int maxSize = RealmConstant.COMBINE_MAX_COUNT;
		List<List<WordLink>> data = RealmUtil.split(wordLinks, maxSize);
		int parallelCount = data.size();

		Flowable<List<Word>> flowable = Flowable.just(new ArrayList<>());
		for(int idx = 0; idx < parallelCount; ++ idx) {
			flowable = flowable.zipWith(getWordsCombineUnit(data.get(idx)),
			                            (words, words2) -> {
				                            words.addAll(words2);
				                            return words;
			                            });
		}
		// 这边先简单处理, 如果有一个单词没找到, 那么这边也不会发射值
		return flowable;
	}

	@Override
	public Maybe<List<Word>> getWords(List<WordLink> wordLinks) {
		Flowable<List<Word>> flowable = Flowable.empty();
		int maxCount = RealmConstant.COMBINE_MAX_COUNT * RealmConstant.PARALLEL_MAX_COUNT;
		List<List<WordLink>> data = RealmUtil.split(wordLinks, maxCount);
		final int size = data.size();
		// 如果有一个 zip 没有发射值,
		for(int idx = 0; idx < size; ++ idx) {
			flowable = flowable.concatWith(getWordsParallelUnit(data.get(idx)));
		}
		return flowable.toList().flatMapMaybe(lists -> {
			int length = lists.size();
			if(length != size) {
				return Maybe.empty();
			}
			int totalSize = 0;
			for(int idx = 0; idx < length; ++ idx) {
				totalSize += lists.get(idx).size();
			}
			List<Word> result = new ArrayList<>(totalSize);
			for(int idx = 0; idx < length; ++ idx) {
				result.addAll(lists.get(idx));
			}
			return Maybe.just(result);
		});
	}

	@Override
	public Flowable<Word> getWord(String word) {
		return Flowable.create(emitter -> {
			WordL wordL = RealmUtil.getInstance().where(WordL.class)
			                       .equalTo(WordConstant.FIELD_WORD, word)
			                       .findFirst();
			if(wordL != null) {
				emitter.onNext(wordL.convertToCache());
			}
			emitter.onComplete();
		}, BackpressureStrategy.BUFFER);
	}

	@Override
	public Flowable<Boolean> saveWord(final Word word) {
		return Flowable.create(emitter -> {
			RealmUtil.getInstance().executeTransactionAsync(realm -> {
				WordL wordL = realm.createObject(WordL.class);
				wordL.setWord(word.getWord());
				wordL.setExplain(word.getExplain());
				wordL.setId(word.getId());
				wordL.setPhonetic(word.getPhonetic());
			}, () -> {
				emitter.onNext(true);
				emitter.onComplete();
			}, error -> {
				RealmUtil.logErrorInfo(error);
				emitter.onNext(false);
				emitter.onComplete();
			});
		}, BackpressureStrategy.LATEST);
	}

	@Override
	public Flowable<Boolean> saveWords(final List<Word> words) {
		return Flowable.create(emitter -> {
			try {
				Realm realm = RealmUtil.getInstance();
				realm.beginTransaction();
				int size = words.size();
				for(int idx = 0; idx < size; ++ idx) {
					realm.copyToRealmOrUpdate(words.get(idx).convertToLocal());
				}
				realm.commitTransaction();
			} catch(Error error) {
				RealmUtil.logErrorInfo(error);
				emitter.onError(error);
				return;
			}
			emitter.onNext(true);
			emitter.onComplete();
		}, BackpressureStrategy.LATEST);
	}

}
