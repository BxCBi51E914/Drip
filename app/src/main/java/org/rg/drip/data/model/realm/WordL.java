package org.rg.drip.data.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.Index;

/**
 * Author : Tank
 * Time : 17/03/2018
 */
public class WordL extends RealmObject {

	private Integer id;         // 单词 id
	@Index
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
}
