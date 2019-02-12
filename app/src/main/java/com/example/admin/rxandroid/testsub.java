package com.example.admin.rxandroid;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class testsub {
    private static final String TAG = "testsub";

    public void onCreate() {



        Observable<List<String>> listObservable1 = Observable.fromCallable(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                return getTheColorName();
            }
        });


        listObservable1
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<String> strings) {
                        Log.d(TAG, "onNext: color" + strings);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public List<String> getTheColorName() {
        List<String> colors = new ArrayList<>();
        colors.add("red");
        colors.add("blue");
        colors.add("white");
        colors.add("pink");
        colors.add("green");
        return colors;
    }
}
