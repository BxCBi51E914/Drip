package org.rg.drip.data.source.remote;

import org.rg.drip.constant.UserConstant;
import org.rg.drip.data.model.User;
import org.rg.drip.data.model.bmob.UserR;
import org.rg.drip.data.source.contract.UserContract;
import org.rg.drip.utils.BmobQueryUtil;
import org.rg.drip.utils.BmobUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * Author : Tank
 * Time : 22/03/2018
 */
public class UserRemoteSource implements UserContract.Remote {

	@Override
	public Flowable<User> getUserByUsername(String username) {
		return Flowable.create(emitter -> {
			BmobQuery<UserR> query = new BmobQuery<>();
			query.addWhereEqualTo(UserConstant.USERNAME, username)
			     .findObjects(new FindListener<UserR>() {
				     @Override
				     public void done(List<UserR> list, BmobException e) {
					     if(e == null && list.size() > 0) {
						     emitter.onNext(((UserR) list.get(0)).convertToNormal());
						     emitter.onComplete();
						     return;
					     }

					     emitter.onError(e);
				     }
			     });
		}, BackpressureStrategy.BUFFER);
	}

	@Override
	public Flowable<Boolean> checkUsernameExist(String username) {
		return Flowable.create(emitter -> {
			BmobQuery<UserR> query = new BmobQuery<>();
			query.addWhereEqualTo(UserConstant.USERNAME, username)
			     .findObjects(new FindListener<UserR>() {
				     @Override
				     public void done(List<UserR> list, BmobException e) {
					     if(e == null) {
						     emitter.onNext(list.size() > 0);
						     emitter.onComplete();
						     return;
					     }

					     emitter.onError(e);
				     }
			     });
		}, BackpressureStrategy.BUFFER);
	}

	@Override
	public Flowable<Boolean> checkPassword(String username, String password) {
//		return Flowable.create(emitter -emitter> {
//			BmobQueryUtil<UserR> query = new BmobQueryUtil<>();
//			query.add(new BmobQuery<UserR>().addWhereEqualTo(UserConstant.USERNAME, username))
//			     .add(new BmobQuery<UserR>().addWhereEqualTo(UserConstant.PASSWORD, password))
//			     .compile(BmobQueryUtil.AND)
//			     .findObjects(new FindListener<UserR>() {
//				     @Override
//				     public void done(List<UserR> list, BmobException e) {
//					     if(e == null) {
//						     emitter.onNext(list.size() > 0);
//						     emitter.onComplete();
//						     return;
//					     }
//
//					     emitter.onError(e);
//				     }
//			     });
//		}, BackpressureStrategy.BUFFER);
		return Flowable.create(emitter -> {
			BmobQuery<UserR> query = new BmobQuery<>();
			query.addWhereEqualTo(UserConstant.USERNAME, username)
			     .findObjects(new FindListener<UserR>() {
				     @Override
				     public void done(List<UserR> list, BmobException e) {
					     if(e == null || list.size() > 0) {
						     emitter.onNext(list.get(0).getPassword().equals(password));
						     emitter.onComplete();
						     return;
					     }

					     emitter.onError(e);
				     }
			     });
		}, BackpressureStrategy.BUFFER);
	}

	@Override
	public Flowable<Boolean> CreateUser(User user) {
		return null;
	}

	@Override
	public Flowable<Boolean> changeUserPassword(String password) {
		return null;
	}

	@Override
	public Flowable<Boolean> verifyEmail() {
		return null;
	}

	@Override
	public Flowable<Boolean> checkEmailVarified() {
		return null;
	}
}
