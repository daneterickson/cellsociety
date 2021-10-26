package cellsociety.model.parser;

import java.io.File;
import java.io.FileNotFoundException;
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
  void testGetType() throws FileNotFoundException {
    myParser.readFile(new File("data/game_of_life/blinkers.sim"));
    String expected = "GameOfLife";
    String actual = myParser.getType();
    assertEquals(expected, actual);
  }

  @Test
  void testGetTitle() throws FileNotFoundException {
    myParser.readFile(new File("data/game_of_life/blinkers.sim"));
    String expected = "Blinkers";
    String actual = myParser.getTitle();
    assertEquals(expected, actual);
  }

  @Test
  void testGetInitialStates() throws FileNotFoundException {
    myParser.readFile(new File("data/game_of_life/blinkers.sim"));
    String expected = "game_of_life/blinkers.csv";
    String actual = myParser.getInitialStates();
    assertEquals(expected, actual);
  }

  @Test
  void testGetParameter() throws FileNotFoundException {
    myParser.readFile(new File("data/spreading_of_fire/single_flame.sim"));
    double expected = 0.55;
    double actual = myParser.getParameters(0);
    assertEquals(expected, actual);
  }

  @Test
  void testGetMultipleParameters() throws FileNotFoundException {
    myParser.readFile(new File("data/predator_prey/eat_forward.sim"));
    assertEquals(10, myParser.getParameters(0));
    assertEquals(11, myParser.getParameters(1));
    assertEquals(12, myParser.getParameters(2));
  }

  @Test
  void testStatesColorMap() throws FileNotFoundException {
    myParser.readFile(new File("data/percolation/long_pipe.sim"));
    String colors[] = {"FFFFFF","0000FF","000000"};
    for (int i=0; i<colors.length; i++) {
      String expected = colors[i];
      String actual = myParser.getStateColorMap().get(i);
      assertEquals(expected, actual);
    }
  }

  @Test
  void testBadFileName() {
    assertThrows(FileNotFoundException.class, () -> {
      myParser.readFile(new File("abc"));
    });
  }

  @Test
  void testEmptyFIle() throws FileNotFoundException {
    myParser.readFile(new File("data/game_of_life/empty.sim"));
    assertEquals(null, myParser.getInitialStates());
    assertEquals(null, myParser.getTitle());
    assertEquals(null, myParser.getStateColorMap());
    assertEquals(null, myParser.getType());
    assertEquals(0.0, myParser.getParameters(0));
  }


}
