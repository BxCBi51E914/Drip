package org.rg.drip.data.model.local;

import org.rg.drip.data.contract.ModelContract;
import org.rg.drip.data.model.cache.LexicalItem;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by TankGq
 * on 2018/4/16.
 */
public class LexicalItemL extends RealmObject implements ModelContract.Cache<LexicalItem> {
	
	@PrimaryKey
	private Integer id;         // 主键, 从 1 开始自增
	private Integer lexicalId;  // 所属的 Lexical 的 id
	private String key;         // 键
	private String value;       // 值
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getLexicalId() {
		return lexicalId;
	}
	
	public void setLexicalId(Integer lexicalId) {
		this.lexicalId = lexicalId;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public LexicalItem convertToCache() {
		LexicalItem lexicalItem = new LexicalItem();
		lexicalItem.setId(this.getId());
		lexicalItem.setLexicalId(this.getLexicalId());
		lexicalItem.setKey(this.getKey());
		lexicalItem.setValue(this.getValue());
		return lexicalItem;
	}
}
