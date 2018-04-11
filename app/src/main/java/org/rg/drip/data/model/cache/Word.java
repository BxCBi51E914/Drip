package org.rg.drip.data.model.cache;

import org.rg.drip.data.contract.ModelContract;
import org.rg.drip.data.model.local.WordL;
import org.rg.drip.data.model.remote.WordR;

/**
 * 单词
 * <p>
 * Author : Tank
 * Time : 08/03/2018
 */
public class Word implements ModelContract.Local<WordL>, ModelContract.Remote<WordR> {
	
	private Integer id;         // 单词 id, 从1开始自增
	private String word;        // 单词名称
	private String phonetic;    // 音标
	private String explain;     // 中文释义
//	private String definition;  // 单词释义（英文），每行一个释义
//	private String translation; // 单词释义（中文），每行一个释义
//	private String pos;         // 词语位置，用 "/" 分割不同位置
//	private String collins;     // 柯林斯星级
//	private String oxford;      // 是否是牛津三千核心词汇
//	private String tag;         // 字符串标签：zk/中考，gk/高考，cet4/四级 等等标签，空格分割
//	private String bnc;         // 英国国家语料库词频顺序
//	private String frq;         // 当代语料库词频顺序
//	private String exchange;    // 时态复数等变换，使用 "/" 分割不同项目，见后面表格
//	private String detail;      // json 扩展信息，字典形式保存例句（待添加）
//	private String audio;       // 读音音频 url （待添加）
	
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public String getPhonetic() {
		return phonetic;
	}
	
	public void setPhonetic(String phonetic) {
		this.phonetic = phonetic;
	}
	
	public String getExplain() {
		return explain;
	}
	
	public void setExplain(String explain) {
		this.explain = explain;
	}

	@Override
	public WordL convertToLocal() {
		WordL word = new WordL();
		word.setId(this.getId());
		word.setWord(this.getWord());
		word.setPhonetic(this.getPhonetic());
		word.setExplain(this.getExplain());
		return word;
	}

	@Override
	public WordR convertToRemote() {
		WordR word = new WordR();
		word.setId(this.getId());
		word.setWord(this.getWord());
		word.setPhonetic(this.getPhonetic());
		word.setExplain(this.getExplain());
		return word;
	}
}
