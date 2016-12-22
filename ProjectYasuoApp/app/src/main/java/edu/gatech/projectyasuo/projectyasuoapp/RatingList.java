package edu.gatech.projectyasuo.projectyasuoapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Registration class that allows Users to Register and save Users data
 * into our database.
 *
 * @author Jongwoo Jang
 */
public final class RatingList {
    private final List<CriticsRating> ratings = new ArrayList<>();
    private static final RatingList LIST_RATINGS = new RatingList();
    private static String thePath = "";

    public static int getNumberFound() {
        return numberFound;
    }



    private static int numberFound;

    /**
     * Default Constructor
     */
    private RatingList() {
    }

    /**
     * Initializes path to database
     * @param path the path to database
     */
    public static void initPath(String path) {
        thePath = path;
    }

    /**
     * getter for RatingList
     * @return LIST_RATINGS
     */
    public static RatingList getRatingList() {
        return LIST_RATINGS;
    }

    /**
     * Adds the CriticsRating object into ArrayList of Ratings
     * @param a the CriticsRating to add
     */
    public void addRating(CriticsRating a) {
        ratings.add(a);
        LIST_RATINGS.saveRate();
    }

    /**
     * Updates the movies by major with the major passed in as parameter.
     * From ratings object, check the major of user
     * From movie object, check the movie id of movie
     * @param movies the ArrayList of movies to update from
     * @param major the major to sort by
     * @return the new ArrayList of Movies within the major passed in inside the Movies ArrayList
     */
    public List<Movie> updateByMajor(List<Movie> movies, String major) {
        numberFound = 0;
        //go through rating
        //if rate's user id's major equals && rate's movie id equals then put the movie.
        //then update with critic score by only that major. make a function for a hint.
        ArrayList<Movie> retVal = new ArrayList<>();
        for(CriticsRating rate : ratings) {
            if(UserList.getUserList().getUser(rate.getUserEmail()).getUserMajor().equals(major) && contains(movies, rate)) {
                retVal.add(MovieList.getMovieList().getMovie(rate.getMovieID()));
                numberFound++;
            }
        }
        return retVal;
    }

    /**
     * History of Users rating
     * @return the ArrayList of ratings
     */
    public List<CriticsRating> getUserHistory() {
        List<CriticsRating> retVal = new ArrayList<>();
        for(CriticsRating r : ratings) {
            if(r.getUserEmail().equals(User.getLoggedInUser().getUserEmail())) {
                retVal.add(r);
            }
        }
        return retVal;
    }

    /**
     * To find whether the movie from Critics Rating object is inside the ArrayList of movies
     * @param movies the movies to search through
     * @param rate the rate to get movie id to compare
     * @return true if found, false if not found
     */
    private boolean contains(List<Movie> movies, CriticsRating rate) {
        for(Movie movie : movies) {
            if(movie.getMovieID() == rate.getMovieID()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates all the movies with the ratings inside the database.
     * @param movies list of movies from the database
     */
    public void updateRating(List<Movie> movies) {
        for(Movie movie : movies) {
            movie.setCriticsRating(LIST_RATINGS.getAvgCriticsRate(movie.getMovieID()));
        }
    }

    /**
     * Update the movie its critics rating with parameter passed in
     * @param cRate the Critics Rate to update
     * @param movieToRate the Movie to be updated
     */
    public void updateRating(int cRate, Movie movieToRate) {
        boolean found = false;
        for (CriticsRating rate : ratings) {
            if (User.getLoggedInUser().getUserEmail().equals(rate.getUserEmail()) && movieToRate.getMovieID() == rate.getMovieID()) {
                found = true;
                rate.setcriticsRate(cRate);

            }
        }
        if (!found) {
            CriticsRating theRate = new CriticsRating(movieToRate.getMovieID(), User.getLoggedInUser().getUserEmail(), cRate);
            ratings.add(theRate);

        }
        LIST_RATINGS.saveRate();
    }

    /**
     * Calculates the Average Critics Rate of the movie passed in as movie id
     * @param movieID the Id of movie to calculate Average Critics Rate
     * @return the Average.
     */
    public int getAvgCriticsRate(int movieID) {
        int sum = 0;
        int count = 0;
        for(CriticsRating rate : ratings) {
            if (rate.getMovieID() == movieID) {
                sum += rate.getcriticsRate();
                count++;
            }
        }
        if(count == 0) {
            return 0;
        }
        return sum/count;
    }
//    public boolean existingRate(int id) {
//        return ratings.;
//    }

    /**
     * Save data to our database
     */
    private void saveRate() {
        BufferedWriter writeout = null;

        try {
            writeout = new BufferedWriter(new FileWriter(thePath));
            for(CriticsRating curRate : ratings) {
                writeout.append(curRate.ratingInfo()).append("\n");
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

    /**
     * load rates from our database
     */
    public void loadRates() {
        ratings.clear();
        BufferedReader writein = null;
        String rateInfo;
        try {
            writein = new BufferedReader(new FileReader(thePath));
            while((rateInfo = writein.readLine()) != null) {
                CriticsRating getRateFromDB = new CriticsRating(rateInfo);

                ratings.add(getRateFromDB);

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