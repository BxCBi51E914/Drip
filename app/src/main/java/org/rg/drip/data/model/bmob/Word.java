package org.rg.drip.data.model.bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by TankGq
 * on 2018/3/16.
 */
public class Word extends BmobObject {

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

	public void setWord(String words) {
		this.word = word;
	}
}
