package com.endymion.collection;

import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

        User user1 = new User(1, "Anna");
        User user2 = new User(2, "Bob");
        User user3 = new User(3, "Catherine");

        HashSet<User> userSet12 = new HashSet<>();
        userSet12.add(user1);
        userSet12.add(user2);

        HashSet<User> userHashSet12 = new HashSet<>();
        userHashSet12.add(user1);
        userHashSet12.add(user2);

        HashSet<User> userHashSet13 = new HashSet<>();
        userHashSet13.add(user1);
        userHashSet13.add(user3);

        HashSet<User> userHashSet123 = new HashSet<>();
        userHashSet123.add(user1);
        userHashSet123.add(user2);
        userHashSet123.add(user3);

        System.out.println("12, 12 = " + isServiceTypesContained(userSet12, userHashSet12));
        System.out.println("12, 123 = " + isServiceTypesContained(userHashSet12, userHashSet123));
        System.out.println("13, 123 = " + isServiceTypesContained(userHashSet13, userHashSet123));
        System.out.println("12, 13 = " + isServiceTypesContained(userHashSet12, userHashSet13));
    }

    public class User {
        int id;
        String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    static boolean isServiceTypesContained(HashSet<User> set1, HashSet<User> set2) {
        HashSet<User> mySet1 = new HashSet<>(set1);
        HashSet<User> mySet2 = new HashSet<>(set2);
        return !mySet1.addAll(mySet2) || !mySet2.addAll(mySet1);
    }
}