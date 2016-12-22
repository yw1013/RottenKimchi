package edu.gatech.projectyasuo.projectyasuoapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Movie searching page that allows user to type in a name of movie to search through either
 * API or Internal Android Data file
 * @author Jongwoo Jang
 */
public class MoviePage extends AppCompatActivity implements View.OnClickListener {
    private JSONObject jobject;
    private boolean passed;
    private final MovieList movieList = MovieList.getMovieList();
    private EditText userInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_page);

        userInput = (EditText) findViewById(R.id.movieName);
        ImageButton home = (ImageButton) findViewById(R.id.homeButton);
        ImageButton toSearch = (ImageButton) findViewById(R.id.SearchBtn);
        ImageButton recentSearchM = (ImageButton) findViewById(R.id.MListBtn);
        ImageButton recentSearchD = (ImageButton) findViewById(R.id.DListBtn);
        ImageButton searchM = (ImageButton) findViewById(R.id.SearchMBtn);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(MoviePage.this, MainPage.class));
            }
        });

        toSearch.setOnClickListener(this);

        searchM.setOnClickListener(this);
        recentSearchM.setOnClickListener(this);

        recentSearchD.setOnClickListener(this);
    }
    /**
     * Initializes jobject with JSONObject passed in
     * @param input the JSONObject
     */
    private void initializeJObject(JSONObject input) {
        jobject = input;
    }

    /**
     * Access the Http with url passed in. Read in the data
     * and returns the JSONObject
     * @param url the url of api to access
     * @return JSONObject from the api
     */
    private JSONObject getRequest(String url) {
        JSONObject js;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            js = new JSONObject(response.toString());
            return js;

        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Formats the name of movie into correct API Search term.
     * @param input the title of movie user typed.
     * @return correct format of title
     */
    private String correctSearchTerm(String[] input) {
        String retVal =input[0];
        for(int i = 1; i < input.length; i++) {
            retVal += "+" + input[i];
        }
        return retVal;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.SearchBtn) {
            if(userInput.getText().toString().equals(null) || userInput.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(),
                        "Type a movie name ....", Toast.LENGTH_SHORT).show();
            } else {
                MovieList.getMovieList().loadMovie();
                if (movieList.existingMovie(userInput.getText().toString())) {
                    Movie movie = movieList.getMovie(userInput.getText().toString());
                    Movie.initSearchedMovie(movie);
                    startActivity(new Intent(MoviePage.this, SearchMovie.class));
                } else {
                    String[] searchTerm = userInput.getText().toString().split(" ");
                    String corrected = correctSearchTerm(searchTerm);
                    String movieUrl = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=yedukp76ffytfuy24zsqk7f5&q=" + corrected + "&page_limit=1";
                    jobject = null;
                    new searchInBackground().execute(movieUrl);
                    Toast.makeText(getApplicationContext(),
                            "Searching Movie ....", Toast.LENGTH_SHORT).show();
                    while (jobject == null) {

                    }
                    Movie searchMovie = new Movie();
                    searchMovie.movieFromAPI(jobject);
                    Movie.initSearchedMovie(searchMovie);
                    movieList.addMovie(Movie.getSearchedMovie());
                    startActivity(new Intent(MoviePage.this, SearchMovie.class));

                }
            }
        } else if(v.getId()==R.id.SearchMBtn) {
            if (userInput.getText().toString().equals(null) || userInput.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(),
                        "Type a movie name....", Toast.LENGTH_SHORT).show();

            } else {
                String[] searchTerm = userInput.getText().toString().split(" ");
                String corrected = correctSearchTerm(searchTerm);
                String movieUrl = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=yedukp76ffytfuy24zsqk7f5&q=" + corrected + "&page_limit=30";
                jobject = null;
                passed = false;
                new searchInBackground().execute(movieUrl);

                while (!passed) {

                }
                Movie searchedMovie = new Movie();
                String[] recentMovies = Movie.recentMovies(jobject);
                Movie.initRecentMovies(recentMovies);

                List<Movie> moviesList = searchedMovie.moviesFromAPI(jobject);
                for(Movie movie : moviesList) {
                    if (!(movieList.existingMovie(movie.getTitle()))) {
                        movieList.addMovie(movie);
                    }
                }
                Movie.initListMovies(moviesList);

                startActivity(new Intent(MoviePage.this, RecentMovies.class));
            }
        } else if(v.getId()==R.id.MListBtn) {

            String url = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?apikey=yedukp76ffytfuy24zsqk7f5&q=&page_limit=30";
            jobject = null;
            passed = false;
            new searchInBackground().execute(url);
            Toast.makeText(getApplicationContext(),
                    "Getting Recent Movies ....", Toast.LENGTH_SHORT).show();
            while (!passed) {
            }
            Movie searchedMovie = new Movie();
            String[] recentMovies = Movie.recentMovies(jobject);
            Movie.initRecentMovies(recentMovies);

            List<Movie> moviesList = searchedMovie.moviesFromAPI(jobject);
            for(Movie movie : moviesList) {
                if (!(movieList.existingMovie(movie.getTitle()))) {
                    movieList.addMovie(movie);
                }

            }
            Movie.initListMovies(moviesList);

            startActivity(new Intent(MoviePage.this, RecentMovies.class));
        } else if(v.getId()==R.id.DListBtn) {
            String url = "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/new_releases.json?apikey=yedukp76ffytfuy24zsqk7f5&page_limit=30";
            jobject = null;
            passed = false;
            new searchInBackground().execute(url);
            Toast.makeText(getApplicationContext(),
                    "Getting Recent DVDs ....", Toast.LENGTH_SHORT).show();
            while (!passed) {

            }
            Movie searchedMovie = new Movie();
            String[] recentMovies = Movie.recentMovies(jobject);
            Movie.initRecentMovies(recentMovies);

            List<Movie> moviesList = searchedMovie.moviesFromAPI(jobject);
            for (Movie movie : moviesList) {
                if (!(movieList.existingMovie(movie.getTitle()))) {
                    movieList.addMovie(movie);
                }
            }
            Movie.initListMovies(moviesList);

            startActivity(new Intent(MoviePage.this, RecentMovies.class));
        }
    }

    /**
     * AsyncTask to do the operation of searching movie from API in a
     * thread in background so that android application would work just fine
     * while search process occurs.
     * Parameters of AsyncTask is
     * String the name of movie typed in by user
     * Void don't have to worry about it in this case
     * Movie the movie that will be returned
     * Return JSONObject the JSONObject that was searched
     */
    private class searchInBackground extends AsyncTask<String, Void, JSONObject> {

        protected JSONObject doInBackground(String... movieUrl) {
            JSONObject jobj = getRequest(movieUrl[0]);
            initializeJObject(jobj);

            passed = true;
            return jobj;

        }


    }
}