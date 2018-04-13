package org.rg.drip.fragment.wordbook;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.rg.drip.R;
import org.rg.drip.base.BaseMainFragment;
import org.rg.drip.constant.MessageConstant;
import org.rg.drip.event.MessageEvent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author : TankGq
 * Time : 03/04/2018
 */
public class WordbookMainFragment extends BaseMainFragment
		implements BaseMainFragment.OnBackToFirstListener {
	
	@BindView(R.id.tv_wordbook_name) TextView mBookNameTv;
	@BindView(R.id.bt_back) Button mCloseChooseWordbookBt;
	@BindView(R.id.bt_choose_wordbook) Button mChooseWordbookBt;
	@OnClick({ R.id.bt_back, R.id.bt_choose_wordbook})
	void onClick(View v) {
		switch(v.getId()) {
			case R.id.bt_choose_wordbook:
				showChooseWordbookBt(false);
				loadChooseWordbookFragment();
				break;
			case R.id.bt_back:
				popToChild(StudyActionFragment.class, false);
				showChooseWordbookBt(true);
				break;
		}
	}

	public static WordbookMainFragment newInstance() {
		WordbookMainFragment fragment = new WordbookMainFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.wordbook_fragment_main;
	}


	@Override
	protected boolean isFirstFragment() {
		return true;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
	}

	@Override
	public void onBackToFirstFragment() {
		_mBackToFirstListener.onBackToFirstFragment();
	}

	@Override
	protected void initViewOnActivityCreated() {
		if(findChildFragment(StudyStateFragment.class) == null) {
			loadFragment();
		}
	}

	private void loadFragment() {
		loadRootFragment(R.id.fragment_wordbook_container_upper, StudyStateFragment.newInstance());
		loadRootFragment(R.id.fragment_wordbook_container_lower, StudyActionFragment.newInstance());
	}
	
	private void loadChooseWordbookFragment() {
		if(findFragment(ChooseWordbookFragment.class) == null) {
			loadRootFragment(R.id.fragment_wordbook_container_whole, ChooseWordbookFragment.newInstance());
		} else {
			popToChild(ChooseWordbookFragment.class, false);
		}
	}

	@Override
	protected void initViewOnLazyInitView() {
	}
	
	
	@Override
	protected boolean registerEventBus() {
		return true;
	}
	
	private void showChooseWordbookBt(boolean bShow) {
		if(bShow) {
			mChooseWordbookBt.setVisibility(View.VISIBLE);
			mCloseChooseWordbookBt.setVisibility(View.INVISIBLE);
		} else {
			mChooseWordbookBt.setVisibility(View.INVISIBLE);
			mCloseChooseWordbookBt.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * 更新 TopBar 中选择单词本和返回按钮的显示和隐藏
	 */
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void updateTopBar(MessageEvent event) {
		if(event.getCode() != MessageConstant.CHOOSE_WORDBOOK_HIDE) {
			return;
		}
		showChooseWordbookBt(true);
	}
	
	/**
	 * 更新当前选择的单词本的名字
	 */
	@Subscribe(threadMode = ThreadMode.MAIN)
	void updateSelectWordbookName(MessageEvent event) {
		if(event.getCode() != MessageConstant.UPDATE_SELECT_WORDBOOK_NAME) {
			return;
		}
	}
	
}
