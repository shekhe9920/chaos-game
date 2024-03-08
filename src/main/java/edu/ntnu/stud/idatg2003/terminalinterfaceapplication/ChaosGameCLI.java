package edu.ntnu.stud.idatg2003.terminalinterfaceapplication;

import edu.ntnu.stud.idatg2003.engine.ChaosCanvas;
import edu.ntnu.stud.idatg2003.engine.ChaosGame;
import edu.ntnu.stud.idatg2003.engine.ChaosGameDescription;
import edu.ntnu.stud.idatg2003.filehandling.ChaosGameFileHandler;
import java.io.IOException;
import java.util.Scanner;


/**
 * The {@code ChaosGameCLI} is a simple command line interface application,
 * where the user is able to interact with the terminal and generate fractals.
 *
 * @version 0.0.1
 * @since 0.0.1
 */
public class ChaosGameCLI {


    /**
     * The main method for running the command line interface application.
     *
     * @param args The command-line arguments.
     * @since 0.0.1
     */
    public static void main(String[] args) {
        int height = 0;
        int width = 0;
        int maxIterations;
        String filePath = null;
        Scanner scanner = new Scanner(System.in);
        ChaosGameDescription gameDescription = null;
        ChaosCanvas canvas = null;
        ChaosGame game = null;

        boolean running = true;
        while (running) {
            System.out.println("====== Menu ======");
            System.out.println("1. Choose canvas dimensions");
            System.out.println("2. Load description from file");
            System.out.println("3. Save description to file");
            System.out.println("4. Run iterations");
            System.out.println("5. Print ASCII fractal");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.println("Choose the dimensions of the canvas");
                    System.out.println("Enter the width of the canvas: ");
                    width = scanner.nextInt();
                    System.out.println("Enter the height of the canvas: ");
                    height = scanner.nextInt();
                    break;

                case 2:
                    System.out.print("Enter file name to load description from: ");
                    filePath = scanner.nextLine();
                    try {
                        gameDescription = ChaosGameFileHandler.readFromFile(filePath);
                        canvas = new ChaosCanvas(width, height, gameDescription.getMinCoords(), gameDescription.getMaxCoords());
                        game = new ChaosGame(gameDescription, canvas.getWidth(), canvas.getHeight());
                        System.out.println("Description loaded successfully.");
                    } catch (IOException e) {
                        System.err.println("Error loading description from file: " + e.getMessage());
                    }
                    break;

                case 3:
                    if (gameDescription != null) {
                        System.out.print("Enter file name to save description to: ");
                        String saveFileName = scanner.nextLine();
                        try {
                            ChaosGameFileHandler.writeToFile(gameDescription, saveFileName);
                            System.out.println("Description saved successfully.");
                        } catch (IOException e) {
                            System.err.println("Error saving description to file: " + e.getMessage());
                        }
                    } else {
                        System.out.println("No description loaded yet.");
                    }
                    break;

                case 4:
                    if (game != null) {
                        System.out.print("Enter number of iterations: ");
                        int iterations = scanner.nextInt();
                        game.runSteps(iterations);
                        System.out.println("Iterations completed.");
                    } else {
                        System.out.println("No game description loaded yet.");
                    }
                    break;

                case 5:
                    if (canvas != null) {
                        System.out.print("Enter number of iterations: ");
                        maxIterations = scanner.nextInt();

                        printCanvas(filePath, maxIterations);
                    } else {
                        System.out.println("No canvas created yet.");
                    }
                    break;

                case 6:
                    running = false;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
                    break;
            }
        }

        scanner.close();
    }

    /**
     * Prints a fractal based on the chaos game description in the specified file.
     *
     * @param filePath     The path to the file containing the chaos game description.
     * @param maxIterations The maximum number of iterations to run the chaos game.
     * @since 0.0.1
     */
    private static void printCanvas(String filePath, int maxIterations) {
        ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
        try {
            ChaosGameDescription description = fileHandler.readFromFile(filePath);
            ChaosGame chaosGame = new ChaosGame(description, 1000, 800); // canvas size
            chaosGame.runSteps(maxIterations); // Run with maximum iterations
            ChaosCanvas canvas = chaosGame.getCanvas();
            int[][] canvasArray = canvas.getCanvasArray();

            StringBuilder outputBuilder = new StringBuilder(); // StringBuilder to build the output string

            // Iterating over the canvas array and building the output string
            for (int i = 0; i < canvasArray.length; i++) {
                for (int j = 0; j < canvasArray[i].length; j++) {
                    // "X" for colored pixels and space for blank pixels
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
}
