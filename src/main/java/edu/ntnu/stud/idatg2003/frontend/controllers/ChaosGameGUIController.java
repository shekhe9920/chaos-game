package edu.ntnu.stud.idatg2003.frontend.controllers;

import edu.ntnu.stud.idatg2003.backend.ChaosGameObserver;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGame;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescription;
import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescriptionFactory;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class ChaosGameGUIController implements ChaosGameObserver {
  private ChaosGame chaosGame;
  private GraphicsContext gc;
  private Canvas canvas;

  public ChaosGameGUIController(Canvas canvas) {
    ChaosGameDescription sierpinskiDescription = ChaosGameDescriptionFactory.createSierpinskiTriangle();

    this.canvas = canvas;
    this.gc = canvas.getGraphicsContext2D();
    this.chaosGame = new ChaosGame(sierpinskiDescription, 600, 600);
    this.chaosGame.addObserver(this);
  }


  @Override
  public void onChaosGameUpdated() {
    Platform.runLater(this::drawFractal);
  }

  @Override
  public void onChaosDescriptionChanged(ChaosGameDescription newDescription) {
    // Updating the chaosGame with a new description:
    this.chaosGame.setDescription(newDescription);
    // TODO: maybe update other GUI components that reflect the state of the chaos game?
  }

  public void setNewChaosGameDescription(ChaosGameDescription newDescription) {
    if(chaosGame != null) {
      chaosGame.updateDescription(newDescription);
    }
  }

  public void drawFractal() {
    int[][] pixelArray = chaosGame.getCanvas().getCanvasArray();
    Platform.runLater(() -> {
      gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
      for (int i = 0; i < pixelArray.length; i++) {
        for (int j = 0; j < pixelArray[i].length; j++) {
          if (pixelArray[i][j] == 1) {
            gc.fillRect(j, canvas.getHeight() - i, 1, 1);
          }
        }
      }
    });
  }
}
