package com.example.csc413_volley_template;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;


//import com.example.csc413_volley_template.MapsActivity.MapsActivity;
import com.example.csc413_volley_template.MapsActivity.MapsActivity;
import com.example.csc413_volley_template.Splash.SplashActivity;
import com.example.csc413_volley_template.adapter.RecyclerViewAdapter;
import com.example.csc413_volley_template.controller.JsonController;
import com.example.csc413_volley_template.model.Movie;
import com.example.csc413_volley_template.youtube.PlayerViewDemoActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
//import com.example.csc413_volley_template.Maps.GPSTracker;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements
        SearchView.OnQueryTextListener,
        RecyclerViewAdapter.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{

    FloatingActionButton FAB;
    Location mLastLocation;
    GoogleApiClient mGoogleApiClient;

    private RecyclerViewAdapter adapter;
    JsonController controller;
    public static final String movieExtra = "ahmed";
    public static final String latitudeExtra ="There";
    public static final String longitutedExtra ="Here";

    TextView textView;
    RecyclerView recyclerView;


    public void onConnected(Bundle connectionHint) {



        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            FAB.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    String lat = String.valueOf(mLastLocation.getLatitude());
                    String lon = String.valueOf(mLastLocation.getLongitude());
                    Toast.makeText(MainActivity.this, lat + " " + lon, Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    intent.putExtra(latitudeExtra, lat);
                    intent.putExtra(longitutedExtra, lon);


                    startActivity(intent);
                }
            });
    //        mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
    //        mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        }
    }



    @Override
    public void onConnectionSuspended(int oCSuspended){

    }
    @Override
    public void onConnectionFailed(ConnectionResult cResults){

    }
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    /*GPSTracker gps = new GPSTracker (this);
    double latitude = gps.getLatitude();
    double longitude= gps.getLongitude();*/


    @Override
//    protected void onCreateView(Bundle savedInstanceState, LayoutInflater inflater, ViewGroup container) {
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.tvEmptyRecyclerView);
        textView.setText("Search for movies using SearchView in toolbar");

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

//        final View rootView = inflater.inflate(R.layout.fragment_movie, container, false);


        /*Button mapBtn = (Button) rootView.findViewById(R.id.MapButton);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            }
        });*/

        FAB = (FloatingActionButton) findViewById(R.id.fab);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerViewAdapter(new ArrayList<Movie>());
        adapter.setListener(this);

        controller = new JsonController(
            new JsonController.OnResponseListener() {
                @Override
                public void onSuccess(List<Movie> movies) {
                    if(movies.size() > 0) {
                        textView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.invalidate();
                        adapter.updateDataSet(movies);
                        Log.d("update",movies.get(0).getTitle());
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(String errorMessage) {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("Failed to retrieve data");
                    Toast.makeText(MainActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                }
        });
    }

    /**
     * create options from menu/menu_activity_main.xml where we have searchView as one of the menu items
     * @param menu
     * @return
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint("Search");
        searchView.setSubmitButtonEnabled(true);
        return true;
    }

    /**
     * this method is invoked when user presses search button in soft keyboard
     * @param query query text in search view
     * @return  boolean
     *                 <p> - true  - query handled </p>
     *                 <p> - false - query not handled (returning false will collapse soft keyboard)</p>
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        if(query.length() > 1) {
            controller.cancelAllRequests();
            controller.sendRequest(query);
            return false;
        } else {
            Toast.makeText(MainActivity.this, "Must provide more than one character", Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            textView.setText("Must provide more than one character to search");
            return true;
        }
    }

    /**
     * this method is invoked on every key press of soft keyboard while user is typing
     * @param newText newText is updated query text on every input of user from soft keyboard
     * @return boolean
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.length() > 1) {
            controller.cancelAllRequests();
            controller.sendRequest(newText);
        } else if(newText.equals("")) {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
        return true;
    }

    /**
     * Interface Implementation
     * <p>This method will be invoked when user press anywhere on cardview</p>
     */

    @Override
    public void onCardClick(Movie movie) {

        Toast.makeText(this, movie.getTitle() + " ",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(), Fullscreen.class);
        intent.putExtra(movieExtra, movie.getid());
        startActivity(intent);
    }

    /**
     * Interface Implementation
     * <p>This method will be invoked when user press on poster of the movie</p>
     */
    @Override
    public void onPosterClick(Movie movie) {
        Toast.makeText(this, movie.getTitle() + " trailer loading", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(),PlayerViewDemoActivity.class);
        intent.putExtra(movieExtra, movie.getid());
        if (movie.getMovie(movie.getid()).getVideoId() != null && !movie.getMovie(movie.getid()).getVideoId().isEmpty())
            startActivity(intent);
    }
}
