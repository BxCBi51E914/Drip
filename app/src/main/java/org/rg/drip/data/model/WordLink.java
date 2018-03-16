package org.rg.drip.data.model;

/**
 * 记录单词属于哪个单词本
 *
 * Created by TankGq
 * on 2018/3/16.
 */
public class WordLink {

	public Integer wordId;          // 单词 id
	public Integer wordBookId;      // 单词本 id

	public Integer getWordId() {
		return wordId;
	}

	public void setWordId(Integer wordId) {
		this.wordId = wordId;
	}

	public Integer getWordBookId() {
		return wordBookId;
	}

	public void setWordBookId(Integer wordBookId) {
		this.wordBookId = wordBookId;
	}
}
