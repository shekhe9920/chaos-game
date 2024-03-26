package edu.ntnu.stud.idatg2003.backend.engine;

import edu.ntnu.stud.idatg2003.backend.mathoperations.Complex;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Matrix2x2;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.transformations.AffineTransform2D;
import edu.ntnu.stud.idatg2003.backend.transformations.JuliaTransform;
import edu.ntnu.stud.idatg2003.backend.transformations.Transform2D;
import java.util.Arrays;

/**
 * A factory class for creating predefined {@code ChaosGameDescription} instances.
 * This class provides static methods to generate descriptions for well-known fractals,
 * such as the Sierpinski Triangle and the Barnsley Fern, as well as a method to generate
 * a description for Julia sets given a specific complex number.
 *
 * @version 0.0.0
 * @since 0.0.3 (The version of ChaosGameEngine application when introduced)
 */
public class ChaosGameDescriptionFactory {




  /**
   * Creates a {@code ChaosGameDescription} for generating the Sierpinski Triangle fractal.
   * This method sets up the necessary affine transformations and bounding coordinates
   * to generate the fractal.
   *
   * @return A {@code ChaosGameDescription} instance configured for the Sierpinski Triangle.
   * @since 0.0.0
   */
  public static ChaosGameDescription createSierpinskiTriangle() {
    Vector2D minCoords = new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(1, 1);

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
    Vector2D minCoords = new Vector2D(-2.65, 0);
    Vector2D maxCoords = new Vector2D(2.65, 10 );

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

    return new ChaosGameDescription(
            minCoords, maxCoords, Arrays.asList(transform1, transform2, transform3, transform4)
    );

  }




  /**
   * Generates a {@code ChaosGameDescription} for creating a Julia set fractal
   * based on a given complex number. The method configures transformations that
   * apply the Julia set iteration based on the specified complex constant. The bounds
   * for this fractal are set to commonly used values for Julia sets, but may need adjustment
   * based on the characteristics of the specific set being generated.
   *
   * @param c The complex constant used in the Julia set transformation.
   * @return A {@code ChaosGameDescription} instance configured for a Julia set
   * based on the given constant.
   * @since 0.0.0
   */
  public static ChaosGameDescription createJuliaSetDescription(Complex c) {
    Vector2D minCoords = new Vector2D(-1.6, -1);
    Vector2D maxCoords = new Vector2D(1.6, 1);

    Transform2D juliaTransform = new JuliaTransform(c, 1);
    Transform2D juliaTransform2 = new JuliaTransform(c, -1);

    return new ChaosGameDescription(
        minCoords, maxCoords, Arrays.asList(juliaTransform, juliaTransform2)
    );

  }

}
