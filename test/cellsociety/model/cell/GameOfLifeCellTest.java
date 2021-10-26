package cellsociety.model.cell;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.model.cell.GameOfLifeCell;
import cellsociety.model.cell.ModelCell;
import java.util.Map;
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
    assertEquals(1, Integer.valueOf(myCell.getProperty("StateNumber")));
  }

  @Test
  void testSetColor() {
    assertEquals("c0c0c0", myCell.getProperty("StateColor"));
  }

  @Test
  void testChangeColor() {
    myCell.changeState(1);
    assertEquals("0000ff", myCell.getProperty("StateColor"));
  }

  @Test
  void testSetName() {
    assertEquals("dead", myCell.getProperty("StateName"));
  }

  @Test
  void testChangeName() {
    myCell.changeState(1);
    assertEquals("alive", myCell.getProperty("StateName"));
  }


}
