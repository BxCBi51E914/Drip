package org.rg.drip.entity;

/**
 * 单词本
 *
 * Created by TankGq
 * on 2018/3/16.
 */
public class WordBook {

	public int id;              // 单词本 id
	public int userId;          // 用户的 id

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
