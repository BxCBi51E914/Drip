package org.rg.drip.data.model.local;

import org.rg.drip.data.contract.ModelContract;
import org.rg.drip.data.model.cache.Lexical;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by TankGq
 * on 2018/4/16.
 */
public class LexicalL extends RealmObject implements ModelContract.Cache<Lexical> {
	
	@PrimaryKey
	private Integer id;         // 主键, 从 1 开始自增
	private Integer userId;     // 所属的用户的 id
	private String name;        // 名称
	
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
	public Lexical convertToCache() {
		Lexical lexical = new Lexical();
		lexical.setId(this.getId());
		lexical.setUserId(this.getUserId());
		lexical.setName(this.getName());
		return lexical;
	}
}
