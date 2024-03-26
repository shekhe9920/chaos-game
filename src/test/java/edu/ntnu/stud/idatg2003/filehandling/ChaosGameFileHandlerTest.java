package edu.ntnu.stud.idatg2003.filehandling;

import edu.ntnu.stud.idatg2003.backend.engine.ChaosGameDescription;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Matrix2x2;
import edu.ntnu.stud.idatg2003.backend.mathoperations.Vector2D;
import edu.ntnu.stud.idatg2003.backend.transformations.AffineTransform2D;
import edu.ntnu.stud.idatg2003.backend.transformations.Transform2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ChaosGameFileHandlerTest {

  @TempDir
  Path tempDir;

  @Test
  void readFile_ValidFile_ShouldSucceed() throws Exception {
    Path file = tempDir.resolve("validFile.txt"); // temporary file

    // Populate the file with valid data
    Files.writeString(file, "Affine2D\n0.0,0.0\n1.0,1.0\n1.0,0.0,0.0,1.0,0.5,0.5\n");

    // Attempt to read the file
    ChaosGameDescription description = ChaosGameFileHandler.readFile(file.toString());

    // Assert that the file was read correctly
    Assertions.assertNotNull(description);
    Assertions.assertFalse(description.getTransformations().isEmpty());
  }

  @Test
  void readFile_NonExistingFile_ShouldThrowException() {
    Assertions.assertThrows(IOException.class, () -> {
      ChaosGameFileHandler.readFile("nonExistingFile.txt");
    }, "Reading a non-existing file should throw IOException.");
  }


  @Test
  void writeToFile_ValidDescription_ShouldSucceed() throws Exception {
    Path file = tempDir.resolve("outputFile.txt");  // temporary file

    // a mock ChaosGameDescription
    Vector2D minCoords = new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(1, 1);
    List<Transform2D> transforms = new ArrayList<>();
    transforms.add(new AffineTransform2D(new Matrix2x2(1, 0, 0, 1), new Vector2D(0.5, 0.5)));
    ChaosGameDescription description = new ChaosGameDescription(minCoords, maxCoords, transforms);

    // Writing the description to the file:
    ChaosGameFileHandler.writeToFile(description, file.toString());

    // Verifying the file was written correctly:
    Assertions.assertTrue(Files.exists(file));
    Assertions.assertTrue(Files.lines(file).count() > 0);
  }

}
