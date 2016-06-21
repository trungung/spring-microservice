package com.demo.rssapplication.activity.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.demo.rssapplication.R;
import com.demo.rssapplication.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListExampleActivity extends BaseActivity {

    @BindView(R.id.recycler_view) RecyclerView listViewExample;
    private SimpleStringAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_example);
        ButterKnife.bind(this);

        listViewExample.setLayoutManager(new LinearLayoutManager(this));
        listViewExample.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new SimpleStringAdapter(this);
        mAdapter.setStrings(getExampleList());
        listViewExample.setAdapter(mAdapter);
        listViewExample.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = null;
                        switch (position) {
                            case 0:
                                intent = new Intent(ListExampleActivity.this, Example1Activity.class);
                                break;
                            case 1:
                                intent = new Intent(ListExampleActivity.this, Example2Activity.class);
                                break;
                            case 2:
                                intent = new Intent(ListExampleActivity.this, Example3Activity.class);
                                break;
                            case 3:
                                intent = new Intent(ListExampleActivity.this, Example4Activity.class);
                                break;
                            case 4:
                                intent = new Intent(ListExampleActivity.this, Example5Activity.class);
                                break;
                            case 5:
                                intent = new Intent(ListExampleActivity.this, Example6Activity.class);
                                break;
                        }

                        startActivity(intent);
                    }
                })
        );
    }

    private static List<String> getExampleList() {
        ArrayList<String> examples = new ArrayList<>();
        examples.add("Example 1: Simple Color List : Observable.just");
        examples.add("Example 2: Favorite Tv Shows : Observable.fromCallable: Observer");
        examples.add("Example 3: Improved Favorite Tv Shows : Observable.fromCallable: SingleSubscriber");
        examples.add("Example 4: Button Counter : Observable PublishSubject");
        examples.add("Example 5: Value Display : Single.just");
        examples.add("Example 6: City Search : SearchResultsSubject");

        return examples;
    }
}
