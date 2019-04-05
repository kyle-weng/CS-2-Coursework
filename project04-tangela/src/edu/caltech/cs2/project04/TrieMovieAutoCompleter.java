package edu.caltech.cs2.project04;

import edu.caltech.cs2.datastructures.TrieMap;
import edu.caltech.cs2.datastructures.LinkedDeque;
import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.ITrieMap;

public class TrieMovieAutoCompleter extends AbstractMovieAutoCompleter {
    private static ITrieMap<String, IDeque<String>, IDeque<String>> titles = new TrieMap<>((IDeque<String> s) -> s);

    public static void populateTitles() {
        titles.clear();
        for (String i: idMap.keySet()) {
            LinkedDeque<String> titleSuffix = new LinkedDeque<>();
            String[] words = i.toLowerCase().split(" ");
            for (int j = words.length - 1; j >= 0; j--) {
                titleSuffix.addFront(words[j]);
                if (titles.containsKey(titleSuffix)) {
                    titles.get(titleSuffix).add(i);
                } else {
                    LinkedDeque<String> title = new LinkedDeque<>();
                    title.add(i);
                    titles.put(titleSuffix, title);
                }
            }
        }
    }

    public static IDeque<String> complete(String term) {
        LinkedDeque<String> termDeq = new LinkedDeque<>();
        LinkedDeque<String> titlesDeq = new LinkedDeque<>();
        String[] words = term.toLowerCase().split(" ");
        for (int j = words.length - 1; j >= 0; j--) {
            termDeq.addFront(words[j]);
        }
        for (IDeque<String> i: titles.getCompletions(termDeq)) {
            for (String j: i) {
                if (!titlesDeq.contains(j)) {
                    titlesDeq.add(j);
                }
            }
        }
        return titlesDeq;
    }
}