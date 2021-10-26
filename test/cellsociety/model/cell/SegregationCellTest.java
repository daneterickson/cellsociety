package cellsociety.model.cell;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
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
  void testChangeState() {
    myCell.changeState(1);
    assertEquals(1, Integer.valueOf(myCell.getProperty("StateNumber")));
    myCell.changeState(2);
    assertEquals(2, Integer.valueOf(myCell.getProperty("StateNumber")));
  }

  @Test
  void testSetColor() {
    assertEquals("c0c0c0", myCell.getProperty("StateColor"));
  }

  @Test
  void testChangeColor() {
    myCell.changeState(1);
    assertEquals("ff0000", myCell.getProperty("StateColor"));
    myCell.changeState(2);
    assertEquals("0000ff", myCell.getProperty("StateColor"));
  }

  @Test
  void testSetName() {
    assertEquals("empty", myCell.getProperty("StateName"));
  }

  @Test
  void testChangeName() {
    myCell.changeState(1);
    assertEquals("race1", myCell.getProperty("StateName"));
    myCell.changeState(2);
    assertEquals("race2", myCell.getProperty("StateName"));
  }

  @Test
  void testInitialColorStates() {
    myStartColors = "000000,111111,222222";
    myCell = new SegregationCell(0, 0, myStartColors, myParameters, 0);
    myCell.changeState(1);
    assertEquals("111111", myCell.getProperty("StateColor"));
    myCell.changeState(2);
    assertEquals("222222", myCell.getProperty("StateColor"));
  }


}
