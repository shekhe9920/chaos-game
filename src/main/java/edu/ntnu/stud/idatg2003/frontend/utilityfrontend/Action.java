package edu.ntnu.stud.idatg2003.frontend.utilityfrontend;

/**
 * Functional interface representing an action that can be performed.
 * This interface is intended to be used with lambda expressions or method references
 * for defining actions without needing to create an anonymous inner class.
 *
 * @version 0.0.1
 * @since 0.0.4 (The version of Chaos-Game application when introduced)
 */
@FunctionalInterface
public interface Action {

  /**
   * Performs the action defined by the implementation of this method.
   */
  void perform();
}
