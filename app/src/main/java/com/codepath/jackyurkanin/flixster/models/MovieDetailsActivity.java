package com.codepath.jackyurkanin.flixster.models;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.jackyurkanin.flixster.R;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieDetailsActivity extends AppCompatActivity {

    // the movie to display
    Movie movie;
    Context context;

    // the view objects
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView trImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        context = getApplicationContext();
        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        rbVoteAverage = findViewById(R.id.rbVoteAverage);
        trImage = findViewById(R.id.trImage);

        // unwrap the movie passed in via intent, using its simple name as a key
        movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        String imageUrl; // getting right image based on orientation
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageUrl = movie.getBackdropPath();
        } else {
            imageUrl = movie.getPosterPath();
        }
        int radius = 30;
        int margin = 10;
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.flicks_movie_placeholder)
                .centerCrop()
                .transform(new RoundedCornersTransformation(radius, margin))
                .into(trImage);

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage / 2.0f);

    }
}