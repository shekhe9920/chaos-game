package edu.ntnu.stud.idatg2003.backend.utilitiesbackend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.ntnu.stud.idatg2003.backend.state.AppState;
import edu.ntnu.stud.idatg2003.backend.transformations.Transform2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The {@code ConfigManager} class is responsible for saving and loading the application state
 * to and from a configuration file in JSON format.
 *
 * <p>
 * It uses the Gson library for serialization and deserialization, with a custom type adapter
 * for the {@code Transform2D} interface.
 * </p>
 *
 * @version 0.0.2
 * @since 0.0.8 (The version of Chaos-Game application when introduced)
 */
public class ConfigManager {

  // Logger for logging messages to the console:
  private static final Logger LOGGER = Logger.getLogger(ConfigManager.class.getName());


  private static final String CONFIG_FILE = "config.json"; // The name of the configuration file

  // Gson instance for serialization and deserialization:
  private static final Gson gson = new GsonBuilder()
      .registerTypeAdapter(Transform2D.class, new Transform2dAdapter())
      .setPrettyPrinting()
      .create();





  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private ConfigManager() {
    // Private constructor to prevent instantiation
  }




  /**
   * Saves the application state to a JSON configuration file.
   *
   * @param state The application state to save.
   * @since 0.0.0
   */
  public static void saveConfig(AppState state) {
    try (FileWriter writer = new FileWriter(CONFIG_FILE)) {

      // serializing the state to JSON and writing it to the file:
      gson.toJson(state, writer);    // for debugging->:
      System.out.println("Config saved at: " + new File(CONFIG_FILE).getAbsolutePath());

    } catch (IOException e) {  // catching any exception that might occur
      LOGGER.warning("Failed to save config file: " + e.getMessage());
    }
  }




  /**
   * Loads the application state from a JSON configuration file located in the resources' directory.
   *
   * @return The loaded application state, or a default {@code AppState} if file cannot be loaded.
   * @since 0.0.0
   */
  public static AppState loadConfig() {
    try {

      // retrieving the config file from resources:
      InputStreamReader reader = new InputStreamReader(
          Objects.requireNonNull(
              ConfigManager.class.getClassLoader().getResourceAsStream(CONFIG_FILE))
      );

      AppState state = gson.fromJson(reader, AppState.class); // deserializing the state from JSON
      System.out.println("Loaded config: " + gson.toJson(state)); // to log the loaded config
      return state; // returning the loaded state

    } catch (Exception e) {  // catching any exception that might occur
      LOGGER.warning("Failed to load config file: " + e.getMessage());
      return new AppState();
    }
  }
}
