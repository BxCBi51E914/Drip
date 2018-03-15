package org.rg.drip.data.model.bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by TankGq
 * on 2018/3/15.
 */

public class Words extends BmobObject {

	private Integer id;

	private String words;

	private Byte type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}
}
