package org.rg.drip.data.source.local;

import org.rg.drip.constant.WordConstant;
import org.rg.drip.data.model.Word;
import org.rg.drip.data.model.realm.WordL;
import org.rg.drip.data.source.contract.WordContract;
import org.rg.drip.utils.RealmUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.realm.RealmResults;

/**
 * Author : Tank
 * Time : 08/03/2018
 */

public class WordLocalSource implements WordContract {
	
	@Override
	public Flowable<Word> getWord(int id) {
		return RealmUtil.getInstance()
		                .where(WordL.class)
		                .equalTo(WordConstant.ID, id)
		                .findFirstAsync()
		                .asFlowable()
		                .filter(wordL -> wordL.isLoaded())
		                .map(wordL -> ((WordL) wordL).convertToNormal());
	}
	
	@Override
	public Flowable<List<Word>> getWords(String word) {
		return RealmUtil.getInstance()
		                .where(WordL.class)
		                .beginsWith(WordConstant.WORD, word)
		                .findAllAsync()
		                .asFlowable()
		                .filter(RealmResults::isLoaded)
		                .map(wordLs -> {
			                List<Word> words = new ArrayList<>();
			                int length = wordLs.size();
			                for (int i = 0; i < length; ++i) {
				                if (wordLs.get(i) == null) {
					                continue;
				                }
				                //noinspection ConstantConditions
				                words.add(wordLs.get(i).convertToNormal());
			                }
			                return words;
		                });
	}
}
