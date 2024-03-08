package edu.ntnu.stud.idatg2003.terminalinterfaceapplication;


import static edu.ntnu.stud.idatg2003.filehandling.ChaosGameFileHandler.readJuliaTransformsFromFile;

import edu.ntnu.stud.idatg2003.engine.ChaosCanvas;
import edu.ntnu.stud.idatg2003.filehandling.ChaosGameFileHandler;
import java.io.IOException;
import edu.ntnu.stud.idatg2003.engine.ChaosGameDescription;
import edu.ntnu.stud.idatg2003.engine.ChaosGame;

/**
 * The {@code ChaosGameFractalPrinter} class provides a main method
 * for running a chaos game and printing the resulting fractal.
 *
 * @version 0.0.1
 * @since 0.0.1
 */
public class ChaosGameFractalPrinter {

  /**
   * Prints a fractal based on the chaos game description in the specified file.
   *
   * @param filePath      The path to the file containing the chaos game description.
   * @param maxIterations The maximum number of iterations to run the chaos game.
   * @since 0.0.1
   */
  public static void printFractalFromDescription(String filePath, int maxIterations) {
    ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
    try {
      ChaosGameDescription description = fileHandler.readFromFile(filePath);
      //ChaosGameDescription description = readJuliaTransformsFromFile(filePath);
      ChaosGame chaosGame = new ChaosGame(description, 200, 100); // canvas size
      chaosGame.runSteps(maxIterations);
      ChaosCanvas canvas = chaosGame.getCanvas();
      int[][] canvasArray = canvas.getCanvasArray();

      StringBuilder outputBuilder = new StringBuilder(); // StringBuilder to build the output string

      // Iterating over the canvas array and building the output string:
      for (int i = 0; i < canvasArray.length; i++) {
        for (int j = 0; j < canvasArray[i].length; j++) {
          // "X" for colored pixels and space for blank pixels:
          outputBuilder.append(canvasArray[i][j] == 1 ? "X" : " ");
        }
        outputBuilder.append("\n"); // New line to start next row
      }

      // Printing the output string to the console:
      System.out.println(outputBuilder);
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
    String filePath = "barnsley-fern.txt";
    //String filePath = "sierpinski.txt"; // The file path to the desired transformation file
    int maxIterations = 1000000; // The maximum number of iterations
    printFractalFromDescription(filePath, maxIterations);
  }

}