package org.rg.drip.data.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.rg.drip.data.contract.WordContract;
import org.rg.drip.data.model.cache.Word;
import org.rg.drip.data.model.cache.WordLink;
import org.rg.drip.utils.CheckUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

/**
 * Author : Tank
 * Time : 08/03/2018
 */
public class WordRepository implements WordContract.Repository {

	@NonNull private static WordContract.Remote mWordRemoteSource;
	@NonNull private static WordContract.Local mWordLocalSource;
	@Nullable private static WordContract.Repository mInstance = null;

	private static HashMap<String, Word> mWordDic = new HashMap<>();

	private WordRepository(@NonNull WordContract.Remote wordRemoteSource,
	                       @NonNull WordContract.Local wordLocalSource) {
		mWordRemoteSource = CheckUtil.checkNotNull(wordRemoteSource);
		mWordLocalSource = CheckUtil.checkNotNull(wordLocalSource);
	}

	public static WordContract.Repository getInstance(@NonNull WordContract.Remote wordRemoteSource,
	                                                  @NonNull WordContract.Local wordLocalSource) {
		if(mInstance == null) {
			mInstance = new WordRepository(wordRemoteSource, wordLocalSource);
		}
		return mInstance;
	}

	public static void destoryInstance() {
		mInstance = null;
	}

	public void storeWords(List<Word> words) {
		int length = words.size();
		for(int idx = 0; idx < length; ++ idx) {
			mWordDic.put(words.get(idx).getWord(), words.get(idx));
		}
	}

	@Override
	public Flowable<List<Word>> getWords(List<WordLink> wordLinks) {
		int size = wordLinks.size();
		// 为了保证返回的结果有序
		final HashMap<String, Integer> needQueryIdxDic = new HashMap<>();
		List<Word> result = new ArrayList<>(size);
		List<WordLink> needQuery = new ArrayList<>(size);
		for(int idx = 0; idx < size; ++ idx) {
			if(mWordDic.containsKey(wordLinks.get(idx).getWord())) {
				result.add(mWordDic.get(wordLinks.get(idx).getWord()));
			} else {
				result.add(null);
				needQueryIdxDic.put(wordLinks.get(idx).getWord(), idx);
				needQuery.add(wordLinks.get(idx));
			}
		}
		Flowable<List<Word>> cache = Flowable.just(result);
		if(needQuery.size() == 0) {
			return cache;
		}
		Maybe<List<Word>> local = mWordLocalSource.getWords(needQuery)
		                                          .doOnSuccess(this::storeWords);
		Maybe<List<Word>> remote = mWordRemoteSource.getWords(needQuery).doOnSuccess(words -> {
			storeWords(words);
			mWordLocalSource.saveWords(words)
			                .subscribeOn(Schedulers.io())
			                .observeOn(Schedulers.io())
			                .subscribe();
		});

		return cache.zipWith(local.concatWith(remote).take(1),
		                     (words, words2) -> {
			                     int length = words2.size();
			                     for(int idx = 0; idx < length; ++ idx) {
				                     words.set(needQueryIdxDic.get(words2.get(idx).getWord()), words2.get(idx));
			                     }
			                     return words;
		                     });
	}

	@Override
	public Flowable<Word> getWord(String word) {
		return null;
	}

	@Override
	public Flowable<List<Word>> getWords(String startWith) {
		return null;
	}
}
