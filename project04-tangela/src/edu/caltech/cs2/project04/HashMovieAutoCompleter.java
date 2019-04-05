package edu.caltech.cs2.project04;

import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.datastructures.LinkedDeque;

import java.util.HashMap;
import java.util.Map;

public class HashMovieAutoCompleter extends AbstractMovieAutoCompleter {
    private static Map<String, IDeque<String>> titles = new HashMap<>();

    public static void populateTitles() {
        titles.clear();
        for (String i: idMap.keySet()) {
            LinkedDeque<String> suffix = new LinkedDeque<>();
            String test = "";
            String[] splitName = i.split(" ");
            for (int j = 0; j < splitName.length; j++) {
                if (!test.equals("")) {
                    test += " ";
                }
                test += splitName[j];
                suffix.addBack(test);
            }
            titles.put(i, suffix);
        }
    }

    public static IDeque<String> complete(String term) {
        LinkedDeque<String> titleMatch = new LinkedDeque<>();
        for (String i: titles.keySet()) {
            for (String j: titles.get(i)) {
                String tmp = " " + j.toLowerCase() + " ";
                if (tmp.contains(" " + term.toLowerCase() + " ")) {
                    titleMatch.addFront(i);
                    break;
                }
            }
        }
        return titleMatch;
    }
}