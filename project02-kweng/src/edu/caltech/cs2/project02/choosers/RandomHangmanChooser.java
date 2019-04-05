package edu.caltech.cs2.project02.choosers;

import edu.caltech.cs2.project02.interfaces.IHangmanChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class RandomHangmanChooser implements IHangmanChooser {

  //private int wordLength;
  //private int maxGuesses;
  private int currentGuesses;
  private final String secretWord;
  private String currentWord;
  private static Random rand = new Random();
  private SortedSet<Character> alreadyGuessed = new TreeSet<Character>();

  public RandomHangmanChooser(int wordLength, int maxGuesses) throws FileNotFoundException, IllegalArgumentException{

    if (wordLength < 1) {
      throw new IllegalArgumentException("Word length must be >= 1.");
    }
    else if (maxGuesses < 1) {
      throw new IllegalArgumentException("Max guesses must be >= 1.");
    }

    SortedSet<String> bank = new TreeSet<String>();
    Scanner in = new Scanner(new File("data/scrabble.txt"));
    while (in.hasNext()) {
      bank.add(in.next());
    }

    SortedSet<String> bankAcceptable = new TreeSet<String>();
    for (String x : bank) {
      if (x.length() == wordLength) {
        bankAcceptable.add(x);
      }
    }
    if (bankAcceptable.size() == 0) {
      throw new IllegalStateException("No words found of length " + wordLength);
    }

    int choice = rand.nextInt(bankAcceptable.size());
    Iterator<String> it = bankAcceptable.iterator();
    String ret = "";
    for (int i = 0; i <= choice; i++) {
      ret = it.next();
    }

    this.secretWord = ret;
    this.currentWord = new String(new char[this.secretWord.length()]).replace("\0", "-");
    //this.wordLength = wordLength;
    //this.maxGuesses = maxGuesses;
    this.currentGuesses = maxGuesses;

    //SortedSet<String> bank = bankConstructor("data/scrabble.txt");
  }

  @Override
  public int makeGuess(char letter) throws IllegalStateException, IllegalArgumentException{
    if ((int)letter < (int)'a' || (int)letter > (int)'z') {
      throw new IllegalArgumentException("The guess must be lowercase.");
    }
    if (currentGuesses < 1) {
      throw new IllegalStateException("No more guesses left!");
    }
    if (alreadyGuessed.contains(letter)) {
      throw new IllegalArgumentException("This letter has already been guessed!");
    }
    int counter = 0;
    for (char x : secretWord.toCharArray()) {
      if (x == letter) {
        counter++;
      }
    }
    if (counter == 0)
      currentGuesses--;

    alreadyGuessed.add(letter);

    String currentState = this.secretWord;
    for (int i = (int)'a'; i <= (int)'z'; i++) {
      if (!alreadyGuessed.contains((char)i)) {
        currentState = currentState.replace((char)(i), '-');
      }
      //System.out.println(currentState);
    }
    this.currentWord = currentState;

    return counter;


  }

  @Override
  public boolean isGameOver() {
    return (currentWord.equals(secretWord) || currentGuesses == 0);
  }

  @Override
  public String getPattern() {
    //first implementation
    /*
    String result = "";
    for (int i = (int)'a'; i <= (int)'a' + secretWord.length() - 1; i++) {
      if (alreadyGuessed.contains((char)i)) {
        //now you have to find the specific indices where this occurs
        result += (char)i;
      }
      else {
        result += "-";
      }
    }
    return result;
    */

    //testing loop for printing stuff from alreadyGuessed
    /*
    System.out.println("already guessed ");
    for (char i: alreadyGuessed) {
      System.out.print(i);
    }*/
    //System.out.println(this.secretWord);


    //second implementation
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
    currentGuesses = 0; //this should effectively end the game
    return secretWord;
  }
}