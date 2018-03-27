package org.rg.drip.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rg.drip.ui.fragment.third.child.child.ContentFragment;

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
