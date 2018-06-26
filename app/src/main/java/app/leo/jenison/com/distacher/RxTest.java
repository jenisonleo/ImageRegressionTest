package app.leo.jenison.com.distacher;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.support.v4.view.AsyncLayoutInflater;
import android.util.Log;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class RxTest {
    PublishSubject<String> subject;
    Random r=new Random();
    @SuppressLint("CheckResult")
    public RxTest(){
        final ThreadPoolExecutor executorService =(ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        subject=PublishSubject.create();
        subject.groupBy(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                Log.e("event1", " "+Thread.currentThread().getName());
                return s;
            }
        }).subscribe(new Consumer<GroupedObservable<String, String>>() {
            @Override
            public void accept(GroupedObservable<String, String> stringStringGroupedObservable) throws Exception {
                stringStringGroupedObservable.subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String strings) throws Exception {
//                        Thread.sleep(1000);
                            Log.e("jenison", " " + strings+" "+Thread.currentThread().getName()+" "+System.currentTimeMillis());
                    }
                });
            }
        });
    }

    void addEvevnt(){
        Log.e("started"," "+System.currentTimeMillis()) ;
        for(int i=0;i<500;i++) {
            String getx = getx();
            subject.onNext(getx);
        }
    }

    void send(String x){
        subject.onNext(x);
    }

    String getx(){
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        String x = String.valueOf(r.nextInt(5));
        Log.e("sent"," "+x+" "+Thread.currentThread().getName());
        return x;
    }


//    subject=PublishSubject.create();
//        subject.groupBy(new Function<String, String>() {
//        @Override
//        public String apply(String s) throws Exception {
//            Log.e("event1", " "+Thread.currentThread().getName());
//            return s;
//        }
//    }).subscribe(new Consumer<GroupedObservable<String, String>>() {
//        @Override
//        public void accept(GroupedObservable<String, String> stringStringGroupedObservable) throws Exception {
//            stringStringGroupedObservable.buffer(1, TimeUnit.SECONDS,Schedulers.from(executorService), Integer.MAX_VALUE).filter(new Predicate<List<String>>() {
//                @Override
//                public boolean test(List<String> strings) throws Exception {
//                    return strings.size() > 0;
//                }
//            }).map(new Function<List<String>, String>() {
//                @Override
//                public String apply(List<String> strings) throws Exception {
////                        Log.e("event3", " "+Thread.currentThread().getName());
//                    return strings.get(0);
//                }
//            }).subscribe(new Consumer<String>() {
//                @Override
//                public void accept(String strings) throws Exception {
//                    Thread.sleep(1000);
//                    Log.e("jenison", " " + strings+" "+Thread.currentThread().getName()+" "+System.currentTimeMillis());
//                }
//            });
//        }
//    });

}
