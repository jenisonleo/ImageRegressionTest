package app.leo.jenison.com.distacher;

import android.util.Log;
import android.util.LruCache;

import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class ComputationTest {

    public ComputationTest(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for(int i=0;i<100;i++){
                    emitter.onNext(i);
                }
            }
        }).flatMap(new Function<Integer, ObservableSource<?>>() {
            @Override
            public ObservableSource<Integer> apply(final Integer integer) throws Exception {
                Observable<Integer> objectObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        Thread.sleep(1000);
                        emitter.onNext(integer);
                        Log.e("accept"," "+integer+" "+Thread.currentThread().getName());
                    }
                }).subscribeOn(Schedulers.computation());
                return objectObservable;
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Log.e("event"," "+o);
            }
        });
        PublishSubject<Integer> subject= PublishSubject.create();
        subject.observeOn(Schedulers.from(Executors.newFixedThreadPool(8))).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer o) throws Exception {
                Thread.sleep(1000);
                        Log.e("accept"," "+o+" "+Thread.currentThread().getName());
            }
        });
        for(int i=0;i<100;i++){
                    subject.onNext(i);
                }
    }


}
