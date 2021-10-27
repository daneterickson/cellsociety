package cellsociety.model.cell;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.model.exceptions.KeyNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpreadingOfFireCellTest {

  private ModelCell myCell;
  private String myStartColors;
  private String myParameters;

  @BeforeEach
  void setUp() {
    myCell = new SpreadingOfFireCell(0, 0, myStartColors, myParameters, 0);
  }

  @Test
  void testChangeState() throws KeyNotFoundException {
    myCell.changeState(1);
    assertEquals(1, Integer.valueOf(myCell.getCellProperty("StateNumber")));
    myCell.changeState(2);
    assertEquals(2, Integer.valueOf(myCell.getCellProperty("StateNumber")));
  }

  @Test
  void testSetColor() throws KeyNotFoundException {
    assertEquals("ffff00", myCell.getCellProperty("StateColor"));
  }

  @Test
  void testChangeColor() throws KeyNotFoundException {
    myCell.changeState(1);
    assertEquals("00ff00", myCell.getCellProperty("StateColor"));
    myCell.changeState(2);
    assertEquals("ff0000", myCell.getCellProperty("StateColor"));
  }

  @Test
  void testSetName() throws KeyNotFoundException {
    assertEquals("empty", myCell.getCellProperty("StateName"));
  }

  @Test
  void testChangeName() throws KeyNotFoundException {
    myCell.changeState(1);
    assertEquals("tree", myCell.getCellProperty("StateName"));
    myCell.changeState(2);
    assertEquals("burn", myCell.getCellProperty("StateName"));
  }


}
