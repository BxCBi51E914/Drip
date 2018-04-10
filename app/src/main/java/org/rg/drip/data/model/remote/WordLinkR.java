package org.rg.drip.data.model.remote;

import org.rg.drip.data.contract.ModelContract;
import org.rg.drip.data.model.cache.WordLink;

import cn.bmob.v3.BmobObject;

/**
 * 记录单词属于哪个单词本, 以及该单词的状态
 * <p>
 * Author : Tank
 * Time : 20/03/2018
 */
public class WordLinkR extends BmobObject implements ModelContract.Cache<WordLink> {

	private Integer id;             // 单词 id
	private String word;            // 单词
	private Integer wordBookId;     // 所属单词本 id
	private Byte state;             // 状态的 id

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Integer getWordBookId() {
		return wordBookId;
	}

	public void setWordBookId(Integer wordBookId) {
		this.wordBookId = wordBookId;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	@Override
	public WordLink convertToCache() {
		WordLink wordLink = new WordLink();
		wordLink.setId(this.getId());
		wordLink.setWord(this.getWord());
		wordLink.setState(this.getState());
		wordLink.setState(this.getState());
		return wordLink;
	}
}
