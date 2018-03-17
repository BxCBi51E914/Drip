package org.rg.drip.data.model.realm;

import org.rg.drip.base.ModelContract;
import org.rg.drip.data.model.Word;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Author : Tank
 * Time : 17/03/2018
 */
public class WordL extends RealmObject implements ModelContract<Word> {

	@PrimaryKey
	private Integer id;         // 单词 id
	private String word;        // 单词

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	@Override
	public Word toNormal() {
		Word word = new Word();
		word.setId(this.getId());
		word.setWord(this.getWord());
		return word;
	}
}
