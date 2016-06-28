package com.demo.rssapplication.common.service;

import retrofit2.Response;

/**
 * The base interface for response listener
 */
public interface OnResponseListener {

    void onSuccess(Response response);

    void onFailure(ResponseError error);
}
