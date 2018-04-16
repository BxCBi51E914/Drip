package org.rg.drip.fragment.wordbook;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.rg.drip.R;
import org.rg.drip.base.BaseSubFragment;
import org.rg.drip.constant.MessageEventConstant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author : TankGq
 * Time : 03/04/2018
 */
public class StudyActionFragment extends BaseSubFragment {

	@BindView(R.id.bt_start_learn) Button mStartLearnBt;
	@BindView(R.id.bt_browse_in_card) Button mBrowseInCardBt;
	@BindView(R.id.bt_word_list) Button mWordListBt;
	@BindView(R.id.bt_review_setting) Button mReviewSettingBt;
	@BindView(R.id.tv_describe) TextView mDescribeTv;

	@OnClick({ R.id.bt_start_learn,
	           R.id.bt_browse_in_card,
	           R.id.bt_word_list,
	           R.id.bt_review_setting })
	void onClick(View v) {
		switch(v.getId()) {
			case R.id.bt_start_learn:
				EventBus.getDefault().post(MessageEventConstant.SHOW_START_LEARN_EVENT);
				break;

			case R.id.bt_browse_in_card:
				EventBus.getDefault().post(MessageEventConstant.SHOW_BROWSE_IN_CARD_EVENT);
				break;

			case R.id.bt_word_list:

				break;

			case R.id.bt_review_setting:

				break;
		}
	}

	public static StudyActionFragment newInstance() {
		StudyActionFragment fragment = new StudyActionFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.wordbook_fragment_study_action;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		mDescribeTv.setText(String.format(getString(R.string.tip_current_target), 999, 999));
	}
}
