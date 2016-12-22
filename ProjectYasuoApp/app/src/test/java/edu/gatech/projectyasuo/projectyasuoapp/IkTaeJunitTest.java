package edu.gatech.projectyasuo.projectyasuoapp;

import android.util.Log;

import org.junit.Test;

import java.util.HashMap;

import edu.gatech.projectyasuo.projectyasuoapp.UserList;
import edu.gatech.projectyasuo.projectyasuoapp.User;
import static org.junit.Assert.*;

/**
 * Created by tonykim0328 on 4/8/16.
 */
public class IkTaeJunitTest {
    @Test
    public void testUserList() throws Exception {
        UserList list = UserList.getUserList();

        User user = new User("hayden123", "hayden123", "hayden123@gatech.edu", "hayden123",
                "20", "Computer Science", "Sophomore", "true", ".");

        list.addUser(user);
        try {
            list.saveUser();
        } catch (Exception e) {
        }

        assertTrue("User does not exist in the database", list.getUser("hayden123@gatech.edu").getUserID() == "hayden123");
        assertTrue(list.getUser("hayden123@gatech.edu").getUserAge() == "20");





//        assertTrue(list.getUser("tktktk@gatech.edu").getUserID() == "tktktk");
//        User user2 = new User("tktktk", "tktktk", "tktktk@gatech.edu", "tktktk",
//                "21", "Computer Science", "Sophomore", "true", movieRate);
//        list.addUser(user2);
//        try {
//            list.saveUser();
//        } catch (Exception e) {
//        }
//
//        assertTrue(list.getUser("tktktk@gatech.edu").getUserID() == "tktktk");
    }
}
