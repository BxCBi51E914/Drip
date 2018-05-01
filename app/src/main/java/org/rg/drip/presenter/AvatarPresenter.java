package org.rg.drip.presenter;

import android.support.annotation.NonNull;

import org.rg.drip.R;
import org.rg.drip.contract.AvatarContract;
import org.rg.drip.data.contract.UserContract;
import org.rg.drip.utils.CheckUtil;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by eee
 * on 2018/4/3.
 */
public class AvatarPresenter implements AvatarContract.Presenter {
	
	@NonNull private final UserContract.Repository mUserRepository;
	
	@NonNull private final AvatarContract.View mView;
	
	@NonNull private CompositeDisposable mCompositeDisposable;
	
	public AvatarPresenter(@NonNull UserContract.Repository userRepository, @NonNull AvatarContract.View avatarView) {
		mUserRepository = CheckUtil.checkNotNull(userRepository);
		mView = CheckUtil.checkNotNull(avatarView);
		
		mCompositeDisposable = new CompositeDisposable();
		mView.setPresenter(this);
	}
	
	@Override
	public void signOut() {
		mUserRepository.signOut();
	}
	
	@Override
	public void verifyEmail() {
		mCompositeDisposable.add(mUserRepository.verifyEmail()
		                                        .subscribeOn(Schedulers.io())
		                                        .observeOn(AndroidSchedulers.mainThread())
		                                        .subscribe(
				                                        // onNext
				                                        result -> {
					                                        if(result) {
						                                        mView.showTip(R.string.tip_email_send_succeed);
					                                        } else {
						                                        mView.showTip(R.string.tip_email_send_failed);
					                                        }
					                                        mView.showLoadingTipDialog(false);
				                                        },
				                                        // onError
				                                        throwable -> {
					                                        mView.showTip(R.string.tip_email_send_failed);
					                                        mView.showLoadingTipDialog(false);
				                                        }));
		mView.showLoadingTipDialog(true);
	}
	
	@Override
	public void changeEmail(String email) {
		if(email.length() == 0) {
			mView.showTip(R.string.tip_email);
			return;
		}
		if(! CheckUtil.checkEmail(email)) {
			mView.showTip(R.string.tip_email_error);
			return;
		}
		
		mCompositeDisposable.add(mUserRepository.changeEmail(email)
		                                        .subscribeOn(Schedulers.io())
		                                        .observeOn(AndroidSchedulers.mainThread())
		                                        .subscribe(
				                                        // onNext
				                                        result -> {
					                                        if(result) {
						                                        mView.showTip(R.string.tip_change_succeed);
					                                        } else {
						                                        mView.showTip(R.string.tip_change_failed);
					                                        }
					                                        mView.showLoadingTipDialog(false);
				                                        },
				                                        // onError
				                                        throwable -> {
					                                        mView.showTip(R.string.tip_change_failed);
					                                        mView.showLoadingTipDialog(false);
				                                        }));
		mView.showLoadingTipDialog(true);
	}
	
	@Override
	public void updateSignInUser() {
		mView.loadUser(mUserRepository.getCurrentUser());
	}
	
	@Override
	public void subscribe() {
	}
	
	@Override
	public void unSubscribe() {
		mCompositeDisposable.clear();
	}
}
