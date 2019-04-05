package edu.caltech.cs2.lab02;

import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class BinarySearcherTests {

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class HandWrittenBinarySearcherTests {
       /* TODO for student:
            - Copy all of the linear searcher tests to this class (both the provided and student-written ones).
            - Make sure to change all calls to linearSearch to calls to binarySearch!
        */

    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class StressBinarySearcherTests {
        public int[] generateRandomSortedArray(Random r, int length) {
            int[] arr = new int[length];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = r.nextInt();
            }
            Arrays.sort(arr);
            return arr;
        }

        @Test
        @Order(0)
        public void testBinarySearchUsingLinearSearch() {
            // Choose a number of random arrays to generate
            int TRIALS = 10000;

            // Initialize a single random object to use throughout.
            // We provide a constant "seed" to make the test deterministic.
            // The seed (in this case "1337") initializes the random number generator
            // to use a specific state.
            Random r = new Random(1337);

            int trial = 0;
            while (trial < TRIALS) {
                // Generate a random array of a random length
                int[] arr = generateRandomSortedArray(r, Math.abs(r.nextInt() % 10000));

                // We want to test both elements that are in the array and not in the array
                // So, run through all the elements in the array, and for each one test both
                // that element and one that is likely to not be in the array
                for (int x : arr) {
                    int y = r.nextInt();
                    assertEquals(Searcher.linearSearch(y, arr), Searcher.binarySearch(y, arr));
                    assertEquals(Searcher.linearSearch(x, arr), Searcher.binarySearch(x, arr));
                }
                trial++;
            }
        }
    }
}
