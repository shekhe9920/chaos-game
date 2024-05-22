package edu.ntnu.stud.idatg2003.commonutilities.filehandling;

import edu.ntnu.stud.idatg2003.backend.engine.ChaosGame;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescription;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Matrix2x2;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.transformations.AffineTransform2D;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link ChaosGameFileHandler}.
 * This class contains tests for verifying the functionality of the ChaosGameFileHandler class,
 * including both positive and negative tests for readFile and writeToFile methods.
 */
class ChaosGameFileHandlerTest {

  /**
   * Tests readFile method with a valid file.
   */
  @Test
  void testReadFile_Valid() throws IOException {
    String path = "src/test/resources/valid_chaos_game.txt";
    Pair<ChaosGameDescription, List<Double>> result = ChaosGameFileHandler.readFile(path);

    assertNotNull(result, "Result should not be null");
    assertEquals(3, result.getValue().size(), "Weights size should be 3");
    assertEquals(3, result.getKey().getTransformations().size(), "Transformations size should be 3");
  }

  /**
   * Tests readFile method with an invalid file.
   */
  @Test
  void testReadFile_Invalid() {
    String path = "src/test/resources/invalid_chaos_game.txt";
    IOException exception = assertThrows(IOException.class, () -> ChaosGameFileHandler.readFile(path));
    assertEquals("Incorrect number of values for Affine2D transformation on one of the lines.", exception.getMessage());
  }

  /**
   * Tests readFile method with an empty file.
   */
  @Test
  void testReadFile_Empty() {
    String path = "src/test/resources/empty_file.txt";
    NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> ChaosGameFileHandler.readFile(path));
    assertEquals("No line found", exception.getMessage());
  }




  /**
   * Tests writeToFile method with a null ChaosGame instance.
   */
  @Test
  void testWriteToFile_NullChaosGame() {
    String path = "src/test/resources/output_chaos_game.txt";
    NullPointerException exception = assertThrows(NullPointerException.class, () ->
        ChaosGameFileHandler.writeToFile(null, path, "Affine2D"));
    assertEquals("game cannot be null", exception.getMessage());
  }

  private static ChaosGame initializeChaosGameWithWeights() {
    Vector2D minCoords = new Vector2D(0.0, 0.0);
    Vector2D maxCoords = new Vector2D(1.0, 1.0);

    Matrix2x2 matrix0 = new Matrix2x2(0.5, 0.0, 0.0, 0.5);
    Vector2D vector0 = new Vector2D(0.0, 0.0);
    AffineTransform2D transform0 = new AffineTransform2D(matrix0, vector0);

    Matrix2x2 matrix1 = new Matrix2x2(0.5, 0.0, 0.0, 0.5);
    Vector2D vector1 = new Vector2D(0.5, 0.0);
    AffineTransform2D transform1 = new AffineTransform2D(matrix1, vector1);

    ChaosGameDescription description = new ChaosGameDescription(minCoords, maxCoords, List.of(transform0, transform1));
    List<Double> weights = getDefaultWeights(description);

    ChaosGame game = new ChaosGame(description, 100, 100);
    game.setTransformWeights(weights);

    return game;
  }



  private static List<Double> getDefaultWeights(ChaosGameDescription description) {
    int size = description.getTransformations().size();
    List<Double> weights = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      weights.add(1.0 / size); // setting equal weights for all transformations
    }
    return weights;
  }
}
