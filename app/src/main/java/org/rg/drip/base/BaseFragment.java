package org.rg.drip.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.rg.drip.DripApplication;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Author : Tank
 * Time : 08/03/2018
 */
public abstract class BaseFragment extends SupportFragment {
	
	private Unbinder mUnbinder;
	
	/**
	 * 获取布局ID
	 */
	protected abstract int getContentViewLayoutID();
	
	/**
	 * 界面初始化
	 */
	protected abstract void initView(Bundle savedInstanceState);
	
	/**
	 * 在调用 onCreateView 时进行的初始化
	 */
	protected void initViewOnCreateView(View view) {
	}
	
	/**
	 * 在调用 onLazyInitView 时进行的初始化
	 */
	protected void initViewOnLazyInitView() {
	
	}
	
	/**
	 * 在调用 onActivityCreated 时进行初始化
	 */
	protected void initViewOnActivityCreated() {
	}
	
	/**
	 * 是否要注册 EventBus， 分别在 onResume 和 onPause 时注册和取消注册
	 */
	protected boolean registerEventBus() {
		return false;
	}

	/**
	 * 是否要在 onPause 的时候取消注册 EventBus,
	 * 如果为否, 且有注册 EventBus 那么就在 OnDestroy 的时候取消注册
	 */
	protected boolean unregisterWhenOnPause() {
		return true;
	}
	private boolean mIsUnregisterEventBus = false;
	
	private Activity mActivity;
	
	@Nullable
	@Override
	public Context getContext() {
		if(mActivity == null) {
			return DripApplication.getInstance();
		}
		return mActivity;
	}
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
	                         @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		View view;
		if(getContentViewLayoutID() != 0) {
			view = inflater.inflate(getContentViewLayoutID(), container, false);
		} else {
			view = super.onCreateView(inflater, container, savedInstanceState);
		}
		mUnbinder = ButterKnife.bind(this, view);
		initView(savedInstanceState);
		return view;
	}
	
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mActivity = getActivity();
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if(mUnbinder != null) mUnbinder.unbind();
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViewOnActivityCreated();
	}
	
	@Override
	public void onLazyInitView(@Nullable Bundle savedInstanceState) {
		super.onLazyInitView(savedInstanceState);
		initViewOnLazyInitView();
	}
	
	public interface OnEnterAnimator {
		void onEnterAnimator();
	}

	@Override
	public void onResume() {
		super.onResume();
		if(registerEventBus() && ! mIsUnregisterEventBus) {
			EventBus.getDefault().register(this);
			mIsUnregisterEventBus = true;
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if(registerEventBus() && unregisterWhenOnPause()) {
			EventBus.getDefault().unregister(this);
			mIsUnregisterEventBus = false;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(registerEventBus() && ! unregisterWhenOnPause()) {
			EventBus.getDefault().unregister(this);
			mIsUnregisterEventBus = false;
		}
	}
}
