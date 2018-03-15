package org.rg.drip.data.model;

/**
 * 一个单词对应一条记录有点奢侈，
 * 所以在一个 words 里面存储多个单词
 *
 * Created by TankGq
 * on 2018/3/15.
 */
public class Words {

	private Integer id;

	private String words;

	private Byte type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}
}
