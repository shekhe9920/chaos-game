package edu.ntnu.stud.idatg2003;

import edu.ntnu.stud.idatg2003.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.transformation.AffineTransform2D;


/**
 *
 */
public class ChaosCanvas {

  private int canvas[][];
  private int width;
  private int height;

  private Vector2D minCoords;
  private Vector2D maxCoords;
  private AffineTransform2D transformCoordsToIndices;

  /**
   * Creates a new ChaosCanvas with the given width and height.
   * The canvas is initially empty, i.e. all pixels are set to 0.
   *
   * @param width  The width of the canvas.
   * @param height The height of the canvas.
   */
  public ChaosCanvas(int width, int height, Vector2D minCoords, Vector2D maxCoords) {
    this.width = width;
    this.height = height;
    canvas = new int[width][height];
  }

  /**
   * Transforms the given vector to the corresponding pixel indices.
   * @param point The vector transform
   * @return The pixel indices.
   */
  public Vector2D getPixel(Vector2D point) {
    return new Vector2D(point.getX0(), point.getX1());
  }


  /**
   * Puts a pixel at the given point.
   *
   * @param point The point to put the pixel at.
   * @throws IllegalArgumentException If the point is null.
   */
  public void putPixel(Vector2D point) {
    if (point != null) {
      int x0 = (int) point.getX0();
      int x1 = (int) point.getX1();


      canvas[x0][x1] = 1;
    } else {
      throw new IllegalArgumentException("Point cannot be null");
    }
  }


  /**
   * Gets the canvas array.
   *
   * @return The canvas array.
   */
  public int[][] getCanvasArray() {
    return canvas;
  }

  /**
   * Clears the canvas, i.e., sets all pixels to 0.
   */
  public void clear() {
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        canvas[i][j] = 0;
      }
    }
  }
}
