package com.demo.rssapplication.activity.movie;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.demo.rssapplication.R;

import nucleus.view.NucleusActivity;

public class MovieListActivity extends NucleusActivity<MoviePresnterImpl> {

    private static final String TAG = MovieListActivity.class.getSimpleName();

//    @BindView(R.id.movies_recycler_view) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        getPresenter().getTopRatedMovies();
    }
}
