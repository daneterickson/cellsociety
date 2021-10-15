package cellsociety;

import cellsociety.model.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ParserTest {

  private Parser myParser;


  @BeforeEach
  void setUp() {
    myParser = new Parser("data/game_of_life/blinkers.csv");
  }

  @Test
  void testRowNumCSVFile() {
    assertEquals(10, myParser.getNumRows());
  }

  @Test
  void testColNumCSVFile() {
    assertEquals(10, myParser.getNumCols());
  }

  @Test
  void testStartStatesCSVFile() {
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
    for (int row=0; row<myParser.getNumRows(); row++) {
      for (int col=0; col<myParser.getNumCols(); col++) {
        assertEquals(expected[row][col], myParser.getStartStates()[row][col]);
      }
    }
  }
}
