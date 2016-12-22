package edu.gatech.projectyasuo.projectyasuoapp;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import edu.gatech.projectyasuo.projectyasuoapp.MovieList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;


public class YooJunitTest {
    @Test
    public void testGetMovie() throws Exception {

        //HashMap<String, Integer> movieHashMap = new HashMap<>();

        HashMap<String, User> hashMap1 = new HashMap<>();
        HashMap<String, User> hashMap2 = new HashMap<>();
        HashMap<String, User> hashMap3 = new HashMap<>();

        List <User> emptyList = new ArrayList<>();
        List <User> list1 = new ArrayList<>();
        List <User> list2 = new ArrayList<>();
        List <User> list3 = new ArrayList<>();


        User a = new User ("kelly", "kelly", "kelly@gatech.edu", "kelly", "19", "CS", "sophomore", "true", ".");
        User b = new User ("tony", "tony", "tony@gatech.edu", "tony", "21", "CS", "sophomore", "true", ".");
        User c = new User ("julie", "julie", "julie@gatech.edu", "julie", "20", "CS", "sophomore", "true", ".");
        User d = new User ("hayden", "hayden", "hayden@gatech.edu", "hayden", "20", "CS", "sophomore", "true", ".");
        User e = new User ("nick", "nick", "nick@gatech.edu", "nick", "22", "CS", "junior", "true", ".");

        assertEquals(UserList.getUserList().getItemArray(hashMap1), emptyList);

        hashMap1.put("kelly", a);
        list1.add(a);
        assertEquals(UserList.getUserList().getItemArray(hashMap1), list1);

        hashMap2.put("kelly", a);
        hashMap2.put("tony", b);
        hashMap2.put("julie", c);
        list2.add(b);
        list2.add(c);
        list2.add(a);
        assertEquals(UserList.getUserList().getItemArray(hashMap2), list2);


        hashMap3.put("kelly", a);
        hashMap3.put("tony", b);
        hashMap3.put("julie", c);
        hashMap3.put("hayden", d);
        hashMap3.put("nick", e);
        list3.add(b);
        list3.add(e);
        list3.add(c);
        list3.add(d);
        list3.add(a);

        assertNotEquals(UserList.getUserList().getItemArray(hashMap3), list2);
        assertEquals(UserList.getUserList().getItemArray(hashMap3), list3);

    }
}