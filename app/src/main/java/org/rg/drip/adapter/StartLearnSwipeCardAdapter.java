package org.rg.drip.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.huxq17.swipecardsview.BaseCardAdapter;

import org.rg.drip.R;
import org.rg.drip.constant.UIConstant;
import org.rg.drip.data.model.cache.Word;
import org.rg.drip.utils.CheckUtil;
import org.rg.drip.utils.TypefaceUtil;

import java.util.List;

/**
 * Created by TankGq
 * on 2018/4/10.
 */
public class StartLearnSwipeCardAdapter extends BaseCardAdapter {
	
	private Context context;
	private List<Word> data;
	
	public StartLearnSwipeCardAdapter(Context context, List<Word> data) {
		this.context = context;
		this.data = data;
	}

	public void setData(List<Word> data) {
		this.data = data;
	}
	
	@Override
	public int getCount() {
		if(data == null) {
			return 0;
		}
		return data.size();
	}
	
	@Override
	public int getCardLayoutId() {
		return R.layout.wordbook_fragment_start_learn_card_item;
	}
	
	@Override
	public void onBindData(int position, View cardView) {
		if(null == data || data.size() == 0) {
			return;
		}
		TextView wordTv = cardView.findViewById(R.id.tv_word);
		TextView phoneticTv = cardView.findViewById(R.id.tv_phonetic);
		phoneticTv.setTypeface(TypefaceUtil.TYPE_UNICODE_IPI_AC);
		TextView explainTv = cardView.findViewById(R.id.tv_explain);
		Word word = data.get(CheckUtil.checkElementIndex(position, data.size()));
		wordTv.setText(word.getWord());
		phoneticTv.setText(word.getPhonetic());
		explainTv.setText(word.getExplain());
	}
	
	@Override
	public int getVisibleCardCount() {
		return UIConstant.SWIPE_VISIBLE_CARD_COUNT;
	}
}
