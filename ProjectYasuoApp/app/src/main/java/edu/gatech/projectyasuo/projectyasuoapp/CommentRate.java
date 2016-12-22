package edu.gatech.projectyasuo.projectyasuoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Allows User to comment and rate movie
 */
public class CommentRate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_rate);

        final User loggedUser = User.getLoggedInUser();
        final Movie movieClicked = Movie.getSearchedMovie();

        ImageButton submit = (ImageButton) findViewById(R.id.submit);
        ImageButton cancel = (ImageButton) findViewById(R.id.backToSearch);
        final RatingBar star = (RatingBar) findViewById(R.id.ratingBar);

        TextView showID = (TextView) findViewById(R.id.email);
        TextView showEmail = (TextView) findViewById(R.id.major);
        TextView showYear = (TextView) findViewById(R.id.movieYear);
        TextView showMovie = (TextView) findViewById(R.id.movieTitle);
        TextView showAPIScore = (TextView) findViewById(R.id.APIScore);
        TextView showCriticsScore = (TextView) findViewById(R.id.criticsScore);

        showID.setText("Email: " + loggedUser.getUserEmail());
        showEmail.setText("Major: " + loggedUser.getUserMajor());
        showYear.setText("Movie: " + movieClicked.getYear());
        showMovie.setText("Movie: " + movieClicked.getTitle());
        showAPIScore.setText("Audience Score: " + movieClicked.getUserRating());
        showCriticsScore.setText("Critics Score: " + movieClicked.getCriticsRating());


        //star.setRating((float) loggedUser.getMovieRate().get(Movie.getSearchedMovie().getTitle()));


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //startActivity(new Intent(CommentRate.this, RecentMovies.class));
            }
        });

        /**
         * Updates the Rating in rating list with the movie user is rating and the rating user input
         * Saves the rating in the database.
         * For each movie, its critics score will be updated as average of all the critics score on that movie from database
         * Then will be updated into movie database.
         */
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double starNum = star.getRating();
                starNum = starNum * 10;
                RatingList.getRatingList().updateRating((int) starNum, movieClicked);
                movieClicked.setCriticsRating(RatingList.getRatingList().getAvgCriticsRate(movieClicked.getMovieID()));
                MovieList.getMovieList().addMovie(movieClicked);
                Toast.makeText(getApplicationContext(), "Your rating has been submitted", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(CommentRate.this, MoviePage.class));
            }
        });


    }

}