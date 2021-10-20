package cellsociety.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GridTest {

  private Grid myGrid;
  private int myStates[][];
  private int numRows;
  private int numCols;

  @BeforeEach
  void setUp () {
    myStates = new int[][]{{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 1, 1, 1, 0}, {0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0}};
    numRows = 5;
    numCols = 5;
    String type = "GameOfLife";
    myGrid = new Grid(numRows, numCols, myStates, type);
  }

  @Test
  void testSetStartStates () {
    for (int row=0; row<numRows; row++) {
      for (int col=0; col<numCols; col++) {
        assertEquals(myStates[row][col], myGrid.getCellState(row,col));
      }
    }
  }

//  @Test
//  void testSetCell () { // not sure how to test this without making setCell() public
//  }

}
