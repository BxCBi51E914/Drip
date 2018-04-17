package org.rg.drip.utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * Author : TankGq
 * Time : 2018/4/17
 */
public class RetryWhenProcess<E extends Exception> implements Function<Flowable<? extends Throwable>, Flowable<?>> {

	private onJudgeThrowableNeedRetry mJudge;
	private long mInterval;
	private int mCount;

	public RetryWhenProcess(long interval, int count) {
		mInterval = interval;
		mCount = count;
	}

	public void setCondition(onJudgeThrowableNeedRetry judge) {
		this.mJudge = judge;
	}

	@Override
	public Flowable<?> apply(Flowable<? extends Throwable> observable) {
		return observable.flatMap((Function<Throwable, Flowable<?>>) throwable -> {
			return observable.flatMap((Function<Throwable, Flowable<?>>) throwable2 -> {
				if(mCount == 0 || mJudge == null || ! mJudge.isNeedRetry(throwable2)) {
					return Flowable.error(throwable2);
				}
				-- mCount;
				return Flowable.just(throwable2).delay(mInterval, TimeUnit.SECONDS);
			});
		});
	}

	interface onJudgeThrowableNeedRetry {

		/**
		 * 判断什么样的错误需要 retry
		 */
		boolean isNeedRetry(Throwable throwable);
	}
}




