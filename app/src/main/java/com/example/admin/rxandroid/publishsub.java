package com.example.admin.rxandroid;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class publishsub {
    private static final String TAG = "publishsub";

    PublishSubject subject = PublishSubject.create();

    public void ondo() {


        subject.subscribe(new Observer<Integer>(){
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void countOn(int a) {
        subject.onNext(a++);
    }
}
