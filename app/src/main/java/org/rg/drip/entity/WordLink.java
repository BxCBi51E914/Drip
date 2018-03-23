package org.rg.drip.entity;

/**
 * 记录单词属于哪个单词本, 以及该单词的状态
 *
 * Author : Tank
 * Time : 20/03/2018
 */
public class WordLink {

	private int id;             // 单词 id
	private String word;        // 单词
	private int wordBookId;     // 所属单词本 id
	private byte state;         // 状态的 id

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getWordBookId() {
		return wordBookId;
	}

	public void setWordBookId(int wordBookId) {
		this.wordBookId = wordBookId;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public byte getState() {
		return state;
	}

	public void setState(byte state) {
		this.state = state;
	}
}
