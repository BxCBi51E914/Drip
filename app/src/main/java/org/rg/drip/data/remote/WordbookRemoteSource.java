package org.rg.drip.data.remote;

import org.rg.drip.constant.WordbookConstant;
import org.rg.drip.data.contract.WordBookContract;
import org.rg.drip.data.model.cache.WordLink;
import org.rg.drip.data.model.cache.Wordbook;
import org.rg.drip.data.model.remote.WordLinkR;
import org.rg.drip.data.model.remote.WordbookR;
import org.rg.drip.utils.BmobQueryUtil;
import org.rg.drip.utils.BmobUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TankGq
 * on 2018/4/10.
 */
public class WordbookRemoteSource implements WordBookContract.Remote {

	private static WordbookRemoteSource mInstance = null;

	private WordbookRemoteSource getInstance() {
		if(mInstance == null) {
			mInstance = new WordbookRemoteSource();
		}

		return mInstance;
	}

	@Override
	public Flowable<Wordbook> getWordbook(int wordbookId, int userId) {
		// 没找到的话就直接执行 onComplete
		if(wordbookId > WordbookConstant.WORDBOOK_ID_SPLIT &&
		   userId == WordbookConstant.DEFAULT_WORDBOOK_USER_ID) {
			return Flowable.empty();
		}
		if(wordbookId == WordbookConstant.WORDBOOK_ID_SPLIT) {
			return Flowable.empty();
		}
		return Flowable.create(
				emitter ->
						new BmobQueryUtil<WordbookR>()
								.add(new BmobQuery<WordbookR>().addWhereEqualTo(WordbookConstant.ID,
								                                                wordbookId))
								.add(new BmobQuery<WordbookR>().addWhereEqualTo(WordbookConstant.USER_ID,
								                                                userId))
								.compileWithAnd()
								.findObjects(new FindListener<WordbookR>() {
									@Override
									public void done(List<WordbookR> list,
									                 BmobException e) {
										if(e != null) {
											BmobUtil.logBmobErrorInfo(e);
											emitter.onError(e);
											return;
										}
										if(null != list && list.size() != 0) {
											emitter.onNext(list.get(0)
											                   .convertToCache());
										}
										emitter.onComplete();
									}
								}),
				BackpressureStrategy.BUFFER);
	}

	@Override
	public Flowable<Boolean> checkWordbookExist(int wordbookId, int userId) {
		if(wordbookId > WordbookConstant.WORDBOOK_ID_SPLIT &&
		   userId == WordbookConstant.DEFAULT_WORDBOOK_USER_ID) {
			return Flowable.empty();
		}
		if(wordbookId == WordbookConstant.WORDBOOK_ID_SPLIT) {
			return Flowable.empty();
		}

		return Flowable.create(
				emitter ->
						new BmobQueryUtil<WordbookR>()
								.add(new BmobQuery<WordbookR>().addWhereEqualTo(WordbookConstant.ID,
								                                                wordbookId))
								.add(new BmobQuery<WordbookR>().addWhereEqualTo(WordbookConstant.USER_ID,
								                                                userId))
								.compileWithAnd()
								.count(WordbookR.class, new CountListener() {
									@Override
									public void done(Integer integer, BmobException e) {
										if(e != null) {
											BmobUtil.logBmobErrorInfo(e);
											emitter.onError(e);
											return;
										}
										emitter.onNext(null != integer && integer == 0);
										emitter.onComplete();
									}
								})
				, BackpressureStrategy.BUFFER);
	}

	@Override
	public Flowable<Wordbook> createWordbookByDefault(int userId, Wordbook wordbook) {
		return getWordsInWordBook(wordbook.getId(), WordbookConstant.DEFAULT_WORDBOOK_USER_ID)
				       .subscribeOn(Schedulers.io())
				       .observeOn(Schedulers.io())
				       .flatMap(wordLinks -> {
					       if(wordLinks.size() == 0) {
						       return Flowable.empty();
					       }
					       return Flowable.create(emitter -> {
						       WordbookR copyWordbook = wordbook.convertToRemote();
						       copyWordbook.setUserId(userId);
						       int length = wordLinks.size();
						       List<BmobObject> copyWordLinks = new ArrayList<>(length);
						       for(int idx = 0; idx < length; ++ idx) {
							       copyWordLinks.set(idx, wordLinks.get(idx).convertToRemote());
						       }
						       new BmobBatch().insertBatch(Collections.singletonList(copyWordbook))
						                      .insertBatch(copyWordLinks)
						                      .doBatch(new QueryListListener<BatchResult>() {
							                               @Override
							                               public void done(List<BatchResult> list,
							                                                BmobException e) {
								                               if(e != null) {
									                               BmobUtil.logBmobErrorInfo(e);
									                               emitter.onError(e);
									                               return;
								                               }
								                               
							                               }
						                               }
						                      );
					       }, BackpressureStrategy.BUFFER);
				       });
	}

	@Override
	public Flowable<Wordbook> createWordbook() {
		return null;
	}

	@Override
	public Flowable<Boolean> insertWordsIntoWordbook(int wordbookId, List<Integer> wordIds) {
		return null;
	}

	@Override
	public Flowable<Boolean> deleteWordsInWordBook(int wordbookId, List<Integer> wordIds) {
		return null;
	}

	@Override
	public Flowable<Boolean> getWordsStateInWordBook(int wordbookId, List<Integer> wordIds) {
		return null;
	}

	@Override
	public Flowable<Boolean> changeWordsStateInWordBook(int wordbookId,
	                                                    List<Integer> wordIds,
	                                                    int state) {
		return null;
	}

	@Override
	public Flowable<List<WordLink>> getWordsInWordBook(int wordbookId, int userId) {
		return null;
	}

	@Override
	public Flowable<Integer> getWorkBookCount(int wordbookId) {
		return null;
	}

	@Override
	public Flowable<Integer> getWordBookStateCount(int wordbookId, int state) {
		return null;
	}
}
