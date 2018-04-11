package org.rg.drip.data.remote;

import org.reactivestreams.Publisher;
import org.rg.drip.constant.BmobConstant;
import org.rg.drip.constant.WordConstant;
import org.rg.drip.constant.WordLinkConstant;
import org.rg.drip.constant.WordbookConstant;
import org.rg.drip.data.contract.WordBookContract;
import org.rg.drip.data.model.cache.User;
import org.rg.drip.data.model.cache.Word;
import org.rg.drip.data.model.cache.WordLink;
import org.rg.drip.data.model.cache.Wordbook;
import org.rg.drip.data.model.remote.WordLinkR;
import org.rg.drip.data.model.remote.WordbookR;
import org.rg.drip.utils.BmobUtil;

import java.util.ArrayList;
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
import cn.bmob.v3.listener.SaveListener;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
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
	public Flowable<Wordbook> getWordbook(final Wordbook wordbook) {
		if(wordbook.getId() > WordbookConstant.WORDBOOK_ID_SPLIT
		   && wordbook.getUserId() == WordbookConstant.DEFAULT_WORDBOOK_USER_ID) {
			return Flowable.just(WordbookConstant.nullptr);
		}
		if(wordbook.getId() == WordbookConstant.WORDBOOK_ID_SPLIT) {
			return Flowable.just(WordbookConstant.nullptr);
		}
		return Flowable.create(emitter -> {
			new BmobQuery<WordbookR>()
					.addWhereEqualTo(WordbookConstant.ID, wordbook.getId())
					.addWhereEqualTo(WordbookConstant.USER_ID, wordbook.getUserId())
					.findObjects(new FindListener<WordbookR>() {
						@Override
						public void done(List<WordbookR> list, BmobException e) {
							if(e != null) {
								BmobUtil.logBmobErrorInfo(e);
								emitter.onError(e);
								return;
							}
							if(null == list || list.size() == 0) {
								emitter.onNext(WordbookConstant.nullptr);
							} else {
								emitter.onNext(list.get(0).convertToCache());
							}
							emitter.onComplete();
						}
					});
		}, BackpressureStrategy.BUFFER);
	}
	
	@Override
	public Flowable<Boolean> checkWordbookExist(final Wordbook wordbook) {
		if(wordbook.getId() > WordbookConstant.WORDBOOK_ID_SPLIT
		   && wordbook.getUserId() == WordbookConstant.DEFAULT_WORDBOOK_USER_ID) {
			return Flowable.just(false);
		}
		if(wordbook.getId() == WordbookConstant.WORDBOOK_ID_SPLIT) {
			return Flowable.just(false);
		}
		return Flowable.create(emitter -> {
			new BmobQuery<WordbookR>()
					.addWhereEqualTo(WordbookConstant.ID, wordbook.getId())
					.addWhereEqualTo(WordbookConstant.USER_ID, wordbook.getUserId())
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
					});
		}, BackpressureStrategy.BUFFER);
	}
	
	@Override
	public Flowable<Wordbook> createByDefault(final Wordbook defaultWordbook,
	                                          final User currentUser) {
		if(defaultWordbook.getId() > WordbookConstant.WORDBOOK_ID_SPLIT
		   && defaultWordbook.getUserId() == WordbookConstant.DEFAULT_WORDBOOK_USER_ID) {
			return Flowable.just(WordbookConstant.nullptr);
		}
		if(defaultWordbook.getId() == WordbookConstant.WORDBOOK_ID_SPLIT) {
			return Flowable.just(WordbookConstant.nullptr);
		}
		return Flowable.create(emitter -> {
			WordbookR copyWordbook = defaultWordbook.convertToRemote();
			copyWordbook.setUserId(currentUser.getId());
			copyWordbook.save(new SaveListener<String>() {
				@Override
				public void done(String s, BmobException e) {
					if(e != null) {
						BmobUtil.logBmobErrorInfo(e);
						emitter.onError(e);
						return;
					}
					emitter.onNext(copyWordbook.convertToCache());
					emitter.onComplete();
				}
			});
		}, BackpressureStrategy.BUFFER);
	}
	
	@Override
	public Flowable<Wordbook> createWordbook(final User currentUser) {
		return Flowable.create(emitter -> {
			WordbookR wordbook = new WordbookR();
			wordbook.setUserId(currentUser.getId());
			wordbook.save(new SaveListener<String>() {
				@Override
				public void done(String s, BmobException e) {
					if(e != null) {
						BmobUtil.logBmobErrorInfo(e);
						emitter.onError(e);
						return;
					}
					// 执行完后 wordbook 已经被 Bmob 更新了
					emitter.onNext(wordbook.convertToCache());
					emitter.onComplete();
				}
			});
		}, BackpressureStrategy.BUFFER);
	}
	
	/**
	 * 在确保 words.size() <= maxSize 的前提下批量插入数据
	 */
	private Flowable<Integer> insertWords(final Wordbook wordbook,
	                                      final List<Word> words,
	                                      int maxSize) {
		if(words.size() > maxSize) {
			return Flowable.empty();
		}
		int size = words.size();
		List<BmobObject> workLinks = new ArrayList<>(size);
		String wordbookCode = wordbook.getCode();
		WordLinkR wordLink;
		for(int idx = 0; idx < size; ++ idx) {
			wordLink = new WordLinkR();
			wordLink.setState(WordConstant.STATE_UNKNOWN);
			wordLink.setWord(words.get(idx).getWord());
			wordLink.setWordBookCode(wordbookCode);
			workLinks.set(idx, wordLink);
		}
		return Flowable.create(emitter -> {
			new BmobBatch().insertBatch(workLinks).doBatch(new QueryListListener<BatchResult>() {
				@Override
				public void done(List<BatchResult> list, BmobException e) {
					if(e != null) {
						BmobUtil.logBmobErrorInfo(e);
						emitter.onError(e);
						return;
					}
					int size = list.size(), count = 0;
					for(int idx = 0; idx < size; ++ idx) {
						if(list.get(idx).isSuccess()) {
							++ count;
						} else {
							BmobUtil.logBmobErrorInfo(list.get(idx).getError());
						}
					}
					emitter.onNext(count);
					emitter.onComplete();
				}
			});
		}, BackpressureStrategy.BUFFER);
	}
	
	@Override
	public Flowable<Integer> insertWords(final Wordbook wordbook, final List<Word> words) {
		if(wordbook.getId() < WordbookConstant.WORDBOOK_ID_SPLIT
		   || null == words
		   || words.size() == 0) {
			return Flowable.just(0);
		}
		List<List<Word>> data = BmobUtil.split(words, BmobConstant.BATCH_MAX_COUNT);
		
		Flowable<Integer> flowable = insertWords(wordbook,
		                                         data.get(0),
		                                         BmobConstant.BATCH_MAX_COUNT);
		int batchCount = data.size();
		for(int idx = 1; idx < batchCount; ++ idx) {
			flowable = flowable.concatWith(insertWords(wordbook,
			                                           data.get(idx),
			                                           BmobConstant.BATCH_MAX_COUNT));
		}
		return flowable;
	}
	
	/**
	 * 在确保 words.size() <= maxSize 的前提下批量删除数据
	 */
	private Flowable<Integer> deleteWords(final Wordbook wordbook,
	                                      final List<BmobObject> wordLinks,
	                                      int maxSize) {
		if(wordLinks.size() > maxSize) {
			return Flowable.empty();
		}
		return Flowable.create(emitter -> {
			new BmobBatch().insertBatch(wordLinks).doBatch(new QueryListListener<BatchResult>() {
				@Override
				public void done(List<BatchResult> list, BmobException e) {
					if(e != null) {
						BmobUtil.logBmobErrorInfo(e);
						emitter.onError(e);
						return;
					}
					int size = list.size(), count = 0;
					for(int idx = 0; idx < size; ++ idx) {
						if(list.get(idx).isSuccess()) {
							++ count;
						} else {
							BmobUtil.logBmobErrorInfo(list.get(idx).getError());
						}
					}
					emitter.onNext(count);
					emitter.onComplete();
				}
			});
		}, BackpressureStrategy.BUFFER);
	}
	
	@Override
	public Flowable<Integer> deleteWords(final Wordbook wordbook, final List<WordLink> wordLinks) {
		if(wordbook.getId() > WordbookConstant.WORDBOOK_ID_SPLIT
		   && wordbook.getUserId() == WordbookConstant.DEFAULT_WORDBOOK_USER_ID) {
			return Flowable.just(0);
		}
		
		return getWordLinks(wordbook, wordLinks)
				.observeOn(Schedulers.io())
				.subscribeOn(Schedulers.io())
				.flatMap((Function<List<WordLinkR>, Flowable<Integer>>) wordLinkRs -> {
					List<BmobObject> bmobObjects = new ArrayList<>();
					bmobObjects.addAll(wordLinkRs);
					List<List<BmobObject>> data = BmobUtil.split(bmobObjects,
					                                             BmobConstant.BATCH_MAX_COUNT);
					Flowable<Integer> flowable = deleteWords(wordbook,
					                                         data.get(0),
					                                         BmobConstant.BATCH_MAX_COUNT);
					int batchCount = data.size();
					for(int idx = 1; idx < batchCount; ++ idx) {
						flowable = flowable.concatWith(deleteWords(wordbook,
						                                           data.get(idx),
						                                           BmobConstant.BATCH_MAX_COUNT));
					}
					return flowable;
				});
	}
	
	@Override
	public Flowable<List<WordLink>> getWordsState(Wordbook wordbook,
	                                              int state,
	                                              int skip,
	                                              int limit) {
		return null;
	}
	
	
	@Override
	public Flowable<Integer> changeWordsState(Wordbook wordbook, List<Word> words, int state) {
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
	
	private Flowable<List<WordLinkR>> getWordLinks(final Wordbook wordbook,
	                                               final List<String> words,
	                                               final int maxSize) {
		if(words.size() > maxSize) {
			return Flowable.empty();
		}
		return Flowable.create(emitter -> {
			new BmobQuery<WordLinkR>()
					.addWhereEqualTo(WordLinkConstant.WORDBOOK_CODE, wordbook.getCode())
					.addWhereContainedIn(WordLinkConstant.WORD, words)
					.findObjects(new FindListener<WordLinkR>() {
						@Override
						public void done(List<WordLinkR> list, BmobException e) {
							if(e != null) {
								BmobUtil.logBmobErrorInfo(e);
								emitter.onError(e);
								return;
							}
							emitter.onNext(list);
							emitter.onComplete();
						}
					});
		}, BackpressureStrategy.BUFFER);
	}
	
	@Override
	public Flowable<List<WordLinkR>> getWordLinks(final Wordbook wordbook,
	                                              final List<WordLink> wordLinks) {
		if(wordbook.getId() > WordbookConstant.WORDBOOK_ID_SPLIT
		   && wordbook.getUserId() == WordbookConstant.DEFAULT_WORDBOOK_USER_ID) {
			return Flowable.just(new ArrayList<>());
		}
		int size = wordLinks.size();
		List<String> words = new ArrayList<>();
		for(int idx = 0; idx < size; ++ idx) {
			if(wordLinks.get(idx).getState() != WordConstant.STATE_UNKNOWN) {
				words.add(wordLinks.get(idx).getWord());
			}
		}
		
		List<List<String>> data = BmobUtil.split(words, BmobConstant.LIMIT_MAX_COUNT);
		
		Flowable<List<WordLinkR>> flowable = getWordLinks(wordbook,
		                                                  data.get(0),
		                                                  BmobConstant.LIMIT_MAX_COUNT);
		int batchCount = data.size();
		for(int idx = 1; idx < batchCount; ++ idx) {
			flowable = flowable.concatWith(getWordLinks(wordbook,
			                                            data.get(idx),
			                                            BmobConstant.LIMIT_MAX_COUNT));
		}
		return flowable;
	}
}
