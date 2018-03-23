package org.rg.drip.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;

/**
 * Author : Tank
 * Time : 23/03/2018
 */
public class BmobQueryUtil<T> {

	private List<BmobQuery<T>> mQueryList = new ArrayList<>();

	/**
	 * 加入一个查询
	 */
	public BmobQueryUtil<T> add(BmobQuery<T> query) {
		mQueryList.add(query);
		return this;
	}
	
	/**
	 * 加入一个与的组合查询
	 */
	public BmobQueryUtil<T> combineWithAnd(BmobQuery<T>... queries) {
		List<BmobQuery<T>> queryList = new ArrayList<>();
		Collections.addAll(queryList, queries);
		mQueryList.add(new BmobQuery<T>().and(queryList));
		return this;
	}
	
	/**
	 * 加入一个或的组合查询
	 */
	public BmobQueryUtil<T> combineWithOr(BmobQuery<T>... queries) {
		List<BmobQuery<T>> queryList = new ArrayList<>();
		Collections.addAll(queryList, queries);
		mQueryList.add(new BmobQuery<T>().or(queryList));
		return this;
	}

	/**
	 * 获得所有条件与的结果
	 */
	public BmobQuery<T> compileWithAnd() {
		return new BmobQuery<T>().and(mQueryList);
	}
	
	/**
	 * 获得所有条件或的结果
	 */
	public BmobQuery<T> compileWithOr() {
		return new BmobQuery<T>().or(mQueryList);
	}
}
