package org.rg.drip.data.model.cache;

import org.rg.drip.data.contract.ModelContract;
import org.rg.drip.data.model.local.LexicalItemL;
import org.rg.drip.data.model.remote.LexicalItemR;

/**
 * Created by TankGq
 * on 2018/4/16.
 */
public class LexicalItem implements ModelContract.Local<LexicalItemL>, ModelContract.Remote<LexicalItemR> {
	
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
	public LexicalItemL convertToLocal() {
		LexicalItemL lexicalItem = new LexicalItemL();
		lexicalItem.setId(this.getId());
		lexicalItem.setLexicalId(this.getLexicalId());
		lexicalItem.setKey(this.getKey());
		lexicalItem.setValue(this.getValue());
		return lexicalItem;
	}
	
	@Override
	public LexicalItemR convertToRemote() {
		LexicalItemR lexicalItem = new LexicalItemR();
		lexicalItem.setId(this.getId());
		lexicalItem.setLexicalId(this.getLexicalId());
		lexicalItem.setKey(this.getKey());
		lexicalItem.setValue(this.getValue());
		return lexicalItem;
	}
}
