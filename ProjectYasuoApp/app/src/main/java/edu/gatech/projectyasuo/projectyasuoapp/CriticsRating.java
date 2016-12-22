package edu.gatech.projectyasuo.projectyasuoapp;

/**
 * Registration class that allows Users to Register and save Users data
 * into our database.
 *
 * @author Jongwoo Jang
 */
class CriticsRating {
    private int movieID;
    private String userEmail;
    private int criticsRate;



    /**
     * Initializes variables with string read from the database file
     * @param readFile the line read from database file
     */
    public CriticsRating(String readFile) {
        String[] info = readFile.split("/");
        movieID = Integer.parseInt(info[0]);
        userEmail = info[1];
        criticsRate = Integer.parseInt(info[2]);
    }

    /**
     * Initializes variables with parameters
     * @param movieI the movie id
     * @param userE the user email
     * @param cRate the rate of user
     */
    public CriticsRating(int movieI, String userE, int cRate) {
        this.movieID = movieI;
        this.userEmail = userE;
        this.criticsRate = cRate;
    }

    /**
     * String form the information of each rating object
     * @return the information of rating object in right format
     */
    public String ratingInfo() {
        return getMovieID() + "/" + getUserEmail() + "/" + getcriticsRate();
    }

    public int getcriticsRate() {
        return criticsRate;
    }

    public void setcriticsRate(int cRate) {
        this.criticsRate = cRate;
    }

    public int getMovieID() {
        return movieID;
    }

    public String getUserEmail() {
        return userEmail;
    }

}