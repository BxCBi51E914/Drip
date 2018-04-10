package org.rg.drip.data.remote;

import org.rg.drip.data.contract.WordBookContract;
import org.rg.drip.data.model.cache.WordBook;
import org.rg.drip.data.model.cache.WordLink;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by TankGq
 * on 2018/4/10.
 */
public class WordbookRemoteSource implements WordBookContract.Remote {
	
	@Override
	public WordBook getWordbook(int wordbookId) {
		return null;
	}
	
	@Override
	public Flowable<Boolean> createWordbookByDeafult(int wordbookId) {
		return null;
	}
	
	@Override
	public Flowable<Boolean> createWordbook() {
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
	public Flowable<List<WordLink>> getWordBook(int wordbookId) {
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
