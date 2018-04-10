package org.rg.drip.data.model.cache;

import org.rg.drip.data.contract.ModelContract;
import org.rg.drip.data.model.local.WordLinkL;
import org.rg.drip.data.model.remote.WordLinkR;

/**
 * 记录单词属于哪个单词本, 以及该单词的状态
 *
 * Author : Tank
 * Time : 20/03/2018
 */
public class WordLink implements ModelContract.Local<WordLinkL>, ModelContract.Remote<WordLinkR> {

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
	public WordLinkL convertToLocal() {
		WordLinkL wordLink = new WordLinkL();
		wordLink.setId(this.getId());
		wordLink.setWord(this.getWord());
		wordLink.setState(this.getState());
		wordLink.setState(this.getState());
		return wordLink;
	}

	@Override
	public WordLinkR convertToRemote() {
		WordLinkR wordLink = new WordLinkR();
		wordLink.setId(this.getId());
		wordLink.setWord(this.getWord());
		wordLink.setState(this.getState());
		wordLink.setState(this.getState());
		return wordLink;
	}
}
