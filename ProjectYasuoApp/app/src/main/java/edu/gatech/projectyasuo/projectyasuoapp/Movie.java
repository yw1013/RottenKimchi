package edu.gatech.projectyasuo.projectyasuoapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Movie Class that has data of Searched Movies that will be saved in Hashmap
 * to be written in text file inside Android Internal Storage.
 * @author Jongwoo Jang
 */
@SuppressWarnings("ALL")
public class Movie {
    private int movieID;
    private String title;
    private int year;
    private String mpaaRating;
    private int userRating;
    private int criticsRating;
    private static Movie searchedMovie;
    private static String[] recentMovies;
    private static List<Movie> listMovies = new ArrayList<Movie>();
    private String apiTitle = "title";
    private String apiYear = "year";
    private String apiMpaaRating = "mpaa_rating";

    /**
     * static Method that Initializes Movie array of movies
     * @param a the movie array passed in
     */
    public static void initListMovies(List<Movie> a) {
        listMovies = a;
    }

    /**
     * static method that returns array of movies
     * @return list of movies in array
     */
    public static List<Movie> getListMovies() {
        return listMovies;
    }
    /**
     * Static Method that Initializes String array of recent movie titles
     * @param a the String array passed in.
     */
    public static void initRecentMovies(String[] a) {
        recentMovies = Arrays.copyOf(a, a.length);
    }

    /**
     * Static Method that returns String array of recent movie titles
     * @return recent movie titles.
     */
    public static String[] getRecentMovies() {
        return recentMovies;
    }

    /**
     * Static Method that Initializes the Searched Movie.
     * @param a the Movie a to be passed in from MoviePage.java
     */
    public static void initSearchedMovie(Movie a) {
        searchedMovie = a;
    }

    /**
     * static method that returns searched movies
     * @return searchedMovie
     */
    public static Movie getSearchedMovie() {
        return searchedMovie;
    }
    public Movie() {
    }

    /**
     * Movie constructor that initializes movie information
     * @param movieInfo the information of movie passed in
     * @throws IllegalArgumentException
     */
    public Movie(String movieInfo) throws IllegalArgumentException {
        try {
            JSONObject jobject = new JSONObject(movieInfo);
            this.movieID = jobject.getInt("id");
            this.title = jobject.getString(apiTitle);
            this.year = jobject.getInt(apiYear);
            this.mpaaRating = jobject.getString(apiMpaaRating);
            this.userRating = jobject.getInt("userRating");
            this.criticsRating = jobject.getInt("criticsRating");

        } catch (Exception e) {
            throw new IllegalArgumentException("cannot convert movieInfo into JSON object", e);
        }
    }

    /**
     * Initialized movie information retrieved from API as JSONObject
     * @param jobject the JSONObject from API
     * @throws IllegalArgumentException
     */
    public void movieFromAPI(JSONObject jobject) throws IllegalArgumentException {
        try {
            JSONArray arrMovies = jobject.getJSONArray("movies");
            JSONObject movie = arrMovies.getJSONObject(0);
//            movie.getString("title");
            this.movieID = movie.getInt("id");
            this.title = movie.getString(apiTitle);
            this.year = movie.getInt(apiYear);
            this.mpaaRating = movie.getString(apiMpaaRating);
            JSONObject ratings = movie.getJSONObject("ratings");
            this.userRating = ratings.getInt("audience_score");
        } catch (Exception e) {
            throw new IllegalArgumentException("cannot convert movieInfo into JSON object", e);
        }
    }

    public List<Movie> moviesFromAPI(JSONObject jobject) throws IllegalArgumentException {
        try {
            JSONArray arrMovies = jobject.getJSONArray("movies");
            ArrayList<Movie> retVal = new ArrayList<>();
            for(int i = 0; i < arrMovies.length(); i++) {
                JSONObject movie = arrMovies.getJSONObject(i);
                Movie input = new Movie();
                input.setMovieID(movie.getInt("id"));
                input.setTitle(movie.getString(apiTitle));
                Object obj = movie.get(apiYear);
                if(obj instanceof Integer) {
                    input.setYear(movie.getInt(apiYear));
                }
                input.setmpaaRating(movie.getString(apiMpaaRating));
                JSONObject ratings = movie.getJSONObject("ratings");
                input.setUserRating(ratings.getInt("audience_score"));
                retVal.add(input);
            }
            return retVal;
        } catch (Exception e) {
            throw new IllegalArgumentException("cannot convert movies into json objects", e);
        }
    }

    /**
     * Stores the titles of recent movies from JSONObject passed in
     * @param jobject the JSONObject from API
     * @return String array of titles
     * @throws IllegalArgumentException
     */
    public static String[] recentMovies(JSONObject jobject) throws IllegalArgumentException {
        try {
            JSONArray arrMovies = jobject.getJSONArray("movies");
            arrMovies.length();
            // int total = jobject.getInt("total");
            String[] recentList = new String[arrMovies.length()];
            for(int i = 0; i < recentList.length; i++) {
                JSONObject movie = arrMovies.getJSONObject(i);
                recentList[i] = movie.getString("title");
            }
            return recentList;

        } catch (Exception e) {
            throw new IllegalArgumentException("cannot get JSONObject", e);
        }
    }

    /**
     * Movie contructor that Initializes movie information
     * @param title movie title
     * @param year movie year
     * @param mpaa_rating movie mpaa rating
     * @param userRating user rating of movie
     */

    public Movie(int movieI, String movieT, int movieY, String mRating, int uRating, int cRating) {
        this.movieID = movieI;
        this.title = movieT;
        this.year = movieY;
        this.mpaaRating = mRating;
        this.userRating = uRating;
        this.criticsRating = cRating;
    }

    /**
     * getter method for Movie ID
     * @return movieID
     */
    public int getMovieID() {
        return movieID;
    }

    /**
     * setter method for Movie id
     * @param movieI movieID
     */
    private void setMovieID(int movieI) {
        this.movieID = movieI;
    }
    /**
     * getter method for Movie title
     * @return movie title
     */
    public String getTitle() {
        return title;
    }
    /**
     * setter method for Movie title
     * @param movieT Movie title
     */
    private void setTitle(String movieT) {  this.title = movieT; }
    /**
     * getter method for User rating
     * @return user rate
     */
    public int getUserRating() {
        return userRating;
    }
    /**
     * setter method for User Rating
     * @param uRating user rate
     */
    private void setUserRating(int uRating) {
        this.userRating = uRating;
    }
    /**
     * getter method for mpaa rating
     * @return mpaa rating
     */
    public String getmpaaRating() {
        return mpaaRating;
    }
    /**
     * setter method for mpaa rating
     * @param mRating mpaa rating
     */
    private void setmpaaRating(String mRating) {
        this.mpaaRating = mRating;
    }
    /**
     * getter method for Movie year
     * @return movie year
     */
    public int getYear() {
        return year;
    }
    /**
     * setter method for Movie year
     * @param movieY movie year
     */
    private void setYear(int movieY) {
        this.year = movieY;
    }
    /**
     * getter method for critics rating
     * @return critics rating
     */
    public int getCriticsRating() {
        return criticsRating;
    }
    /**
     * setter method for critics rating
     * @param cRating critics rating
     */
    public void setCriticsRating(int cRating) {
        this.criticsRating = cRating;
    }

    /**
     * toString of movie information
     * @return toString of movie information
     * @throws NullPointerException
     */
    public String movieInfo() throws JSONException {

            JSONObject jobject = new JSONObject();
            jobject.put("id", this.movieID);
            jobject.put("title", this.title);
            jobject.put("year", this.year);
            jobject.put("mpaa_rating", this.mpaaRating);
            jobject.put("userRating",this.userRating); //audience score
            jobject.put("criticsRating", this.criticsRating);

            return jobject.toString();

    }
}