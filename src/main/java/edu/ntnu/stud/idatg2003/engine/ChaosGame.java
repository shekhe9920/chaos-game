package edu.ntnu.stud.idatg2003.engine;

import edu.ntnu.stud.idatg2003.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.transformation.Transform2D;
import java.util.Random;

/**
 * The {@code ChaosGame} class represents a chaos game, which involves playing a given number of rounds
 * to generate a fractal based on a provided chaos game description.
 * <p>
 * The chaos game requires both a description and a canvas for drawing. Additionally, it keeps track of
 * the current point where it is positioned.
 *
 * @version 0.0.1
 * @since 0.0.1
 */
public class ChaosGame {

  private final ChaosCanvas canvas;
  private final ChaosGameDescription description;
  private Vector2D currentPoint;
  private Random random;


  /**
   * Constructs a new {@code ChaosGame} object with the given chaos game description and canvas size.
   *
   * @param description The chaos game description specifying transformations and bounds.
   * @param width       The width of the canvas.
   * @param height      The height of the canvas.
   * @since 0.0.1
   */
  public ChaosGame(ChaosGameDescription description, int width, int height) {
    this.description = description;
    this.canvas =
        new ChaosCanvas(width, height, description.getMinCoords(), description.getMaxCoords());
  }


  /**
   * Retrieves the canvas used by this chaos game.
   *
   * @return The canvas object.
   * @since 0.0.1
   */
  public ChaosCanvas getCanvas() {
    return canvas;
  }


  /**
   * Plays the chaos game for the specified number of steps.
   * Within each step, a random transformation is chosen from the game description,
   * and the current point is updated accordingly.
   * The updated point is then drawn on the canvas.
   *
   * @param steps The number of steps to play the chaos game.
   * @since 0.0.1
   */
  public void runSteps(int steps) {
    currentPoint = new Vector2D(0, 0);
    random = new Random();
    for (int i = 0; i < steps; i++) {
      int index = random.nextInt(description.getTransformations().size());
      //System.out.println("currentPoint; " + currentPoint.getX0() + ", " + currentPoint.getX1());
      currentPoint = description.getTransformations().get(index).transform(currentPoint);

      Vector2D pixel = canvas.getPixel(currentPoint);
      //System.out.println("currentPoint; " + pixel.getX0() + ", " + pixel.getX1());
      try {
        canvas.putPixel(pixel);
      } catch (IllegalArgumentException e) {
        //System.out.println("(runSteps) Exception: " + e.getMessage());
      }
    }
  }

}
