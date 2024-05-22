package edu.ntnu.stud.idatg2003.backend.utilitiesbackend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link WeightedRandomSampler}.
 * This class contains tests for verifying the functionality of the WeightedRandomSampler class,
 * including both positive and negative tests.
 */
class WeightedRandomSamplerTest {

  private List<Double> weights;
  private WeightedRandomSampler sampler;

  @BeforeEach
  void setUp() {
    weights = Arrays.asList(1.0, 2.0, 3.0, 4.0);
    sampler = new WeightedRandomSampler(weights);
  }

  /**
   * Tests the constructor of WeightedRandomSampler with valid weights.
   */
  @Test
  void testConstructor_ValidWeights() {
    assertNotNull(sampler, "WeightedRandomSampler should be instantiated");
  }





  /**
   * Tests the nextIndex method to ensure it returns valid indices based on weights.
   */
  @Test
  void testNextIndex_Valid() {
    int index = sampler.nextIndex();
    assertTrue(index >= 0 && index < weights.size(), "Index should be within the range of weights list size");
  }

  /**
   * Tests the constructor of WeightedRandomSampler with negative weights to ensure it handles them appropriately.
   */
  @Test
  void testConstructor_NegativeWeights() {
    List<Double> negativeWeights = Arrays.asList(-1.0, -2.0, -3.0);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new WeightedRandomSampler(negativeWeights));
    assertEquals("Weights must be non-negative", exception.getMessage());
  }
}
