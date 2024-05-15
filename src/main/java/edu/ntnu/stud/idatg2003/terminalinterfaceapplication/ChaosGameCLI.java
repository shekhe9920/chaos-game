package edu.ntnu.stud.idatg2003.terminalinterfaceapplication;

import edu.ntnu.stud.idatg2003.backend.engine.ChaosCanvas;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGame;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescription;
import edu.ntnu.stud.idatg2003.filehandling.ChaosGameFileHandler;
import java.io.IOException;
import java.util.Scanner;
import javax.print.DocFlavor.STRING;


/**
 * The {@code ChaosGameCLI} is a simple command line interface application,
 * where the user is able to interact with the terminal and generate fractals.
 *
 * @version 0.0.2
 * @since 0.0.1 (The version of ChaosGameEngine application when introduced)
 */
public class ChaosGameCLI {

  private static final int READ_FRACTAL_DESCRIPTION_FROM_FILE = 1;
  private static final int WRITE_FRACTAL_DESCRIPTION_TO_FILE = 2;
  private static final int RUN_ITERATIONS = 3;
  private static final int PRINT_ASCII_FRACTAL_TO_CONSOLE = 4;
  private static final int EXIT = 5;


  /**
  * Prints the menu for the command line interface.
  *
  * @since 0.0.1
  */
  public static void printMenu() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("====== Chaos Game ======\n").append(
            "1. Choose canvas dimensions\n").append(
            "2. Load description from file\n").append(
            "3. Save description to file\n").append(
            "4. Run iterations\n").append(
            "5. Print ASCII fractal\n").append(
            "6. Exit\n")
        .append("Choose an option: ");
  }


  /**
  * The main method for running the command line interface application.
  *
  * @param args The command-line arguments.
  * @since 0.0.1
  */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Welcome to the Chaos Game CLI!");
    ChaosGame game = null;
    boolean running = true; // Control variable for the loop

    while (running) {

      printMenu();

      int choice = scanner.nextInt();
      switch (choice) {
        case READ_FRACTAL_DESCRIPTION_FROM_FILE:
          System.out.print("Enter the file path: ");
          String readPath = scanner.next();
          try {
            ChaosGameDescription description = ChaosGameFileHandler.readFile(readPath);
            game = new ChaosGame(description, 500, 150);
            System.out.println("Fractal description read successfully!");
          } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
          }
          break;

        case WRITE_FRACTAL_DESCRIPTION_TO_FILE:
          // TODO: Implement this feature:
          /*
          if (game == null) {
            System.out.println("No game loaded. Read a description first.");
            break;
          }
          System.out.print("Enter the file path to save: ");
          String writePath = scanner.next();
          try {
            ChaosGameFileHandler.writeToFile(game.getDescription(), writePath);
            System.out.println("Fractal description written successfully!");
          } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
          }
          break;*/

        case RUN_ITERATIONS:
          if (game == null) {
            System.out.println("No game loaded. Read a description first.");
            break;
          }
          System.out.print("Enter the number of iterations: ");
          int iterations = scanner.nextInt();
          game.runSteps(iterations);
          System.out.println("Iterations completed!");
          break;

        case PRINT_ASCII_FRACTAL_TO_CONSOLE:
          if (game == null) {
            System.out.println("No game loaded. Read a description first.");
            break;
          }
          game.printFractal();
          break;

        case EXIT:
          System.out.println("Exiting...");
          running = false; // Setting 'running' to 'false', to exit the loop.
          break;
        default:
          System.out.println("Invalid choice. Please enter a number between 1 and 5.");
      }
    }

    scanner.close();
  }

}
