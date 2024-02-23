package edu.ntnu.stud.idatg2003;

import edu.ntnu.stud.idatg2003.mathoperations.Vector2D;
import java.util.Random;

public class ChaosGame {

  private ChaosCanvas canvas;
  private ChaosGameDescription description;
  private Vector2D currentPoint;
  private Random random;

  public ChaosGame(ChaosGameDescription description, int width, int height) {
    this.description = description;
    this.canvas =
        new ChaosCanvas(width, height, description.getMinCoords(), description.getMaxCoords());
  }

  public ChaosCanvas getCanvas() {
    return canvas;
  }

  public void runSteps(int steps) {
    currentPoint = new Vector2D(0, 0);
    random = new Random();
    for (int i = 0; i < steps; i++) {
      int index = random.nextInt(description.getTransformations().size());
      currentPoint = description.getTransformations().get(index).transform(currentPoint);
      canvas.putPixel(canvas.getPixel(currentPoint));
    }
  }

}
