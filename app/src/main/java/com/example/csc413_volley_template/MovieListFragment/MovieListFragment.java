/*
package com.example.csc413_volley_template.MovieListFragment;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import com.example.csc413_volley_template.R;

*/
/**
 * Created by NoLimitProduction on 12/13/16.
 *//*


public class MovieListFragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            v = inflater.inflate(R.layout.fragment_movie_list, parent, false);
//        } else {
            v = super.onCreateView(inflater, parent, savedInstanceState);
//        }

        View addButton = v.findViewById(R.id.add_button);
        addButton.setOutlineProvider(new ViewOutlineProvider() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void getOutline(View view, Outline outline) {
                int diameter = getResources().getDimensionPixelSize(R.dimen.diameter);
                outline.setOval(0, 0, diameter, diameter);
            }
        });
        addButton.setClipToOutline(true);


    }
}
*/
