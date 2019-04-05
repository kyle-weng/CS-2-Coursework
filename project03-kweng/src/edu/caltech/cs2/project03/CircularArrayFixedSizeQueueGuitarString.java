package edu.caltech.cs2.project03;

import edu.caltech.cs2.datastructures.CircularArrayFixedSizeQueue;
import edu.caltech.cs2.interfaces.IFixedSizeQueue;
import java.util.Random;


public class CircularArrayFixedSizeQueueGuitarString {

  private IFixedSizeQueue<Double> string;
  private static Random rand = new Random();
  private static final double samplingRate = 44100;
  private static final double energyDecay = 0.996;

  public CircularArrayFixedSizeQueueGuitarString(double frequency) {
    int n = (int)Math.ceil(samplingRate / frequency);
    double n_d = (double)n;
    string = new CircularArrayFixedSizeQueue<>(n);
    for (int i = 0; i <= n; i++) {
      string.enqueue(0.0);
    }
  }

  public int length() {
    return string.size();
  }

  public void pluck() {
    int y = length();
    for (int i = 0; i < y; i++) {
      string.dequeue();
      string.enqueue(rand.nextDouble() - 0.5);
    }
  }

  public void tic() {
    double temp = string.dequeue();
    double temp2 = string.peek();
    string.enqueue(((temp + temp2) * 0.5 * energyDecay));
  }

  public double sample() {
    return (double)(string.peek());
  }
}
