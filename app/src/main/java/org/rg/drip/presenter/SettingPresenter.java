package org.rg.drip.presenter;

import android.support.annotation.NonNull;

import org.rg.drip.GlobalData;
import org.rg.drip.R;
import org.rg.drip.contract.SettingContract;
import org.rg.drip.data.contract.UserContract;
import org.rg.drip.utils.CheckUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TankGq
 * on 2018/4/13.
 */
public class SettingPresenter implements SettingContract.Presenter {
	
	@NonNull private final UserContract.Repository mUserRepository;
	
	@NonNull private final SettingContract.View mView;
	
	@NonNull private CompositeDisposable mCompositeDisposable;
	
	public SettingPresenter(@NonNull UserContract.Repository userRepository,
	                        @NonNull SettingContract.View settingView) {
		mUserRepository = CheckUtil.checkNotNull(userRepository);
		mView = CheckUtil.checkNotNull(settingView);
		
		mCompositeDisposable = new CompositeDisposable();
		mView.setPresenter(this);
	}
	
	@Override
	public void saveUserConfig() {
		final String config = GlobalData.config;
		mCompositeDisposable.add(mUserRepository.saveUserConfig(config)
		                                        .subscribeOn(Schedulers.io())
		                                        .observeOn(AndroidSchedulers.mainThread())
		                                        .subscribe(
				                                        // onNext
				                                        result -> {
					                                        if(result) {
					                                        	mView.updateLanguageItem();
						                                        mView.showTip(R.string.tip_save_succeed);
					                                        } else {
						                                        mView.showTip(R.string.tip_save_failed);
					                                        }
				                                        },
				                                        // onError
				                                        throwable -> mView.showTip(R.string.error)));
	}
	
	@Override
	public void subscribe() {
	}
	
	@Override
	public void unSubscribe() {
		mCompositeDisposable.clear();
	}
}
