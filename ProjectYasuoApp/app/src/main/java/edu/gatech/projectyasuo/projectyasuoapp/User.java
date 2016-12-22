package edu.gatech.projectyasuo.projectyasuoapp;


/**
 * User Class that has data of Registered Users that will be saved in Hashmap
 * to be written in text file inside Android Internal Storage.
 * @author Jongwoo Jang
 */
@SuppressWarnings("SameParameterValue")
public class User {
    private String userID;
    private String userPW;
    private final String userEmail;
    private String userNickname;
    private String userAge;
    private String userMajor;
    private String userStanding;
    private String permission;
    private String isAdmin;
    private static User loggedInUser;

    /**
     * check if logged in user is admin
     * @return
     */
    public boolean checkAdmin() {
        int i = Integer.parseInt(isAdmin);
        return  i == 1;
    }

    /**
     * Static Method that Initializes the Logged in User.
     * @param a the User a to be passed in from LoginActivity.java
     */
    public static void initloggedInUser(User a) {
        loggedInUser = a;
    }

    /**
     * Static Get Method for Logged in User
     * @return Logged in User.
     */
    public static User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Constructor that initializes User information
     * Break the User Info in one line of String and initialize variables with them.
     * @param readFile the User Information in Text File as one line
     */
    public User(String readFile) {
        String[] info = readFile.split("/");
        int i = 0;
        userID = info[i];
        userPW = info[++i];
        userEmail = info[++i];
        userNickname = info[++i];
        userAge = info [++i];
        userMajor = info[++i];
        userStanding = info[++i];
        permission = info[++i];
        isAdmin = info[++i];
    }

    /**
     * Contructor that initializes User information
     * @param userI the userID to be initialized
     * @param uPW the userPW to be initialized
     * @param uEmail the userEmail to be initialized
     * @param uNickname the userNickname to be initialized
     * @param uAge the userAgeto be initialized
     * @param uMajor the userMajor to be initialized
     * @param uStanding the userStanding to be initialized
     * @param uPermission the permission to be initialized
     * @param isAdm the admin or not to be initialized
     */
    public User(String userI, String uPW, String uEmail, String uNickname, String uAge, String uMajor, String uStanding, String uPermission, String isAdm) {
        this.userID = userI;
        this.userPW = uPW;
        this.userEmail = uEmail;
        this.userNickname = uNickname;
        this.userAge = uAge;
        this.userMajor = uMajor;
        this.userStanding = uStanding;
        this.permission = uPermission;
        this.isAdmin = isAdm;
    }

    /**
     * getter method for isAdmin
     * @return isAdmin
     */
    private String getIsAdmin() {
        return isAdmin;
    }

    /**
     * setter for Admin
     * @param isAdm the isAdmin to be initialized
     */
    public void setIsAdmin(String isAdm) {
        this.isAdmin = isAdm;
    }
    /**
     * Get method for UserID
     * @return userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Set method for UserID
     * @param uID the userID to be initialized
     */
    public void setUserID(String uID) {
        this.userID = uID;
    }

    /**
     * Get method for UserPW
     * @return userPW
     */
    public String getUserPW() {
        return userPW;
    }

    /**
     * Set method for UserPW
     * @param uPW the userPW to be initialized
     */
    public void setUserPW(String uPW) {
        this.userPW = uPW;
    }

    /**
     * Get method for UserEmail
     * @return userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Get method for UserNickname
     * @return userNickname
     */
    public String getUserNickname() {
        return userNickname;
    }

    /**
     * Set method for UserNickname
     * @param uNickname the userNickname to be initialized
     */
    public void setUserNickname(String uNickname) {
        this.userNickname = uNickname;
    }

    /**
     * Get method for UserAge
     * @return userAge
     */
    public String getUserAge() {
        return userAge;
    }

    /**
     * Set method for UserAge
     * @param uAge the userNickname to be initialized
     */
    public void setUserAge(String uAge) {
        this.userAge = uAge;
    }

    /**
     * Get method for UserMajor
     * @return userMajor
     */
    public String getUserMajor() {
        return userMajor;
    }

    /**
     * Set method for UserMajor
     * @param uMajor the userMajor to be initialized
     */
    public void setUserMajor(String uMajor) {
        this.userMajor = uMajor;
    }

    /**
     * Get method for UserStanding
     * @return userStanding
     */
    public String getUserStanding() {
        return userStanding;
    }

    /**
     * Set method for UserStanding
     * @param uStanding the userStanding to be initialized
     */
    public void setUserStanding(String uStanding) {
        this.userStanding = uStanding;
    }


    /**
     * Get method for permission
     * @return permission
     */
    public String getPermission() { return permission; }

    /**
     * Set method for permission
     * @param uPermission the permisson to be initialized
     */
    public void setPermission(String uPermission) {
        this.permission = uPermission;
    }

    /**
     * Get method for user information
     * @return user information in string divided by '/' for each information
     */
    public String userInfo() {
        return getUserID() + "/" + getUserPW() + "/" + getUserEmail() + "/" + getUserNickname() + "/" + getUserAge() + "/"+ getUserMajor() + "/" + getUserStanding() + "/" + getPermission() + "/" + getIsAdmin();
    }

}
