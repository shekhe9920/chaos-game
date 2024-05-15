package edu.ntnu.stud.idatg2003.backend.engine;

import edu.ntnu.stud.idatg2003.backend.ChaosGameObserver;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.utilitiesbackend.WeightedRandomSampler;
import edu.ntnu.stud.idatg2003.backend.transformations.Transform2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a chaos game for generating fractals through iterative application of transformations.
 * This class manages the game process, including selecting transformations randomly for each step,
 * updating the current position, and drawing the resultant fractal on a canvas.
 * Observers can be attached to monitor changes in the game's state,
 * such as updates to the game description.
 *
 * @version 0.0.5
 * @since 0.0.1 (The version of Chaos-Game application when introduced)
 */
public class ChaosGame {

  // List of observers monitoring the game's state:
  private final List<ChaosGameObserver> observers = new ArrayList<>();

  private ChaosGameDescription description; // Description of the chaos game
  private final ChaosCanvas canvas; // Canvas for drawing the fractal
  private Vector2D currentPoint; // Current point in the fractal space
  private int lastRunSteps = 0; // Number of steps in the last run of the game

  private List<Double> weights;    // Weights for selecting transformations
  private WeightedRandomSampler weightedRandom;  // Random sampler for selecting transformations



  /**
   * Initializes a new instance of {@code ChaosGame}
   * with specified dimensions and a game description.
   * A canvas is prepared based on the provided dimensions and fractal bounds specified in the game
   * description.
   * The starting point is initialized to (0, 0), but can be modified as needed.
   *
   * @param description The specifications of the chaos game, including transformations and bounds.
   * @param width       The width of the canvas in pixels.
   * @param height      The height of the canvas in pixels.
   * @since 0.0.1
   */
  public ChaosGame(ChaosGameDescription description, int width, int height) {
    this.canvas = new ChaosCanvas(width, height, description.getMinCoords(), description.getMaxCoords());
    this.description = description;
    this.weights = new ArrayList<>();
    this.weightedRandom = new WeightedRandomSampler(weights);
    this.currentPoint = new Vector2D(0, 0); // Starting point, which can be customized
  }



  /**
   * Sets the starting point for the chaos game.
   * This point is where the first iteration of transformations will begin.
   *
   * @param startPoint A {@code Vector2D} representing the initial point in fractal space.
   * @since 0.0.4
   */
  public void setStartingPoint(Vector2D startPoint) {
    if (startPoint == null) {
      throw new IllegalArgumentException("Starting point cannot be null");
    }
    this.currentPoint = startPoint;
  }




  /**
   * Sets the weights for the transformations.
   *
   * @param weights The weights for the transformations.
   * @since 0.0.5
   */
  public void setTransformWeights(List<Double> weights) {
    if (weights == null || weights.isEmpty()) {
      throw new IllegalArgumentException("Weights cannot be null or empty");
    }
    this.weights = weights;
    this.weightedRandom = new WeightedRandomSampler(weights);
  }




  /**
   * Retrieves the weights used for selecting transformations during the chaos game.
   *
   * @return The list of weights for the transformations.
   * @since 0.0.5
   */
  public List<Double> getWeights() {
    return weights;
  }




  /**
   * Executes the chaos game for a specified number of steps.
   * At each step, a transformation is randomly selected and applied to the current point,
   * and the result is drawn on the canvas.
   * This method is the core of the fractal generation process.
   *
   * @param steps The number of iterations to perform in the chaos game.
   * @since 0.0.1
   */
  public void runSteps(int steps) {
    if (weights == null || weights.isEmpty()) {
      throw new IllegalStateException("Weights are not set.");
    }

    lastRunSteps = steps; // Storing the number of steps for later retrieval

    for (int i = 0; i < steps; i++) {
      int index = weightedRandom.nextIndex();
      Transform2D transformation = description.getTransformations().get(index);

      currentPoint = transformation.transform(currentPoint);
      canvas.putPixel(currentPoint, 1); // '1' is the color value for a plotted point
    }
    notifyObserversGameUpdated(); // Notifying observers only once after all steps
  }




  /**
   * Retrieves the number of steps in the last run of the chaos game.
   *
   * @return The number of steps in the last run.
   * @since 0.0.4
   */
  public int getSteps() {
    return lastRunSteps;
  }




  /**
   * Prints the generated fractal to the console using ASCII characters.
   * Each pixel set to '1' on the canvas is represented as an 'x';
   * otherwise, a space character is printed.
   * This provides a textual visualization of the fractal pattern.
   *
   * @since 0.0.1
   */
  public void printFractal() {
    int[][] pixelArray = canvas.getCanvasArray();
    // Starting from the last row and ending at the first row to flip the fractal vertically:
    for (int i = pixelArray.length - 1; i >= 0; i--) {
      for (int j = 0; j < pixelArray[i].length; j++) {
        if (pixelArray[i][j] == 1) {
          System.out.print("x");
        } else {
          System.out.print(" ");
        }
      }
      System.out.println();
    }
  }




  /**
   * Retrieves the canvas associated with this chaos game.
   * The canvas contains the drawing of the fractal generated during the game iterations.
   *
   * @return The {@code ChaosCanvas} object used for fractal drawing.
   * @since 0.0.1
   */
  public ChaosCanvas getCanvas() {
    return canvas;
  }




  /**
   * Accessor for the current game description.
   * This description includes the transformations and bounds used for fractal generation.
   *
   * @return The current {@code ChaosGameDescription}.
   * @since 0.0.3
   */
  public ChaosGameDescription getDescription() {
    return description;
  }




  /**
   * Provides the current position in the fractal space.
   * This point is the latest in the sequence of transformations
   * applied during the chaos game iterations.
   *
   * @return The current {@code Vector2D} point in the fractal space.
   * @since 0.0.1
   */
  public Vector2D getCurrentPoint() {
    return currentPoint;
  }




  /**
   * Removes a specified observer from the list of observers monitoring this chaos game.
   * Observers are notified of changes in the game's state, such as description updates.
   *
   * @param observer The {@code ChaosGameObserver} to remove.
   * @since 0.0.3
   */
  public void removeObserver(ChaosGameObserver observer) {
    observers.remove(observer);
  }




  /**
   * Adds an observer to the list of observers
   * that are notified of changes in the chaos game's state,
   * such as updates to the description or the fractal generation progress.
   *
   * @param observer The {@code ChaosGameObserver} to add.
   * @since 0.0.3
   */
  public void addObserver(ChaosGameObserver observer) {
    observers.add(observer);
  }




  /**
   * Notifies all registered observers of a change in the chaos game description.
   * This method is called internally whenever the game description is updated.
   *
   * @param newDescription The new {@code ChaosGameDescription} that has been set.
   * @since 0.0.3
   */
  private void notifyDescriptionChanged(ChaosGameDescription newDescription) {
    for (ChaosGameObserver observer : observers) {
      observer.onChaosDescriptionChanged(newDescription);
    }
  }




  /**
   * Updates the game description if it's different from the current description and notifies all
   * registered observers about the update.
   * This method ensures that unnecessary notifications are
   * avoided by checking for actual changes in the description.
   *
   * @param newDescription The new {@code ChaosGameDescription} to set, if it's different.
   * @since 0.0.3
   */
  public void updateDescription(ChaosGameDescription newDescription) {
    this.description = newDescription;
    notifyDescriptionChanged(newDescription);
  }




  /**
   * Notifies all registered observers that the chaos game has been updated.
   * This method is called after each iteration or significant update to the game state.
   *
   * @since 0.0.4
   */
  private void notifyObserversGameUpdated() {
    for (ChaosGameObserver observer : observers) {
      observer.onChaosGameUpdated();
    }
  }




  /**
   * Updates the chaos game description and notifies observers of the change.
   * If the new description is different from the current one, observers are informed,
   * and any necessary updates are made to adapt the game to the new description.
   *
   * @param newDescription The new {@code ChaosGameDescription} to apply to the game.
   * @since 0.0.3
   */
  public void setDescription(ChaosGameDescription newDescription) {
    // Checking if the new description is different from the current description:
    if (!this.description.equals(newDescription)) {
      this.description = newDescription;
      // Notifying the observers only if there is an actual change
      notifyDescriptionChanged(newDescription);
    }
  }

}
