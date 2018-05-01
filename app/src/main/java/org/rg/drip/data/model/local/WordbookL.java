package org.rg.drip.data.model.local;

import org.rg.drip.data.contract.ModelContract;
import org.rg.drip.data.model.cache.Wordbook;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 单词本
 * <p>
 * Created by eee
 * on 2018/3/16.
 */
public class WordbookL extends RealmObject implements ModelContract.Cache<Wordbook> {

	@PrimaryKey
	private String code;            // 作为主键加快索引
	private Integer id;             // 单词本 id, 从1开始自增
	private Integer userId;         // 用户的 id
	private String name;            // 单词本的名称

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String generateCode() {
		this.setCode(Wordbook.getCode(this.getId(), this.getUserId()));
		return this.getCode();
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
