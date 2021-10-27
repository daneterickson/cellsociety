package cellsociety.model.cell;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.model.exceptions.KeyNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameOfLifeCellTest {

  private ModelCell myCell;
  private String myStartColors;
  private String myParameters;

  @BeforeEach
  void setUp() {
    myCell = new GameOfLifeCell(0, 0, myStartColors, myParameters, 0);
  }

  @Test
  void testChangeState() throws KeyNotFoundException {
    myCell.changeState(1);
    assertEquals(1, Integer.valueOf(myCell.getCellProperty("StateNumber")));
  }

  @Test
  void testSetColor() throws KeyNotFoundException {
    assertEquals("c0c0c0", myCell.getCellProperty("StateColor"));
  }

  @Test
  void testChangeColor() throws KeyNotFoundException {
    myCell.changeState(1);
    assertEquals("0000ff", myCell.getCellProperty("StateColor"));
  }

  @Test
  void testSetName() throws KeyNotFoundException {
    assertEquals("dead", myCell.getCellProperty("StateName"));
  }

  @Test
  void testChangeName() throws KeyNotFoundException {
    myCell.changeState(1);
    assertEquals("alive", myCell.getCellProperty("StateName"));
  }


}
