package com.demo.rssapplication.activity.example;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.demo.rssapplication.R;
import com.demo.rssapplication.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;

public class Example5Activity extends BaseActivity {

    private TextView mValueDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureLayout();

        Single.just(4).map(integer -> String.valueOf(integer)).subscribe(new SingleSubscriber<String>() {
            @Override
            public void onSuccess(String value) {
                mValueDisplay.setText(value);
            }

            @Override
            public void onError(Throwable error) {

            }
        });


        // -----------------------------------------------------------------------------------------
        // Filter even numbers
        Observable
                .just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .filter(integer -> integer % 2 == 0)
                .subscribe(System.out::println);
        /** => 2, 4, 6, 8, 10 */

        // -----------------------------------------------------------------------------------------
        // Iterating with "forEach"
        Observable
                .just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .forEach(System.out::println);
        /** => 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 */

        // -----------------------------------------------------------------------------------------
        // Group by
        Observable
                .just(1, 2, 3, 4, 5)
                .groupBy(integer -> integer % 2 == 0).subscribe(grouped -> {
            grouped.toList().subscribe(integers -> {
                Log.d("Group by", "" + integers);
            });
        });

        // [1, 3, 5]
        // [2, 4]

        // -----------------------------------------------------------------------------------------
        // Take only the first N values emitted
        Observable
                .just(1, 2, 3, 4, 5)
                .take(2)
                .subscribe(System.out::println);

        // => 1, 2

        // -----------------------------------------------------------------------------------------
        // First
        Observable
                .just(1, 2, 3, 4, 5)
                .first()
                .subscribe(System.out::println);

        // => 1

        // -----------------------------------------------------------------------------------------
        // Last
        Observable
                .just(1, 2, 3, 4, 5)
                .last()
                .subscribe(System.out::println);

        // => 5

        // -----------------------------------------------------------------------------------------
        // Map()
        Observable.just("Hello world!")
                .map(s -> s.hashCode())
                .subscribe(i -> Log.d("Map", Integer.toString(i)));

        // => 121287312

        // -----------------------------------------------------------------------------------------
        // Iterate an array list
        List<String> tmps = new ArrayList<>();

        tmps.add("jon snow");
        tmps.add("tyrion lannister");

        Observable
                .just(tmps)
                .concatMap(tmpList -> Observable.from(tmpList))
                .subscribe(tmp -> Log.d("Iterate an array list", tmp));

        // concatMap: when applied to an item emitted by the source Observable, returns an Observable
        // => "jon snow", "tyrion lannister"


    }

    private void configureLayout() {
        setContentView(R.layout.activity_example5);
        mValueDisplay = (TextView) findViewById(R.id.value_display);

        showActionBar(true);
    }
}
