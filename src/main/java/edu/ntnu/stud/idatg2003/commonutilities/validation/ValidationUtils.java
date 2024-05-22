package edu.ntnu.stud.idatg2003.commonutilities.validation;


/**
 * Utility class for performing various validation checks.
 *
 * @version 0.0.1
 * @since 0.1.0 (The version of Chaos-Game application when introduced)
 */
public class ValidationUtils {


  // Private constructor to prevent instantiation
  private ValidationUtils() {
    throw new UnsupportedOperationException("Utility class");
  }


  /**
   * Checks if any of the provided objects are null.
   *
   * @param objects the objects to check for null
   * @throws IllegalStateException if any object is null
   * @since 0.0.1
   */
  public static void checkNotNull(Object... objects) {
    for (Object obj : objects) {
      if (obj == null) {
        throw new IllegalStateException("UI elements are not initialized");
      }
    }
  }


}
