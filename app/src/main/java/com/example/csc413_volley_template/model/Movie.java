package com.example.csc413_volley_template.model;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.csc413_volley_template.MainActivity;
import com.example.csc413_volley_template.controller.JsonController;
import com.example.csc413_volley_template.controller.VideoJsonController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Ahmed on 12/11/16.
 */


/**
 * Model class for movie
 */
public class Movie {

    private String title;
    private String popularity;
    private String id;
    private String overview;
    private String posterUrl;

    private String videoId;

    private static Movie movie;
    private static final List<Movie> movies = new ArrayList<>();
    VideoJsonController controller;

    public static Movie get(){
        if(movie == null){
            movie= new Movie();
        }
        return movie;
    }

    public Movie getMovie(String id){
        for (Movie movie : movies){
            if(movie.getid().equals(id)){
                return movie;
            }
        }
        return null;
    }

    /**
     *
     * @param jsonObject    {@link JSONObject} response, received in Volley success listener
     * @return  list of movies
     * @throws JSONException
     */
    public static List<Movie> parseJson(JSONObject jsonObject) throws JSONException{
        movies.clear();
        // Check if the JSONObject has object with key "Search"
        if(jsonObject.has("results")){
            // Get JSONArray from JSONObject
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for(int i = 0; i < jsonArray.length(); i++){
                // Create new Movie object from each JSONObject in the JSONArray
                if (!jsonArray.getJSONObject(0).isNull("poster_path"))
                    movies.add(new Movie(jsonArray.getJSONObject(i)));
            }
        }

        return movies;
    }

    /**
     * <p>Class constructor</p>
     * <p>Sample Movie JSONObject</p>
     * <pre>
     * {
     *  "Title": "Batman Begins",
     *  "Year": "2005",
     *  "imdbID": "tt0372784",
     *  "Type": "movie",
     *  "Poster": "https://images-na.ssl-images-amazon.com/images/M/MV5BNTM3OTc0MzM2OV5BMl5BanBnXkFtZTYwNzUwMTI3._V1_SX300.jpg"
     * }
     * </pre>
     * @param jsonObject    {@link JSONObject} from each item in the search result
     * @throws JSONException     when parser fails to parse the given JSON
     */
    public Movie(JSONObject jsonObject) throws JSONException {
        if(jsonObject.has("title")) this.setTitle(jsonObject.getString("title"));
        if(jsonObject.has("popularity")) this.setPopularity(jsonObject.getString("popularity"));
        if(jsonObject.has("id")) this.setid(jsonObject.getString("id"));
        if(jsonObject.has("overview")) this.setOverview(jsonObject.getString("overview"));
//        if(jsonObject.has("poster_path")) this.setPosterUrl(jsonObject.getString("https://api.themoviedb.org/3/movie/" +  jsonObject.getString("poster_path") + "/images?api_key=430771852c3226d571db1e30d4d19a61&language=en-US"));
        if(jsonObject.has("poster_path")) this.setPosterUrl("https://image.tmdb.org/t/p/w600_and_h900_bestv2"+ jsonObject.getString("poster_path"));



    }

    private Movie() {}


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

  /*  public String setYear() {
        return year;
    }

    public void getYear() {
        this.year = year;
    }*/

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }


    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
