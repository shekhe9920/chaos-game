package edu.ntnu.stud.idatg2003.terminalinterfaceapplication;

import edu.ntnu.stud.idatg2003.backend.engine.ChaosGame;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescription;
import edu.ntnu.stud.idatg2003.filehandling.ChaosGameFileHandler;
import javafx.util.Pair;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * The {@code ChaosGameCLI} is a simple command line interface application,
 * where the user is able to interact with the terminal and generate fractals.
 *
 * @version 0.0.3
 * @since 0.0.1 (The version of ChaosGameEngine application when introduced)
 */
public class ChaosGameCLI {

  // The menu options:
  private static final int CHOOSE_CANVAS_DIMENSIONS = 1;
  private static final int READ_FRACTAL_DESCRIPTION_FROM_FILE = 2;
  private static final int WRITE_FRACTAL_DESCRIPTION_TO_FILE = 3;
  private static final int RUN_ITERATIONS = 4;
  private static final int PRINT_ASCII_FRACTAL_TO_CONSOLE = 5;
  private static final int EXIT = 6;

  private static final String NO_GAME_LOADED = "No game loaded. Read a description first.";

  private static int canvasWidth = 100;   // Default canvas width
  private static int canvasHeight = 60;   // Default canvas height
  private static ChaosGame game = null;   // The chaos game object



  /**
   * Prints the menu options for the user to choose from.
   *
   * @since 0.0.1
   */
  private static void printMenu() {
    String stringBuilder = """
            ====== Chaos Game ======
            1. Choose canvas dimensions
            2. Load description from file
            3. Save description to file
            4. Run iterations
            5. Print ASCII fractal
            6. Exit
            Choose an option:\s""";
    System.out.print(stringBuilder);
  }




  /**
   * Gets a validated integer input from the user.
   *
   * @param scanner The scanner object to read input from the user.
   * @param prompt  The prompt to display to the user.
   * @return The validated integer input.
   * @since 0.0.3
   */
  private static int getValidatedIntInput(Scanner scanner, String prompt) {
    int input;
    while (true) {
      System.out.print(prompt);  // Print the prompt
      try {
        input = scanner.nextInt();  // Read the input
        if (input <= 0) {
          throw new InputMismatchException("Input must be a positive integer.");
        }
        return input;  // Return the input if it is valid
      } catch (InputMismatchException e) {
        System.out.println("Invalid input. Please enter a positive integer.");
        scanner.nextLine(); // Clear the buffer
      }
    }
  }




  /**
   * Gets a validated string input from the user.
   *
   * @param scanner The scanner object to read input from the user.
   * @param prompt  The prompt to display to the user.
   * @return The validated string input.
   * @since 0.0.3
   */
  private static String getValidatedStringInput(Scanner scanner, String prompt) {
    System.out.print(prompt);  // printing the prompt
    return scanner.next();
  }





  /**
   * Handles the logic for choosing canvas dimensions.
   *
   * @param scanner The scanner object to read input from the user.
   * @since 0.0.3
   */
  private static void handleChooseCanvasDimensions(Scanner scanner) {
    canvasWidth = getValidatedIntInput(scanner, "Enter canvas width: ");
    canvasHeight = getValidatedIntInput(scanner, "Enter canvas height: ");
    System.out.println("Canvas dimensions set to " + canvasWidth + "x" + canvasHeight);
  }




  /**
   * Handles the logic for reading fractal description from a file.
   *
   * @param scanner The scanner object to read input from the user.
   * @since 0.0.3
   */
  private static void handleReadFractalDescriptionFromFile(Scanner scanner) {
    String readPath = getValidatedStringInput(scanner, "Enter the file path: ");

    try {   // Try to read the file, and set the game object

      // Pair object containing the ChaosGameDescription and the weights
      Pair<ChaosGameDescription, List<Double>> result = ChaosGameFileHandler.readFile(readPath);
      ChaosGameDescription description = result.getKey(); // The ChaosGameDescription object
      List<Double> weights = result.getValue();        // The weights for the affine transformations
      game = new ChaosGame(description, canvasWidth, canvasHeight);
      game.setTransformWeights(weights);
      System.out.println("Fractal description read successfully!");

    } catch (IOException e) {
      System.out.println("Error reading file: " + e.getMessage());
    }
  }




  /**
   * Handles the logic for writing fractal description to a file.
   *
   * @param scanner The scanner object to read input from the user.
   * @since 0.0.3
   */
  private static void handleWriteFractalDescriptionToFile(Scanner scanner) {
    if (game == null) {
      System.out.println(NO_GAME_LOADED);
      return;
    }
    String writePath = getValidatedStringInput(scanner, "Enter the file path to save: ");
    try {
      ChaosGameFileHandler.writeToFile(game, writePath, "Affine2D");
      System.out.println("Fractal description written successfully!");
    } catch (IOException e) {
      System.out.println("Error writing file: " + e.getMessage());
    }
  }





  /**
   * Handles the logic for running iterations.
   *
   * @param scanner The scanner object to read input from the user.
   * @since 0.0.3
   */
  private static void handleRunIterations(Scanner scanner) {
    if (game == null) {
      System.out.println(NO_GAME_LOADED);
      return;
    }
    int iterations = getValidatedIntInput(scanner, "Enter the number of iterations: ");
    game.runSteps(iterations);
    System.out.println("Iterations completed!");
  }




  /**
   * Handles the logic for printing the ASCII fractal to the console.
   *
   * @since 0.0.3
   */
  private static void handlePrintAsciiFractalToConsole() {
    if (game == null) {
      System.out.println(NO_GAME_LOADED);
      return;
    }
    game.printFractal(canvasWidth, canvasHeight);
  }




  /**
   * The main method for running the Chaos Game CLI.
   *
   * @param args The command-line arguments.
   * @since 0.0.1
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Welcome to the Chaos Game CLI!");
    boolean running = true;

    while (running) {
      printMenu();
      int choice = getValidatedIntInput(scanner, "");

      switch (choice) {
        case CHOOSE_CANVAS_DIMENSIONS:
          handleChooseCanvasDimensions(scanner);
          break;

        case READ_FRACTAL_DESCRIPTION_FROM_FILE:
          handleReadFractalDescriptionFromFile(scanner);
          break;

        case WRITE_FRACTAL_DESCRIPTION_TO_FILE:
          handleWriteFractalDescriptionToFile(scanner);
          break;

        case RUN_ITERATIONS:
          handleRunIterations(scanner);
          break;

        case PRINT_ASCII_FRACTAL_TO_CONSOLE:
          handlePrintAsciiFractalToConsole();
          break;

        case EXIT:
          System.out.println("Exiting...");
          running = false;
          break;

        default:
          System.out.println("Invalid choice. Please enter a number between 1 and 6.");
      }
    }

    scanner.close();
  }
}
