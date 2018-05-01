package org.rg.drip.data.contract;

import org.rg.drip.data.model.cache.User;
import org.rg.drip.data.model.cache.Word;
import org.rg.drip.data.model.cache.Wordbook;
import org.rg.drip.data.model.cache.WordLink;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by eee
 * on 2018/4/10.
 */
public interface WordbookContract {

	interface Local {

		/**
		 * 获得相应单词本
		 */
		Flowable<Wordbook> getWordbook(Wordbook wordbook);

		/**
		 * 判断单词本是否存在
		 */
		Flowable<Boolean> checkWordbookExist(Wordbook wordbook);

		/**
		 * 存储单词本
		 */
		Flowable<Boolean> storeWordbook(Wordbook wordbook);

		/**
		 * 批量添加单词
		 */
		Flowable<Boolean> insertWords(Wordbook wordbook, List<WordLink> wordLinks);

		/**
		 * 批量删除单词
		 */
		Flowable<Boolean> deleteWords(Wordbook wordbook, List<WordLink> wordLinks);

		/**
		 * 批量获得相应状态的单词, 分页的方式
		 */
		Flowable<List<WordLink>> getWordsState(Wordbook wordbook, int state, int skip, int limit);

		/**
		 * 批量修改单词的状态
		 */
		Flowable<Integer> changeWordsState(Wordbook wordbook, List<WordLink> wordLinks, int state);

		/**
		 * 获得单词本, 分页的方式
		 */
		Flowable<List<WordLink>> getWordsInWordBook(Wordbook wordbook, int count, int skip);

		/**
		 * 获取单词本中有多少单词
		 */
		Flowable<Integer> getWorkBookCount(Wordbook wordbook);

		/**
		 * 获得单词本中状态为 state 的个数
		 */
		Flowable<Integer> getStateCount(Wordbook wordbook, int state);
	}

	interface Remote {
		
		/**
		 * 获得所有默认单词本
		 */
		Flowable<List<Wordbook>> getDefaulfWordbook();

		/**
		 * 获得相应单词本
		 */
		Flowable<Wordbook> getWordbook(Wordbook wordbook);

		/**
		 * 判断单词本是否存在
		 */
		Flowable<Boolean> checkWordbookExist(Wordbook wordbook);

		/**
		 * 通过默认的单词本创建属于自己的单词本
		 */
		Flowable<Wordbook> createByDefault(Wordbook defaultWordbook, User currentUser);

		/**
		 * 创建单词本
		 */
		Flowable<Wordbook> createWordbook(User currentUser);

		/**
		 * 批量添加单词
		 */
		Flowable<Integer> insertWords(Wordbook wordbook, List<Word> words);

		/**
		 * 批量删除单词
		 */
		Flowable<Integer> deleteWords(Wordbook wordbook, List<WordLink> wordLinks);

		/**
		 * 批量获得相应状态的单词, 分页的方式
		 */
		Flowable<List<WordLink>> getWordsByState(Wordbook wordbook, int state, int skip, int limit);

		/**
		 * 批量修改单词的状态
		 */
		Single<Integer> changeWordsState(Wordbook wordbook, List<WordLink> wordLinks, int state);

		/**
		 * 获得单词本
		 */
		Flowable<HashMap<String, WordLink>> getWords(Wordbook wordbook);

		/**
		 * 获取单词本中有多少单词
		 */
		Flowable<Integer> getCount(Wordbook wordbook);

		/**
		 * 获得单词本中状态为 state 的个数
		 */
		Flowable<Integer> getStateCount(Wordbook wordbook, int state);
	}

	interface Repository {
		
		/**
		 * 获得所有默认单词本
		 */
		Flowable<List<Wordbook>> getDefaultWordbook();
		
		/**
		 * 获得当前选择的单词本
		 */
		Flowable<Wordbook> getCurrentWordBook();

		/**
		 * 获得相应单词本
		 */
		Flowable<Wordbook> getWordbook(Wordbook wordbook);

		/**
		 * 判断单词本是否存在
		 */
		Flowable<Boolean> checkWordbookExist(Wordbook wordbook);

		/**
		 * 通过默认的单词本创建属于自己的单词本
		 */
		Flowable<Wordbook> createByDefault(Wordbook defaultWordbook, User currentUser);

		/**
		 * 创建单词本
		 */
		Flowable<Wordbook> createWordbook(User currentUser);

		/**
		 * 批量添加单词
		 */
		Flowable<Integer> insertWords(Wordbook wordbook, List<Word> words);

		/**
		 * 批量删除单词
		 */
		Flowable<Integer> deleteWords(Wordbook wordbook, List<WordLink> wordLinks);

		/**
		 * 批量获得相应状态的单词, 分页的方式
		 */
		Flowable<List<WordLink>> getWordsState(Wordbook wordbook, int state, int skip, int limit);

		/**
		 * 批量修改单词的状态
		 */
		Flowable<Integer> changeWordsState(Wordbook wordbook, List<WordLink> wordLinks, int state);

		/**
		 * 获得单词本, 分页的方式
		 */
		Flowable<List<WordLink>> getWordsInWordBook(Wordbook wordbook, int count, int skip);

		/**
		 * 获取单词本中有多少单词
		 */
		Flowable<Integer> getWorkBookCount(Wordbook wordbook);

		/**
		 * 获得单词本中状态为 state 的个数
		 */
		Flowable<Integer> getStateCount(Wordbook wordbook, int state);
	}
}
