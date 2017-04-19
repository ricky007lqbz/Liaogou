package com.common.util.rxjava;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ricky on 2016/08/17.
 * <p>
 * rxJava线程转换工具
 */
public class C_RxSchedulersHelper {

    /**
     * io线程执行，主线程读写
     *
     * @param <T> 泛型
     * @return Observable.Transformer
     */
    public static <T> Observable.Transformer<T, T> io_main() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
