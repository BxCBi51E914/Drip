package org.rg.drip.data.contract;

/**
 * Created by TankGq
 * on 2018/3/17.
 */
public interface ModelContract {

	interface Cache<T> {

		/**
		 * 将其从继承于 BmobObject 或者 RealmObject 的对象转化成缓存对象
		 */
		T convertToCache();
	}
	
	interface Local<T> {

		/**
		 * 将其从缓存对象转换成继承于 RealmObject 的对象
		 */
		T convertToLocal();
	}

	interface Remote<T> {

		/**
		 * 将其从缓存对象转换成继承于 BmobObject 的对象
		 */
		T convertToRemote();
	}
}
