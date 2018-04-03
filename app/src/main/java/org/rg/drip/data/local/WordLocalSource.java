package org.rg.drip.data.local;

import org.rg.drip.data.contract.WordContract;

/**
 * Author : Tank
 * Time : 08/03/2018
 */

public class WordLocalSource implements WordContract {
	
//	@Override
//	public Flowable<Word> getWord(int id) {
//		return RealmUtil.getInstance()
//		                .where(WordL.class)
//		                .equalTo(WordConstant.ID, id)
//		                .findFirstAsync()
//		                .asFlowable()
//		                .filter(wordL -> wordL.isLoaded())
//		                .map(wordL -> ((WordL) wordL).convertToNormal());
//	}
//
//	@Override
//	public Flowable<List<Word>> getWords(String word) {
//		return RealmUtil.getInstance()
//		                .where(WordL.class)
//		                .beginsWith(WordConstant.WORDBOOK, word)
//		                .findAllAsync()
//		                .asFlowable()
//		                .filter(RealmResults::isLoaded)
//		                .map(wordLs -> {
//			                List<Word> words = new ArrayList<>();
//			                int length = wordLs.size();
//			                for (int i = 0; i < length; ++i) {
//				                if (wordLs.get(i) == null) {
//					                continue;
//				                }
//				                //noinspection ConstantConditions
//				                words.add(wordLs.get(i).convertToNormal());
//			                }
//			                return words;
//		                });
//	}
}
