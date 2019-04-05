package edu.caltech.cs2.project02.choosers;

import edu.caltech.cs2.project02.interfaces.IHangmanChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class EvilHangmanChooser implements IHangmanChooser {

  private int currentGuesses;
  private String currentWord;
  private SortedSet<String> bankAcceptable;
  private static Random rand = new Random();
  private SortedSet<Character> alreadyGuessed = new TreeSet<Character>();

  public EvilHangmanChooser(int wordLength, int maxGuesses) throws FileNotFoundException, IllegalArgumentException {

    if (wordLength < 1) {
      throw new IllegalArgumentException("Word length must be >= 1.");
    } else if (maxGuesses < 1) {
      throw new IllegalArgumentException("Max guesses must be >= 1.");
    }

    SortedSet<String> bank = new TreeSet<String>();
    Scanner in = new Scanner(new File("data/scrabble.txt"));
    while (in.hasNext()) {
      bank.add(in.next());
    }

    this.bankAcceptable = new TreeSet<String>();
    for (String x : bank) {
      if (x.length() == wordLength) {
        this.bankAcceptable.add(x);
      }
    }
    if (this.bankAcceptable.size() == 0) {
      throw new IllegalStateException("No words found of length " + wordLength);
    }
    this.currentGuesses = maxGuesses;
  }

  @Override
  public int makeGuess(char letter) throws IllegalStateException, IllegalArgumentException {
    if ((int) letter < (int) 'a' || (int) letter > (int) 'z') {
      throw new IllegalArgumentException("The guess must be lowercase.");
    }
    if (currentGuesses < 1) {
      throw new IllegalStateException("No more guesses left!");
    }
    if (alreadyGuessed.contains(letter)) {
      throw new IllegalArgumentException("This letter has already been guessed!");
    }

    alreadyGuessed.add(letter);
    HashMap<String, SortedSet<String>> sortedBank = new HashMap<String, SortedSet<String>>();
    SortedSet<String> set;
    for (String str : bankAcceptable) {
      String pattern = constructPattern(str, letter);
      if (sortedBank.keySet().contains(pattern))
        set = sortedBank.get(pattern);
      else {
        set = new TreeSet<String>();
      }
      set.add(str);
      sortedBank.put(pattern, set);
    }

    int n = 0;
    int terminus = sortedBank.keySet().size();
    String[] keyList = new String[terminus];
    Iterator<String> it = sortedBank.keySet().iterator();
    while (it.hasNext()) {
      keyList[n] = it.next();
    }

    int maxNumber = 0;
    String maxKey = "";
    SortedSet<String> maxSet = new TreeSet<String>();
    for (String key : keyList) {
      try {
        if (sortedBank.get(key).size() > maxNumber) {
          maxKey = key;
          maxSet = sortedBank.get(maxKey);
        }
      }
      catch (NullPointerException e) {
        maxKey = key;
        maxSet = sortedBank.get(maxKey);
      }
      try {
        maxNumber = maxSet.size();
      }
      catch (NullPointerException e) {
        maxNumber = 0;
      }
    }

    for (int i = (int) 'a'; i <= (int) 'z'; i++) {
      if (!alreadyGuessed.contains((char) i)) {
        currentWord = maxKey.replace((char) (i), '-');
      }
      //System.out.println(currentState);
    }
    int counter = 0;
    for (char x: maxKey.toCharArray()) {
      if (x == letter) {
        counter++;
      }
    }
    if (counter == 0) {
      currentGuesses--;
    }
    bankAcceptable = maxSet;
    return counter;
  }

  private String constructPattern(String word, char pattern) {
    String result = "";
    for (char x: word.toCharArray()) {
      if (x == pattern) {
        result += pattern;
      }
      else {
        result += "-";
      }
    }
    return result;
  }

  @Override
  public boolean isGameOver() {
    if (bankAcceptable.size() == 1 && currentWord.equals(bankAcceptable.first())) {
      return true;
    }
    else if (currentGuesses == 0) {
      return true;
    }
    else {
      return false;
    }
  }

  @Override
  public String getPattern() {
    return this.currentWord;
  }

  @Override
  public SortedSet<Character> getGuesses() {
    return alreadyGuessed;
  }

  @Override
  public int getGuessesRemaining() {
    return currentGuesses;
  }

  @Override
  public String getWord() {
    currentGuesses = 0;
    return "";
  }
}