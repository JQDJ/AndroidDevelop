package com.sven.develop.rxjava;


import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Producer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Sven.Zhan on 2016/12/28.
 */

public class RxJavaTest {

   String TAG = RxJavaTest.class.getSimpleName();


    public void testJust(){
        Observable<Integer> observable = Observable.just(1,2,3,null);
        observable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.e(TAG,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"onError e = "+e);
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG,"onNext integer = "+integer);
            }

            @Override
            public void setProducer(Producer p) {
                super.setProducer(p);
                Log.e(TAG,"setProducer p = "+p);
            }

            @Override
            public void onStart() {
                super.onStart();
                Log.e(TAG,"onStart ");
            }
        });
    }

    public void testMap(){
        Observable.just(1)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer s) {
                        return "this is integer "+s;
                    }
                })
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return 3;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer s) {
                        Log.e(TAG,"Action1 call s = "+s);
                    }
                });
    }

    public void testFlatMap(){
        String[] items = new String[]{"Str1", "Str2"};
              // Observable.from(items)
                Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        String x = "Str1";
                        subscriber.onCompleted();
                        subscriber.onNext(x);
                        subscriber.onError(new NullPointerException());

                    }
                })
                .flatMap(new Func1<String, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(String s) {
                        return Observable.just(s).map(new Func1<String, Integer>() {
                            @Override
                            public Integer call(String s) {
                                return Integer.valueOf(s.replace("Str", ""));
                            }
                        });
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG,"onCompleted ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,"onError  e = "+ e);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG,"onNext integer = "+integer);
                    }
                });
    }

    public void testMaterialize(){
        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);
        values.take(3)
                .materialize()
                .subscribe(new PrintSubscriber("Materialize"));
    }


    class PrintSubscriber extends Subscriber{

        private final String name;

        public PrintSubscriber(String name) {
            this.name = name;
        }

        @Override
        public void onCompleted() {
            Log.e(TAG,name + ": Completed");
        }
        @Override
        public void onError(Throwable e) {
            Log.e(TAG,name + ": Error: " + e);
        }
        @Override
        public void onNext(Object v) {
            Log.e(TAG,name + ": " + v);
        }
    }
}
