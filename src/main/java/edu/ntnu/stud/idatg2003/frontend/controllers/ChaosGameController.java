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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javafx.util.Pair;

/**
 * The {@code ChaosGameController} class manages the Chaos Game simulation.
 * It initializes the game, updates its state, and handles observers that monitor changes.
 *
 * @version 0.0.6
 * @since 0.0.3 (The version of Chaos-Game application when introduced)
 */
public class ChaosGameController {

  private ChaosGame chaosGame;  // The Chaos Game instance
  private final List<ChaosGameObserver> observers = new ArrayList<>(); // List of observers




  /**
   * Initializes the Chaos Game with the given description and canvas dimensions.
   *
   * @param description The description of the Chaos Game, including transformations and bounds.
   * @param canvasWidth The width of the canvas in pixels.
   * @param canvasHeight The height of the canvas in pixels.
   * @since 0.0.1
   */
  public void initializeGame(ChaosGameDescription description, int canvasWidth, int canvasHeight, int steps) {
    try {
      chaosGame = new ChaosGame(description, canvasWidth, canvasHeight);

      if (chaosGame.getWeights() == null || chaosGame.getWeights().isEmpty()) {
        // setting default weights for transformations only if they are not set:
        if (hasAffineTransformations(description)) {
          setTransformWeights(getDefaultWeights(description));
        }
      } else {  // if weights are set, then they are used
        if (hasAffineTransformations(description)) {
          setTransformWeights(chaosGame.getWeights());
        }
      }

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
  public void updateGame(
      int steps, Vector2D minCoords, Vector2D maxCoords, List<Transform2D> transformations) {

    if (chaosGame != null) {
      ChaosGameDescription description = chaosGame.getDescription();
      description.setMinCoords(minCoords);
      description.setMaxCoords(maxCoords);

      if (transformations != null) {
        description.setTransformations(new ArrayList<>(transformations));

      } else {   // if transformations are null, then an empty list is set
        description.setTransformations(new ArrayList<>());
      }


      chaosGame.setDescription(description);
      chaosGame.getCanvas().clearCanvas();
      chaosGame.runSteps(steps);
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
  public void updateJuliaSetGame(
      int steps, Vector2D minCoords, Vector2D maxCoords, Complex c, int order) {

    ChaosGameDescription description = ChaosGameDescriptionFactory.createJuliaSetDescription(c, order);
    description.setMinCoords(minCoords);
    description.setMaxCoords(maxCoords);

    chaosGame.setDescription(description);

    chaosGame.getCanvas().clearCanvas();
    chaosGame.runSteps(steps);
    notifyObserversAboutUpdate();
    notifyObserversAboutDescriptionChange(description);
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
  public void updateAffineTransformationGame(
      int steps, Vector2D minCoords, Vector2D maxCoords, List<Transform2D> transformations) {

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

      // Only setting weights if there are affine transformations:
      if (hasAffineTransformations(description)) {
        List<Double> currentWeights = chaosGame.getWeights();

        // Setting default weights if they are not set:
        if (currentWeights == null || currentWeights.size() != description.getTransformations().size()) {
          currentWeights = new ArrayList<>(Collections.nCopies(description.getTransformations().size(), 1.0));
          chaosGame.setTransformWeights(currentWeights);
        }
      }

      // Running the game with the new description:
      chaosGame.getCanvas().clearCanvas();
      chaosGame.runSteps(steps);
      notifyObserversAboutUpdate();
      notifyObserversAboutDescriptionChange(description);
    }
  }





  /**
   * Saves the current fractal configuration to a file.
   *
   * @param path The file path to save the configuration.
   * @param typeOfTransformation The type of transformation used.
   * @since 0.0.1
   */
public void saveFractal(String path, String typeOfTransformation) {
  try {                  // writing the Chaos Game to a file
    ChaosGameFileHandler.writeToFile(chaosGame, path, typeOfTransformation);

  } catch (IOException e) {
    System.err.println("Failed to save fractal: " + e.getMessage());
  }
}





  /**
   * Loads a fractal configuration from a file and updates the game.
   *
   * @param path The file path to load the configuration from.
   * @since 0.0.1
   */
  public void loadFractal(String path) {
    try {
      // pair of Chaos Game description and weights:
      Pair<ChaosGameDescription, List<Double>> result = ChaosGameFileHandler.readFile(path);
      ChaosGameDescription description = result.getKey(); // description of the Chaos Game
      List<Double> weights = result.getValue(); // weights for the transformations

      // only set weights if there are affine transformations
      if (hasAffineTransformations(description)) {
        // setting default weights if they are not set:
        if (weights == null || weights.isEmpty()) {
          throw new IllegalArgumentException("Weights cannot be null or empty");
        }

        // updating the Chaos Game with the new description and weights
        chaosGame.updateDescription(description);
        chaosGame.setTransformWeights(weights);
      } else {  // if there are no affine transformations, then only the description is updated
        chaosGame.updateDescription(description);
      }

    } catch (IOException e) {
      System.err.println("Failed to load fractal: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      System.err.println("Failed to set weights: " + e.getMessage());
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
    return List.of(); // returns an empty list if no description or transformations are found
  }




  /**
   * Sets the weights for the transformations in the Chaos Game.
   *
   * @param weights the list of weights for the transformations
   * @since 0.0.5
   */
  public void setTransformWeights(List<Double> weights) {
    if (chaosGame != null) {
      if (hasAffineTransformations(chaosGame.getDescription())) {
        chaosGame.setTransformWeights(weights);
      }
    } else {
      throw new IllegalStateException("Chaos game has not been initialized yet.");
    }
  }




  /**
   * Retrieves the default weights for the transformations.
   *
   * @param description The description of the Chaos Game.
   * @return The list of default weights.
   * @since 0.0.5
   */
  public List<Double> getDefaultWeights(ChaosGameDescription description) {
    int size = description.getTransformations().size();  // size = the number of transformations
    List<Double> weights = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      weights.add(1.0 / size); // setting equal weights for all transformations
    }
    return weights;
  }




  /**
   * Updates the Chaos Game with new weights and runs the specified number of steps.
   *
   * @param newDescription The new description of the Chaos Game.
   * @param steps The number of steps to run.
   * @param weights The list of weights for the transformations.
   * @since 0.0.5
   */
  public void updateGameWithWeights(ChaosGameDescription newDescription, int steps, List<Double> weights) {
    if (chaosGame != null) {
      chaosGame.setDescription(newDescription);

      // only setting weights if there are affine transformations
      if (hasAffineTransformations(newDescription)) {
        chaosGame.setTransformWeights(weights); // setting new weights
      }

      chaosGame.getCanvas().clearCanvas();
      chaosGame.runSteps(steps);
      notifyObserversAboutUpdate();
      notifyObserversAboutDescriptionChange(newDescription);
    }
  }




  /**
   * Calculates the fractal point for a given fractal type.
   *
   * @param fractalType The type of fractal to calculate.
   * @param zx The x-coordinate of the point.
   * @param zy The y-coordinate of the point.
   * @param order The order of the fractal.
   * @param cRe The real part of the complex constant.
   * @param cIm The imaginary part of the complex constant.
   * @return The fractal point.
   * @since 0.0.6
   */
  public int calculateFractalPoint(
      String fractalType, double zx, double zy, int order, double cRe, double cIm) {

    JuliaTransform transform = // creating a new JuliaTransform object
        new JuliaTransform(new Complex(cRe, cIm), 1, order, order > 2);

    return switch (fractalType) {

      case "Julia Set Fractal" -> transform.calculateJuliaSetPoint(zx, zy, order, cRe, cIm);

      case "Mandelbrot Set Fractal" -> transform.calculateMandelbrotSetPoint(zx, zy, order);

      default -> throw new IllegalArgumentException("Unknown fractal type: " + fractalType);
    };
  }



  /**
   * Checks if the Chaos Game description has affine transformations.
   * This is used to determine if weights should be set for the transformations.
   * Helper method.
   *
   * @param description The description of the Chaos Game.
   * @return {@code true} if the description has affine transformations, {@code false} otherwise.
   * @since 0.0.6
   */
  private boolean hasAffineTransformations(ChaosGameDescription description) {
    return description.getTransformations().stream().anyMatch(AffineTransform2D.class::isInstance);
  }


}
