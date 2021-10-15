package cellsociety;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.model.Grid;
import cellsociety.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelTest {

  private Model myModel;
  private Grid myGrid;
  private int myStates[][];
  private int numRows;
  private int numCols;


  @BeforeEach
  void setUp() {
    myStates = new int[][]{{0, 0, 0, 0, 0},
                          {0, 0, 0, 0, 0},
                          {0, 1, 1, 1, 0},
                          {0, 0, 0, 0, 0},
                          {0, 0, 0, 0, 0}};

    numRows = 5;
    numCols = 5;
    myGrid = new Grid(numRows, numCols, myStates);
    myModel = new Model(myGrid);
  }

  @Test
  void testGameOfLife() {
    int[][] expected = new int[][]{{0, 0, 0, 0, 0},
        {0, 0, 1, 0, 0},
        {0, 0, 1, 0, 0},
        {0, 0, 1, 0, 0},
        {0, 0, 0, 0, 0}};
    myModel.iterateGrid();
    Grid returnedGrid = myModel.getNewGrid();
    for (int row = 0; row < numRows; row++) {
      System.out.println(returnedGrid.getCellState(row, 0) +
          " " + returnedGrid.getCellState(row, 1) + " " + returnedGrid.getCellState(row, 2) + " "
          + returnedGrid.getCellState(row, 3) + " " + returnedGrid.getCellState(row, 4));
      for (int col = 0; col < numCols; col++) {
        assertEquals(expected[row][col], returnedGrid.getCellState(row, col));
      }
    }
  }


}
