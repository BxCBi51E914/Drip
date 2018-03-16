package org.rg.drip.data.model;

import java.util.List;

/**
 * 记录单词本的状态
 *
 * Created by TankGq
 * on 2018/3/16.
 */
public class WorkBookState {

	public Integer wordBookLinkId;          // 单词本与用户关联的 id
	public List<Integer> wordIdList;        // 单词本内单词的 id 的数组
	public List<Byte> wordBookStateList;    // 单词本内对应单词的状态的数组

	public Integer getWordBookLinkId() {
		return wordBookLinkId;
	}

	public void setWordBookLinkId(Integer wordBookLinkId) {
		this.wordBookLinkId = wordBookLinkId;
	}

	public List<Integer> getWordIdList() {
		return wordIdList;
	}

	public void setWordIdList(List<Integer> wordIdList) {
		this.wordIdList = wordIdList;
	}

	public List<Byte> getWordBookStateList() {
		return wordBookStateList;
	}

	public void setWordBookStateList(List<Byte> wordBookStateList) {
		this.wordBookStateList = wordBookStateList;
	}
}
