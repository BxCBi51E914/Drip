package org.rg.drip.data.source.contract;

/**
 * Created by TankGq
 * on 2018/3/17.
 */
public interface ModelContract<T> {
	
	/**
	 * 将其从继承于 BmobObject 或者 RealmObject 的对象转化成普通的 JavaBean 对象
	 *
	 * @return 相应对象
	 */
	T convertToNormal();
}
