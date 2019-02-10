package com.example.admin.rxandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<List<String>> listObservable = Observable.just(getTheColorName());

        listObservable.subscribe(new Observer<List<String>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<String> strings) {
                for (int i = 0; i < strings.size(); i++) {
                    Log.d(TAG, "onNext: color " + strings.get(i));
                }
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
