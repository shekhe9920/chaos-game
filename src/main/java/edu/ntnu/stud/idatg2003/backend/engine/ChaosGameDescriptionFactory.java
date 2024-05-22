package edu.ntnu.stud.idatg2003.backend.engine;

import edu.ntnu.stud.idatg2003.backend.mathoperations.Complex;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Matrix2x2;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.transformations.AffineTransform2D;
import edu.ntnu.stud.idatg2003.backend.transformations.JuliaTransform;
import edu.ntnu.stud.idatg2003.backend.transformations.Transform2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A factory class for creating predefined {@code ChaosGameDescription} instances.
 * This class provides static methods to generate descriptions for well-known fractals,
 * such as the Sierpinski Triangle and the Barnsley Fern, as well as a method to generate
 * a description for Julia sets given a specific complex number.
 *
 * @version 0.0.2
 * @since 0.0.3 (The version of Chaos-Game application when introduced)
 */
public class ChaosGameDescriptionFactory {


  /**
   * Private constructor to prevent instantiation of this class.
   */
  ChaosGameDescriptionFactory() {
    // Private constructor to prevent instantiation
  }


  /**
   * Creates a {@code ChaosGameDescription} for generating the Sierpinski Triangle fractal.
   * This method sets up the necessary affine transformations and bounding coordinates
   * to generate the fractal.
   *
   * @return A {@code ChaosGameDescription} instance configured for the Sierpinski Triangle.
   * @since 0.0.0
   */
  public static ChaosGameDescription createSierpinskiTriangle() {
    Vector2D minCoords = new Vector2D(0, 0); // Lower left corner
    Vector2D maxCoords = new Vector2D(1, 1); // Upper right corner

    // Define the three affine transformations for the Sierpinski Triangle:
    Transform2D transform1 =
        new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5),
            new Vector2D(0, 0)
        );

    Transform2D transform2 =
        new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5),
            new Vector2D(0.25, 0.5)
        );

    Transform2D transform3 =
        new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5),
            new Vector2D(0.5, 0)
        );

    // Return a new ChaosGameDescription with the transformations and bounds
    return new ChaosGameDescription(
        minCoords, maxCoords, Arrays.asList(transform1, transform2, transform3)
    );

  }




  /**
   * Creates a {@code ChaosGameDescription} for generating the Barnsley Fern fractal.
   * This method defines affine transformations tailored to produce the intricate patterns
   * of the Barnsley Fern, along with appropriate bounding coordinates.
   *
   * @return A {@code ChaosGameDescription} instance configured for the Barnsley Fern.
   * @since 0.0.0
   */
  public static ChaosGameDescription createBarnsleyFern() {
    Vector2D minCoords = new Vector2D(-2.65, 0);   // Lower left corner
    Vector2D maxCoords = new Vector2D(2.65, 10 );  // Upper right corner

    // Define the four affine transformations for the Barnsley Fern:
    Transform2D transform1 =
        new AffineTransform2D(
            new Matrix2x2(0, 0, 0, .16),
            new Vector2D(0, 0)
        );

    Transform2D transform2 =
        new AffineTransform2D(
            new Matrix2x2(.85, .04, -.04, .85),
            new Vector2D(0, 1.6)
        );

    Transform2D transform3 =
        new AffineTransform2D(
            new Matrix2x2(.2, -.26, .23, .22),
            new Vector2D(0, 1.6)
        );

    Transform2D transform4 =
        new AffineTransform2D(
            new Matrix2x2(-.15, .28, .26, .24),
            new Vector2D(0, .44)
        );

    // Return a new ChaosGameDescription with the transformations and bounds
    return new ChaosGameDescription(
            minCoords, maxCoords, Arrays.asList(transform1, transform2, transform3, transform4)
    );

  }




  /**
   * Creates a {@code ChaosGameDescription} for generating the Dragon Curve fractal.
   *
   * @return A {@code ChaosGameDescription} instance configured for the Dragon Curve.
   * @since 0.0.2
   */
  public static ChaosGameDescription createDragonCurve() {
    List<Transform2D> transformations = new ArrayList<>();

    transformations.add(
        new AffineTransform2D(
            new Matrix2x2(0.824074, 0.281428, -0.212346, 0.864198),
            new Vector2D(-1.882290, -0.110607)));

    transformations.add(
        new AffineTransform2D(
            new Matrix2x2(0.088272, 0.520988, -0.463889, -0.377778),
        new Vector2D(-0.996432, -0.107766)));

    return new ChaosGameDescription(new Vector2D(-6.8, -2.5), new Vector2D(2.6, 8), transformations);
  }



  /**
   * Creates a {@code ChaosGameDescription} for generating the Maple Leaf fractal.
   *
   * @return A {@code ChaosGameDescription} instance configured for the Maple Leaf.
   * @since 0.0.2
   */
  public static ChaosGameDescription createMapleLeaf() {
    List<Transform2D> transformations = new ArrayList<>();

    transformations.add(
        new AffineTransform2D(
            new Matrix2x2(0.14, 0.01, 0, 0.51), new Vector2D(-0.08, -1.31))
    );

    transformations.add(
        new AffineTransform2D(
            new Matrix2x2(0.43, 0.52, -0.45, 0.5), new Vector2D(1.49, -0.75))
    );

    transformations.add(
        new AffineTransform2D(
            new Matrix2x2(0.45, -0.49, 0.47, 0.47), new Vector2D(-1.62, -0.74))
    );

    transformations.add(
        new AffineTransform2D(
            new Matrix2x2(0.49, 0, 0, 0.51), new Vector2D(0.02, 1.62))
    );

    return new ChaosGameDescription(new Vector2D(-4, -4), new Vector2D(5, 4), transformations);
  }



  /**
   * Creates a {@code ChaosGameDescription} for generating the Spiral fractal.
   *
   * @return A {@code ChaosGameDescription} instance configured for the Spiral.
   * @since 0.0.2
   */
  public static ChaosGameDescription createSpiral() {
    List<Transform2D> transformations = new ArrayList<>();

    transformations.add(
        new AffineTransform2D(
            new Matrix2x2(0.787879, -0.424242, 0.242424, 0.859848),
            new Vector2D(1.7586, -1.7586)));

    transformations.add(
        new AffineTransform2D(
            new Matrix2x2(-0.121212, 0.257576, 0.151515, 0.054545),
            new Vector2D(-6.0404, 1.5152)));

    return new ChaosGameDescription(new Vector2D(-20, -10), new Vector2D(20, 5), transformations);
  }






  /**
   * Generates a {@code ChaosGameDescription} for creating a Julia set fractal
   * based on a given complex number. The method configures transformations that
   * apply the Julia set iteration based on the specified complex constant. The bounds
   * for this fractal are set to commonly used values for Julia sets, but may need adjustment
   * based on the characteristics of the specific set being generated.
   *
   * @param c The complex constant used in the Julia set transformation.
   * @param order The order of the Julia set
   *              (e.g., 2 for a standard Julia set, 3 for cubic Julia set).
   * @return A {@code ChaosGameDescription} instance configured for a Julia set
   * based on the given constant and order.
   * @since 0.0.0
   */
  public static ChaosGameDescription createJuliaSetDescription(Complex c, int order) {
    Vector2D minCoords = new Vector2D(-1.5, -1.5);  // Lower left corner
    Vector2D maxCoords = new Vector2D(1.5, 1.5);    // Upper right corner

    // Defining the two Julia set transformations:
    Transform2D juliaTransform = new JuliaTransform(c, 1, order, order != 2);
    Transform2D juliaTransform2 = new JuliaTransform(c, -1, order, order != 2);

    // return of a new ChaosGameDescription with the transformations and bounds
    return new ChaosGameDescription(
        minCoords, maxCoords, Arrays.asList(juliaTransform, juliaTransform2)
    );
  }





  /**
   * Generates a {@code ChaosGameDescription} for creating a Mandelbrot set fractal.
   * The method configures transformations that apply the Mandelbrot set iteration.
   * The bounds for this fractal are set to commonly used values for Mandelbrot sets,
   * but may need adjustment based on the characteristics of the specific set being generated.
   *
   *
   * @return A {@code ChaosGameDescription} instance configured for a Mandelbrot set
   * based on the given order.
   * @since 0.0.2
   */
  public static ChaosGameDescription createMandelbrotSetDescription() {
    Vector2D minCoords = new Vector2D(-2.5, -2);  // Lower left corner
    Vector2D maxCoords = new Vector2D(1.5, 2);    // Upper right corner

    //defining the Mandelbrot set transformation:
    Transform2D mandelbrotTransform = ChaosGameDescriptionFactory::transform;

    // return of a new ChaosGameDescription with the transformations and bounds
    return new ChaosGameDescription(
        minCoords, maxCoords, List.of(mandelbrotTransform)
    );
  }


  /**
   * A transformation function for the Mandelbrot set fractal.
   * This function is used to generate the Mandelbrot set fractal.
   *
   * @param z The complex number to transform.
   * @return The transformed complex number.
   * @since 0.0.2
   */
  private static Vector2D transform(Vector2D z) {
    return z; // identity transformation, actual iteration is handled in the fractal generation
  }

}
