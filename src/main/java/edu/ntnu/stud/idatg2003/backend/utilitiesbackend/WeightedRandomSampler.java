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
   * @throws IllegalArgumentException if any weight is negative
   * @throws IllegalStateException if the list of weights is empty
   * @since 0.0.1
   */
  public WeightedRandomSampler(List<Double> weights) {
    // list of cumulative weights:
    this.cumulativeWeights = new ArrayList<>();
    double cumulativeSum = 0.0; // initial sum of weights

    // Iterate over weights:
    for (double weight : weights) {

      if (weight < 0) {
        throw new IllegalArgumentException("Weights must be non-negative");
      }

      cumulativeSum += weight;               // Adding the current weight to the running total
      cumulativeWeights.add(cumulativeSum);  // Adding the running total to the list
    }


    this.random = new Random(); // this random number generator is used for selecting weights
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

    // Iterate over cumulative weights:
    for (int i = 0; i < cumulativeWeights.size(); i++) {
      if (randomValue < cumulativeWeights.get(i)) {
        return i;  // the index of the first total weight greater than the random value
      }
    }
    return -1; // this should never happen if weights are set correctly
  }


}
