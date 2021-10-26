package cellsociety.model.cell;

import static org.junit.jupiter.api.Assertions.*;

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
  void testChangeState() {
    myCell.changeState(1);
    assertEquals(1, Integer.valueOf(myCell.getCellProperty("StateNumber")));
  }

  @Test
  void testSetColor() {
    assertEquals("c0c0c0", myCell.getCellProperty("StateColor"));
  }

  @Test
  void testChangeColor() {
    myCell.changeState(1);
    assertEquals("0000ff", myCell.getCellProperty("StateColor"));
  }

  @Test
  void testSetName() {
    assertEquals("dead", myCell.getCellProperty("StateName"));
  }

  @Test
  void testChangeName() {
    myCell.changeState(1);
    assertEquals("alive", myCell.getCellProperty("StateName"));
  }


}
