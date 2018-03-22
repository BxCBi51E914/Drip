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

	public static int AND = 0;
	public static int OR = 1;

	private List<BmobQuery<T>> mQueryList = new ArrayList<>();

	/**
	 * 加入一个查询
	 */
	public BmobQueryUtil<T> add(BmobQuery<T> query) {
		mQueryList.add(query);
		return this;
	}

	/**
	 * 加入一个组合查询
	 */
	public BmobQueryUtil<T> andCombine(int type, BmobQuery<T>... queries) {
		List<BmobQuery<T>> queryList = new ArrayList<>();
		Collections.addAll(queryList, queries);
		if(type == AND) {
			mQueryList.add(new BmobQuery<T>().and(queryList));
		} else if(type == OR) {
			mQueryList.add(new BmobQuery<T>().or(queryList));
		}
		return this;
	}

	/**
	 *
	 */
	public BmobQuery<T> compile(int type) {
		if(type == AND) {
			return new BmobQuery<T>().and(mQueryList);
		} else if(type == OR) {
			return new BmobQuery<T>().or(mQueryList);
		}
		return new BmobQuery<>();
	}
}
