package edu.gatech.projectyasuo.projectyasuoapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * MovieList class that implements HashMap and Backend Recording
 * into the Android's Internal Storage (Textfile is created)
 * @author Jongwoo Jang
 */
public final class MovieList {
    private final Map<String, Movie> movies = new HashMap<>();

    private static final MovieList LIST = new MovieList();
    private static String thePath = "";

    private MovieList() {
    }

    /**
     * Sets the Critics Rating of movie by calculating the average of that movies rating from database
     */
    public void setAvgCriticsRate() {
        for(Movie m : movies.values()) {
            m.setCriticsRating(RatingList.getRatingList().getAvgCriticsRate(m.getMovieID()));
        }
        // Movie.getSearchedMovie().setCriticsRating(RatingList.getRatingList().getAvgCriticsRate(Movie.getSearchedMovie().getMovieID()));
    }

    /**
     * Initializes the path to database
     * @param path the path to database
     */
    public static void initPath(String path) {
        thePath = path;
    }

    public static MovieList getMovieList() {
        return LIST;
    }

    /**
     * adds movie and saves it
     * @param a the movie
     */
    public void addMovie(Movie a) {
        movies.put(a.getTitle(), a);
        LIST.saveMovie();
    }
    public Movie getMovie(String title) {
        return movies.get(title);
    }

    /**
     * gets the movie by movie ID from HashMap
     * @param movieID the movieID to search
     * @return movie with movieID
     */
    public Movie getMovie(int movieID) {
        for(Movie cur : movies.values()) {
            if(cur.getMovieID() == movieID) {
                return cur;
            }
        }
        return null;
    }

    public boolean existingMovie(String title) {

        return movies.containsKey(title);
    }

    private void saveMovie() {
        BufferedWriter writeout = null;

        try {
            writeout = new BufferedWriter(new FileWriter(thePath));
            for(Movie curMovie : movies.values()) {
                try {
                    writeout.append(curMovie.movieInfo()).append("\n");

                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
            writeout.close();

        } catch (Exception e) {
            if(writeout != null) {
                try {
                    writeout.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public void loadMovie() {
        movies.clear();
        BufferedReader writein = null;
        String movieInfo;
        try {
            writein = new BufferedReader(new FileReader(thePath));
            while((movieInfo = writein.readLine()) != null) {
                Movie getMovieFromDB = new Movie(movieInfo);

                movies.put(getMovieFromDB.getTitle(), getMovieFromDB);

            }
        } catch (Exception e) {
            if(writein != null) {
                try {
                    writein.close();
                } catch (IOException e1) {
                }
            }
        }
    }

}