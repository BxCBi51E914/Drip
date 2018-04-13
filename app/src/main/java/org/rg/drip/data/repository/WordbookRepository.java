package org.rg.drip.data.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.reactivestreams.Publisher;
import org.rg.drip.constant.ConfigConstant;
import org.rg.drip.data.contract.WordBookContract;
import org.rg.drip.data.model.cache.User;
import org.rg.drip.data.model.cache.Word;
import org.rg.drip.data.model.cache.WordLink;
import org.rg.drip.data.model.cache.Wordbook;
import org.rg.drip.utils.CheckUtil;
import org.rg.drip.utils.ConfigUtil;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TankGq
 * on 2018/4/13.
 */
public class WordbookRepository implements WordBookContract.Repository {

	@NonNull private static WordBookContract.Remote mWordbookRemoteSource;
	@NonNull private static WordBookContract.Local mWordbookLocalSource;
	@Nullable private static WordBookContract.Repository mInstance = null;

	private Wordbook mCurrentWordbook = null;

	private WordbookRepository(@NonNull WordBookContract.Remote wordbookRemoteSource,
	                           @NonNull WordBookContract.Local wordbookLocalSource) {
		mWordbookRemoteSource = CheckUtil.checkNotNull(wordbookRemoteSource);
		mWordbookLocalSource = CheckUtil.checkNotNull(wordbookLocalSource);
	}

	public static WordBookContract.Repository getInstance(
			                                                     @NonNull WordBookContract.Remote wordbookRemoteSource,
			                                                     @NonNull WordBookContract.Local wordbookLocalSource) {
		if(mInstance == null) {
			mInstance = new WordbookRepository(wordbookRemoteSource, wordbookLocalSource);
		}
		return mInstance;
	}

	public static void destoryInstance() {
		mInstance = null;
	}

	@Override
	public Wordbook getWordbook(int wordbookId) {
		return null;
	}

	@Override
	public Flowable<Wordbook> getCurrentWordBook() {
		Flowable<Wordbook> cache = Flowable.just(mCurrentWordbook)
		                                   .flatMap(wordbook -> {
			                                   if(wordbook == null) {
				                                   return Flowable.empty();
			                                   }
			                                   return Flowable.just(wordbook);
		                                   });
		Wordbook currentWordbook = ConfigUtil.getCurrentWordBook();
		Flowable<Wordbook> local = mWordbookLocalSource.getWordbook(currentWordbook)
		                                               .doOnNext(wordbook -> mCurrentWordbook = wordbook);
		Flowable<Wordbook> remote = mWordbookRemoteSource.getWordbook(currentWordbook).doOnNext(
				wordbook -> mWordbookLocalSource.storeWordbook(wordbook)
				                                .subscribeOn(Schedulers.io())
				                                .observeOn(Schedulers.io())
				                                .subscribe()
		);
		return cache.switchIfEmpty(local)
		            .switchIfEmpty(remote);
	}

	@Override
	public Flowable<Wordbook> getWordbook(Wordbook wordbook) {
		Flowable<Wordbook> cache = Flowable.just(mCurrentWordbook)
		                                   .flatMap(wordbook1 -> {
			                                   if(wordbook == null) {
				                                   return Flowable.empty();
			                                   }
			                                   return Flowable.just(wordbook);
		                                   });
		Wordbook currentWordbook = ConfigUtil.getCurrentWordBook();
		Flowable<Wordbook> local = mWordbookLocalSource.getWordbook(currentWordbook)
		                                               .doOnNext(wordbook2 -> mCurrentWordbook = wordbook);
		Flowable<Wordbook> remote = mWordbookRemoteSource.getWordbook(currentWordbook).doOnNext(
				wordbook3 -> mWordbookLocalSource.storeWordbook(wordbook)
				                                 .subscribeOn(Schedulers.io())
				                                 .observeOn(Schedulers.io())
				                                 .subscribe()
		);
		return cache.switchIfEmpty(local)
		            .switchIfEmpty(remote);
	}

	@Override
	public Flowable<Boolean> checkWordbookExist(Wordbook wordbook) {
		return null;
	}

	@Override
	public Flowable<Wordbook> createByDefault(Wordbook defaultWordbook, User currentUser) {
		return null;
	}

	@Override
	public Flowable<Wordbook> createWordbook(User currentUser) {
		return null;
	}

	@Override
	public Flowable<Integer> insertWords(Wordbook wordbook, List<Word> words) {
		return null;
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
