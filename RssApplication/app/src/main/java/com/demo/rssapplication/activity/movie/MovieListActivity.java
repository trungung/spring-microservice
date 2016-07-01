package com.demo.rssapplication.activity.movie;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.demo.rssapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusActionBarActivity;

@RequiresPresenter(MoviePresenterImpl.class)
public class MovieListActivity extends NucleusActionBarActivity<MoviePresenterImpl> {

    private static final String TAG = MovieListActivity.class.getSimpleName();

    @BindView(R.id.movies_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        unbinder = ButterKnife.bind(this);

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        showProgress();

        // Load movies data
        getPresenter().getTopRatedMovies();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}
