package org.rg.drip.presenter;

import android.support.annotation.NonNull;

import org.rg.drip.R;
import org.rg.drip.contract.user.SignUpContract;
import org.rg.drip.data.contract.UserContract;
import org.rg.drip.data.model.cache.User;
import org.rg.drip.utils.CheckUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TankGq
 * on 2018/3/29.
 */
public class SignUpPresenter implements SignUpContract.Presenter {

	@NonNull
	private final UserContract.Repository mUserRepository;

	@NonNull
	private final SignUpContract.View mView;

	@NonNull
	private CompositeDisposable mCompositeDisposable;

	public SignUpPresenter(@NonNull UserContract.Repository userRepository,
	                       @NonNull SignUpContract.View signUpView) {
		mUserRepository = CheckUtil.checkNotNull(userRepository);
		mView = CheckUtil.checkNotNull(signUpView);

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
	public void signUp(String username,
	                   String password,
	                   String repeatPassword,
	                   String nickname,
	                   String email) {
		if(username.length() == 0) {
			mView.showTip(R.string.tip_username);
			return;
		}
		if(password.length() == 0) {
			mView.showTip(R.string.tip_password);
			return;
		}
		if(repeatPassword.length() == 0) {
			mView.showTip(R.string.tip_repeat_password);
			return;
		}
		if(! repeatPassword.equals(password)) {
			mView.showTip(R.string.tip_password_no_equal);
			return;
		}
		if(nickname.length() == 0) {
			mView.showTip(R.string.tip_nickname);
			return;
		}
		if(email.length() == 0) {
			mView.showTip(R.string.tip_email);
			return;
		}
		if(! CheckUtil.checkEmail(email)) {
			mView.showTip(R.string.tip_input_email);
			return;
		}
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setNickname(nickname);
		user.setEmail(email);
		mView.showLoadingTipDialog(true);
		mUserRepository.signUp(user)
		               .subscribeOn(Schedulers.io())
		               .observeOn(AndroidSchedulers.mainThread())
		               .subscribe(
		               		// onNext
		               		result -> {
								if(result) {
									mView.signUpOk();
								} else {
									mView.showTip(R.string.tip_sign_up_failed);
								}
								mView.showLoadingTipDialog(false);
			                },
		                    // onError
		                    throwable -> mView.showLoadingTipDialog(false)
		               );
	}
}
