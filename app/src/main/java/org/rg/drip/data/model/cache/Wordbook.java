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

	private Integer id;         // 单词本 id
	private Integer userId;     // 用户的 id
	private String md5;         // 单词本的 md5
	
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
	
	public String getMd5() {
		return md5;
	}
	
	public void setMd5(String md5) {
		this.md5 = md5;
	}

	@Override
	public WordbookL convertToLocal() {
		WordbookL wordbook = new WordbookL();
		wordbook.setId(this.getId());
		wordbook.setUserId(this.getUserId());
		wordbook.setMd5(this.getMd5());
		return wordbook;
	}

	@Override
	public WordbookR convertToRemote() {
		WordbookR wordbook = new WordbookR();
		wordbook.setId(this.getId());
		wordbook.setUserId(this.getUserId());
		wordbook.setMd5(this.getMd5());
		return wordbook;
	}
}
