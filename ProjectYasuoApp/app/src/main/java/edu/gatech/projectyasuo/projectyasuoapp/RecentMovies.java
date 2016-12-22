package edu.gatech.projectyasuo.projectyasuoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Implementation of Recent Movies
 * @author Jongwoo Jang
 */
public class RecentMovies extends AppCompatActivity implements View.OnClickListener {



    private final List<Movie> listMovies = Movie.getListMovies();
    private String[] recentMovies;
    private Spinner userMajor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_movies);
        recentMovies = Movie.getRecentMovies();
        ListView listView = (ListView) findViewById(R.id.listView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recentMovies);
        listView.setAdapter(adapter);



        ImageButton yearFilter = (ImageButton) findViewById(R.id.yearFilter);
        ImageButton userRatingFilter = (ImageButton) findViewById(R.id.userRatingFilter);
        ImageButton criticsRatingFilter = (ImageButton) findViewById(R.id.criticsRatingFilter);
        ImageButton majorFilter = (ImageButton) findViewById(R.id.majorFilter);
        RatingList.getRatingList().updateRating(listMovies);

       userMajor = (Spinner) findViewById(R.id.userMajor_spinner);
        ArrayAdapter<CharSequence> majorAdapter = ArrayAdapter.createFromResource(this, R.array.major_arrays, android.R.layout.simple_spinner_item);
        majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userMajor.setAdapter(majorAdapter);

        /**
         * Filters movie by year
         */
        yearFilter.setOnClickListener(this);

        /**
         * Filters movie by Ratings from API
         */
        userRatingFilter.setOnClickListener(this);


        /**
         * Filter movie by Ratings from Application
         */
        criticsRatingFilter.setOnClickListener(this);

        /**
         * Filter movies by major from highest critics score to lowest.
         */
        majorFilter.setOnClickListener(this);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private final MovieList movieList = MovieList.getMovieList();

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String movieTitle = adapter.getItem(position);
                Movie movieClicked = movieList.getMovie(movieTitle);
                Movie.initSearchedMovie(movieClicked);

                //finish();
                startActivity(new Intent(RecentMovies.this, CommentRate.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.yearFilter) {
            // ArrayList<Movie> listMovies = Movie.getListMovies();
            Collections.sort(listMovies, new Comparator<Movie>() {
                public int compare(Movie a, Movie b) {
                    if (a.getYear() > b.getYear()) {
                        return -1;
                    } else if (a.getYear() < b.getYear()) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
            String[] sorted = new String[recentMovies.length];
            int i = 0;
            for (Movie m : listMovies) {
                sorted[i] = m.getTitle();
                i++;
            }
            Movie.initRecentMovies(sorted);
            finish();
            startActivity(new Intent(RecentMovies.this, RecentMovies.class));
        } else if(v.getId()==R.id.userRatingFilter) {
            //   ArrayList<Movie> listMovies = Movie.getListMovies();
            Collections.sort(listMovies, new Comparator<Movie>() {
                public int compare(Movie a, Movie b) {
                    if (a.getUserRating() > b.getUserRating()) {
                        return -1;
                    } else if (a.getUserRating() < b.getUserRating()) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
            String[] sorted = new String[recentMovies.length];
            int i = 0;
            for (Movie m : listMovies) {
                sorted[i] = m.getTitle();
                i++;
            }
            Movie.initRecentMovies(sorted);
            finish();
            startActivity(new Intent(RecentMovies.this, RecentMovies.class));
        } else if(v.getId()==R.id.criticsRatingFilter) {
            //  ArrayList<Movie> listMovies = Movie.getListMovies();
            // MovieList.getMovieList().loadMovie();
            Collections.sort(listMovies, new Comparator<Movie>() {
                public int compare(Movie a, Movie b) {
                    if (a.getCriticsRating() > b.getCriticsRating()) {
                        return -1;
                    } else if (a.getCriticsRating() < b.getCriticsRating()) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
            String[] sorted = new String[listMovies.size()];
            int i = 0;
            for (Movie m : listMovies) {
                sorted[i] = m.getTitle();
                i++;
            }
            Movie.initRecentMovies(sorted);
            finish();
            startActivity(new Intent(RecentMovies.this, RecentMovies.class));
        } else if(v.getId()==R.id.majorFilter) {
            String major = userMajor.getSelectedItem().toString();
            List<Movie> listMoviesMajor;

            //go through rating
            //if rate's user id's major equals && rate's movie id equals then put the movie.
            //then update with critic score by only that major. make a function for a hint.
            listMoviesMajor = RatingList.getRatingList().updateByMajor(listMovies, major);

            Collections.sort(listMoviesMajor, new Comparator<Movie>() {
                public int compare(Movie a, Movie b) {
                    if (a.getCriticsRating() > b.getCriticsRating()) {
                        return -1;
                    } else if (a.getCriticsRating() < b.getCriticsRating()) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
            if(RatingList.getNumberFound() != 0) {
                String[] sorted = new String[RatingList.getNumberFound()];
                int i = 0;
                for (Movie m : listMoviesMajor) {
                    sorted[i] = m.getTitle();
                    i++;
                }
                Movie.initRecentMovies(sorted);

            } else {
                Toast.makeText(getApplicationContext(),
                        "No rating found with your major", Toast.LENGTH_SHORT).show();
                finish();
            }

            finish();
            startActivity(new Intent(RecentMovies.this, RecentMovies.class));
        }
    }
}