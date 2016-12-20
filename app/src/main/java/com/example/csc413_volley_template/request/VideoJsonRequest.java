package com.example.csc413_volley_template.request;

/*
 * Created by ahmed on 12/11/16.
 */

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.csc413_volley_template.model.Movie;
import com.example.csc413_volley_template.model.VideoId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Volley request to receive JSON as response and parse it to create list of movies
 */
public class VideoJsonRequest extends Request<List<VideoId>> {

    // Success listener implemented in controller
    private Response.Listener<List<VideoId>> successListener;

    /**
     * Class constructor
     * @param method            Request method
     * @param url               url to API
     * @param successListener   success listener
     * @param errorListener     failure listener
     */
    public VideoJsonRequest(int method,
                            String url,
                            Response.Listener<List<VideoId>> successListener,
                            Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.successListener = successListener;
    }

    @Override
    protected Response<List<VideoId>> parseNetworkResponse(NetworkResponse response) {
        // Convert byte[] data received in the response to String
        String jsonString = new String(response.data);
        List<VideoId> videoIds;
        JSONObject jsonObject;
        Log.i(this.getClass().getName(), jsonString);
        // Try to convert JsonString to list of videoIds
        try {
            // Convert JsonString to JSONObject
            jsonObject = new JSONObject(jsonString);
//            Log.i("moviesfirst",jsonString);
            // Get list of videoIds from received JSON
            videoIds = VideoId.parseJson(jsonObject);
//            Log.i("moviesfirst",videoIds.get(0).getTitle());
        }
        // in case of exception, return volley error
        catch (JSONException e) {
            e.printStackTrace();
            // return new volley error with message
            return Response.error(new VolleyError("Failed to process the request"));
        }
        // return list of videoIds
        return Response.success(videoIds, getCacheEntry());
    }

    @Override
    protected void deliverResponse(List<VideoId> videoIds) {
        successListener.onResponse(videoIds);
    }
}
