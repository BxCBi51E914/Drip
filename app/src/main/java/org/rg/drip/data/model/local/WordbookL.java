package org.rg.drip.data.model.local;

import org.rg.drip.data.contract.ModelContract;
import org.rg.drip.data.model.cache.Wordbook;

import io.realm.RealmObject;

/**
 * 单词本
 * <p>
 * Created by TankGq
 * on 2018/3/16.
 */
public class WordbookL extends RealmObject implements ModelContract.Cache<Wordbook> {

	private Integer id;             // 单词本 id, 从1开始自增
	private Integer userId;         // 用户的 id

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
	public Wordbook convertToCache() {
		Wordbook wordbook = new Wordbook();
		wordbook.setId(this.getId());
		wordbook.setUserId(this.getUserId());
		return wordbook;
	}
}
