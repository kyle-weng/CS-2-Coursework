package edu.caltech.cs2.lab06;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class AnagramGenerator {

    public static void printPhrases(String phrase, List<String> dictionary) {
        LetterBag bag = new LetterBag(phrase);
        List<String> acc = new ArrayList<>();
        printPhrases2(bag, acc, dictionary);
    }

    private static void printPhrases2(LetterBag bag, List<String> acc,
                                      List<String> dictionary) {

        if (bag.isEmpty()) {
            System.out.println(acc);
        } else {
            for (String word : dictionary) {
                LetterBag other = new LetterBag(word);
                if (bag.subtract(other) != null) {
                    acc.add(word);
                    printPhrases2(bag.subtract(other), acc, dictionary);
                    acc.remove(word);
                }
            }
        }


        // bag is what you're comparing to
        // curr is what you have in the accumulator
        /* String str = "";
        for (String word : acc) {
            str += word;
        }
        LetterBag curr = new LetterBag(str);
        if (curr.equals(bag)) {
            System.out.println(acc);
            return;
        } else {
            for (String word : dictionary) {
                LetterBag other = new LetterBag(word);
                if (bag.subtract(other) != null && !(bag.subtract(other).isEmpty())) {
                    acc.add(word);
                    printPhrases2(bag, acc, dictionary);
                    acc.remove(word);
                }
            }
        } */
    }

    public static void printWords(String word, List<String> dictionary) {
        for (String dictionaryWord : dictionary) {
            if (new LetterBag(dictionaryWord).equals(new LetterBag(word))) {
                System.out.println(dictionaryWord);
            }
        }
    }
}
