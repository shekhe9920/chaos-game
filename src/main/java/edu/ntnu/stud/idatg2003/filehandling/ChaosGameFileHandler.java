package edu.ntnu.stud.idatg2003.filehandling;

import edu.ntnu.stud.idatg2003.engine.ChaosGameDescription;
import edu.ntnu.stud.idatg2003.mathoperations.Complex;
import edu.ntnu.stud.idatg2003.mathoperations.Matrix2x2;
import edu.ntnu.stud.idatg2003.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.transformation.AffineTransform2D;
import edu.ntnu.stud.idatg2003.transformation.JuliaTransform;
import edu.ntnu.stud.idatg2003.transformation.Transform2D;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * The {@code ChaosGameFileHandler} class provides methods for reading and writing chaos game descriptions
 * from/to files.
 *
 * @version 0.0.1
 * @since 0.0.1
 */
public class ChaosGameFileHandler {

  /**
   * Reads a chaos game description from the specified file.
   *
   * @param path The path to the file to read.
   * @return A {@code ChaosGameDescription} object representing the chaos game description.
   * @throws FileNotFoundException If the file is not found.
   * @throws IllegalArgumentException If the file format is invalid.
   * @since 0.0.1
   */

  public static ChaosGameDescription readFromFile(String path) throws FileNotFoundException {
    try (Scanner scanner = new Scanner(new File(path))) {
      // Skip comment line
      scanner.nextLine();

      // Read min coordinates
      String[] minCoordsLine = scanner.nextLine().split("#")[0].split(",");
      double x0Min = Double.parseDouble(minCoordsLine[0].trim());
      double x1Min = Double.parseDouble(minCoordsLine[1].trim());
      Vector2D minCoords = new Vector2D(x0Min, x1Min);

      // Read max coordinates
      String[] maxCoordsLine = scanner.nextLine().split("#")[0].split(",");
      double x0Max = Double.parseDouble(maxCoordsLine[0].trim());
      double x1Max = Double.parseDouble(maxCoordsLine[1].trim());
      Vector2D maxCoords = new Vector2D(x0Max, x1Max);

      // Read transformations
      List<Transform2D> transformations = new ArrayList<>();
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine().trim();
        // Skip empty lines and comments
        if (line.isEmpty() || line.startsWith("#")) {
          continue;
        }
        String[] transformParams = line.split("#")[0].split(",");
        double a00 = Double.parseDouble(transformParams[0].trim());
        double a01 = Double.parseDouble(transformParams[1].trim());
        double a10 = Double.parseDouble(transformParams[2].trim());
        double a11 = Double.parseDouble(transformParams[3].trim());
        double b0 = Double.parseDouble(transformParams[4].trim());
        double b1 = Double.parseDouble(transformParams[5].trim());
        transformations.add(new AffineTransform2D(
            new Matrix2x2(new double[][]{{a00, a01}, {a10, a11}}),
            new Vector2D(b0, b1)));
      }

      return new ChaosGameDescription(transformations, minCoords, maxCoords);
    }
  }


  /**
   * Reads a chaos game description from a file containing Julia transforms.
   *
   * @param filePath The path to the file to read.
   * @return A {@code ChaosGameDescription} object representing the chaos game description.
   * @throws FileNotFoundException If the file is not found.
   * @since 0.0.1
   */

  public static ChaosGameDescription readJuliaTransformsFromFile(String filePath) throws FileNotFoundException {
    try (Scanner scanner = new Scanner(new File(filePath))) {
      scanner.nextLine(); // Skip comment line
      // Read min coordinates
      String[] minCoordsLine = scanner.nextLine().split("#")[0].split(",");
      double x0Min = Double.parseDouble(minCoordsLine[0].trim());
      double x1Min = Double.parseDouble(minCoordsLine[1].trim());
      Vector2D minCoords = new Vector2D(x0Min, x1Min);

      // Read max coordinates
      String[] maxCoordsLine = scanner.nextLine().split("#")[0].split(",");
      double x0Max = Double.parseDouble(maxCoordsLine[0].trim());
      double x1Max = Double.parseDouble(maxCoordsLine[1].trim());
      Vector2D maxCoords = new Vector2D(x0Max, x1Max);

      // Read constant c
      String[] cValues = scanner.nextLine().split("#")[0].split(",");
      double cReal = Double.parseDouble(cValues[0].trim());
      double cImaginary = Double.parseDouble(cValues[1].trim());

      return new ChaosGameDescription(
          Collections.singletonList(new JuliaTransform(new Complex(cReal, cImaginary), 1)),
          minCoords, maxCoords);
    }
  }


  /**
   * Writes the given chaos game description to the specified file.
   *
   * @param chaosGameDescription The {@code ChaosGameDescription} object to write.
   * @param path                 The path to the file to write to.
   * @throws IOException If an I/O error occurs.
   * @since 0.0.1
   */
  public static void writeToFile(ChaosGameDescription chaosGameDescription, String path)
      throws IOException {

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
      // Write the type of fractal
      writer.write("Affine2D\n");

      // Write min and max coordinates
      writer.write(String.format("%f, %f%n", chaosGameDescription.getMinCoords().getX0(),
          chaosGameDescription.getMinCoords().getX1()));
      writer.write(String.format("%f, %f%n", chaosGameDescription.getMaxCoords().getX0(),
          chaosGameDescription.getMaxCoords().getX1()));

      // Write transformations
      for (Transform2D transformation : chaosGameDescription.getTransformations()) {
        if (transformation instanceof AffineTransform2D) {
          Matrix2x2 matrix = ((AffineTransform2D) transformation).getMatrix();
          Vector2D vector = ((AffineTransform2D) transformation).getVector();
          writer.write(String.format("%f, %f, %f, %f, %f, %f%n",
              matrix.getElement(0, 0), matrix.getElement(0, 1),
              matrix.getElement(1, 0), matrix.getElement(1, 1),
              vector.getX0(), vector.getX1())
          );
        }
      }
    }
  }
}
