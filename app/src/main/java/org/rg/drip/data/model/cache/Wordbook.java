package org.rg.drip.data.model.cache;

import org.rg.drip.constant.WordbookConstant;
import org.rg.drip.data.contract.ModelContract;
import org.rg.drip.data.model.local.WordbookL;
import org.rg.drip.data.model.remote.WordbookR;
import org.rg.drip.utils.CheckUtil;

/**
 * 单词本
 * <p>
 * Created by eee
 * on 2018/3/16.
 */
public class Wordbook implements ModelContract.Local<WordbookL>, ModelContract.Remote<WordbookR> {

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
	public WordbookL convertToLocal() {
		WordbookL wordbook = new WordbookL();
		wordbook.setId(this.getId());
		wordbook.setUserId(this.getUserId());
		wordbook.setName(this.getName());
		return wordbook;
	}

	@Override
	public WordbookR convertToRemote() {
		WordbookR wordbook = new WordbookR();
		wordbook.setId(this.getId());
		wordbook.setUserId(this.getUserId());
		wordbook.setName(this.getName());
		return wordbook;
	}

	public static String getCode(int id, int userId) {
		return id + "_" + userId;
	}

	public String getCode() {
		return getCode(this.getId(), this.getUserId());
	}

	public static Wordbook ofCode(String code) {
		String[] arr = code.split("_");
		if(arr.length != 2) {
			return null;
		}
		Integer id, userId;
		try {
			id = Integer.parseInt(arr[0]);
			userId = Integer.parseInt(arr[1]);
		} catch(Error error) {
			return null;
		}
		Wordbook wordbook = new Wordbook(id, userId);
		if(! CheckUtil.checkWordbook(wordbook)) {
			return null;
		}
		return wordbook;
	}

	public String getDefaultCode() {
		return getCode(this.getId(), WordbookConstant.DEFAULT_WORDBOOK_USER_ID);
	}

	public Wordbook() {
	}

	public Wordbook(Integer id, Integer userId) {
		this.id = id;
		this.userId = userId;
	}
}
