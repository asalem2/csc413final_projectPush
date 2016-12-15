package com.example.csc413_volley_template;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.system.ErrnoException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.csc413_volley_template.adapter.RecyclerViewAdapter;
import com.example.csc413_volley_template.model.Movie;

import org.json.JSONException;

import static com.example.csc413_volley_template.MainActivity.movieExtra;



import static com.example.csc413_volley_template.model.Movie.movies;

//import static com.example.csc413_volley_template.model.Movie;
public class Fullscreen extends AppCompatActivity {


    private TextView tvOverview;
    private TextView tvPopularity;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Movie movie = Movie.get();



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvOverview = (TextView) findViewById(R.id.tvOverview);
        tvPopularity = (TextView) findViewById(R.id.tvPopularity);
        tvTitle = (TextView) findViewById(R.id.tvTitle);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        String movieId = (String) getIntent()
                .getSerializableExtra(movieExtra);

        movie = movie.getMovie(movieId);

        String title = movie.getTitle();
        String overview = movie.getOverview();
        String popularity = movie.getPopularity();

//        RecyclerViewAdapter.CardViewHolder.class
        Log.d("movie", title + "");
        Log.d("hi", movie.getOverview() + "");
        tvTitle.setText(title);
        tvPopularity.setText(popularity);
        tvOverview.setText(movie.getOverview());
    }

}
