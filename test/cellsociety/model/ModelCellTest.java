package cellsociety.model;
import static org.junit.jupiter.api.Assertions.*;

import cellsociety.model.cell.GameOfLifeCell;
import cellsociety.model.cell.ModelCell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelCellTest {

  private ModelCell myCell;

  @BeforeEach
  void setUp () {
    myCell = new GameOfLifeCell(0,0,0);
  }

  @Test
  void testChangeState () {
    myCell.changeState(1);
    assertEquals(1, myCell.getStateNumber());
  }

  @Test
  void testSetColor () {
    assertEquals("c0c0c0", myCell.getStateColor());
  }

  @Test
  void testChangeColor () {
    myCell.changeState(1);
    assertEquals("0000ff", myCell.getStateColor());
  }

  @Test
  void testSetName () {
    assertEquals("Dead", myCell.getStateName());
  }

  @Test
  void testChangeName () {
    myCell.changeState(1);
    assertEquals("Alive", myCell.getStateName());
  }


}
