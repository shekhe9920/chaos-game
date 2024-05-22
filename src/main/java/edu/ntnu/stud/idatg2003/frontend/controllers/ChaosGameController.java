package edu.ntnu.stud.idatg2003.frontend.controllers;

import static edu.ntnu.stud.idatg2003.commonutilities.errorutilitie.ErrorHandler.GAME_NOT_INITIALIZED;

import edu.ntnu.stud.idatg2003.backend.ChaosGameObserver;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGame;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescription;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescriptionFactory;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Complex;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.state.AppState;
import edu.ntnu.stud.idatg2003.backend.transformations.AffineTransform2D;
import edu.ntnu.stud.idatg2003.backend.transformations.JuliaTransform;
import edu.ntnu.stud.idatg2003.backend.transformations.Transform2D;
import edu.ntnu.stud.idatg2003.backend.utilitiesbackend.ConfigManager;
import edu.ntnu.stud.idatg2003.commonutilities.errorutilitie.ErrorHandler;
import edu.ntnu.stud.idatg2003.commonutilities.filehandling.ChaosGameFileHandler;
import edu.ntnu.stud.idatg2003.frontend.utilityfrontend.GuiErrorDisplay;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import javafx.util.Pair;

/**
 * The {@code ChaosGameController} class manages the Chaos Game simulation.
 * It initializes the game, updates its state, and handles observers that monitor changes.
 *
 * @version 0.0.7
 * @since 0.0.3 (The version of Chaos-Game application when introduced)
 */
public class ChaosGameController {

  // Logger for the ChaosGameController class
  private static final Logger LOGGER = Logger.getLogger(ChaosGameController.class.getName());


  private ChaosGame chaosGame;  // The Chaos Game instance
  private final List<ChaosGameObserver> observers = new ArrayList<>(); // List of observers

  private final boolean isInitialLoad = true;  // flag to check if the game is being initialized



  /**
   * Initializes the Chaos Game with the given description and canvas dimensions.
   *
   * @param description The description of the Chaos Game, including transformations and bounds.
   * @param canvasWidth The width of the canvas in pixels.
   * @param canvasHeight The height of the canvas in pixels.
   * @since 0.0.1
   */
  public void initializeGame(
      ChaosGameDescription description, int canvasWidth, int canvasHeight, int steps) {

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
//      chaosGame.runIterations(steps);

    } catch (Exception e) {
      ErrorHandler.handleException(e, LOGGER.getName());
      GuiErrorDisplay.showError("An error occurred while initializing the Chaos Game.");
    }

  }






  /**
   * Loads the Chaos Game state from the configuration file.
   * If the state is successfully loaded, the game is updated and the observers are notified.
   * If the state cannot be loaded, the game is initialized with default values.
   *
   * @since 0.0.1
   */
  private boolean loadState() {
    AppState state = ConfigManager.loadConfig();
    if (state != null && state.getDescription() != null) {
      ChaosGameDescription description = state.getDescription();
      int canvasWidth = state.getCanvasWidth();
      int canvasHeight = state.getCanvasHeight();
      int steps = state.getLastRunSteps();

      try {
        chaosGame = new ChaosGame(description, canvasWidth, canvasHeight);

        List<Double> weights = state.getWeights();
        if (weights != null && weights.size() == description.getTransformations().size()) {
          chaosGame.setTransformWeights(weights);
        } else {
          chaosGame.setTransformWeights(getDefaultWeights(description));
        }

        chaosGame.runSteps(steps);
//        chaosGame.runIterations(steps);
        notifyObserversAboutUpdate();
        return true;
      } catch (Exception e) {
        ErrorHandler.handleException(e, LOGGER.getName());
        GuiErrorDisplay.showError("An error occurred while loading the Chaos Game state.");
      }
    }
    return false;
  }



  /**
   * Saves the current Chaos Game state to the configuration file, allowing it to be loaded later.
   *
   *
   * @since 0.0.7
   */
  private void saveState() {
    AppState newState = new AppState();
    newState.setDescription(chaosGame.getDescription());
    newState.setCanvasWidth(getCanvasWidth());
    newState.setCanvasHeight(getCanvasHeight());
    newState.setWeights(chaosGame.getWeights());
    newState.setLastRunSteps(chaosGame.getSteps());
    ConfigManager.saveConfig(newState);
  }


  /**
   * Retrieves the width of the canvas.
   *
   * @return The width of the canvas.
   * @since 0.0.7
   */
  private int getCanvasWidth() {
    return chaosGame.getCanvas().getWidth();
  }

  /**
   * Retrieves the height of the canvas.
   *
   * @return The height of the canvas.
   * @since 0.0.7
   */
  private int getCanvasHeight() {
    return chaosGame.getCanvas().getHeight();
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
    } else {
      throw new IllegalStateException(GAME_NOT_INITIALIZED);
    }
  }




  /**
   * Updates the Chaos Game for a Julia set with new parameters
   * and runs the specified number of steps.
   *
   * @param steps The number of steps to run.
   * @param minCoords The minimum coordinates of the canvas.
   * @param maxCoords The maximum coordinates of the canvas.
   * @param c The complex constant for the Julia set.
   * @since 0.0.4
   */
  public void updateJuliaSetGame(
      int steps, Vector2D minCoords, Vector2D maxCoords, Complex c, int order) {

    try {

      ChaosGameDescription description =
          ChaosGameDescriptionFactory.createJuliaSetDescription(c, order);
      description.setMinCoords(minCoords);
      description.setMaxCoords(maxCoords);

      chaosGame.setDescription(description);

      chaosGame.getCanvas().clearCanvas();
      chaosGame.runSteps(steps);
      notifyObserversAboutUpdate();
      notifyObserversAboutDescriptionChange(description);

    } catch (Exception e) {
      ErrorHandler.handleException(e, LOGGER.getName());
      GuiErrorDisplay.showError("An error occurred while updating the Julia Set.");
    }
  }







  /**
   * Updates the Chaos Game for an affine transformation with new parameters
   * and runs the specified number of steps.
   *
   * @param steps The number of steps to run.
   * @param minCoords The minimum coordinates of the canvas.
   * @param maxCoords The maximum coordinates of the canvas.
   * @param transformations The list of transformations to apply.
   * @since 0.0.4
   */
  public void updateAffineTransformationGame(
      int steps, Vector2D minCoords, Vector2D maxCoords, List<Transform2D> transformations) {

    try {

      ChaosGameDescription description =
          new ChaosGameDescription(minCoords, maxCoords, transformations);
      updateChaosGame(description, steps);

    } catch (Exception e) {
      ErrorHandler.handleException(e, LOGGER.getName());
      GuiErrorDisplay.showError("An error occurred while updating the Affine Transformation.");
    }

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
        if (currentWeights == null
            || currentWeights.size() != description.getTransformations().size()) {

          currentWeights =   // setting equal weights for all transformations
              new ArrayList<>(Collections.nCopies(description.getTransformations().size(), 1.0));
          chaosGame.setTransformWeights(currentWeights);

        }
      }

      // Running the game with the new description:
      chaosGame.getCanvas().clearCanvas();
      chaosGame.runSteps(steps);
      notifyObserversAboutUpdate();
      notifyObserversAboutDescriptionChange(description);
    } else {
      throw new IllegalStateException(GAME_NOT_INITIALIZED);
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
      GuiErrorDisplay.showError("I/O Error: " + e.getMessage());
      ErrorHandler.handleException(e, LOGGER.getName());
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
      GuiErrorDisplay.showError("I/O Error: " + e.getMessage());
      ErrorHandler.handleException(e, LOGGER.getName());
    } catch (IllegalArgumentException e) {
      GuiErrorDisplay.showError("Invalid input: " + e.getMessage());
      ErrorHandler.handleException(e, LOGGER.getName());
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
      throw new IllegalStateException(GAME_NOT_INITIALIZED);

    }
  }




  /**
   * Retrieves the list of current affine transformations.
   *
   * @return A list of current affine transformations.
   * @since 0.0.4
   */
  public List<AffineTransform2D> getCurrentTransformations() {
    try {

      ChaosGameDescription description = chaosGame.getDescription();

      if (description != null) {

        // filtering affine transformations from the list of transformations:
        return description.getTransformations().stream()
            .filter(AffineTransform2D.class::isInstance)  // checking the type of transformation
            .map(t -> (AffineTransform2D) t)     // casting to AffineTransform2D
            .toList(); // returns a list of affine transformations
      }

      return List.of(); // returns an empty list if no description or transformations are found

    } catch (Exception e) {

      GuiErrorDisplay.showError(
          "An error occurred while getting transformations: " + e.getMessage());
      ErrorHandler.handleException(e, LOGGER.getName());
      return List.of(); // returns an empty list if an exception occurs

    }

  }




  /**
   * Sets the weights for the transformations in the Chaos Game.
   *
   * @param weights the list of weights for the transformations
   * @since 0.0.5
   */
  public void setTransformWeights(List<Double> weights) {
    if (chaosGame != null) {

      // weights are set only if there are affine transformations:
      if (hasAffineTransformations(chaosGame.getDescription())) {
        chaosGame.setTransformWeights(weights);
      }

    } else {
      throw new IllegalStateException(GAME_NOT_INITIALIZED);
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
  public void updateGameWithWeights(ChaosGameDescription newDescription,
      int steps, List<Double> weights) {

    if (chaosGame != null) {
      if (newDescription.getTransformations().size() != weights.size()) {
        throw new IllegalStateException("Number of transformations and weights do not match.");
      }

      chaosGame.setDescription(newDescription);
      chaosGame.setTransformWeights(weights);

      chaosGame.getCanvas().clearCanvas(); // clearing the previous canvas to redraw
      chaosGame.runSteps(steps);

      // notifying observers about the update and description change:
      notifyObserversAboutUpdate();
      notifyObserversAboutDescriptionChange(newDescription);
    } else {
      throw new IllegalStateException(GAME_NOT_INITIALIZED);
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
   * @param maxIterations The maximum number of iterations.
   * @return The fractal point.
   * @since 0.0.6
   */
  public int calculateFractalPoint(String fractalType, double zx, double zy,
      int order, double cRe, double cIm, int maxIterations) {


    JuliaTransform transform = // creating a new JuliaTransform object
        new JuliaTransform(new Complex(cRe, cIm), 1, order, order > 2);

    return switch (fractalType) {

      case "Julia Set Fractal" ->
          transform.calculateJuliaSetPoint(zx, zy, order, cRe, cIm, maxIterations);

      case "Mandelbrot Set Fractal" ->
          transform.calculateMandelbrotSetPoint(zx, zy, order, maxIterations);

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


  public void clearCanvas() {
      chaosGame.getCanvas().clearCanvas();
  }


}
