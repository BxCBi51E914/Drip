package org.rg.drip.fragment.lexical;

import android.os.Bundle;

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
import org.rg.drip.fragment.wordbook.BrowseInCardFragment;

/**
 * Created by TankGq
 * on 2018/3/20.
 */
public class LexicalMainFragment extends BaseMainFragment implements BaseMainFragment.OnBackToFirstListener {
	
	public static LexicalMainFragment newInstance() {
		
		Bundle args = new Bundle();
		
		LexicalMainFragment fragment = new LexicalMainFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	protected int getContentViewLayoutID() {
		return R.layout.fragment_custom_main;
	}
	
	@Override
	protected void initView(Bundle savedInstanceState) {
	
	}
	
	@Override
	protected void initViewOnActivityCreated() {
		if(findChildFragment(LexicalListFragment.class) == null) {
			loadRootFragment(R.id.fragment_lexical_container, LexicalListFragment.newInstance());
		}
	}
	
	@Override
	protected void initViewOnLazyInitView() {
		// 这里可以不用懒加载,因为Adapter的场景下,Adapter内的子Fragment只有在父Fragment是show状态时,才会被Attach,Create
	}
	
	@Override
	public void onBackToFirstFragment() {
		_mBackToFirstListener.onBackToFirstFragment();
	}
	
	@Override
	protected boolean registerEventBus() {
		return true;
	}
	
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void showCreateLexivalItem(MessageEvent event) {
		if(event.getCode() != MessageEventConstant.SHOW_CREATE_LEXICAL_ITEM) {
			return;
		}
		
		BaseFragment createLexicalItemFragment = findChildFragment(CreateLexicalItemFragment.class);
		if(createLexicalItemFragment == null) {
			loadRootFragment(R.id.fragment_lexical_container, CreateLexicalItemFragment.newInstance());
		} else {
//			popToChild(CreateLexicalItemFragment.class, false);
			QMUIViewHelper.fadeIn(createLexicalItemFragment.getView(), UIConstant.FADE_ANIMATOR_DURATION, null, true);
			EventBus.getDefault().post(MessageEventConstant.CREATE_LEXICAL_ITEM_REFRESH_EVENT);
		}
	}
}
