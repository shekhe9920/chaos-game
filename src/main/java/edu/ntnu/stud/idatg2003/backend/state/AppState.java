package edu.ntnu.stud.idatg2003.backend.state;

import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescription;
import java.util.List;

/**
 * The {@code AppState} class represents the state of the application.
 * It contains information about the Chaos Game description, canvas dimensions,
 * weights for the transformations, and the number of steps in the last run.
 *
 * @version 0.0.2
 * @since 0.0.8 (The version of Chaos-Game application when introduced)
 */
public class AppState {

  private ChaosGameDescription description;   // The description of the Chaos Game
  private int canvasWidth;                    // The width of the canvas
  private int canvasHeight;                   // The height of the canvas
  private List<Double> weights;               // The list of weights for the transformations
  private int lastRunSteps;                   // The number of steps in the last run


  /**
   * Gets the description of the Chaos Game.
   *
   * @return The description.
   * @since 0.0.0
   */
  public ChaosGameDescription getDescription() {
    return description;
  }



  /**
   * Sets the description of the Chaos Game.
   *
   * @param description The description to set.
   * @since 0.0.0
   */
  public void setDescription(ChaosGameDescription description) {
    this.description = description;
  }



  /**
   * Gets the width of the canvas.
   *
   * @return The canvas width.
   * @since 0.0.0
   */
  public int getCanvasWidth() {
    return canvasWidth;
  }



  /**
   * Sets the width of the canvas.
   *
   * @param canvasWidth The canvas width to set.
   * @since 0.0.0
   */
  public void setCanvasWidth(int canvasWidth) {
    this.canvasWidth = canvasWidth;
  }



  /**
   * Gets the height of the canvas.
   *
   * @return The canvas height.
   * @since 0.0.0
   */
  public int getCanvasHeight() {
    return canvasHeight;
  }



  /**
   * Sets the height of the canvas.
   *
   * @param canvasHeight The canvas height to set.
   * @since 0.0.0
   */
  public void setCanvasHeight(int canvasHeight) {
    this.canvasHeight = canvasHeight;
  }



  /**
   * Gets the list of weights for the transformations.
   *
   * @return The list of weights.
   * @since 0.0.0
   */
  public List<Double> getWeights() {
    return weights;
  }



  /**
   * Sets the list of weights for the transformations.
   *
   * @param weights The weights to set.
   * @since 0.0.0
   */
  public void setWeights(List<Double> weights) {
    this.weights = weights;
  }



  /**
   * Gets the number of steps in the last run of the Chaos Game.
   *
   * @return The number of steps.
   * @since 0.0.0
   */
  public int getLastRunSteps() {
    return lastRunSteps;
  }



  /**
   * Sets the number of steps in the last run of the Chaos Game.
   *
   * @param lastRunSteps The number of steps to set.
   * @since 0.0.0
   */
  public void setLastRunSteps(int lastRunSteps) {
    this.lastRunSteps = lastRunSteps;
  }
}
