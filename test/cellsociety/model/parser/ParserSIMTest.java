package cellsociety.model.parser;

import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserSIMTest {

  private ParserSIM myParser;


  @BeforeEach
  void setUp() {
    myParser = new ParserSIM();
  }

  @Test
  void testGetType() {
    myParser.readFile(new File("data/game_of_life/blinkers.sim"));
    String expected = "GameOfLife";
    String actual = myParser.getType();
    assertEquals(expected, actual);
  }

  @Test
  void testGetTitle() {
    myParser.readFile(new File("data/game_of_life/blinkers.sim"));
    String expected = "Blinkers";
    String actual = myParser.getTitle();
    assertEquals(expected, actual);
  }

  @Test
  void testGetInitialStates() {
    myParser.readFile(new File("data/game_of_life/blinkers.sim"));
    String expected = "game_of_life/blinkers.csv";
    String actual = myParser.getInitialStates();
    assertEquals(expected, actual);
  }

  @Test
  void testStatesColorMap() {
    myParser.readFile(new File("data/percolation/long_pipe.sim"));
    String colors[] = {"FFFFFF","0000FF","000000"};
    for (int i=0; i<colors.length; i++) {
      String expected = colors[i];
      String actual = myParser.getStateColor(i);
      assertEquals(expected, actual);
    }

  }

}
