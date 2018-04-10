package org.rg.drip.contract;

import org.rg.drip.base.BasePresenter;
import org.rg.drip.base.BaseView;
import org.rg.drip.data.model.cache.Word;

import java.util.List;

/**
 * Created by TankGq
 * on 2018/4/10.
 */
public interface StartLearnContract {
	
	interface View extends BaseView<Presenter> {
		
		/**
		 * 更新卡片的数据
		 */
		void updateSwipeCardsView(List<Word> data);
	}
	
	interface Presenter extends BasePresenter {
		
		/**
		 * 获取数据
		 */
		List<Word> getData();
		
		
	}
}
