package org.rg.drip.presenter;

import android.support.annotation.NonNull;

import org.rg.drip.R;
import org.rg.drip.contract.user.SignInContract;
import org.rg.drip.data.contract.UserContract;
import org.rg.drip.utils.CheckUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author : TankGq
 * Time : 28/03/2018
 */
public class SignInPresenter implements SignInContract.Presenter {

	@NonNull
	private final UserContract.Repository mUserRepository;

	@NonNull
	private final SignInContract.View mView;

	@NonNull
	private CompositeDisposable mCompositeDisposable;

	public SignInPresenter(@NonNull UserContract.Repository userRepository,
	                       @NonNull SignInContract.View signInView) {
		mUserRepository = CheckUtil.checkNotNull(userRepository);
		mView = CheckUtil.checkNotNull(signInView);

		mCompositeDisposable = new CompositeDisposable();
		mView.setPresenter(this);
	}

	@Override
	public void subscribe() {
	}

	@Override
	public void unSubscribe() {
		mCompositeDisposable.clear();
	}

	@Override
	public void signIn(String usernameOrEmail, String password) {
		if(usernameOrEmail.length() == 0) {
			mView.showTip(R.string.err_msg_username);
			return;
		}
		if(password.length() == 0) {
			mView.showTip(R.string.err_msg_password);
			return;
		}

		mCompositeDisposable.add(
				mUserRepository.signIn(usernameOrEmail, password)
				               .subscribeOn(Schedulers.io())
				               .observeOn(AndroidSchedulers.mainThread())
				               .subscribe(
						               // onNext
						               result -> {
							               if(result) {
								               mView.signInOk();
							               } else {
								               mView.showTip(R.string.err_msg_sign_in_error);
							               }
						               },
						               // onError
						               throwable -> {
							               mView.showTip(R.string.err_msg_sign_in_fail);
						               }
				               ));
	}

	@Override
	public void forgetPassword(String email) {
		if(! CheckUtil.checkEmail(email)) {
			mView.showTip(R.string.tip_input_email_above);
			return;
		}
		mCompositeDisposable.add(
				mUserRepository.changePasswordByEmail(email)
				               .subscribeOn(Schedulers.io())
				               .observeOn(AndroidSchedulers.mainThread())
				               .subscribe(
						               // onNext
						               result -> {
							               if(result) {
								               mView.showTip(R.string.tip_email_send_succeed);
							               } else {
								               mView.showTip(R.string.err_email_send_failed);
							               }
						               },
						               // onError
						               throwable -> {
							               mView.showTip(R.string.err_email_send_failed);
						               }
				               ));
	}
}
