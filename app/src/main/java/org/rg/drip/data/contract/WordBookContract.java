package org.rg.drip.data.contract;

import org.rg.drip.data.model.cache.Wordbook;
import org.rg.drip.data.model.cache.WordLink;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by TankGq
 * on 2018/4/10.
 */
public class WordBookContract {
	
	public interface Local {
		
		/**
		 * 存储单词本
		 */
		Flowable<Boolean> storeWordbook(Wordbook wordbookId, List<WordLink> wordLinks);
		
		/**
		 * 更新单词本
		 */
		Flowable<Boolean> updateWordbook(Wordbook wordbook, List<WordLink> wordLinks);
		
		/**
		 * 创建单词本
		 */
		Flowable<Boolean> createWordbook();
		
		/**
		 * 批量添加单词
		 */
		Flowable<Boolean> insertWordsIntoWordbook(int wordbookId, List<Integer> wordIds);
		
		/**
		 * 批量删除单词
		 */
		Flowable<Boolean> deleteWordsInWordBook(int wordbookId, List<Integer> wordIds);
		
		/**
		 * 获得单词本中状态为 state 的单词
		 */
		Flowable<List<WordLink>> getWordsByStateInWordBook(int wordbookId, int state);
		
		/**
		 * 批量获得单词的状态
		 */
		Flowable<Boolean> getWordsStateInWordBook(int wordbookId, List<Integer> wordIds);
		
		/**
		 * 批量修改单词的状态
		 */
		Flowable<Boolean> changeWordsStateInWordBook(int wordbookId,
		                                             List<Integer> wordIds,
		                                             int state);
	}
	
	public interface Remote {
		
		/**
		 * 获得相应单词本
		 */
		Flowable<Wordbook> getWordbook(int wordbookId, int userId);

		/**
		 * 判断单词本是否存在
		 */
		Flowable<Boolean> checkWordbookExist(int wordbookId, int userId);
		
		/**
		 * 通过默认的单词本创建属于自己的单词本
		 */
		Flowable<Wordbook> createWordbookByDefault(int userId, Wordbook wordbook);
		
		/**
		 * 创建单词本
		 */
		Flowable<Wordbook> createWordbook();
		
		/**
		 * 批量添加单词
		 */
		Flowable<Boolean> insertWordsIntoWordbook(int wordbookId, List<Integer> wordIds);
		
		/**
		 * 批量删除单词
		 */
		Flowable<Boolean> deleteWordsInWordBook(int wordbookId, List<Integer> wordIds);
		
		/**
		 * 批量获得单词的状态
		 */
		Flowable<Boolean> getWordsStateInWordBook(int wordbookId, List<Integer> wordIds);
		
		/**
		 * 批量修改单词的状态
		 */
		Flowable<Boolean> changeWordsStateInWordBook(int wordbookId,
		                                             List<Integer> wordIds,
		                                             int state);
		
		/**
		 * 获得单词本
		 */
		Flowable<List<WordLink>> getWordsInWordBook(int wordbookId, int userId);
		
		/**
		 * 获取单词本中有多少单词
		 */
		Flowable<Integer> getWorkBookCount(int wordbookId);
		
		/**
		 * 获得单词本中状态为 state 的个数
		 */
		Flowable<Integer> getWordBookStateCount(int wordbookId, int state);
	}
	
	public interface Repository {

		/**
		 * 获得相应单词本
		 */
		Wordbook getWordbook(int wordbookId);

		/**
		 * 获得当前选择的单词本
		 */
		Wordbook getCurrentWordBook();

		/**
		 * 判断单词本是否存在
		 */
		Flowable<Boolean> checkWordbookExist(int wordbookId, int userId);

		/**
		 * 通过默认的单词本创建属于自己的单词本
		 */
		Flowable<Wordbook> createWordbookByDefault(int userId, Wordbook wordbook);
		
		/**
		 * 设置当前用户选择的单词本
		 */
		void setCurrentWordBookId(Wordbook wordbook);

		/**
		 * 创建单词本
		 */
		Flowable<Boolean> createWordbook();
		
		/**
		 * 批量添加单词
		 */
		Flowable<Boolean> insertWordsIntoWordbook(int wordbookId, List<Integer> wordIds);
		
		/**
		 * 批量删除单词
		 */
		Flowable<Boolean> deleteWordsInWordBook(int wordbookId, List<Integer> wordIds);
		
		/**
		 * 批量获得单词的状态
		 */
		Flowable<Boolean> getWordsStateInWordBook(int wordbookId, List<Integer> wordIds);
		
		/**
		 * 批量修改单词的状态
		 */
		Flowable<Boolean> changeWordsStateInWordBook(int wordbookId,
		                                             List<Integer> wordIds,
		                                             int state);
		
		/**
		 * 获得单词本
		 */
		Flowable<List<WordLink>> getWordBook(int wordbookId);
		
		/**
		 * 获取单词本中有多少单词
		 */
		Flowable<Integer> getWorkBookCount(int wordbookId);
		
		/**
		 * 获得单词本中状态为 state 的个数
		 */
		Flowable<Integer> getWordBookStateCount(int wordbookId, int state);
	}
}
