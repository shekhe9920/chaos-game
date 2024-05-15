package edu.ntnu.stud.idatg2003.backend;

import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescription;


/**
 * The {@code ChaosGameObserver} interface defines the contract for observers that wish to be
 * notified about updates to the chaos game state or its description.
 * Implementing this interface allows an object to monitor changes in the game,
 * such as iterations of the fractal generation process or modifications to the game's
 * configuration.
 * Implementers of this interface can be registered with a {@code ChaosGame} instance to receive
 * callbacks when the game state is updated or when the game's description changes.
 *
 * @version 0.0.0
 * @since 0.0.3 (The version of Chaos-Game application when introduced)
 */
public interface ChaosGameObserver {

  /**
   * Invoked when the chaos game has been updated.
   * This method is called after each iteration or significant update to the game state,
   * providing observers with an opportunity to react to the progress of the game.
   * Implementations of this method might include refreshing a user interface,
   * logging the update, or performing additional calculations based on the new game state.
   *
   * @since 0.0.0
   */
  void onChaosGameUpdated();



  /**
   * Called when the description of the chaos game changes. This method provides observers with the
   * new game description, allowing them to adjust to changes in the game's configuration.
   *
   * @param newDescription The new {@code ChaosGameDescription} instance representing the updated
   *                       game settings.
   *                       This includes any changes to the transformations used for fractal
   *                       generation or adjustments to the bounds of the fractal drawing area.
   *
   * @since 0.0.0
   */
  void onChaosDescriptionChanged(ChaosGameDescription newDescription);

}
