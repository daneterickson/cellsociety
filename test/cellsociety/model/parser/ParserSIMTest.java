package cellsociety.model.parser;

import cellsociety.model.exceptions.InvalidFileException;
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
  void testGetType() throws FileNotFoundException, InvalidFileException {
    myParser.readFile(new File("data/game_of_life/blinkers.sim"));
    String expected = "GameOfLife";
    String actual = myParser.getInfo("Type");
    assertEquals(expected, actual);
  }

  @Test
  void testGetTitle() throws FileNotFoundException, InvalidFileException {
    myParser.readFile(new File("data/game_of_life/blinkers.sim"));
    String expected = "Blinkers";
    String actual = myParser.getInfo("Title");
    assertEquals(expected, actual);
  }

  @Test
  void testGetInitialStates() throws FileNotFoundException, InvalidFileException {
    myParser.readFile(new File("data/game_of_life/blinkers.sim"));
    String expected = "game_of_life/blinkers.csv";
    String actual = myParser.getInfo("InitialStates");
    assertEquals(expected, actual);
  }

  @Test
  void testGetParameter() throws FileNotFoundException, InvalidFileException {
    myParser.readFile(new File("data/spreading_of_fire/single_flame.sim"));
    double expected = 0.55;
    double actual = Double.valueOf(myParser.getInfo("Parameters").split(",")[0]);
    assertEquals(expected, actual);
  }

  @Test
  void testGetMultipleParameters() throws FileNotFoundException, InvalidFileException {
    myParser.readFile(new File("data/predator_prey/eat_forward.sim"));
    Double actual = Double.valueOf(myParser.getInfo("Parameters").split(",")[0]);
    assertEquals(10, actual);
    actual = Double.valueOf(myParser.getInfo("Parameters").split(",")[1]);
    assertEquals(11, actual);
    actual = Double.valueOf(myParser.getInfo("Parameters").split(",")[2]);
    assertEquals(12, actual);
  }

  @Test
  void testStatesColorMap() throws FileNotFoundException, InvalidFileException {
    myParser.readFile(new File("data/percolation/long_pipe.sim"));
    String colors[] = {"FFFFFF", "0000FF", "000000"};
    for (int i = 0; i < colors.length; i++) {
      String expected = colors[i];
      String actual = myParser.getInfo("StateColors").split(",")[i];
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
  void testEmptyFIle() {
    Exception e = assertThrows(InvalidFileException.class, () -> {
      myParser.readFile(new File("data/game_of_life/empty.sim"));
    });
    assertTrue(e.getMessage().contains("Empty SIM File"));
  }


}
