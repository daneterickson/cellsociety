package cellsociety.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.model.cell.GameOfLifeCell;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GridTest {

  private Grid myGrid;
  private int myStates[][];
  private ArrayList<ArrayList<Integer>> myStatesList;
  private int numRows;
  private int numCols;

  @BeforeEach
  void setUp() {
    myStates = new int[][]{{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 1, 1, 1, 0}, {0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0}};
    myStatesList = new ArrayList<>();
    numRows = 5;
    numCols = 5;
    String type = "GameOfLife";
    myGrid = new Grid(numRows, numCols, myStates, type);

  }

  @Test
  void testSetStartStates() {
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numCols; col++) {
        assertEquals(myStates[row][col], myGrid.getCellStateNumber(row, col));
      }
    }
  }

  @Test
  void testCellType() {
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numCols; col++) {
        assertEquals(GameOfLifeCell.class, myGrid.getCell(row, col).getClass());
      }
    }
  }

  @Test
  void testChangeState() {
    myGrid.updateCell(0, 0, 1);
    assertEquals(1, myGrid.getCellStateNumber(0, 0));
  }

//  @Test
//  void testSetCell () { // not sure how to test this without making setCell() public
//  }

}
