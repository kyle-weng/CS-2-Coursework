package edu.caltech.cs2.lab02;

public class Searcher {
    public static boolean isSorted(int[] array) {
        int prev = Integer.MIN_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (prev > array[i]) {
                return false;
            }
            prev = array[i];
        }
        return true;
    }

    public static int linearSearch(int needle, int[] haystack) throws NullPointerException{
        for (int i = 0; i < haystack.length; i++) {
            if (haystack[i] == needle) {
                return i;
            }
            else if (haystack[i] > needle) {
                return ((-1 * i) - 1);
            }
        }
    }

    public static int binarySearch(int needle, int[] haystack) {
        //midpoint is the middle-most element of the array, not an index
        boolean endCondition = false;
        int lowerBound = 0;
        int upperBound = haystack.length;
        while(upperBound - lowerBound > 1) {
            int midpointIndex = Math.round(upperBound / 2);
            if (needle == haystack[midpointIndex]) {
                return midpointIndex;
            }
            if (needle < haystack[midpointIndex]) {
                upperBound = midpointIndex;
            } else if (needle > haystack[midpointIndex]) {
                lowerBound = midpointIndex;
            }
        }
        return (-1 * lowerBound - 1);
    }
}
