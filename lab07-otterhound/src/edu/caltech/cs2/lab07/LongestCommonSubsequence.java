package edu.caltech.cs2.lab07;

import java.util.HashMap;
import java.util.Map;

public class LongestCommonSubsequence {

    private static int[][] memo;
    private static int max;

    public static int findLCS(String string1, String string2) {
        memo = new int[string1.length()+1][string2.length()+1];
        return doFindLCS(string1, string2);
    }

    public static int doFindLCS(String string1, String string2) {
        // Initialize the memo table
        max = 0;

        for (int i = 0; i < string1.length(); i++) {
            memo[i][0] = 0;
        }
        for (int j = 0; j < string2.length(); j++) {
            memo[0][j] = 0;

        }

        for (int i = 1; i < memo.length; i++) {
            for (int j = 1; j < memo[0].length; j++) {
                if (string1.charAt(i - 1) == string2.charAt(j - 1)) {
                    memo[i][j] = memo[i-1][j-1] + 1;
                } else {
                    memo[i][j] = Math.max(memo[i-1][j], memo[i][j-1]);
                }
                if (memo[i][j] > max) {
                    max = memo[i][j];
                }
            }
        }
        return max;
    }
}