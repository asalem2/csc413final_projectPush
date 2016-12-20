package com.example.csc413_volley_template.model;

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
 * Model class for videoId1
 */
public class VideoId {

    private String id;

    private static VideoId videoId1;
    private static final List<VideoId> VIDEO_IDS = new ArrayList<>();

    public static VideoId get(){
        if(videoId1 == null){
            videoId1 = new VideoId();
        }
        return videoId1;
    }

    public VideoId getVideoId(String id){
        for (VideoId videoId : VIDEO_IDS){
            if(videoId.getid().equals(id)){
                return videoId;
            }
        }
        return null;
    }

    /**
     *
     * @param jsonObject    {@link JSONObject} response, received in Volley success listener
     * @return  list of VIDEO_IDS
     * @throws JSONException
     */
    public static List<VideoId> parseJson(JSONObject jsonObject) throws JSONException{
        VIDEO_IDS.clear();
        // Check if the JSONObject has object with key "Search"
        if(jsonObject.has("results")){
            // Get JSONArray from JSONObject
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for(int i = 0; i < jsonArray.length(); i++){
                // Create new Movie object from each JSONObject in the JSONArray
                if (jsonArray.getJSONObject(0).has("key"))
                    VIDEO_IDS.add(new VideoId(jsonArray.getJSONObject(i)));
            }
        }

        return VIDEO_IDS;
    }

    /**
     * <p>Class constructor</p>
     * <p>Sample Movie JSONObject</p>
     * <pre>
     * {
     *  "Title": "Batman Begins",
     *  "Year": "2005",
     *  "imdbID": "tt0372784",
     *  "Type": "videoId1",
     *  "Poster": "https://images-na.ssl-images-amazon.com/images/M/MV5BNTM3OTc0MzM2OV5BMl5BanBnXkFtZTYwNzUwMTI3._V1_SX300.jpg"
     * }
     * </pre>
     * @param jsonObject    {@link JSONObject} from each item in the search result
     * @throws JSONException     when parser fails to parse the given JSON
     */
    public VideoId(JSONObject jsonObject) throws JSONException {

        if(jsonObject.has("key")) this.setid(jsonObject.getString("key"));

    }

    private VideoId() {}

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }
}
