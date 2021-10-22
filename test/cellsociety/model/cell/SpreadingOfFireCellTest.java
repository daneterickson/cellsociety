package cellsociety.model.cell;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpreadingOfFireCellTest {

  private ModelCell myCell;

  @BeforeEach
  void setUp() {
    myCell = new SpreadingOfFireCell(0, 0, 0);
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
    assertEquals("ffff00", myCell.getStateColor());
  }

  @Test
  void testChangeColor() {
    myCell.changeState(1);
    assertEquals("00ff00", myCell.getStateColor());
    myCell.changeState(2);
    assertEquals("ff0000", myCell.getStateColor());
  }

  @Test
  void testSetName() {
    assertEquals("empty", myCell.getStateName());
  }

  @Test
  void testChangeName() {
    myCell.changeState(1);
    assertEquals("tree", myCell.getStateName());
    myCell.changeState(2);
    assertEquals("burn", myCell.getStateName());
  }


}
