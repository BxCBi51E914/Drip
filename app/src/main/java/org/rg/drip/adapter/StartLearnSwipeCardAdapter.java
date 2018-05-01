package org.rg.drip.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.huxq17.swipecardsview.BaseCardAdapter;

import org.rg.drip.R;
import org.rg.drip.constant.UIConstant;
import org.rg.drip.data.model.cache.Word;
import org.rg.drip.utils.CheckUtil;
import org.rg.drip.utils.LoggerUtil;
import org.rg.drip.utils.Rotatable;
import org.rg.drip.utils.TypefaceUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by eee
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
	
	private SparseBooleanArray mHasRotationY = new SparseBooleanArray();
	
	@Override
	public void onBindData(int position, final View cardView) {
		if(null == data || data.size() == 0) {
			return;
		}
		LoggerUtil.d("cardViewId: %d, hashCode: %d", cardView.getId(), cardView.hashCode());
		FrameLayout container = cardView.findViewById(R.id.container);
		final CardView front = cardView.findViewById(R.id.front);
		final CardView back = cardView.findViewById(R.id.back);
		TextView wordTv = cardView.findViewById(R.id.tv_word);
		TextView phoneticTv = cardView.findViewById(R.id.tv_phonetic);
		TextView wordTvBack = cardView.findViewById(R.id.tv_word_back);
		TextView phoneticTvBack = cardView.findViewById(R.id.tv_phonetic_back);
		TextView explainTvBack = cardView.findViewById(R.id.tv_explain_back);
		phoneticTv.setTypeface(TypefaceUtil.TYPE_UNICODE_IPI_AC);
		phoneticTvBack.setTypeface(TypefaceUtil.TYPE_UNICODE_IPI_AC);
		Word word = data.get(CheckUtil.checkElementIndex(position, data.size()));
		wordTv.setText(word.getWord());
		wordTvBack.setText(word.getWord());
		phoneticTv.setText(word.getPhonetic());
		phoneticTvBack.setText(word.getPhonetic());
		explainTvBack.setText(word.getExplain());
		front.setVisibility(View.VISIBLE);
		back.setVisibility(View.INVISIBLE);
		// 改变视角距离, 贴近屏幕
		container.setCameraDistance(cardView.getResources()
		                                    .getDisplayMetrics().density * 10000);
		final Rotatable rotatable = new Rotatable.Builder(container).sides(R.id.front, R.id.back)
		                                                            .direction(Rotatable.ROTATE_Y)
		                                                            .build();
		final int hashCode = cardView.hashCode();
		if(0 <= mHasRotationY.indexOfKey(hashCode) && mHasRotationY.get(hashCode)) {
			rotatable.rotate(Rotatable.ROTATE_Y, 0);
			mHasRotationY.put(hashCode, false);
		}
		View.OnClickListener onClickListener = v -> {
			if(View.VISIBLE == back.getVisibility()) {
				rotatable.rotate(Rotatable.ROTATE_Y, 0);
				mHasRotationY.put(hashCode, false);
			} else if(View.VISIBLE == front.getVisibility()) {
				rotatable.rotate(Rotatable.ROTATE_Y, 180);
				mHasRotationY.put(hashCode, true);
			}
		};
		front.setOnClickListener(onClickListener);
		back.setOnClickListener(onClickListener);
	}
	
	@Override
	public int getVisibleCardCount() {
		return UIConstant.SWIPE_VISIBLE_CARD_COUNT;
	}
}
