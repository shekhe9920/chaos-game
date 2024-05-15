package edu.ntnu.stud.idatg2003.filehandling;

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



/**
 * Provides functionality for persisting and retrieving {@link ChaosGameDescription}
 * instances to and from files.
 * This class allows chaos game configurations, including transformations and drawing bounds,
 * to be saved to a file for later use, or loaded from a file to recreate a game state.
 * It supports both affine transformations and Julia sets,
 * identifying the correct type based on the file content.
 *
 * @version 0.0.3
 * @since 0.0.1 (The version of Chaos-Game application when introduced)
 */
public class ChaosGameFileHandler {


  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  ChaosGameFileHandler () {
  }


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
  public static ChaosGameDescription readFile(String path) throws IOException {
    try (Scanner fileScanner = new Scanner(new File(path))) {
      fileScanner.useLocale(Locale.ENGLISH);

      // Extracting the type of fractal (Affine2D or Julia):
      String transformationType = fileScanner.nextLine().split("#")[0].trim();


      // Processing the coordinates for the lower left corner, ignoring comments:
      String[] lowerLeftValues =
          fileScanner.nextLine().split("#")[0].trim().split(",");

      Vector2D minCoords =
          new Vector2D(
              Double.parseDouble(lowerLeftValues[0].trim()),
              Double.parseDouble(lowerLeftValues[1].trim()));

      // Processing the coordinates for the upper right corner, ignoring comments:
      String[] upperRightValues =
          fileScanner.nextLine().split("#")[0].trim().split(",");

      Vector2D maxCoords =
          new Vector2D(
              Double.parseDouble(upperRightValues[0].trim()),
              Double.parseDouble(upperRightValues[1].trim()));


      List<Transform2D> transforms = new ArrayList<>();
      while (fileScanner.hasNextLine()) {
        String line = fileScanner.nextLine();

        if (line.startsWith("#") || line.trim().isEmpty()) {
          continue; // Skipping comments and empty lines
        }

        // Splitting the line on '#' to remove comments, then split on ',' to get numeric values:
        String[] numericValues = line.split("#")[0].trim().split(",");

        // Handling different transformation types based on the fractal type specified:
        if (transformationType.equals("Affine2D")) {
          double[] matrixValues = Stream.of(numericValues)
              .map(String::trim)
              .mapToDouble(Double::parseDouble)
              .toArray();

          if (matrixValues.length == 6) {
            Matrix2x2 matrix =
                new Matrix2x2(matrixValues[0], matrixValues[1], matrixValues[2], matrixValues[3]);
            Vector2D vector =
                new Vector2D(matrixValues[4], matrixValues[5]);

            transforms.add(new AffineTransform2D(matrix, vector));
          } else {
            throw new IOException(
                    "Incorrect number of values for Affine2D transformation on one of the lines.");
          }

          // Handling Julia transformations:
        } else if (transformationType.equals("Julia")) {
          if (numericValues.length == 2) {
            Complex constant =
                new Complex(Double.parseDouble(numericValues[0].trim()),
                    Double.parseDouble(numericValues[1].trim()));

            // Adding two Julia transformations with opposite signs to the list:
            transforms.add(new JuliaTransform(constant, 1));
            transforms.add(new JuliaTransform(constant, -1));
          } else {
            throw new IOException(
                    "Incorrect number of values for Julia transformation on one of the lines.");
          }
        }


      }
      return new ChaosGameDescription(minCoords, maxCoords, transforms);
    }

  }






  /**
   * Writes the given {@link ChaosGameDescription} to a file at the specified path.
   * The output file will contain the fractal type, the drawing area coordinates,
   * and details of each transformation in a format compatible with {@link #readFile(String)}.
   *
   * @param description The chaos game description to write to the file.
   * @param path The file path where the description should be saved.
   * @throws IOException If an error occurs during file writing.
   * @since 0.0.1
   */
  /*
  public static void writeToFile(ChaosGameDescription description, String path) throws IOException {
    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path))) {

      writer.write("Affine2D\n");
      writer.write(description.getMinCoords().getX0() + ","
          + description.getMinCoords().getX1() + "\n");
      writer.write(description.getMaxCoords().getX0() + ","
          + description.getMaxCoords().getX1() + "\n");

      for (Transform2D transform : description.getTransformations()) {
        // Here we need to cast to the expected transform type to access its properties:
        if (transform instanceof AffineTransform2D affine) {
          Matrix2x2 matrix = affine.getMatrix();
          Vector2D vector = affine.getVector();
          writer.write(matrix.getA00() + "," + matrix.getA01() + ","
              + matrix.getA10() + "," + matrix.getA11() + ","
              + vector.getX0() + "," + vector.getX1() + "\n");
        }
      }
    } catch (IOException e) {
      throw new IOException("An error occurred while writing to the file.", e);
    }
  }*/


  public static void writeToFile
  (ChaosGameDescription description, String path, String typeOfTransformation) throws IOException {

    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path))) {

      writer.write(typeOfTransformation + "\n");

      writer.write(description.getMinCoords().getX0() + ","
          + description.getMinCoords().getX1() + "\n");
      writer.write(description.getMaxCoords().getX0() + ","
          + description.getMaxCoords().getX1() + "\n");

      if ("Affine2D".equals(typeOfTransformation)) {
        for (Transform2D transform : description.getTransformations()) {
          if (transform instanceof AffineTransform2D affine) {
            Matrix2x2 matrix = affine.getMatrix();
            Vector2D vector = affine.getVector();
            writer.write(matrix.getA00() + "," + matrix.getA01() + ","
                + matrix.getA10() + "," + matrix.getA11() + ","
                + vector.getX0() + "," + vector.getX1() + "\n");
          }
        }
      } else if ("Julia".equals(typeOfTransformation)) {
        for (Transform2D transform : description.getTransformations()) {
          if (transform instanceof JuliaTransform julia) {
            Complex constant = julia.getPoint();

            writer.write(constant.getX0() + "," + constant.getX1() + "\n");
          }
        }
      }
    } catch (IOException e) {
      throw new IOException("An error occurred while writing to the file.", e);
    }
  }

}
