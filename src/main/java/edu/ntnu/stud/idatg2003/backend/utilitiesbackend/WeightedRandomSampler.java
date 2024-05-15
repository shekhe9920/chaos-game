package edu.ntnu.stud.idatg2003.backend.utilitiesbackend;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class to handle weighted random selection from a list of weights.
 *
 * @version 0.0.1
 * @since 0.0.5 (The version of Chaos-Game application when introduced)
 */
public class WeightedRandomSampler {

  // List of cumulative weights, meaning the sum of all weights up to the current index:
  private final List<Double> cumulativeWeights;

  // Random number generator for selecting weights:
  private final Random random;


  /**
   * Constructs a WeightedRandom object from a list of weights.
   * The weights are accumulated to form a list of cumulative weights.
   *
   * @param weights the list of weights
   * @since 0.0.1
   */
  public WeightedRandomSampler(List<Double> weights) {
    this.cumulativeWeights = new ArrayList<>();
    double cumulativeSum = 0.0;
    for (double weight : weights) {          // Iterate over weights
      cumulativeSum += weight;               // Add the current weight to the cumulative sum
      cumulativeWeights.add(cumulativeSum);  // Add the cumulative sum to the list
    }
    this.random = new Random();
  }




  /**
   * Returns the index of the next randomly selected weight based on their cumulative weights.
   *
   * @return the index of the selected weight
   * @throws IllegalStateException if the list of weights is empty
   * @since 0.0.1
   */
  public int nextIndex() {
    if (cumulativeWeights.isEmpty()) {
      throw new IllegalStateException("The list of weights is empty.");
    }
    double randomValue = random.nextDouble() * cumulativeWeights.getLast();
    for (int i = 0; i < cumulativeWeights.size(); i++) {      // Iterate over cumulative weights
      if (randomValue < cumulativeWeights.get(i)) {
        return i;  // Return the index of the first cumulative weight greater than the random value
      }
    }
    return -1; // This should never happen if weights are set correctly
  }


}
