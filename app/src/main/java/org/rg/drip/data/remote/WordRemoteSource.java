package org.rg.drip.data.remote;

import org.rg.drip.constant.BmobConstant;
import org.rg.drip.constant.WordConstant;
import org.rg.drip.data.contract.WordContract;
import org.rg.drip.data.model.cache.Word;
import org.rg.drip.data.model.cache.WordLink;
import org.rg.drip.data.model.remote.WordR;
import org.rg.drip.utils.BmobUtil;
import org.rg.drip.utils.LoggerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.functions.Function;

/**
 * Author : Tank
 * Time : 08/03/2018
 */

public class WordRemoteSource implements WordContract.Remote {

	private static WordRemoteSource mInstance = null;

	public static WordRemoteSource getInstance() {
		if(mInstance == null) {
			mInstance = new WordRemoteSource();
		}

		return mInstance;
	}

	private Flowable<List<Word>> getWordsCombineUnit(final List<String> words) {
		int size = words.size();
		if(size > BmobConstant.BATCH_MAX_COUNT) {
			LoggerUtil.e(size + " > BmobConstant.BATCH_MAX_COUNT");
			return Flowable.empty();
		}
		return Flowable.<List<Word>> create(emitter -> {
			new BmobQuery<WordR>().addWhereContainedIn(WordConstant.FIELD_WORD, words)
			                      .findObjects(new FindListener<WordR>() {
				                      @Override
				                      public void done(List<WordR> list, BmobException e) {
					                      if(e != null) {
						                      BmobUtil.logErrorInfo(e);
						                      emitter.onError(e);
						                      return;
					                      }
					                      int size = list.size();
					                      List<Word> result = new ArrayList<>(size);
					                      for(int idx = 0; idx < size; ++ idx) {
						                      result.add(list.get(idx)
						                                     .convertToCache());
					                      }
					                      emitter.onNext(result);
					                      emitter.onComplete();
				                      }
			                      });
		}, BackpressureStrategy.BUFFER).retryWhen(attempts -> {
			return attempts.flatMap((Function<Throwable, Flowable<?>>) throwable -> {
				LoggerUtil.d("error: " + throwable.getMessage());
				if(throwable.getMessage()
				            .contains("Qps beyond the limit")) {
					return Flowable.just(1)
					               .delay(BmobConstant.QPS_WAIT_TIME, TimeUnit.SECONDS);
				}
				return Flowable.error(throwable);
			});
		});
	}

	private Flowable<List<Word>> getWordsParallelUnit(final List<String> words) {
		int size = words.size();
		if(size > BmobConstant.BATCH_MAX_COUNT * BmobConstant.QPS_MAX_COUNT) {
			LoggerUtil.e(size + " > BmobConstant.BATCH_MAX_COUNT * BmobConstant.QPS_MAX_COUNT");
			return Flowable.empty();
		}
		int maxSize = BmobConstant.QPS_MAX_COUNT;
		List<List<String>> data = BmobUtil.split(words, maxSize);
		int parallelCount = data.size();

		Flowable<List<Word>> zipFlowable = Flowable.just(new ArrayList<>());
		for(int idx = 0; idx < parallelCount; ++ idx) {
			zipFlowable = zipFlowable.zipWith(getWordsCombineUnit(data.get(idx)), (result, result2) -> {
				result.addAll(result2);
				return result;
			});
		}
		// 加入延时操作, 防止网络比较好的情况下造成 QPS 的 error
		return zipFlowable.zipWith(Flowable.just(1)
		                                   .delay(BmobConstant.MIN_TIME_ONCE, TimeUnit.SECONDS),
		                           (result, integer) -> result);
	}

	@Override
	public Maybe<List<Word>> getWords(final List<WordLink> wordLinks) {
		int maxCount = BmobConstant.BATCH_MAX_COUNT * BmobConstant.QPS_MAX_COUNT;
		int size = wordLinks.size();
		List<String> words = new ArrayList<>();
		for(int idx = 0; idx < size; ++ idx) {
			words.add(wordLinks.get(idx)
			                   .getWord());
		}
		List<List<String>> data = BmobUtil.split(words, maxCount);
		Flowable<List<Word>> flowable = Flowable.empty();
		final int size1 = data.size();
		for(int idx = 0; idx < size1; ++ idx) {
			flowable = flowable.concatWith(getWordsParallelUnit(data.get(idx)));
		}

		return flowable.toList()
		               .flatMapMaybe(lists -> {
			               int length = lists.size();
			               int totalSize = 0;
			               for(int idx = 0; idx < length; ++ idx) {
				               totalSize += lists.get(idx)
				                                 .size();
			               }
			               List<Word> result = new ArrayList<>(totalSize);
			               for(int idx = 0; idx < length; ++ idx) {
				               result.addAll(lists.get(idx));
			               }
			               return Maybe.just(result);
		               });
	}

	@Override
	public Flowable<Word> getWord(final String word) {
		return Flowable.create(emitter -> {
			new BmobQuery<WordR>().addWhereEqualTo(WordConstant.FIELD_WORD, word)
			                      .findObjects(new FindListener<WordR>() {
				                      @Override
				                      public void done(List<WordR> list, BmobException e) {
					                      if(e != null) {
						                      BmobUtil.logErrorInfo(e);
						                      emitter.onError(e);
						                      return;
					                      }
					                      if(list.size() > 0) {
						                      emitter.onNext(list.get(0).convertToCache());
					                      }
					                      emitter.onComplete();
				                      }
			                      });
		}, BackpressureStrategy.BUFFER);
	}

	@Override
	public Flowable<List<Word>> getWords(String startWith) {
		return null;
	}
}
