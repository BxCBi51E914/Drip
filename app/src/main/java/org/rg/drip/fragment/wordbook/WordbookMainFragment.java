package org.rg.drip.fragment.wordbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIViewHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.rg.drip.R;
import org.rg.drip.base.BaseFragment;
import org.rg.drip.base.BaseMainFragment;
import org.rg.drip.constant.MessageEventConstant;
import org.rg.drip.constant.UIConstant;
import org.rg.drip.event.MessageEvent;
import org.rg.drip.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Author : eee
 * Time : 03/04/2018
 */
public class WordbookMainFragment extends BaseMainFragment
		implements BaseMainFragment.OnBackToFirstListener {

	@BindView(R.id.tv_wordbook_name) TextView mWordbookNameTv;
	@BindView(R.id.bt_back) Button mCloseChooseWordbookBt;
	@BindView(R.id.bt_choose_wordbook) Button mChooseWordbookBt;

	@OnClick({ R.id.bt_back, R.id.bt_choose_wordbook })
	void onClick(View v) {
		switch(v.getId()) {
			case R.id.bt_choose_wordbook:
				showChooseWordbookBt(false);
				loadChooseWordbookFragment();
				break;
			case R.id.bt_back:
				EventBus.getDefault().post(MessageEventConstant.BACK_TO_WORDBOOK_MAIN_EVENT);
//				EventBus.getDefault().post(MessageEventConstant.HIDE_CHOOSE_WORDBOOK_EVENT);
				updateTopBar(MessageEventConstant.HIDE_CHOOSE_WORDBOOK_EVENT);
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
		Fragment chooseWordbookFragment = findChildFragment(ChooseWordbookFragment.class);
		if(chooseWordbookFragment == null) {
			loadRootFragment(R.id.fragment_wordbook_container_whole, ChooseWordbookFragment.newInstance());
		} else {
//			popToChild(ChooseWordbookFragment.class, false);
			QMUIViewHelper.fadeIn(chooseWordbookFragment.getView(),
			                      UIConstant.FADE_ANIMATOR_DURATION,
			                      null,
			                      true);
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
		if(event.getCode() != MessageEventConstant.HIDE_CHOOSE_WORDBOOK) {
			return;
		}
		showChooseWordbookBt(true);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void showStartLearnFragment(MessageEvent event) {
		if(event.getCode() != MessageEventConstant.SHOW_START_LEARN) {
			return;
		}

		Fragment startLearnFragment = findChildFragment(StartLearningFragment.class);
		if(startLearnFragment == null) {
			loadRootFragment(R.id.fragment_wordbook_container_full, StartLearningFragment.newInstance());
		} else {
//			popToChild(StartLearningFragment.class, false);
			QMUIViewHelper.fadeIn(startLearnFragment.getView(),
			                      UIConstant.FADE_ANIMATOR_DURATION,
			                      null,
			                      true);
		}
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void showBrowseInCardFragment(MessageEvent event) {
		if(event.getCode() != MessageEventConstant.SHOW_BROWSE_IN_CARD) {
			return;
		}

		BaseFragment browseInCardFragment = findChildFragment(BrowseInCardFragment.class);
		if(browseInCardFragment == null) {
			loadRootFragment(R.id.fragment_wordbook_container_full, BrowseInCardFragment.newInstance());
		} else {
			if(browseInCardFragment.getView() == null) {
				popToChild(BrowseInCardFragment.class, false);
				ToastUtil.showCustumToast(getContext(), "null");
			} else {
				QMUIViewHelper.fadeIn(browseInCardFragment.getView(),
				                      UIConstant.FADE_ANIMATOR_DURATION,
				                      null,
				                      true);
			}
		}
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void backToWordbookMainFragment(MessageEvent event) {
		if(event.getCode() != MessageEventConstant.BACK_TO_WORDBOOK_MAIN) {
			return;
		}

		popToChild(StudyActionFragment.class, false);
	}

	/**
	 * 更新当前选择的单词本的名字
	 */
	@Subscribe(threadMode = ThreadMode.MAIN)
	void updateSelectWordbookName(MessageEvent event) {
		if(event.getCode() != MessageEventConstant.UPDATE_SELECT_WORDBOOK_NAME) {
			return;
		}

		if(event.getMessage() != null) {
			mWordbookNameTv.setText(event.getMessage());
		} else {
			mWordbookNameTv.setText(getString(R.string.tip_unselected_wordbook));
		}
	}

}
