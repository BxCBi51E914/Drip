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
	private Integer wordBookId; // 所属单词本 id
	private String word;        // 单词
	private Byte state;         // 状态的 id
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getWordBookId() {
		return wordBookId;
	}
	
	public void setWordBookId(Integer wordBookId) {
		this.wordBookId = wordBookId;
	}
	
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public Byte getState() {
		return state;
	}
	
	public void setState(Byte state) {
		this.state = state;
	}
	
	@Override
	public Word convertToNormal() {
		Word word = new Word();
		word.setId(this.getId());
		word.setWordBookId(this.getWordBookId());
		word.setWord(this.getWord());
		word.setState(this.getState());
		return word;
	}
}
