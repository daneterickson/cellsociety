package cellsociety.model;
import static org.junit.jupiter.api.Assertions.*;

import cellsociety.model.ModelCell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelCellTest {

  private ModelCell myCell;

  @BeforeEach
  void setUp () {
    myCell = new ModelCell(0,0,0);
  }

  @Test
  void testChangeState () {
    myCell.changeState(1);
    assertEquals(1, myCell.getState());
  }



}
