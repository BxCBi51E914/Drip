package org.rg.drip.data.model;

/**
 * 单词 (没有详细信息)
 *
 * Author : Tank
 * Time : 08/03/2018
 */
public class Word {

	private int id;             // 单词 id
	private int wordBookId;     // 所属单词本 id
	private String word;        // 单词
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
