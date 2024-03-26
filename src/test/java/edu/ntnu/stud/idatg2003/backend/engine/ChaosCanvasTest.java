package edu.ntnu.stud.idatg2003.backend.engine;

import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChaosCanvasTest {

  private ChaosCanvas chaosCanvas;
  private Vector2D minCoords;
  private Vector2D maxCoords;
  private int width;
  private int height;

  @BeforeEach
  void setUp() {
    width = 100;
    height = 100;
    minCoords = new Vector2D(0, 0);
    maxCoords = new Vector2D(99, 99);
    chaosCanvas = new ChaosCanvas(width, height, minCoords, maxCoords);
  }

  @Test
  void testPutPixel_ValidPoint_ShouldSucceed() {
    Vector2D point = new Vector2D(50, 50);
    int color = 1;
    chaosCanvas.putPixel(point, color);

    Assertions.assertEquals(color, chaosCanvas.getPixel(point),
        "Pixel color should be set to the specified color after putPixel call.");
  }

  @Test
  void testPutPixel_PointOutsideCanvas_ShouldThrowException() {
    Vector2D point = new Vector2D(width, height); // Outside the canvas

    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
      chaosCanvas.putPixel(point, 1);
    }, "putPixel should throw IndexOutOfBoundsException when trying to put a pixel outside of canvas bounds.");
  }

  @Test
  void testClearCanvas_AfterSettingPixels_ShouldClearCanvas() {
    Vector2D point = new Vector2D(50, 50);
    int color = 1;
    chaosCanvas.putPixel(point, color);

    chaosCanvas.clearCanvas();

    Assertions.assertEquals(0, chaosCanvas.getPixel(point),
        "Canvas should be cleared to default value (0) after clearCanvas call.");
  }

  @Test
  void testGetCanvasArray_AfterClearCanvas_ShouldBeEmpty() {
    chaosCanvas.clearCanvas();
    int[][] canvasArray = chaosCanvas.getCanvasArray();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Assertions.assertEquals(0, canvasArray[i][j],
            "Canvas should be empty (all zeros) after clearCanvas call.");
      }
    }
  }


}