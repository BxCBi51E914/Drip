package org.rg.drip.data.model;

/**
 * 记录单词本属于哪个用户
 *
 * Created by TankGq
 * on 2018/3/16.
 */
public class WordBookLink {

	public Integer id;              // 单词本与用户关联的 id
	public Integer userId;          // 用户的 id
	public Integer wordBookId;      // 单词本的 id

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

	public Integer getWordBookId() {
		return wordBookId;
	}

	public void setWordBookId(Integer wordBookId) {
		this.wordBookId = wordBookId;
	}
}
