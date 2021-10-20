package cellsociety.model.parser;

import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserSIMTest {

  private ParserSIM myParser;


  @BeforeEach
  void setUp() {
    myParser = new ParserSIM(new File("data/game_of_life/blinkers.sim"));
    myParser.readFile();
  }

  @Test
  void testGetType() {
    String expected = "GameOfLife";
    String actual = myParser.getType();
    assertEquals(expected, actual);
  }

  @Test
  void testGetTitle() {
    String expected = "Blinkers";
    String actual = myParser.getTitle();
    assertEquals(expected, actual);
  }

  @Test
  void testGetInitialStates() {
    String expected = "game_of_life/blinkers.csv";
    String actual = myParser.getInitialStates();
    assertEquals(expected, actual);
  }

}
