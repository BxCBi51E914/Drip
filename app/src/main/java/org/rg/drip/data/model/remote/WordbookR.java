package org.rg.drip.data.model.remote;

import org.rg.drip.data.contract.ModelContract;
import org.rg.drip.data.model.cache.Wordbook;
import org.rg.drip.data.model.local.WordbookL;

import cn.bmob.v3.BmobObject;

/**
 * 单词本
 *
 * Created by TankGq
 * on 2018/3/16.
 */
public class WordbookR extends BmobObject implements ModelContract.Cache<Wordbook> {

	private Integer id;         // 单词本 id, 从1开始自增
	private Integer userId;     // 用户的 id
	private String name;        // 单词本的名称
	
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public Wordbook convertToCache() {
		Wordbook wordbook = new Wordbook();
		wordbook.setId(this.getId());
		wordbook.setUserId(this.getUserId());
		wordbook.setName(this.getName());
		return wordbook;
	}
}
