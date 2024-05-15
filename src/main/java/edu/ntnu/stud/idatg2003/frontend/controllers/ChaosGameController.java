package edu.ntnu.stud.idatg2003.frontend.controllers;

import edu.ntnu.stud.idatg2003.backend.ChaosGameObserver;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGame;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescription;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescriptionFactory;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Complex;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.transformations.AffineTransform2D;
import edu.ntnu.stud.idatg2003.backend.transformations.JuliaTransform;
import edu.ntnu.stud.idatg2003.backend.transformations.Transform2D;
import edu.ntnu.stud.idatg2003.filehandling.ChaosGameFileHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code ChaosGameController} class manages the Chaos Game simulation.
 * It initializes the game, updates its state, and handles observers that monitor changes.
 *
 * @version 0.0.4
 * @since 0.0.3 (The version of Chaos-Game application when introduced)
 */
public class ChaosGameController {


  private ChaosGame chaosGame;  // The Chaos Game instance
  private List<ChaosGameObserver> observers = new ArrayList<>(); // List of observers




  /**
   * Initializes the Chaos Game with the given description and canvas dimensions.
   *
   * @param description The description of the Chaos Game, including transformations and bounds.
   * @param canvasWidth The width of the canvas in pixels.
   * @param canvasHeight The height of the canvas in pixels.
   * @since 0.0.1
   */
  public void initializeGame(ChaosGameDescription description, int canvasWidth,
      int canvasHeight, int steps) {

    try {
      chaosGame = new ChaosGame(description, canvasWidth, canvasHeight);
      chaosGame.runSteps(steps); // initial steps

    } catch (Exception e) {
      e.printStackTrace();
    }

  }





  /**
   * Adds an observer to the list of observers monitoring the Chaos Game.
   *
   * @param observer The observer to add.
   * @since 0.0.1
   */
  public void addObserver(ChaosGameObserver observer) {
    observers.add(observer);
  }




  /**
   * Notifies all observers about an update in the Chaos Game.
   *
   * @since 0.0.1
   */
  public void notifyObserversAboutUpdate() {
    for (ChaosGameObserver observer : observers) {
      observer.onChaosGameUpdated();
    }
  }




  /**
   * Notifies all observers about a change in the Chaos Game description.
   *
   * @param newDescription The new description to notify observers about.
   * @since 0.0.1
   */
  public void notifyObserversAboutDescriptionChange(ChaosGameDescription newDescription) {
    for (ChaosGameObserver observer : observers) {
      observer.onChaosDescriptionChanged(newDescription);
    }
  }





  /**
   * Retrieves the current Chaos Game instance.
   *
   * @return The current Chaos Game instance.
   * @since 0.0.1
   */
  public ChaosGame getGame() {
    return chaosGame;
  }






  /**
   * Updates the Chaos Game with new parameters and runs the specified number of steps.
   *
   * @param steps The number of steps to run.
   * @param minCoords The minimum coordinates of the canvas.
   * @param maxCoords The maximum coordinates of the canvas.
   * @param transformations The list of transformations to apply.
   * @since 0.0.1
   */
  public void updateGame(int steps, Vector2D minCoords, Vector2D maxCoords, List<Transform2D> transformations) {
    if (chaosGame != null) {
      ChaosGameDescription description = chaosGame.getDescription();
      description.setMinCoords(minCoords);
      description.setMaxCoords(maxCoords);
      if (transformations != null) {
        description.setTransformations(new ArrayList<>(transformations));
      } else {
        description.setTransformations(new ArrayList<>());
      }
      chaosGame.setDescription(description);
      chaosGame.getCanvas().clearCanvas();
      chaosGame.runSteps(steps);
      System.out.println("Updated game with steps: (ChaosGameController: updateGame)" + steps);
    }
  }




  /**
   * Updates the Chaos Game for a Julia set with new parameters and runs the specified number of steps.
   *
   * @param steps The number of steps to run.
   * @param minCoords The minimum coordinates of the canvas.
   * @param maxCoords The maximum coordinates of the canvas.
   * @param c The complex constant for the Julia set.
   * @since 0.0.4
   */
  public void updateJuliaSetGame(int steps, Vector2D minCoords, Vector2D maxCoords, Complex c) {
    ChaosGameDescription description = ChaosGameDescriptionFactory.createJuliaSetDescription(c);
    description.setMinCoords(minCoords);
    description.setMaxCoords(maxCoords);
    updateChaosGame(description, steps);
  }




  /**
   * Updates the Chaos Game for an affine transformation with new parameters and runs the specified number of steps.
   *
   * @param steps The number of steps to run.
   * @param minCoords The minimum coordinates of the canvas.
   * @param maxCoords The maximum coordinates of the canvas.
   * @param transformations The list of transformations to apply.
   * @since 0.0.4
   */
  public void updateAffineTransformationGame(int steps, Vector2D minCoords, Vector2D maxCoords, List<Transform2D> transformations) {
    ChaosGameDescription description = new ChaosGameDescription(minCoords, maxCoords, transformations);
    updateChaosGame(description, steps);
  }




  /**
   * Updates the Chaos Game with a new description and runs the specified number of steps.
   *
   * @param description The new game description.
   * @param steps The number of steps to run.
   * @since 0.0.2
   */
  private void updateChaosGame(ChaosGameDescription description, int steps) {
    if (chaosGame != null) {
      chaosGame.setDescription(description);
      chaosGame.getCanvas().clearCanvas();
      chaosGame.runSteps(steps);
      System.out.println("updateChaosGame called with steps: (ChaosGameController: updateChaosGame)" + steps);
      notifyObserversAboutUpdate();
      notifyObserversAboutDescriptionChange(description);
    }
  }




  /**
   * Saves the current fractal configuration to a file.
   *
   * @param description The description of the Chaos Game.
   * @param filePath The file path to save the configuration.
   * @param typeOfTransformation The type of transformation used.
   * @since 0.0.1
   */
  public void saveFractal(ChaosGameDescription description, String filePath, String typeOfTransformation) {
    try {
      ChaosGameFileHandler.writeToFile(description, filePath, typeOfTransformation);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }




  /**
   * Loads a fractal configuration from a file and updates the game.
   *
   * @param filePath The file path to load the configuration from.
   * @return The loaded game description.
   * @since 0.0.1
   */
  public ChaosGameDescription loadFractal(String filePath) {
    try {
      ChaosGameDescription description = ChaosGameFileHandler.readFile(filePath);
      int defaultSteps = 10000; // Standard value for total steps

      if (description.getTransformations().stream().anyMatch(JuliaTransform.class::isInstance)) {
        JuliaTransform juliaTransform = (JuliaTransform) description.getTransformations().get(0);
        Complex c = juliaTransform.getPoint();
        updateJuliaSetGame(defaultSteps, description.getMinCoords(), description.getMaxCoords(), c);
      } else {
        updateGame(defaultSteps, description.getMinCoords(), description.getMaxCoords(), description.getTransformations());
      }

      notifyObserversAboutDescriptionChange(description);
      return description;
    } catch (Exception e) {
      e.printStackTrace();
      return null; // Return null if error occurs
    }
  }




  /**
   * Retrieves the current game description.
   *
   * @return The current game description.
   * @throws IllegalStateException If the game has not been initialized.
   * @since 0.0.4
   */
  public ChaosGameDescription getCurrentGameDescription() {
    if (chaosGame != null) {
      return chaosGame.getDescription();
    } else {
      throw new IllegalStateException("Chaos game has not been initialized yet.");
    }
  }




  /**
   * Retrieves the list of current affine transformations.
   *
   * @return A list of current affine transformations.
   * @since 0.0.4
   */
  public List<AffineTransform2D> getCurrentTransformations() {
    ChaosGameDescription description = chaosGame.getDescription();
    if (description != null) {
      return description.getTransformations().stream()
          .filter(AffineTransform2D.class::isInstance)
          .map(t -> (AffineTransform2D) t)
          .collect(Collectors.toList());
    }
    return List.of(); // Returns an empty list if no description or transformations are found
  }
}
