package edu.gatech.projectyasuo.projectyasuoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;


/**
 * Implementation of Search Movie
 * @author Jongwoo Jang
 */
public class SearchMovie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);



        EditText title = (EditText) findViewById(R.id.title);
        EditText year = (EditText) findViewById(R.id.year);
        EditText mpaa = (EditText) findViewById(R.id.Mpaa);
        EditText userRate = (EditText) findViewById(R.id.userRate);
        EditText criticsRate = (EditText) findViewById(R.id.criticsRate);


        ImageButton toMoviePage = (ImageButton) findViewById(R.id.backToMain);
        ImageButton toCommentRate = (ImageButton) findViewById(R.id.commentButton);


        title.setText(Movie.getSearchedMovie().getTitle());
        year.setText(String.valueOf(Movie.getSearchedMovie().getYear()));
        mpaa.setText(Movie.getSearchedMovie().getmpaaRating());
        userRate.setText(String.valueOf(Movie.getSearchedMovie().getUserRating()));
        criticsRate.setText(String.valueOf(Movie.getSearchedMovie().getCriticsRating()));

        toMoviePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                startActivity(new Intent(SearchMovie.this, MoviePage.class));
            }
        });

        toCommentRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SearchMovie.this, CommentRate.class));
            }
        });
    }
}