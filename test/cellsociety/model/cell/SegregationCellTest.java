package cellsociety.model.cell;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.model.exceptions.KeyNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SegregationCellTest {

  private ModelCell myCell;
  private String myStartColors;
  private String myParameters;

  @BeforeEach
  void setUp() {
    myCell = new SegregationCell(0, 0, myStartColors, myParameters, 0);
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
    assertEquals("c0c0c0", myCell.getCellProperty("StateColor"));
  }

  @Test
  void testChangeColor() throws KeyNotFoundException {
    myCell.changeState(1);
    assertEquals("ff0000", myCell.getCellProperty("StateColor"));
    myCell.changeState(2);
    assertEquals("0000ff", myCell.getCellProperty("StateColor"));
  }

  @Test
  void testSetName() throws KeyNotFoundException {
    assertEquals("empty", myCell.getCellProperty("StateName"));
  }

  @Test
  void testChangeName() throws KeyNotFoundException {
    myCell.changeState(1);
    assertEquals("race1", myCell.getCellProperty("StateName"));
    myCell.changeState(2);
    assertEquals("race2", myCell.getCellProperty("StateName"));
  }

  @Test
  void testInitialColorStates() throws KeyNotFoundException {
    myStartColors = "000000,111111,222222";
    myCell = new SegregationCell(0, 0, myStartColors, myParameters, 0);
    myCell.changeState(1);
    assertEquals("111111", myCell.getCellProperty("StateColor"));
    myCell.changeState(2);
    assertEquals("222222", myCell.getCellProperty("StateColor"));
  }


}
