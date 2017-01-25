package com.companion.locationapp;

import com.companion.locationapp.model.User;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    User user1 = new User("latitute1", "longitude1", "Jack");
    User user1Bis = new User("latitute1", "longitude1", "Jack");
    User user2 = new User("latitute2", "longitude2", "Mike");
    ArrayList<User> userArrayList = new ArrayList<>();
    @Before
    public void createList(){
        userArrayList.add(user1);
        userArrayList.add(user2);
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void checkIfEquals() throws Exception {
        assertEquals(false, user1==user1Bis);
    }
    @Test
    public void checkIfEqualsCustomFct() throws Exception {
        assertEquals(true, user1.isEqual(user1Bis));
    }
    @Test
    public void checkArrayListContainWithDifferentObject() throws Exception {
        assertEquals(false, userArrayList.contains(user1Bis));
    }
    @Test
    public void checkArrayListContainWithSameObject() throws Exception {
        userArrayList.add(user1);
        userArrayList.add(user2);
        assertEquals(true, userArrayList.contains(user1));
    }

    @Test
    public void getUserByNameFct() throws Exception {
        assertEquals(user1, Utlis.getUserByNameFromList("Jack", userArrayList));
    }
}