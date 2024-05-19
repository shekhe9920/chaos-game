package edu.ntnu.stud.idatg2003.terminalinterfaceapplication;

import edu.ntnu.stud.idatg2003.filehandling.ChaosGameFileHandler;
import java.io.IOException;
import java.util.List;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescription;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGame;
import javafx.util.Pair;

/**
 * The {@code ChaosGameFractalPrinter} class provides a main method
 * for running a chaos game and printing the resulting fractal.
 *
 * @version 0.0.1
 */
public class ChaosGameFractalPrinter {



  // The file path to the text file containing the chaos game descriptions:
  private static final String TEXT_FILE_PATH = "src/main/resources/txtfiles/";

  private static final int MAX_ITERATIONS = 10000; // The maximum number of iterations
  private static final int CANVAS_WIDTH = 150; // The width of the canvas
  private static final int CANVAS_HEIGHT = 80; // The height of the canvas




  /**
   * Prints a fractal based on the chaos game description in the specified file.
   *
   * @param filePath The path to the file containing the chaos game description.
   * @since 0.0.1
   */
  public static void printFractalFromDescription(String filePath) {
    try {
      // the result is a pair of the chaos game description and the weights
      Pair<ChaosGameDescription, List<Double>> result = ChaosGameFileHandler.readFile(filePath);
      ChaosGameDescription description = result.getKey();
      List<Double> weights = result.getValue();
      ChaosGame chaosGame = new ChaosGame(description, CANVAS_WIDTH, CANVAS_HEIGHT);
      chaosGame.setTransformWeights(weights);
      chaosGame.runSteps(MAX_ITERATIONS);
      chaosGame.printFractal(CANVAS_WIDTH, CANVAS_HEIGHT);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }




  /**
   * The main method for running a chaos game and printing the resulting fractal.
   *
   * @param args The command-line arguments.
   * @since 0.0.1
   */
  public static void main(String[] args) {
    printFractalFromDescription(TEXT_FILE_PATH + "sierpinski.txt");
    printFractalFromDescription(TEXT_FILE_PATH + "barnsley-fern.txt");
  }
}
