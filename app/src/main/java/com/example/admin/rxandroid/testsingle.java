package com.example.admin.rxandroid;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class testsingle {

    public void onDo() {
        Single.just(4).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return String.valueOf(integer);
            }
        }).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {


            }

            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
