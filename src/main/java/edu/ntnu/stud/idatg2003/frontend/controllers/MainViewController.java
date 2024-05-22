package edu.ntnu.stud.idatg2003.frontend.controllers;

import edu.ntnu.stud.idatg2003.frontend.view.AffineTransformView;
import edu.ntnu.stud.idatg2003.frontend.view.JuliaSetView;
import edu.ntnu.stud.idatg2003.frontend.view.MainView;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The {@code MainViewController} class manages the main views of the application.
 * It uses a singleton pattern to ensure only one instance is created.
 * This controller handles switching between the home view,
 * affine transformation view, and Julia set view.
 *
 * @version 0.0.4
 * @since 0.0.3 (The version of Chaos-Game application when introduced)
 */
public class MainViewController {


  private static MainViewController instance; // Singleton instance

  private final MainView mainView;                  // The main view
  private AffineTransformView affineTransformView;  // The affine transformation view
  private JuliaSetView juliaSetView;                // The Julia set view

  private Stage primaryStage;  // The primary stage for the application
  private Node currentView;   // This is to keep track of the current view



  /**
   * Private constructor for a singleton pattern.
   * Initializes the home view.
   *
   * @since 0.0.3
   */
  private MainViewController() {
    mainView = new MainView(this);
  }




  /**
   * Returns the singleton instance of the {@code MainViewController}.
   * If the instance is not created, it creates a new instance.
   *
   * @return The singleton instance.
   * @since 0.0.3
   */
  public static MainViewController getInstance() {
    if (instance == null) {
      instance = new MainViewController();
    }
    return instance;
  }




  /**
   * Initializes the primary stage with the home view.
   *
   * @param stage The primary stage for the application.
   * @since 0.0.1
   */
  public void init(Stage stage) {
    this.primaryStage = stage;
    primaryStage.setScene(new Scene(mainView, 1225, 750)); // Initializing with MainView
    primaryStage.setTitle("Home");
    primaryStage.show();
    currentView = mainView; // Starts with 'MainView' as the initial view
  }




  /**
   * Switches to the affine transformation view.
   * If the view is not initialized, it creates a new instance.
   *
   * @since 0.0.1
   */
  public void switchToAffineTransformationView() {
    if (affineTransformView == null) {
      affineTransformView = new AffineTransformView(new ChaosGameController(), 650, 400);
    }
    primaryStage.getScene().setRoot(affineTransformView);
    primaryStage.sizeToScene();
    primaryStage.setTitle("Affine Transformation");
    currentView = affineTransformView;
  }




  /**
   * Switches to the Julia set view.
   * If the view is not initialized, it creates a new instance.
   *
   * @since 0.0.1
   */
  public void switchToJuliaSetView() {
    if (juliaSetView == null) {
      juliaSetView = new JuliaSetView(new ChaosGameController(), 650, 500);
    }
    primaryStage.getScene().setRoot(juliaSetView);
    primaryStage.sizeToScene();
    primaryStage.setTitle("Julia Set");
    currentView = juliaSetView;
  }




  /**
   * Switches back to the home view.
   *
   * @since 0.0.3
   */
  public void switchToHomeView() {
    primaryStage.getScene().setRoot(mainView);
    primaryStage.sizeToScene();
    primaryStage.setTitle("Home");  // Setting the MainView title to 'Home'
    currentView = mainView;
  }




  /**
   * Opens another fractal window based on the current view.
   * Switches to Julia set view if currently in affine transformation view and vice versa.
   *
   * @since 0.0.4
   */
  public void openOtherFractalWindow() {
    if (currentView instanceof AffineTransformView) {
      switchToJuliaSetView();
    } else if (currentView instanceof JuliaSetView) {
      switchToAffineTransformationView();
    } else {
      // Default to one if none is currently active
      switchToAffineTransformationView();
    }
  }




  /**
   * Gets the current view type as a string.
   *
   * @return The current view type.
   * @since 0.0.4
   */
  public String getCurrentViewType() {
    if (currentView instanceof AffineTransformView) {
      return "Affine Transformations";  // If current is Affine, option to switch to Julia
    } else if (currentView instanceof JuliaSetView) {
      return "Julia Set";              // If current is Julia, option to switch to Affine
    }
    return "Change Fractal Type";  // default
  }




  /**
   * Handles the exit action to close the application.
   *
   * @since 0.0.1
   */
  public void handleExit() {
    System.exit(0);
  }
}
