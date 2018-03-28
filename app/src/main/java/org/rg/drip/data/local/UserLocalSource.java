package org.rg.drip.data.local;

import org.rg.drip.data.contract.UserContract;

/**
 * Author : Tank
 * Time : 22/03/2018
 */
public class UserLocalSource implements UserContract.Local {

	private static UserLocalSource mInstance = null;

	public static UserLocalSource getInstance() {
		if(mInstance == null) {
			mInstance = new UserLocalSource();
		}

		return mInstance;
	}
}
