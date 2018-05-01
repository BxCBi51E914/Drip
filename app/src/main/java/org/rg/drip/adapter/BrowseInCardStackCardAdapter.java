package org.rg.drip.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;
import com.qmuiteam.qmui.util.QMUIDirection;
import com.qmuiteam.qmui.util.QMUIViewHelper;

import org.rg.drip.R;
import org.rg.drip.constant.UIConstant;
import org.rg.drip.data.model.cache.Word;
import org.rg.drip.utils.TypefaceUtil;

/**
 * Author : eee
 * Time : 2018/4/15
 */
public class BrowseInCardStackCardAdapter extends StackAdapter<Word> {


	public BrowseInCardStackCardAdapter(Context context) {
		super(context);
	}

	@Override
	public void bindView(Word data, int position, CardStackView.ViewHolder holder) {
		if(holder instanceof ColorItemViewHolder) {
			ColorItemViewHolder h = (ColorItemViewHolder) holder;
			h.onBind(data, position);
		}
	}

	@Override
	protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
		return new ColorItemViewHolder(getLayoutInflater().inflate(R.layout.wordbook_fragment_browse_in_card_card_item,
		                                                           parent,
		                                                           false));
	}

	static class ColorItemViewHolder extends CardStackView.ViewHolder {
		private View mLayout;
		private View mContainerContent;
		TextView mTextTitle;
		private TextView mWordTv;
		private TextView mPhoneticTv;
		private TextView mExplainTv;

		public ColorItemViewHolder(View view) {
			super(view);
			mLayout = view.findViewById(R.id.frame_list_card_item);
			mContainerContent = view.findViewById(R.id.container_list_content);
			mTextTitle = view.findViewById(R.id.text_list_card_title);
			mWordTv = view.findViewById(R.id.tv_word);
			mPhoneticTv = view.findViewById(R.id.tv_phonetic);
			mExplainTv = view.findViewById(R.id.tv_explain);
		}

		@Override
		public void onItemExpand(boolean expend) {
			if(expend) {
				QMUIViewHelper.slideIn(mContainerContent,
				                       UIConstant.SLIDE_ANIMATOR_DURATION,
				                       null,
				                       true,
				                       QMUIDirection.BOTTOM_TO_TOP);
			} else {
				QMUIViewHelper.slideOut(mContainerContent,
				                        UIConstant.SLIDE_ANIMATOR_DURATION,
				                        null,
				                        true,
				                        QMUIDirection.TOP_TO_BOTTOM);
			}
		}

		public void onBind(Word data, int position) {
			mLayout.getBackground().setColorFilter(UIConstant.getCardColor(position), PorterDuff.Mode.SRC_IN);
			mTextTitle.setText(data.getWord());
			mWordTv.setText(data.getWord());
			mPhoneticTv.setText(data.getPhonetic());
			mPhoneticTv.setTypeface(TypefaceUtil.TYPE_UNICODE_IPI_AC);
			mExplainTv.setText(data.getExplain());
		}
	}
}
