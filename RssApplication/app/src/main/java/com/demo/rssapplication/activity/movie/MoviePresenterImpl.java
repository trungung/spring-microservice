package com.demo.rssapplication.activity.movie;

import android.os.Bundle;
import android.util.Log;

import com.demo.rssapplication.R;
import com.demo.rssapplication.common.model.Movie;
import com.demo.rssapplication.common.model.MoviesResponse;
import com.demo.rssapplication.common.service.base.ApiClient;
import com.demo.rssapplication.common.service.movie.MovieApi;

import java.util.List;

import nucleus.presenter.RxPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviePresenterImpl extends RxPresenter<MovieListActivity> implements MoviePresenter {

    private static final String TAG = MoviePresenterImpl.class.getSimpleName();
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

//        getView().showProgress();

        MovieApi apiService = ApiClient.getClient().create(MovieApi.class);

        Call<MoviesResponse> call = apiService.getTopRatedMovies(ApiClient.API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse>call, Response<MoviesResponse> response) {
                Log.d(TAG, "Number of movies received: ");
                int statusCode = response.code();
                List<Movie> movies = response.body().getResults();
                view.recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, view));
                view.hideProgress();
            }

            @Override
            public void onFailure(Call<MoviesResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                view.hideProgress();
            }
        });

    }
}
