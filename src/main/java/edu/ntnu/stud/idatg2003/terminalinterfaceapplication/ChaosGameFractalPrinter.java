package edu.ntnu.stud.idatg2003.terminalinterfaceapplication;

import edu.ntnu.stud.idatg2003.filehandling.ChaosGameFileHandler;
import java.io.IOException;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescription;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGame;

/**
 * The {@code ChaosGameFractalPrinter} class provides a main method
 * for running a chaos game and printing the resulting fractal.
 *
 * @version 0.0.1
 */
public class ChaosGameFractalPrinter {


  // The file path to the text file containing the chaos game descriptions:
  private static final String TEXT_FILE_PATH = "src/main/resources/txtfiles/";

  private static final int MAX_ITERATIONS = 100000; // The maximum number of iterations
  private static final int CANVAS_WIDTH = 500; // The width of the canvas
  private static final int CANVAS_HEIGHT = 175; // The height of the canvas





  /**
   * Prints a fractal based on the chaos game description in the specified file.
   *
   * @param filePath      The path to the file containing the chaos game description.
   * @since 0.0.1
   */
  public static void printFractalFromDescription(String filePath) {
    try {
      ChaosGameDescription description = ChaosGameFileHandler.readFile(filePath);
      ChaosGame chaosGame = new ChaosGame(description, CANVAS_WIDTH, CANVAS_HEIGHT);
      chaosGame.runSteps(MAX_ITERATIONS);


      chaosGame.printFractal();
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
    printFractalFromDescription(TEXT_FILE_PATH + "julia.txt");
    printFractalFromDescription(TEXT_FILE_PATH + "barnsley-fern.txt");
    //printFractalFromDescription(TEXT_FILE_PATH + "test-fractal.txt");
  }

}