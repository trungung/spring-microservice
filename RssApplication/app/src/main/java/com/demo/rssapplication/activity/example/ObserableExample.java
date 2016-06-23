package com.demo.rssapplication.activity.example;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class ObserableExample {

    /**
     * Consuming a Observable by Subscribing it.
     * <p/>
     * This prints:
     * <p/>
     * Got: 1
     * Got: 2
     * Got: 3
     * Completed Observable.
     */
    public void subscribingObservables() {

        Observable
                .just(1, 2, 3)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Completed Observable.");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.err.println("Whoops: " + throwable.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("Got: " + integer);
                    }
                });
    }

    /**
     * Throwing an artificial exception.
     * <p/>
     * This prints:
     * Got: 1
     * Whoops: I don't like 2
     */
    public void throwingArtificialException() {
        Observable
                .just(1, 2, 3)
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        if (integer.equals(2)) {
                            throw new RuntimeException("I don't like 2");
                        }
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Completed Observable.");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.err.println("Whoops: " + throwable.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("Got: " + integer);
                    }
                });
    }

    /**
     * The following code only takes the first five values and then unsubscribes.
     * <p/>
     * This prints:
     * Got: The
     * Got: Dave
     * Got: Brubeck
     * Got: Quartet
     * Got: Time
     * Completed Observable.
     */
    public void takeUnSubscribles() {
        Observable
                .just("The", "Dave", "Brubeck", "Quartet", "Time", "Out")
                .take(5)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Completed Observable.");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.err.println("Whoops: " + throwable.getMessage());
                    }

                    @Override
                    public void onNext(String name) {
                        System.out.println("Got: " + name);
                    }
                });
    }

    /**
     * Only interested in the data events.
     */
    public void simpleSubScrible() {
        Observable
                .just(1, 2, 3)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println("Got: " + integer);
                    }
                });
    }
}
