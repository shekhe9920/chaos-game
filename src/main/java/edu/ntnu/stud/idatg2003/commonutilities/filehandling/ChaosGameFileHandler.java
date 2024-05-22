package edu.ntnu.stud.idatg2003.commonutilities.filehandling;

import edu.ntnu.stud.idatg2003.backend.engine.ChaosGame;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescription;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Complex;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Matrix2x2;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.transformations.AffineTransform2D;
import edu.ntnu.stud.idatg2003.backend.transformations.JuliaTransform;
import edu.ntnu.stud.idatg2003.backend.transformations.Transform2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Stream;
import javafx.util.Pair;


/**
 * Provides functionality for persisting and retrieving {@link ChaosGameDescription}
 * instances to and from files.
 * This class allows chaos game configurations, including transformations and drawing bounds,
 * to be saved to a file for later use, or loaded from a file to recreate a game state.
 * It supports both affine transformations and Julia sets,
 * identifying the correct type based on the file content.
 *
 * @version 0.0.4
 * @since 0.0.1 (The version of Chaos-Game application when introduced)
 */
public class ChaosGameFileHandler {


  private static final String AFFINE_2D = "Affine2D";
  private static final String JULIA = "Julia";

  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  ChaosGameFileHandler() {
    // This constructor is intentionally empty. Nothing special is needed here.
  }




  //################################################################################################
  //======================= Read and helper methods for reading from file ========================//

  /**
   * Reads a chaos game description from a specified file path.
   * The file is expected to contain a specific format where the first line indicates the type of
   * fractal, followed by the coordinates defining the drawing area,
   * and then the transformation details.
   * Lines starting with "#" are treated as comments and ignored.
   *
   * @param path The file path from which to read the chaos game description.
   * @return A {@link ChaosGameDescription} populated with the data read from the file.
   * @throws IOException If an error occurs during file reading or if the file format is incorrect.
   * @since 0.0.3
   */
  public static Pair<ChaosGameDescription, List<Double>> readFile(String path) throws IOException {
    try (Scanner fileScanner = new Scanner(new File(path))) {
      fileScanner.useLocale(Locale.ENGLISH);

      // reading the header and coordinates of the drawing area:
      String transformationType = readHeader(fileScanner);
      Vector2D minCoords = readCoordinates(fileScanner);
      Vector2D maxCoords = readCoordinates(fileScanner);

      List<Transform2D> transforms = new ArrayList<>();  // list of transformations
      List<Double> weights = new ArrayList<>();          // list of weights

      // reading the transformations:
      readTransformations(fileScanner, transformationType, transforms, weights);

      validateWeights(transformationType, transforms, weights); // validating the number of weights

      ChaosGameDescription description = new ChaosGameDescription(minCoords, maxCoords, transforms);
      return new Pair<>(description, weights);  // pair of ChaosGameDescription and weights
    }
  }




  /**
   * Reads the header of the file to determine the type of transformation.
   *
   * @param fileScanner The scanner used to read the file.
   * @return The type of transformation.
   * @since 0.0.4
   */
  private static String readHeader(Scanner fileScanner) {
    // reading the first line to determine the type of transformation:
    return fileScanner.nextLine().split("#")[0].trim();
  }




  /**
   * Reads the coordinates of the drawing area from the file.
   *
   * @param fileScanner The scanner used to read the file.
   * @return The coordinates of the drawing area.
   * @since 0.0.4
   */
  private static Vector2D readCoordinates(Scanner fileScanner) {
    // reading the coordinates of the drawing area, one line at a time:
    String[] values = fileScanner.nextLine().split("#")[0].trim().split(",");
    return new Vector2D(Double.parseDouble(values[0].trim()), Double.parseDouble(values[1].trim()));
  }




  /**
   * Reads the transformations from the file and populates the provided lists.
   *
   * @param fileScanner The scanner used to read the file.
   * @param transformationType The type of transformation (Affine2D or Julia).
   * @param transforms The list of transformations to populate.
   * @param weights The list of weights to populate.
   * @throws IOException If an error occurs during reading.
   * @since 0.0.4
   */
  private static void readTransformations(Scanner fileScanner, String transformationType,
      List<Transform2D> transforms, List<Double> weights) throws IOException {

    while (fileScanner.hasNextLine()) {  // reading the transformations, one line at a time:
      String line = fileScanner.nextLine();  // next line from the file

      if (line.startsWith("#") || line.trim().isEmpty()) {  // skipping comments and empty lines
        continue;
      }

      // splitting the line by "#" and then by "," to get the numeric values:
      String[] numericValues = line.split("#")[0].trim().split(",");

      // reading the transformation based on the type:
      if (transformationType.equals(AFFINE_2D)) {         // if Affine2D transformation:
        readAffineTransformation(numericValues, transforms, weights);

      } else if (transformationType.equals(JULIA)) {       // if Julia transformation:
        readJuliaTransformation(numericValues, transforms);
      }
    }


  }




  /**
   * Reads an affine transformation from the file and populates the provided lists.
   *
   * @param numericValues The numeric values of the transformation.
   * @param transforms The list of transformations to populate.
   * @param weights The list of weights to populate.
   * @throws IOException If an error occurs during reading.
   * @since 0.0.4
   */
  private static void readAffineTransformation(String[] numericValues, List<Transform2D> transforms,
      List<Double> weights) throws IOException {


    double[] matrixValues =    // converting the numeric values to a double array:
        Stream.of(numericValues).map(String::trim).mapToDouble(Double::parseDouble).toArray();

    if (matrixValues.length == 7) {   // if the number of values is correct:
      Matrix2x2 matrix = // Matrix2x2 = a0, a1, b0, b1
          new Matrix2x2(matrixValues[0], matrixValues[1], matrixValues[2], matrixValues[3]);

      Vector2D vector = new Vector2D(matrixValues[4], matrixValues[5]); // Vector2D = vx, vy
      // adding the transformation to the list:
      transforms.add(new AffineTransform2D(matrix, vector));
      weights.add(matrixValues[6]);  // adding the weight to the list

    } else {

      throw new IOException(
          "Incorrect number of values for Affine2D transformation on one of the lines."
      );

    }

  }




  /**
   * Reads a Julia transformation from the file and populates the provided list.
   *
   * @param numericValues The numeric values of the transformation.
   * @param transforms The list of transformations to populate.
   * @throws IOException If an error occurs during reading.
   * @since 0.0.4
   */
  private static void readJuliaTransformation(String[] numericValues,
      List<Transform2D> transforms) throws IOException {

    if (numericValues.length == 4) {   // expecting 4 values for Julia transformation:
      Complex constant =  // Complex = x, y
          new Complex(Double.parseDouble(numericValues[0].trim()),
              Double.parseDouble(numericValues[1].trim()));

      int order = Integer.parseInt(numericValues[2].trim()); // c^n (order)
      boolean isMultiOrder = Boolean.parseBoolean(numericValues[3].trim()); // multi-order

      // adding the Julia transformations to the list:
      transforms.add(new JuliaTransform(constant, 1, order, isMultiOrder));
      transforms.add(new JuliaTransform(constant, -1, order, isMultiOrder));

    } else {

      throw new IOException(
          "Incorrect number of values for Julia transformation on one of the lines."
      );

    }
  }




  /**
   * Validates the number of weights against the number of transformations.
   *
   * @param transformationType The type of transformation (Affine2D or Julia).
   * @param transforms The list of transformations.
   * @param weights The list of weights.
   * @throws IOException If the number of weights does not match the number of transformations.
   * @since 0.0.4
   */
  private static void validateWeights(String transformationType, List<Transform2D> transforms,
      List<Double> weights) throws IOException {

    // if Affine2D transformation, a check for the number of transformations and weights:
    if (transformationType.equals(AFFINE_2D) && transforms.size() != weights.size()) {
      throw new IOException("Mismatch between number of transformations and weights.");
    }

  }









  //################################################################################################
  //======================= Write and helper methods for writing to file ========================//


  /**
   * Writes the given {@link ChaosGameDescription} to a file at the specified path.
   * The output file will contain the fractal type, the drawing area coordinates,
   * and details of each transformation in a format compatible with {@link #readFile(String)}.
   *
   * @param game The chaos game instance containing the description to write to the file.
   * @param path The file path where the description should be saved.
   * @param typeOfTransformation The type of transformation (Affine2D or Julia).
   * @throws IOException If an error occurs during file writing.
   * @since 0.0.1
   */
  public static void writeToFile(ChaosGame game, String path,
      String typeOfTransformation) throws IOException {
    if (game == null) {
      throw new NullPointerException("game cannot be null");
    }

    ChaosGameDescription description = game.getDescription();

    // buffering the writer to improve performance:
    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path))) {
      // writing the header and coordinates of the drawing area:
      writeHeader(writer, typeOfTransformation, description);

      if (AFFINE_2D.equals(typeOfTransformation)) { // if Affine2D transformation:
        writeAffineTransformations(writer, description, game.getWeights());

      } else if (JULIA.equals(typeOfTransformation)) { // if Julia transformation:
        writeJuliaTransformations(writer, description);
      }

    } catch (IOException e) {
      System.err.println("Error details: " + e.getMessage());
      throw new IOException("An error occurred while writing to the file.", e);
    }

  }


  /**
   * Writes the header of the file containing the fractal type and drawing area coordinates.
   *
   * @param writer The writer used to write to the file.
   * @param typeOfTransformation The type of transformation (Affine2D or Julia).
   * @param description The chaos game description.
   * @throws IOException If an error occurs during writing.
   * @since 0.0.4
   */
  private static void writeHeader(BufferedWriter writer, String typeOfTransformation,
      ChaosGameDescription description) throws IOException {

    writer.write(typeOfTransformation + "\n"); // writing the type of transformation

    writer.write(description.getMinCoords().getX0() // min-coordinates of the drawing area
        + "," + description.getMinCoords().getX1() + "\n");

    writer.write(description.getMaxCoords().getX0()  // max-coordinates of the drawing area
        + "," + description.getMaxCoords().getX1() + "\n");

  }



  /**
   * Writes the affine transformations to the file.
   *
   * @param writer The writer used to write to the file.
   * @param description The chaos game description.
   * @param weights The list of weights for the transformations.
   * @throws IOException If an error occurs during writing.
   * @since 0.0.4
   */
  private static void writeAffineTransformations(BufferedWriter writer,
      ChaosGameDescription description, List<Double> weights) throws IOException {

    if (weights == null || weights.isEmpty()) {
      throw new IOException("Weights list is null or empty.");
    }

    int weightIndex = 0;  // index for the weight list
    // iterating through the transformations and writing the values to the file:
    for (Transform2D transform : description.getTransformations()) {
      if (transform instanceof AffineTransform2D affine) {
        Matrix2x2 matrix = affine.matrix();
        Vector2D vector = affine.vector();

        // the number of weights should match the number of transformations:
        if (weightIndex >= weights.size()) {
          throw new IOException("Mismatch between number of transformations and weights.");
        }

        // writing the transformation values to the file:
        writer.write(
            matrix.a00() + "," + matrix.a01() + ","          // | a00, a01 |
                + matrix.a10() + "," + matrix.a11() + ","        // | a10, a11 |
                + vector.getX0() + "," + vector.getX1() + ","    // v = [ vx, vy ]
                + weights.get(weightIndex++) + "\n");      // weight
      }
    }
  }


  /**
   * Writes the Julia transformations to the file.
   *
   * @param writer The writer used to write to the file.
   * @param description The chaos game description.
   * @throws IOException If an error occurs during writing.
   * @since 0.0.4
   */
  private static void writeJuliaTransformations(BufferedWriter writer,
      ChaosGameDescription description) throws IOException {

    boolean written = false;  // flag to check if the Julia transformation is written

    // iterating through the transformations and writing the values to the file:
    for (Transform2D transform : description.getTransformations()) {

      if (transform instanceof JuliaTransform julia) {  // if Julia transformation:
        Complex constant = julia.getPoint();            // constant for the Julia set, c = x + iy

        writer.write(constant.getX0() + "," + constant.getX1()  // x, y
            + "," + julia.getOrder() + ","    // order n (c^n)
            + julia.isMultiOrder() + "\n"); // multi-order, n > 2

        written = true; // setting the flag to true
        break; // only write once as Julia sets have the same constant for both transformations
      }
    }
    if (!written) {
      throw new IOException("No JuliaTransform found in the transformations.");
    }
  }

}
