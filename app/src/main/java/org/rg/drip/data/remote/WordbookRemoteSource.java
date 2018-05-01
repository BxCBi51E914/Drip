package org.rg.drip.data.remote;

import org.rg.drip.constant.BmobConstant;
import org.rg.drip.constant.WordConstant;
import org.rg.drip.constant.WordLinkConstant;
import org.rg.drip.constant.WordbookConstant;
import org.rg.drip.data.contract.WordbookContract;
import org.rg.drip.data.model.cache.User;
import org.rg.drip.data.model.cache.Word;
import org.rg.drip.data.model.cache.WordLink;
import org.rg.drip.data.model.cache.Wordbook;
import org.rg.drip.data.model.remote.WordLinkR;
import org.rg.drip.data.model.remote.WordbookR;
import org.rg.drip.utils.BmobQueryUtil;
import org.rg.drip.utils.BmobUtil;
import org.rg.drip.utils.CheckUtil;
import org.rg.drip.utils.LoggerUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by eee
 * on 2018/4/10.
 */
public class WordbookRemoteSource implements WordbookContract.Remote {
	
	private static WordbookRemoteSource mInstance = null;
	
	public static WordbookRemoteSource getInstance() {
		if(mInstance == null) {
			mInstance = new WordbookRemoteSource();
		}
		
		return mInstance;
	}
	
	@Override
	public Flowable<List<Wordbook>> getDefaulfWordbook() {
		return Flowable.create(emitter -> {
			new BmobQuery<WordbookR>().addWhereEqualTo(WordbookConstant.FIELD_USER_ID,
			                                           WordbookConstant.DEFAULT_WORDBOOK_USER_ID)
			                          .findObjects(new FindListener<WordbookR>() {
				                          @Override
				                          public void done(List<WordbookR> list, BmobException e) {
					                          if(e != null) {
						                          BmobUtil.logErrorInfo(e);
						                          emitter.onError(e);
						                          return;
					                          }
					                          if(null != list && list.size() != 0) {
						                          int size = list.size();
						                          List<Wordbook> result = new ArrayList<>(size);
						                          for(int idx = 0; idx < size; ++ idx) {
							                          result.add(list.get(idx)
							                                         .convertToCache());
						                          }
						                          emitter.onNext(result);
					                          }
					                          emitter.onComplete();
				                          }
			                          });
		}, BackpressureStrategy.BUFFER);
	}
	
	@Override
	public Flowable<Wordbook> getWordbook(final Wordbook wordbook) {
		if(! CheckUtil.checkWordbook(wordbook)) {
			return Flowable.empty();
		}
		return Flowable.<Wordbook>create(emitter -> {
			new BmobQuery<WordbookR>().addWhereEqualTo(WordbookConstant.FIELD_ID, wordbook.getId())
			                          .addWhereEqualTo(WordbookConstant.FIELD_USER_ID, wordbook.getUserId())
			                          .findObjects(new FindListener<WordbookR>() {
				                          @Override
				                          public void done(List<WordbookR> list, BmobException e) {
					                          if(e != null) {
						                          BmobUtil.logErrorInfo(e);
						                          emitter.onError(e);
						                          return;
					                          }
					                          if(null != list && list.size() != 0) {
						                          emitter.onNext(list.get(0)
						                                             .convertToCache());
					                          }
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
	
	@Override
	public Flowable<Boolean> checkWordbookExist(final Wordbook wordbook) {
		if(! CheckUtil.checkWordbook(wordbook)) {
			return Flowable.just(false);
		}
		return Flowable.create(emitter -> {
			new BmobQuery<WordbookR>().addWhereEqualTo(WordbookConstant.FIELD_ID, wordbook.getId())
			                          .addWhereEqualTo(WordbookConstant.FIELD_USER_ID, wordbook.getUserId())
			                          .count(WordbookR.class, new CountListener() {
				                          @Override
				                          public void done(Integer integer, BmobException e) {
					                          if(e != null) {
						                          BmobUtil.logErrorInfo(e);
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
	public Flowable<Wordbook> createByDefault(final Wordbook defaultWordbook, final User currentUser) {
		if(! CheckUtil.checkWordbook(defaultWordbook, WordbookConstant.TYPE_DEFAULT)) {
			return Flowable.empty();
		}
		return Flowable.create(emitter -> {
			WordbookR copyWordbook = defaultWordbook.convertToRemote();
			copyWordbook.setUserId(currentUser.getId());
			copyWordbook.save(new SaveListener<String>() {
				@Override
				public void done(String s, BmobException e) {
					if(e != null) {
						BmobUtil.logErrorInfo(e);
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
						BmobUtil.logErrorInfo(e);
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
	 * 在确保 words.size() <= BmobConstant.BATCH_MAX_COUNT 的前提下批量插入数据
	 */
	private Flowable<Integer> insertWordsPiece(final Wordbook wordbook, final List<Word> words) {
		if(words.size() > BmobConstant.BATCH_MAX_COUNT) {
			LoggerUtil.e("size > BmobConstant.BATCH_MAX_COUNT, size: " + words.size());
			return Flowable.empty();
		}
		int size = words.size();
		List<BmobObject> workLinks = new ArrayList<>(size);
		String wordbookCode = wordbook.getCode();
		WordLinkR wordLink;
		for(int idx = 0; idx < size; ++ idx) {
			wordLink = new WordLinkR();
			wordLink.setState(WordConstant.STATE_UNKNOWN);
			wordLink.setWord(words.get(idx)
			                      .getWord());
			wordLink.setWordBookCode(wordbookCode);
			workLinks.set(idx, wordLink);
		}
		return Flowable.create(emitter -> {
			new BmobBatch().insertBatch(workLinks)
			               .doBatch(new QueryListListener<BatchResult>() {
				               @Override
				               public void done(List<BatchResult> list, BmobException e) {
					               if(e != null) {
						               BmobUtil.logErrorInfo(e);
						               emitter.onError(e);
						               return;
					               }
					               int size = list.size(), count = 0;
					               for(int idx = 0; idx < size; ++ idx) {
						               if(list.get(idx)
						                      .isSuccess()) {
							               ++ count;
						               } else {
							               BmobUtil.logErrorInfo(list.get(idx)
							                                         .getError());
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
		if(! CheckUtil.checkWordbook(wordbook, WordbookConstant.TYPE_USER_CREATE)) {
			return Flowable.just(0);
		}
		List<List<Word>> data = BmobUtil.split(words, BmobConstant.BATCH_MAX_COUNT);
		
		Flowable<Integer> flowable = insertWordsPiece(wordbook, data.get(0));
		int batchCount = data.size();
		for(int idx = 1; idx < batchCount; ++ idx) {
			flowable = flowable.concatWith(insertWordsPiece(wordbook, data.get(idx)));
		}
		return flowable;
	}
	
	/**
	 * 在确保 words.size() <= BmobConstant.BATCH_MAX_COUNT 的前提下批量删除数据
	 */
	private Flowable<Integer> deleteWordsPiece(final Wordbook wordbook, final List<BmobObject> wordLinks) {
		if(wordLinks.size() > BmobConstant.BATCH_MAX_COUNT) {
			LoggerUtil.e("size > BmobConstant.BATCH_MAX_COUNT, size: " + wordLinks.size());
			return Flowable.empty();
		}
		return Flowable.create(emitter -> {
			new BmobBatch().insertBatch(wordLinks)
			               .doBatch(new QueryListListener<BatchResult>() {
				               @Override
				               public void done(List<BatchResult> list, BmobException e) {
					               if(e != null) {
						               BmobUtil.logErrorInfo(e);
						               emitter.onError(e);
						               return;
					               }
					               int size = list.size(), count = 0;
					               for(int idx = 0; idx < size; ++ idx) {
						               if(list.get(idx)
						                      .isSuccess()) {
							               ++ count;
						               } else {
							               BmobUtil.logErrorInfo(list.get(idx)
							                                         .getError());
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
		if(! CheckUtil.checkWordbook(wordbook, WordbookConstant.TYPE_USER_CREATE)) {
			return Flowable.just(0);
		}
		
		return getWordLinks(wordbook, wordLinks).observeOn(Schedulers.io())
		                                        .subscribeOn(Schedulers.io())
		                                        .flatMap(wordLinkRs -> {
			                                        List<BmobObject> bmobObjects = new ArrayList<>();
			                                        bmobObjects.addAll(wordLinkRs);
			                                        List<List<BmobObject>> data = BmobUtil.split(bmobObjects,
			                                                                                     BmobConstant.BATCH_MAX_COUNT);
			                                        Flowable<Integer> flowable = deleteWordsPiece(wordbook,
			                                                                                      data.get(0));
			                                        int batchCount = data.size();
			                                        for(int idx = 1; idx < batchCount; ++ idx) {
				                                        flowable = flowable.concatWith(deleteWordsPiece(wordbook,
				                                                                                        data.get(idx)));
			                                        }
			                                        return flowable;
		                                        });
	}
	
	@Override
	public Flowable<List<WordLink>> getWordsByState(final Wordbook wordbook,
	                                                final int state,
	                                                final int skip,
	                                                final int limit) {
		if(! CheckUtil.checkWordbook(wordbook)) {
			return Flowable.just(new ArrayList<>());
		}
		return Flowable.<List<WordLink>>create(emitter -> {
			BmobQuery<WordLinkR> query = new BmobQuery<>();
			query.addWhereEqualTo(WordLinkConstant.FIELD_STATE, state);
			if(state == WordConstant.STATE_UNKNOWN) {
				query.addWhereEqualTo(WordLinkConstant.FIELD_WORDBOOK_CODE, wordbook.getDefaultCode())
				     .order(WordLinkConstant.FIELD_ID);
				
			} else {
				query.addWhereEqualTo(WordLinkConstant.FIELD_WORDBOOK_CODE, wordbook.getCode());
			}
			query.setSkip(skip)
			     .setLimit(limit)
			     .findObjects(new FindListener<WordLinkR>() {
				     @Override
				     public void done(List<WordLinkR> list, BmobException e) {
					     if(e != null) {
						     BmobUtil.logErrorInfo(e);
						     emitter.onError(e);
						     return;
					     }
					     int size = list.size();
					     List<WordLink> wordLinks = new ArrayList<>();
					     for(int idx = 0; idx < size; ++ idx) {
						     wordLinks.add(list.get(idx)
						                       .convertToCache());
					     }
					     emitter.onNext(wordLinks);
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
	
	private Flowable<Integer> changeWordsStatePiece(final Wordbook wordbook,
	                                                final List<WordLinkR> wordLinks,
	                                                final int state) {
		if(wordLinks.size() > BmobConstant.BATCH_MAX_COUNT) {
			LoggerUtil.e("size > BmobConstant.BATCH_MAX_COUNT, size: " + wordLinks.size());
			return Flowable.empty();
		}
		WordLinkR wordLinkR, tmp;
		int size = wordLinks.size();
		String wordbookCode = wordbook.getCode();
		final List<BmobObject> updateList = new ArrayList<>();
		final List<BmobObject> insertList = new ArrayList<>();
		
		for(int idx = 0; idx < size; ++ idx) {
			tmp = wordLinks.get(idx);
			wordLinkR = new WordLinkR();
			wordLinkR.setObjectId(tmp.getObjectId());
			wordLinkR.setState(state);
			// 如果是 WordConstant.STATE_UNKNOWN 则表示该单词本使用的是默认单词本中对应的 WordLink
			// 还没为这个单词创建相应 WordLink, 所以这边需要插入数据
			if(wordLinks.get(idx)
			            .getState() == WordConstant.STATE_UNKNOWN) {
				wordLinkR.setWord(tmp.getWord());
				wordLinkR.setWordBookCode(wordbookCode);
				insertList.add(wordLinkR);
			} else {
				updateList.add(wordLinkR);
			}
		}
		return Flowable.create(emitter -> {
			BmobBatch batch = new BmobBatch();
			if(updateList.size() > 0) {
				batch.updateBatch(updateList);
			}
			if(insertList.size() > 0) {
				batch.insertBatch(insertList);
			}
			batch.doBatch(new QueryListListener<BatchResult>() {
				@Override
				public void done(List<BatchResult> list, BmobException e) {
					if(e != null) {
						BmobUtil.logErrorInfo(e);
						emitter.onError(e);
						return;
					}
					int size = list.size(), count = 0;
					for(int idx = 0; idx < size; ++ idx) {
						if(list.get(idx)
						       .isSuccess()) {
							++ count;
						} else {
							BmobUtil.logErrorInfo(list.get(idx)
							                          .getError());
						}
					}
					emitter.onNext(count);
					emitter.onComplete();
				}
			});
		}, BackpressureStrategy.BUFFER);
	}
	
	@Override
	public Single<Integer> changeWordsState(final Wordbook wordbook, final List<WordLink> wordLinks, final int state) {
		if(! CheckUtil.checkWordbook(wordbook, WordbookConstant.TYPE_USER_CREATE) &&
		   ! CheckUtil.checkWordbook(wordbook, WordbookConstant.TYPE_USER_COPY)) {
			return Single.just(0);
		}
		return getWordLinks(wordbook, wordLinks).subscribeOn(Schedulers.io())
		                                        .observeOn(Schedulers.io())
		                                        .flatMap(wordLinkRs -> {
			                                        List<List<WordLinkR>> data = BmobUtil.batchSplit(wordLinkRs);
			                                        Flowable<Integer> flowable = changeWordsStatePiece(wordbook,
			                                                                                           data.get(0),
			                                                                                           state);
			                                        int batchCount = data.size();
			                                        for(int idx = 1; idx < batchCount; ++ idx) {
				                                        flowable = flowable.concatWith(changeWordsStatePiece(wordbook,
				                                                                                             data.get(
						                                                                                             idx),
				                                                                                             state));
			                                        }
			                                        return flowable;
		                                        })
		                                        .toList()
		                                        .map(integers -> {
			                                        int count = 0, size = integers.size();
			                                        for(int idx = 0; idx < size; ++ idx) {
				                                        count += integers.get(idx);
			                                        }
			                                        return count;
		                                        });
	}
	
	/**
	 * 最小的查询模块, 大小的限制为 BmobConstant.LIMIT_MAX_COUNT
	 */
	private Flowable<List<WordLink>> getWordsPiece(final Wordbook wordbook, final int limit, final int skip) {
		if(limit > BmobConstant.LIMIT_MAX_COUNT) {
			LoggerUtil.e(limit + " > BmobConstant.LIMIT_MAX_COUNT");
			return Flowable.empty();
		}
		return Flowable.<List<WordLink>>create(emitter -> {
			                                       new BmobQueryUtil<WordLinkR>().add(new BmobQuery<WordLinkR>().addWhereEqualTo(WordLinkConstant.FIELD_WORDBOOK_CODE,
			                                                                                                                     wordbook.getCode()))
			                                                                     .add(new BmobQuery<WordLinkR>().addWhereEqualTo(WordLinkConstant.FIELD_WORDBOOK_CODE,
			                                                                                                                     wordbook.getDefaultCode()))
			                                                                     .compileWithOr()
			                                                                     .setLimit(limit)
			                                                                     .setSkip(skip)
			                                                                     .findObjects(new FindListener<WordLinkR>() {
				                                                                     @Override
				                                                                     public void done(List<WordLinkR> list, BmobException e) {
					                                                                     if(e != null) {
						                                                                     BmobUtil.logErrorInfo(e);
						                                                                     emitter.onError(e);
						                                                                     return;
					                                                                     }
					                                                                     int size = list.size();
					                                                                     List<WordLink> wordLinks = new ArrayList<>(size);
					                                                                     for(int idx = 0; idx < size; ++ idx) {
						                                                                     wordLinks.add(list.get(idx)
						                                                                                       .convertToCache());
					                                                                     }
					                                                                     emitter.onNext(wordLinks);
					                                                                     emitter.onComplete();
				                                                                     }
			                                                                     });
		                                       },
		                                       BackpressureStrategy.BUFFER).retryWhen(attempts -> attempts.flatMap((Function<Throwable, Flowable<?>>) throwable -> {
			LoggerUtil.d("skip: " + skip + ", error: " + throwable.getMessage());
			if(throwable.getMessage()
			            .contains("Qps beyond the limit")) {
				return Flowable.just(1)
				               .delay(BmobConstant.QPS_WAIT_TIME, TimeUnit.SECONDS);
			}
			return Flowable.error(throwable);
		}));
	}
	
	/**
	 * 次最小的查询模块, count 的限制为 BmobConstant.BATCH_MAX_COUNT * BmobConstant.QPS_MAX_COUNT,
	 * 会将查询分拆成一个个 Flowable, 通过 zipWith 进行组合起来进行并发查询
	 */
	private Flowable<List<WordLink>> getWords(final Wordbook wordbook, final int count, final int skip) {
		if(count > BmobConstant.BATCH_MAX_COUNT * BmobConstant.QPS_MAX_COUNT) {
			LoggerUtil.e(count + " > BmobConstant.BATCH_MAX_COUNT * BmobConstant.QPS_MAX_COUNT");
			return Flowable.empty();
		}
		int zipCount = BmobConstant.QPS_MAX_COUNT;
		final int limit = BmobConstant.BATCH_MAX_COUNT;
		// 因为是 zipWith, 如果这边直接赋值成 Flowable.empty() 的话就会导致 zip 后不发送任何数据
		Flowable<List<WordLink>> zipFlowable = Flowable.just(new ArrayList<>());
		for(int idx = 0; idx < zipCount; ++ idx) {
			final int curSkip = skip + idx * limit;
			// 如果因为 QPS 导致请求失败, 则等待 BmobConstant.QPS_WAIT_TIME 秒后重试
			zipFlowable = zipFlowable.zipWith(getWordsPiece(wordbook, limit, curSkip), (wordLinks, wordLinks2) -> {
				wordLinks.addAll(wordLinks2);
				return wordLinks;
			});
		}
		// 加入延时操作, 防止网络比较好的情况下造成 QPS 的 error
		return zipFlowable.zipWith(Flowable.just(1)
		                                   .delay(BmobConstant.MIN_TIME_ONCE, TimeUnit.SECONDS),
		                           (words, integer) -> words);
	}
	
	/**
	 * count 大小无限制, 会将查询分拆成一次次并发的查询, 然后通过 concatWith 操作符组合
	 */
	private Flowable<List<WordLink>> getWords(final Wordbook wordbook, final int count) {
		Flowable<List<WordLink>> flowable = Flowable.empty();
		final int pieceCount = BmobConstant.BATCH_MAX_COUNT * BmobConstant.QPS_MAX_COUNT;
		int concatCount = (count + pieceCount - 1) / pieceCount;
		for(int idx = 0; idx < concatCount; ++ idx) {
			final int skip = idx * pieceCount;
			flowable = flowable.concatWith(getWords(wordbook, pieceCount, skip));
		}
		
		return flowable;
	}
	
	@Override
	public Flowable<HashMap<String, WordLink>> getWords(final Wordbook wordbook) {
		if(! CheckUtil.checkWordbook(wordbook)) {
			return Flowable.just(WordbookConstant.emptyMap);
		}
		Flowable<Integer> total;
		// 先查询到单词本的总个数
		if(CheckUtil.checkWordbook(wordbook, WordbookConstant.TYPE_USER_COPY)) {
			total = Flowable.create(emitter -> {
				new BmobQueryUtil<WordLinkR>().add(new BmobQuery<WordLinkR>().addWhereEqualTo(WordLinkConstant.FIELD_WORDBOOK_CODE,
				                                                                              wordbook.getCode()))
				                              .add(new BmobQuery<WordLinkR>().addWhereEqualTo(WordLinkConstant.FIELD_WORDBOOK_CODE,
				                                                                              wordbook.getDefaultCode()))
				                              .compileWithOr()
				                              .count(WordLinkR.class, new CountListener() {
					                              @Override
					                              public void done(Integer integer, BmobException e) {
						                              if(e != null) {
							                              BmobUtil.logErrorInfo(e);
							                              emitter.onError(e);
							                              return;
						                              }
						                              emitter.onNext(integer);
						                              emitter.onComplete();
					                              }
				                              });
			}, BackpressureStrategy.BUFFER);
		} else {
			total = getCount(wordbook);
		}
		return total.flatMap(integer -> getWords(wordbook, integer))
		            .map(wordLinks -> {
			            HashMap<String, WordLink> dict = new HashMap<>();
			            int size = wordLinks.size();
			            WordLink wordLink;
			            String word;
			            for(int idx = 0; idx < size; ++ idx) {
				            wordLink = wordLinks.get(idx);
				            word = wordLink.getWord();
				            // 如果已经存了一个状态是 WordConstant.STATE_UNKNOWN 的单词
				            // 且现在有不是这个状态的相同单词, 则表示当前才是正确的数据
				            if(dict.containsKey(wordLink.getWord()) &&
				               (dict.get(word)
				                    .getState() != WordConstant.STATE_UNKNOWN ||
				                wordLink.getState() == WordConstant.STATE_UNKNOWN)) {
					            continue;
				            }
				            dict.put(word, wordLink);
			            }
			            return dict;
		            });
	}
	
	@Override
	public Flowable<Integer> getCount(final Wordbook wordbook) {
		if(! CheckUtil.checkWordbook(wordbook)) {
			return Flowable.just(0);
		}
		return Flowable.create(emitter -> {
			String wordbookCode = null;
			// 如果是用户从默认单词本中拷贝出来的单词本, 只需要查询下原来的那个默认单词本中的单词个数就好了
			if(CheckUtil.checkWordbook(wordbook, WordbookConstant.TYPE_USER_COPY)) {
				wordbookCode = wordbook.getDefaultCode();
			} else {
				wordbookCode = wordbook.getCode();
			}
			new BmobQuery<WordLinkR>().addWhereEqualTo(WordLinkConstant.FIELD_WORDBOOK_CODE, wordbookCode)
			                          .count(WordLinkR.class, new CountListener() {
				                          @Override
				                          public void done(Integer integer, BmobException e) {
					                          if(e != null) {
						                          BmobUtil.logErrorInfo(e);
						                          emitter.onError(e);
						                          return;
					                          }
					                          emitter.onNext(integer);
					                          emitter.onComplete();
				                          }
			                          });
		}, BackpressureStrategy.BUFFER);
	}
	
	@Override
	public Flowable<Integer> getStateCount(final Wordbook wordbook, final int state) {
		if(! CheckUtil.checkWordbook(wordbook)) {
			return Flowable.just(0);
		}
		if(CheckUtil.checkWordbook(wordbook, WordbookConstant.TYPE_DEFAULT) && state != WordConstant.STATE_UNKNOWN) {
			return Flowable.just(0);
		}
		final boolean isUserCopyType = CheckUtil.checkWordbook(wordbook, WordbookConstant.TYPE_USER_COPY);
		// 如果是用户从默认单词本中拷贝出来的单词本, 而且查询的未知状态,
		// 那么就先计算单词总个数, 然后扣去状态为非未知状态的单词个数
		if(isUserCopyType && state == WordConstant.STATE_UNKNOWN) {
			final Wordbook defaultWordbook = new Wordbook(wordbook.getId(), WordbookConstant.DEFAULT_WORDBOOK_USER_ID);
			return getCount(defaultWordbook).zipWith(Flowable.create(emitter -> {
				                                         new BmobQuery<WordLinkR>().addWhereEqualTo(WordLinkConstant.FIELD_WORDBOOK_CODE,
				                                                                                    defaultWordbook.getCode())
				                                                                   .addWhereNotEqualTo(WordLinkConstant.FIELD_STATE, state)
				                                                                   .count(WordLinkR.class, new CountListener() {
					                                                                   @Override
					                                                                   public void done(Integer integer, BmobException e) {
						                                                                   if(e != null) {
							                                                                   BmobUtil.logErrorInfo(e);
							                                                                   emitter.onError(e);
							                                                                   return;
						                                                                   }
						                                                                   emitter.onNext(integer);
						                                                                   emitter.onComplete();
					                                                                   }
				                                                                   });
			                                         }, BackpressureStrategy.BUFFER),
			                                         (BiFunction<Integer, Integer, Integer>) (total, noEqual) -> total -
			                                                                                                     noEqual);
		}
		return Flowable.create(emitter -> {
			new BmobQuery<WordLinkR>().addWhereEqualTo(WordLinkConstant.FIELD_WORDBOOK_CODE, wordbook.getCode())
			                          .addWhereEqualTo(WordLinkConstant.FIELD_STATE, state)
			                          .count(WordLinkR.class, new CountListener() {
				                          @Override
				                          public void done(Integer integer, BmobException e) {
					                          if(e != null) {
						                          BmobUtil.logErrorInfo(e);
						                          emitter.onError(e);
						                          return;
					                          }
					                          emitter.onNext(integer);
					                          emitter.onComplete();
				                          }
			                          });
		}, BackpressureStrategy.BUFFER);
	}
	
	private Flowable<List<WordLinkR>> getWordLinksPiece(final Wordbook wordbook, final List<String> words) {
		if(words.size() > BmobConstant.BATCH_MAX_COUNT) {
			LoggerUtil.e("size > BmobConstant.BATCH_MAX_COUNT, size: " + words.size());
			return Flowable.empty();
		}
		return Flowable.create(emitter -> {
			new BmobQuery<WordLinkR>().addWhereEqualTo(WordLinkConstant.FIELD_WORDBOOK_CODE, wordbook.getCode())
			                          .addWhereContainedIn(WordLinkConstant.FIELD_WORD, words)
			                          .findObjects(new FindListener<WordLinkR>() {
				                          @Override
				                          public void done(List<WordLinkR> list, BmobException e) {
					                          if(e != null) {
						                          BmobUtil.logErrorInfo(e);
						                          emitter.onError(e);
						                          return;
					                          }
					                          emitter.onNext(list);
					                          emitter.onComplete();
				                          }
			                          });
		}, BackpressureStrategy.BUFFER);
	}
	
	/**
	 * 获得单词对应的 WordLinkR, 主要是为了获得 objectId 给 Bmob 用
	 */
	private Flowable<List<WordLinkR>> getWordLinks(final Wordbook wordbook, final List<WordLink> wordLinks) {
		if(! CheckUtil.checkWordbook(wordbook)) {
			return Flowable.just(new ArrayList<>());
		}
		int size = wordLinks.size();
		List<String> words = new ArrayList<>();
		for(int idx = 0; idx < size; ++ idx) {
			if(wordLinks.get(idx)
			            .getState() != WordConstant.STATE_UNKNOWN) {
				words.add(wordLinks.get(idx)
				                   .getWord());
			}
		}
		
		List<List<String>> data = BmobUtil.split(words, BmobConstant.LIMIT_MAX_COUNT);
		
		Flowable<List<WordLinkR>> flowable = getWordLinksPiece(wordbook, data.get(0));
		int batchCount = data.size();
		for(int idx = 1; idx < batchCount; ++ idx) {
			flowable = flowable.concatWith(getWordLinksPiece(wordbook, data.get(idx)));
		}
		return flowable;
	}
}
