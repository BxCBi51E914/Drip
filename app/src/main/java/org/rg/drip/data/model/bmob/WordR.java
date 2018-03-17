package org.rg.drip.data.model.bmob;

import org.rg.drip.base.ModelContract;
import org.rg.drip.data.model.Word;

import cn.bmob.v3.BmobObject;

/**
 * Created by TankGq
 * on 2018/3/16.
 */
public class WordR extends BmobObject implements ModelContract<Word> {

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
