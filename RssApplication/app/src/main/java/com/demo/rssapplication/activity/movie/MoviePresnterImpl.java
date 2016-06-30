package com.demo.rssapplication.activity.movie;

import android.os.Bundle;
import android.util.Log;

import com.demo.rssapplication.common.model.Movie;
import com.demo.rssapplication.common.service.base.ApiClient;
import com.demo.rssapplication.common.service.movie.MovieApi;

import java.util.List;

import nucleus.presenter.RxPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviePresnterImpl extends RxPresenter<MovieListActivity> implements MoviePresenter {

    private static final String TAG = MoviePresnterImpl.class.getSimpleName();
    private MovieListActivity view;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }

    @Override
    protected void onTakeView(MovieListActivity listActivity) {
        super.onTakeView(listActivity);
        this.view = listActivity;
    }

    @Override
    public void getTopRatedMovies() {

        MovieApi apiService = ApiClient.getClient().create(MovieApi.class);

        Call<List<Movie>> call = apiService.getTopRatedMovies(ApiClient.API_KEY);
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>>call, Response<List<Movie>> movies) {
                Log.d(TAG, "Number of movies received: ");
//                int statusCode = response.code();
//                List<Movie> movies = response.body().getResults();
//                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<List<Movie>>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }
}
