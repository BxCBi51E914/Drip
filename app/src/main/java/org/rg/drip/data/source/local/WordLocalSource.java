package org.rg.drip.data.source.local;

import org.rg.drip.constant.WordConstant;
import org.rg.drip.data.model.Word;
import org.rg.drip.data.model.realm.WordL;
import org.rg.drip.data.source.contract.WordContract;
import org.rg.drip.utils.RealmUtil;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Author : Tank
 * Time : 08/03/2018
 */

public class WordLocalSource implements WordContract.Local {

	@Override
	public Flowable<Word> getWord(int id) {
//		Realm realm = RealmUtil.getInstance();
//		return realm.where(WordL.class)
//		            .equalTo(WordConstant.ID, id)
//		            .findFirstAsync()
//		            .asFlowable()
//		            .filter(wordL -> wordL.isLoaded())
//					.map(wordL -> (WordL)(wordL));
		return null;
	}

	@Override
	public Flowable<List<Word>> getWords(String word) {
		return null;
	}
}
