package org.rg.drip.data.model.cache;

import org.rg.drip.data.contract.ModelContract;
import org.rg.drip.data.model.local.WordbookL;
import org.rg.drip.data.model.remote.WordbookR;

/**
 * 单词本
 *
 * Created by TankGq
 * on 2018/3/16.
 */
public class Wordbook implements ModelContract.Local<WordbookL>, ModelContract.Remote<WordbookR> {

	private Integer id;         // 单词本 id, 从1开始自增
	private Integer userId;     // 用户的 id
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Override
	public WordbookL convertToLocal() {
		WordbookL wordbook = new WordbookL();
		wordbook.setId(this.getId());
		wordbook.setUserId(this.getUserId());
		return wordbook;
	}

	@Override
	public WordbookR convertToRemote() {
		WordbookR wordbook = new WordbookR();
		wordbook.setId(this.getId());
		wordbook.setUserId(this.getUserId());
		return wordbook;
	}
	
	public String getCode() {
		return id + "_" + userId;
	}
	
	public Wordbook() {
	}
	
	public Wordbook(Integer id, Integer userId) {
		this.id = id;
		this.userId = userId;
	}
}
