package edu.ntnu.stud.idatg2003.filehandling;

import edu.ntnu.stud.idatg2003.engine.ChaosGameDescription;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ChaosGameFileHandlerTest {

  private File testFile;

  @BeforeEach
  public void setUp() throws Exception {
    testFile = new File("testFile.txt");
    testFile.createNewFile();
  }

  @AfterEach
  public void tearDown() throws Exception {
    boolean fileDeleted = testFile.delete();
    assertTrue(fileDeleted, "Failed to delete test file");
  }

  private void writeTestDataToFile() throws IOException {
    try (FileWriter writer = new FileWriter(testFile)) {
      writer.write("Affine2D\n");
      writer.write("0, 0\n");
      writer.write("1, 1\n");
      writer.write(".5, 0, 0, .5, 0, 0\n");
      writer.write(".5, 0, 0, .5, .25, .5\n");
      writer.write(".5, 0, 0, .5, .5, 0\n");
    }
  }

  /*

  // need to fix this test
  @Test
  void testReadFromFile() throws IOException {
    writeTestDataToFile();
    ChaosGameDescription description = ChaosGameFileHandler.readFromFile("testFile.txt");
    assertNotNull(description);
    assertEquals(3, description.getTransformations().size());
  }

   */

  @Test
  void testReadFromFileIOException() {
    assertThrows(IOException.class, () -> ChaosGameFileHandler.readFromFile("nonExistentFile.txt"));
  }

  @Test
  void testWriteToFileIOException() throws IOException {
    File readOnlyFile = new File("readonlyfile.txt");
    try {
      readOnlyFile.createNewFile();
      readOnlyFile.setReadOnly();
      ChaosGameDescription description = new ChaosGameDescription(null, null, null);
      assertThrows(IOException.class, () -> ChaosGameFileHandler.writeToFile(description, "readonlyfile.txt"));
    } finally {
      // Ensure cleanup even if an exception occurs during file creation or writing
      boolean fileDeleted = readOnlyFile.delete();
      assertTrue(fileDeleted, "Failed to delete read-only test file");
    }
  }
}
