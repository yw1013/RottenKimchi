package edu.gatech.projectyasuo.projectyasuoapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserList class that implements HashMap and Backend Recording
 * into the Android's Internal Storage (Textfile is created)
 * @author Jongwoo Jang
 */
public final class UserList {
    public final Map<String, User> regiUser = new HashMap<>();
    private static final UserList LIST = new UserList();
    private static String thePath = "";

    /**
     * UserList default constructor to allow me to create
     * static UserList LIST
     */

    private UserList() {
    }


    /**
     * Static Function that Initialize the path
     * to the database file in Android Internal Storage
     * @param path the path to the database file
     */
    public static void initPath(String path) {
        thePath = path;
    }

    /**
     * Static Function that returns the UserList
     * @return UserList
     */
    public static UserList getUserList() {
        return LIST;
    }



    /**
     * Adds User passed in as parameter to the HashMap,
     * Then saves the Users in the LIST to our data base.
     * @param a the User a to be added on the HashMap called regiUser
     */
    public void addUser(User a) {
        regiUser.put(a.getUserEmail(), a);
        LIST.saveUser();

    }

    /**
     * initialize admin
     * @param userEmail the userEmail to check should be admin or not
     */
    public void initAdmin(String userEmail) {
        for(User a : regiUser.values()) {
            if(a.getUserEmail().equals(userEmail)) {
                a.setIsAdmin("1");
            }
//            else {
//                a.setIsAdmin("0");
//            }
        }
        LIST.saveUser();
    }

    /**
     * set the user as not admin
     */
    public void unAdmin() {
        for(User a : regiUser.values()) {
            a.setIsAdmin("0");
        }
        LIST.saveUser();
    }

    /**
     * Removes User with the passed in parameter Key for HashMap,
     * Then saves the Users in the LIST to our data base.
     * @param userEmail the Key to search in the HashMap
     */
    public void removeUser(String userEmail) {
        regiUser.remove(userEmail);
        LIST.saveUser();
    }

    /**
     * Finds the User that has userEmail passed in as parameter
     * inside our regiUser HashMap.
     * @param userEmail the Key to search in the HashMap
     * @return the User that has userEmail passed in as parameter
     */
    public User getUser(String userEmail) {
        return regiUser.get(userEmail);
    }

    /**
     * Returns true if userEmail(Key) passed in as parameter
     * exists inside our HashMap
     * @param userEmail the Key to search in our HashMap
     * @return true if it exists, false if it does not exist in our
     * regiUser HashMap
     */
    public boolean existingEmail(String userEmail) {
        return regiUser.containsKey(userEmail);
    }

    /**
     * Saves the users in our HashMap into our database as text file.
     * Allow us to have our users permanently stored in Internal Storage
     */
    public void saveUser() {
        BufferedWriter writeout = null;

        try {
            writeout = new BufferedWriter(new FileWriter(thePath));
            for(User curUser : regiUser.values()) {
                writeout.append(curUser.userInfo()).append("\n");
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
     * Load Users from our database into HashMap
     * Allow us to restart the application expecting our
     * previously registered users to be still there
     */
    public void loadUser() {
        regiUser.clear();
        BufferedReader writein = null;
        String userInfo;
        try {
            writein = new BufferedReader(new FileReader(thePath));
            while((userInfo = writein.readLine()) != null) {
                User getUserFromDB = new User(userInfo);
                regiUser.put(getUserFromDB.getUserEmail(), getUserFromDB);

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


    /**
     * get the LIST of users
     * @return the LIST of users
     */
    public List<User> getItemArray(Map<String, User> regiUser) {
        List<User> userNames = new ArrayList<>();

        for (User curr : regiUser.values()) {
            userNames.add(curr);
        }
        return userNames;

    }

    /**
     * get the LIST of users
     * @param position the position of the item to be searched
     * @return the User at the position
     */
    public User getUserAt(int position) { return UserList.getUserList().getItemArray(regiUser).get(position); }


}