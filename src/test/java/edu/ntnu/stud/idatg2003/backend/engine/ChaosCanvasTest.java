package edu.ntnu.stud.idatg2003.backend.engine;

import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



/**
 * Test class for ChaosCanvas.
 * This class contains unit tests that verify the correct functionality
 * of the ChaosCanvas class.
 */
class ChaosCanvasTest {

  private ChaosCanvas chaosCanvas;




  @BeforeEach
  void setUp() {
    Vector2D minCoords = new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(10, 10);
    chaosCanvas = new ChaosCanvas(100, 100, minCoords, maxCoords);
  }




  /**
   * Test to ensure the ChaosCanvas is initialized with the correct dimensions.
   */
  @Test
  void testInitialize_Canvas() {
    assertEquals(100, chaosCanvas.getWidth());
    assertEquals(100, chaosCanvas.getHeight());
    assertNotNull(chaosCanvas.getCanvasArray());
    assertNotNull(chaosCanvas.getHitCounts());
  }




  /**
   * Test to ensure the clearCanvas method sets all pixels to 0.
   */
  @Test
  void testClear_Canvas() {
    chaosCanvas.putPixel(new Vector2D(5, 5), 1);
    chaosCanvas.clearCanvas();
    int[][] canvasArray = chaosCanvas.getCanvasArray();
    for (int[] row : canvasArray) {
      for (int pixel : row) {
        assertEquals(0, pixel);
      }
    }
  }




  /**
   * Test to ensure the putPixel method works correctly for points within bounds.
   */
  @Test
  void testPut_Pixel_WithinBounds() {
    Vector2D point = new Vector2D(5, 5);
    chaosCanvas.putPixel(point, 1);
    assertEquals(1, chaosCanvas.getPixel(point));
    int[][] hitCounts = chaosCanvas.getHitCounts();
    assertEquals(1, hitCounts[49][49]);
  }





  /**
   * Test to ensure the putPixel method does not affect the canvas for points out of bounds.
   */
  @Test
  void testPut_Pixel_OutOfBounds() {
    Vector2D outOfBoundsPoint = new Vector2D(-1, -1);
    chaosCanvas.putPixel(outOfBoundsPoint, 1);

    // Ensure no pixel is set for out-of-bounds coordinates
    for (int y = 0; y < chaosCanvas.getHeight(); y++) {
      for (int x = 0; x < chaosCanvas.getWidth(); x++) {
        assertEquals(0, chaosCanvas.getCanvasArray()[y][x], "Pixel at (" + x + "," + y + ") should remain 0");
      }
    }
  }



  /**
   * Test to ensure the transformToCanvasIndices method works correctly for points within bounds.
   */
  @Test
  void testTransform_ToCanvasIndices_WithinBounds() {
    Vector2D point = new Vector2D(5, 5);
    Vector2D expectedCanvasPoint = new Vector2D(49, 49);
    Vector2D actualCanvasPoint = chaosCanvas.transformToCanvasIndices(point);
    assertEquals(expectedCanvasPoint, actualCanvasPoint);
  }




  /**
   * Test to ensure the transformToCanvasIndices method clamps points out of bounds to the canvas edges.
   */
  @Test
  void testTransform_ToCanvasIndices_OutOfBounds() {
    Vector2D point = new Vector2D(15, 15);
    Vector2D expectedCanvasPoint = new Vector2D(99, 99);
    Vector2D actualCanvasPoint = chaosCanvas.transformToCanvasIndices(point);
    // The point is out of bounds, so it should be clamped to the edge of the canvas.
    assertEquals(expectedCanvasPoint, actualCanvasPoint);
  }




  /**
   * Test to ensure the getCanvasArray method returns a non-null 2D array with correct dimensions.
   */
  @Test
  void testGet_CanvasArray() {
    int[][] canvasArray = chaosCanvas.getCanvasArray();
    assertNotNull(canvasArray);
    assertEquals(100, canvasArray.length);
    assertEquals(100, canvasArray[0].length);
  }




  /**
   * Test to ensure the getHitCounts method returns a non-null 2D array with correct dimensions.
   */
  @Test
  void testGet_HitCounts() {
    int[][] hitCounts = chaosCanvas.getHitCounts();
    assertNotNull(hitCounts);
    assertEquals(100, hitCounts.length);
    assertEquals(100, hitCounts[0].length);
  }


}
