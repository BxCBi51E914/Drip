package org.rg.drip.data.model.local;

import org.rg.drip.data.contract.ModelContract;
import org.rg.drip.data.model.cache.WordLink;

import io.realm.RealmObject;

/**
 * 记录单词属于哪个单词本, 以及该单词的状态
 * <p>
 * Author : Tank
 * Time : 20/03/2018
 */
public class WordLinkL extends RealmObject implements ModelContract.Cache<WordLink> {
	
	private Integer id;             // 单词 id, 从1开始自增
	private String word;            // 单词
	private String wordBookCode;    // 所属单词本的标识, 格式为 wordbookId + "_" + userId
	private Integer state;          // 状态的 id
	
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
	
	public String getWordBookCode() {
		return wordBookCode;
	}
	
	public void setWordBookCode(String wordBookCode) {
		this.wordBookCode = wordBookCode;
	}
	
	public Integer getState() {
		return state;
	}
	
	public void setState(Integer state) {
		this.state = state;
	}
	
	@Override
	public WordLink convertToCache() {
		WordLink wordLink = new WordLink();
		wordLink.setId(this.getId());
		wordLink.setWord(this.getWord());
		wordLink.setWordBookCode(this.getWordBookCode());
		wordLink.setState(this.getState());
		return wordLink;
	}
}
