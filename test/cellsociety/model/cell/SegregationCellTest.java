package cellsociety.model.cell;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SegregationCellTest {

  private ModelCell myCell;
  private Map<Integer, String> myStartColors;

  @BeforeEach
  void setUp() {
    myStartColors = new HashMap<>();
    myCell = new SegregationCell(0, 0, myStartColors, 0);
  }

  @Test
  void testChangeState() {
    myCell.changeState(1);
    assertEquals(1, myCell.getStateNumber());
    myCell.changeState(2);
    assertEquals(2, myCell.getStateNumber());
  }

  @Test
  void testSetColor() {
    assertEquals("c0c0c0", myCell.getStateColor());
  }

  @Test
  void testChangeColor() {
    myCell.changeState(1);
    assertEquals("ff0000", myCell.getStateColor());
    myCell.changeState(2);
    assertEquals("0000ff", myCell.getStateColor());
  }

  @Test
  void testSetName() {
    assertEquals("empty", myCell.getStateName());
  }

  @Test
  void testChangeName() {
    myCell.changeState(1);
    assertEquals("race1", myCell.getStateName());
    myCell.changeState(2);
    assertEquals("race2", myCell.getStateName());
  }

  @Test
  void testInitialColorStates() {
    myStartColors.put(0, "000000");
    myStartColors.put(1, "111111");
    myStartColors.put(2, "222222");
    myCell.changeState(1);
    assertEquals("111111", myCell.getStateColor());
    myCell.changeState(2);
    assertEquals("222222", myCell.getStateColor());
  }


}
