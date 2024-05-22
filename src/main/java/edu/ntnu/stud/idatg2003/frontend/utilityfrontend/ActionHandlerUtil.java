package edu.ntnu.stud.idatg2003.frontend.utilityfrontend;

import edu.ntnu.stud.idatg2003.commonutilities.errorutilitie.ActionExecutionException;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

/**
 * Utility class for handling actions for buttons and menu items.
 * This class provides methods to set actions for {@code Button} and {@code MenuItem} controls,
 * ensuring that exceptions are properly handled and displayed in the GUI.
 *
 * @version 0.0.1
 * @since 0.1.0
 */
public class ActionHandlerUtil {



  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private ActionHandlerUtil() {
    // This constructor is intentionally empty. Nothing special is needed here.
  }




  /**
   * Sets the action for the given {@code Button}.
   *
   * @param button The button to set the action for.
   * @param action The action to perform when the button is pressed.
   * @since 0.1.0
   */
  public static void setButtonAction(Button button, Action action) {
    button.setOnAction(e -> {
      try {
        action.perform();
      } catch (Exception ex) {
        GuiErrorDisplay.showError("An error occurred while executing the button action.");
        throw new ActionExecutionException("Error executing button action", ex);
      }
    });
  }






  /**
   * Sets the action for the given {@code MenuItem}.
   *
   * @param menuItem The menu item to set the action for.
   * @param action   The action to perform when the menu item is selected.
   * @since 0.1.0
   */
  public static void setMenuItemAction(MenuItem menuItem, Action action) {
    menuItem.setOnAction(e -> {
      try {
        action.perform();
      } catch (Exception ex) {
        GuiErrorDisplay.showError("An error occurred while executing the menu item action.");
        throw new ActionExecutionException("Error executing menu item action", ex);
      }
    });
  }




}