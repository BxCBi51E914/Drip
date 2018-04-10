package org.rg.drip.data.model.cache;

/**
 * 单词本
 *
 * Created by TankGq
 * on 2018/3/16.
 */
public class WordBook {

	public Integer id;              // 单词本 id
	public Integer userId;          // 用户的 id
	public String md5;              // 单词本的 md5
	
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
}
