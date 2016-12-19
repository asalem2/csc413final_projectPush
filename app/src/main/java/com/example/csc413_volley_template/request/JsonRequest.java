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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Volley request to receive JSON as response and parse it to create list of movies
 */
public class JsonRequest extends Request<List<Movie>> {

    // Success listener implemented in controller
    private Response.Listener<List<Movie>> successListener;

    /**
     * Class constructor
     * @param method            Request method
     * @param url               url to API
     * @param successListener   success listener
     * @param errorListener     failure listener
     */
    public JsonRequest( int method,
                        String url,
                        Response.Listener<List<Movie>> successListener,
                        Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.successListener = successListener;
    }

    @Override
    protected Response<List<Movie>> parseNetworkResponse(NetworkResponse response) {
        // Convert byte[] data received in the response to String
        String jsonString = new String(response.data);
        List<Movie> movies;
        JSONObject jsonObject;
        Log.i(this.getClass().getName(), jsonString);
        // Try to convert JsonString to list of movies
        try {
            // Convert JsonString to JSONObject
            jsonObject = new JSONObject(jsonString);
            Log.i("moviesfirst",jsonString);
            // Get list of movies from received JSON
            movies = Movie.parseJson(jsonObject);
//            Log.i("moviesfirst",movies.get(0).getTitle());
        }
        // in case of exception, return volley error
        catch (JSONException e) {
            e.printStackTrace();
            // return new volley error with message
            return Response.error(new VolleyError("Failed to process the request"));
        }
        // return list of movies
        return Response.success(movies, getCacheEntry());
    }

    @Override
    protected void deliverResponse(List<Movie> movies) {
        successListener.onResponse(movies);
    }
}
