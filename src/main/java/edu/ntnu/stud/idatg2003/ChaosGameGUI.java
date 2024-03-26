package edu.ntnu.stud.idatg2003;


import edu.ntnu.stud.idatg2003.backend.engine.ChaosGame;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescription;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescriptionFactory;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Complex;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.frontend.controllers.ChaosGameGUIController;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChaosGameGUI extends Application {



  private Canvas canvas;
  private ChaosGame chaosGame;
  private GraphicsContext gc;




  @Override
  public void start(Stage primaryStage) {
    VBox root = new VBox();
    MenuBar menuBar = createMenuBar();
    root.getChildren().add(menuBar);

    canvas = new Canvas(600, 600);
    gc = canvas.getGraphicsContext2D();
    chaosGame =
        new ChaosGame(
            new ChaosGameDescription(
                new Vector2D(0, 0), new Vector2D(1, 1), new ArrayList<>()), 600, 600);

    ComboBox<String> fractalChoiceBox = new ComboBox<>();
    fractalChoiceBox.getItems().addAll("Sierpinski Triangle", "Barnsley Fern", "Julia Set");
    fractalChoiceBox.getSelectionModel().selectFirst(); // Automatically select the first item

    root.getChildren().addAll(fractalChoiceBox, canvas);

    ChaosGameGUIController guiController = new ChaosGameGUIController(canvas);

    fractalChoiceBox.setOnAction(event -> {
      String choice = fractalChoiceBox.getValue();
      switch (choice) {
        case "Sierpinski Triangle":
          chaosGame.setDescription(ChaosGameDescriptionFactory.createSierpinskiTriangle());
          break;
        case "Barnsley Fern":
          chaosGame.setDescription(ChaosGameDescriptionFactory.createBarnsleyFern());
          break;
        case "Julia Set":
          Complex c = new Complex(-0.74543, 0.11301); // c constant
          chaosGame.setDescription(ChaosGameDescriptionFactory.createJuliaSetDescription(c));
          break;
      }
    });

    Button startButton = new Button("Start");
    startButton.setOnAction(event -> generateAndDrawFractal());
    root.getChildren().add(startButton);

    Scene scene = new Scene(root, 1100, 700);
    primaryStage.setTitle("Chaos Game");
    primaryStage.setScene(scene);
    primaryStage.show();
  }




  private void generateAndDrawFractal() {
    if (chaosGame == null || chaosGame.getCanvas() == null) {
      System.out.println("Chaos game or canvas is null");
      return;
    }
    ChaosGameDescription chaos = chaosGame.getDescription();
    chaosGame = new ChaosGame(chaos, 600, 600);
    chaosGame.runSteps(100000); // step count
    drawFractal();
  }




  private void drawFractal() {
    // Checking if gc is null:
    if (gc == null) {
      gc = canvas.getGraphicsContext2D();
    }

    // Clearing the canvas before drawing the new fractal:
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

    // Drawing the fractal:
    int[][] pixelArray = chaosGame.getCanvas().getCanvasArray();
    for (int i = 0; i < pixelArray.length; i++) {
      for (int j = 0; j < pixelArray[i].length; j++) {
        if (pixelArray[i][j] == 1) {
          gc.fillRect(j, canvas.getHeight() - i, 1, 1); // Draw each point, flipping the y-coordinate
        }
      }
    }
  }




  private MenuBar createMenuBar() {
    MenuBar menuBar = new MenuBar();
    Menu menuFile = new Menu("File");
    MenuItem newFractal = new MenuItem("New...");
    MenuItem openFile = new MenuItem("Open...");
    MenuItem saveFile = new MenuItem("Save As...");
    menuFile.getItems().addAll(newFractal, openFile, saveFile);
    menuBar.getMenus().add(menuFile);

    // TODO: DEFINE MENU ITEM ACTIONS:
    newFractal.setOnAction(event -> {
      // Handle creating a new fractal...
    });
    openFile.setOnAction(event -> {
      // Handle opening a fractal description from a file...
    });
    saveFile.setOnAction(event -> {
      // Handle saving the current fractal description to a file...
    });

    return menuBar;
  }




  public static void main(String[] args) {
    launch(args);
  }
}