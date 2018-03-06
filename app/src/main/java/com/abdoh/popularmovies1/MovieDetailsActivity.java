package com.abdoh.popularmovies1;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;

/**
 * Created by abdoh.
 */

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        TextView titleTextView = (TextView) findViewById(R.id.TITLE);
        ImageView posterImageView = (ImageView) findViewById(R.id.POSTER);
        TextView plotTextView = (TextView) findViewById(R.id.PLOT);
        TextView ratingTextView = (TextView) findViewById(R.id.RATING);
        TextView releaseDateTextView = (TextView) findViewById(R.id.RELEASE_DATE);

        Intent intent = getIntent();
        MovieInfo movieInfo = intent.getParcelableExtra(getString(R.string.parcel_movie));


        //set the poster
        Picasso.with(this)
                .load(movieInfo.getPoster())
                .resize(getResources().getInteger(R.integer.poster_width), getResources().getInteger(R.integer.poster_height))
                .error(R.drawable.no_poster)
                .placeholder(R.drawable.loading_poster)
                .into(posterImageView);

        //set the title
        titleTextView.setText(movieInfo.getTitle());


        //set plot
        if (movieInfo.getPlot() == null) {
            plotTextView.setText(R.string.no_plot_found);
        }else{
            plotTextView.setText(movieInfo.getPlot());
        }

        //set rating
        ratingTextView.setText(movieInfo.getDetailedVoteAverage());

        //set release date
        releaseDateTextView.setText(movieInfo.getReleaseDate());
    }
}
