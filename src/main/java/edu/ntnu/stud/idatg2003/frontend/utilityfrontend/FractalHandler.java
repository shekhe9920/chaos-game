package edu.ntnu.stud.idatg2003.frontend.utilityfrontend;

/**
 * Interface representing a handler for fractal interactions such as zooming and panning.
 * This interface provides methods to handle zoom and pan actions, which can be implemented
 * by classes that need to respond to these interactions.
 *
 * @version 0.0.7
 * @since 0.0.3 (The version of Chaos-Game application when introduced)
 */
public interface FractalHandler {

  /**
   * Handles the zoom action on the fractal.
   *
   * @param scaleFactor the factor by which to scale the fractal
   */
  void onZoom(double scaleFactor);

  /**
   * Handles the pan action on the fractal.
   *
   * @param translateX the amount to translate the fractal along the X-axis
   * @param translateY the amount to translate the fractal along the Y-axis
   */
  void onPan(double translateX, double translateY);

}
