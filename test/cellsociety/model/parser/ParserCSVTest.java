package cellsociety.model.parser;

import cellsociety.model.exceptions.InvalidFileException;
import cellsociety.model.parser.ParserCSV;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ParserCSVTest {

  private ParserCSV myParser;


  @BeforeEach
  void setUp() {
    myParser = new ParserCSV();
  }

  @Test
  void testRowNumCSVFile() throws CsvValidationException, IOException {
    myParser.readFile(new File("data/game_of_life/blinkers.csv"));
    assertEquals(10, myParser.getNumRows());
  }

  @Test
  void testColNumCSVFile() throws CsvValidationException, IOException {
    myParser.readFile(new File("data/game_of_life/blinkers.csv"));
    assertEquals(10, myParser.getNumCols());
  }

  @Test
  void testStartStatesCSVFile() throws CsvValidationException, IOException {
    myParser.readFile(new File("data/game_of_life/blinkers.csv"));
    int expected[][] = {{0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {1, 0, 0, 0, 1, 0, 0, 0, 0, 0},
        {1, 0, 0, 0, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {0, 1, 1, 1, 0, 0, 0, 0, 0, 1},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 1, 1, 0, 0, 0}};
    checkStartStates(expected);
  }

  @Test
  void testBadFileName() {
    assertThrows(FileNotFoundException.class, () -> {
      myParser.readFile(new File("abc"));
    });
  }

  @Test
  void testEmptyFile() throws CsvValidationException, IOException {
    Exception e = assertThrows(IOException.class, () -> {
      myParser.readFile(new File("data/game_of_life/empty.csv"));
    });
    assertTrue(e.getMessage().contains("Empty CSV File"));
  }

  @Test
  void testRectangularFile() throws CsvValidationException, IOException {
    myParser.readFile(new File("data/game_of_life/penta-decathlon.csv"));
    int expected[][] = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {
        0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, {
        0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0}, {
        0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {
        0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {
        0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {
        0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {
        0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {
        0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {
        0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0}, {
        0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
    assertEquals(11, myParser.getNumCols());
    assertEquals(18, myParser.getNumRows());
    checkStartStates(expected);
  }

  @Test
  void testInvalidRowInputFile() {
    Exception e = assertThrows(IOException.class, () -> {
      myParser.readFile(new File("data/game_of_life/invalid_rows.csv"));
    });
    assertTrue(e.getMessage().contains("Invalid Number of Rows"));
  }

  @Test
  void testInvalidColInputFile() {
    Exception e = assertThrows(IOException.class, () -> {
      myParser.readFile(new File("data/game_of_life/invalid_cols.csv"));
    });
    assertTrue(e.getMessage().contains("Invalid Number of Columns"));
  }


  private void checkStartStates(int[][] expected) {
    for (int row = 0; row < myParser.getNumRows(); row++) {
      for (int col = 0; col < myParser.getNumCols(); col++) {
        assertEquals(expected[row][col], myParser.getStartStates()[row][col]);
      }
    }
  }
}
