package com.example.csc413_volley_template.controller;

import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.csc413_volley_template.app.App;
import com.example.csc413_volley_template.model.Movie;
import com.example.csc413_volley_template.model.VideoId;
import com.example.csc413_volley_template.request.JsonRequest;
import com.example.csc413_volley_template.request.VideoJsonRequest;
import com.example.csc413_volley_template.volley.VolleySingleton;

import java.util.List;

/*
 * Created by ahmed on 12/11/16.
 */

/**
 * <p> Provides interface between {@link android.app.Activity} and {@link com.android.volley.toolbox.Volley} </p>
 */
public class VideoJsonController {

    private final int TAG = 100;

    private OnResponseListener responseListener;

    /**
     *
     * @param responseListener  {@link OnResponseListener}
     */
    public VideoJsonController(OnResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    /**
     * Adds request to volley request queue
     * @param query query term for search
     */
    public void sendRequest(String query){

        // Request Method
        int method = Request.Method.GET;

        String url = "http://api.themoviedb.org/3/movie/" + Uri.encode(query) + "/videos?api_key=430771852c3226d571db1e30d4d19a61";


        // Create new request using JsonRequest
        VideoJsonRequest request
            = new VideoJsonRequest(
                method,
                url,
                new Response.Listener<List<VideoId>>() {
                    @Override
                    public void onResponse(List<VideoId> movies) {
                        responseListener.onSuccess(movies);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseListener.onFailure(error.getMessage());
                    }
                }
        );

        // Add tag to request
        request.setTag(TAG);

        // Get RequestQueue from VolleySingleton
        VolleySingleton.getInstance(App.getContext()).addToRequestQueue(request);
    }

    /**
     * <p>Cancels all request pending in request queue,</p>
     * <p> There is no way to control the request already processed</p>
     */
    public void cancelAllRequests() {
        VolleySingleton.getInstance(App.getContext()).cancelAllRequests(TAG);
    }

    /**
     *  Interface to communicate between {@link android.app.Activity} and {@link JsonRequest}
     *  <p>Object available in {@link JsonRequest} and implemented in {@link com.example.csc413_volley_template.MainActivity}</p>
     */
    public interface OnResponseListener {
        void onSuccess(List<VideoId> videoIds);
        void onFailure(String errorMessage);
    }

}