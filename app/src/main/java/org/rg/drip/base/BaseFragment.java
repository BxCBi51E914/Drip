package org.rg.drip.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
	protected abstract void initView();
	
	/**
	 * 在调用 onCreateView 时进行的初始化
	 */
	protected void initViewOnCreateView() {
	}

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
		if (getContentViewLayoutID() != 0) {
			view = inflater.inflate(getContentViewLayoutID(), container, false);
		} else {
			view = super.onCreateView(inflater, container, savedInstanceState);
		}
		
		initViewOnCreateView();
		return view;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mActivity = getActivity();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mUnbinder = ButterKnife.bind(this, view);
		initView();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mUnbinder.unbind();
	}
}
