package org.rg.drip.data.source.contract;

import org.rg.drip.data.model.Word;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Author : Tank
 * Time : 08/03/2018
 */

public interface WordContract {

	Flowable<Word> getWord(int id);

	Flowable<List<Word>> getWords(String word);


	public interface Local {

		Flowable<Word> getWord(int id);

		Flowable<List<Word>> getWords(String word);
	}
	
	public interface Remote {

		Flowable<Word> getWord(int id);

		Flowable<List<Word>> getWords(String word);
	}
}
