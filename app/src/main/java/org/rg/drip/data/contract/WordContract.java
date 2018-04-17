package org.rg.drip.data.contract;

import org.rg.drip.data.model.cache.Word;
import org.rg.drip.data.model.cache.WordLink;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Author : Tank
 * Time : 08/03/2018
 */
public interface WordContract {

	interface Local {

		/**
		 * 通过 WordLink 查询单词
		 */
		Maybe<List<Word>> getWords(List<WordLink> wordLinks);

		/**
		 * 查询单词
		 */
		Flowable<Word> getWord(String word);

		/**
		 * 保存单词
		 */
		Flowable<Boolean> saveWord(Word word);

		/**
		 * 保存单词
		 */
		Flowable<Boolean> saveWords(List<Word> words);
	}

	interface Remote {

		/**
		 * 通过 WordLink 查询单词
		 */
		Maybe<List<Word>> getWords(List<WordLink> wordLinks);

		/**
		 * 查询单词
		 */
		Flowable<Word> getWord(String word);

		/**
		 * 查找以 startWith 开头的单词列表
		 */
		Flowable<List<Word>> getWords(String startWith);
	}

	interface Repository {

		/**
		 * 通过 WordLink 查询单词
		 */
		Flowable<List<Word>> getWords(List<WordLink> wordLinks);

		interface onTimeoutListener {

			default void onGetWordTimeout() {
			}
		}

		/**
		 * 设置超时处理的监听器
		 */
		default void setOnTimeoutListener(onTimeoutListener listener) {
		}

		/**
		 * 查询单词
		 */
		Flowable<Word> getWord(String word);

		/**
		 * 查找以 startWith 开头的单词列表
		 */
		Flowable<List<Word>> getWords(String startWith);
	}
}
