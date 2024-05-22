package edu.ntnu.stud.idatg2003.frontend.utilityfrontend;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * Handles mouse events for zooming and panning on a fractal displayed on a Canvas.
 *
 *
 * @version 0.0.4
 * @since 0.0.4 (The version of Chaos-Game application when introduced)
 */
public class MouseEventHandler {

  private double scale = 1.0;
  private double lastX;
  private double lastY;
  private double translateX = 0;
  private double translateY = 0;
  private final FractalHandler fractalHandler;

  /**
   * Constructs a MouseEventHandler with the specified FractalHandler.
   *
   * @param fractalHandler the FractalHandler to handle zoom and pan actions.
   */
  public MouseEventHandler(FractalHandler fractalHandler) {
    this.fractalHandler = fractalHandler;
  }

  /**
   * Attaches the mouse event handlers to the specified Canvas.
   *
   * @param canvas the Canvas to attach the handlers to
   */
  public void attachHandlers(Canvas canvas) {
    canvas.setOnScroll(this::handleScroll);
    canvas.setOnMousePressed(this::handleMousePressed);
    canvas.setOnMouseDragged(this::handleMouseDragged);
  }

  /**
   * Handles scroll events to zoom in and out of the fractal.
   *
   * @param event the ScrollEvent triggered by the user
   */
  private void handleScroll(ScrollEvent event) {
    double delta = event.getDeltaY();
    scale *= (delta > 0) ? 1.1 : 0.9; // Zoom in if scrolling up, zoom out if scrolling down
    fractalHandler.onZoom(scale);
  }

  /**
   * Handles mouse press events to record the starting position for panning.
   *
   * @param event the MouseEvent triggered by the user
   */
  private void handleMousePressed(MouseEvent event) {
    lastX = event.getX();
    lastY = event.getY();
  }

  /**
   * Handles mouse drag events to pan the fractal.
   *
   * @param event the MouseEvent triggered by the user
   */
  private void handleMouseDragged(MouseEvent event) {
    translateX += event.getX() - lastX;
    translateY += event.getY() - lastY;
    lastX = event.getX();
    lastY = event.getY();
    fractalHandler.onPan(translateX, translateY);
  }
}
