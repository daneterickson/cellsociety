package cellsociety.model.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpreadingOfFireTest {

  private Model myModel;
  private Grid myGrid;
  private int myStates[][];
  private int numRows;
  private int numCols;
  private Controller myController;
  private String myStartColors;
  private String myParameters;

  @BeforeEach
  void setUp() {
    myStates = new int[][]{{0, 0, 0, 0, 0},
        {0, 2, 1, 2, 0},
        {0, 0, 1, 2, 0},
        {0, 1, 1, 1, 0},
        {0, 0, 0, 0, 0}};

    numRows = 5;
    numCols = 5;
    String type = "SpreadingOfFire";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    myModel = new SpreadingOfFireModel(myController, myGrid);
  }



  @Test
  void testCurrRule()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Method currRule = Model.class.getDeclaredMethod("currRule", int.class, int.class, int.class,
        List.class);
    currRule.setAccessible(true);
//    Can't test random...
//    int[][] expected = new int[][]{{1, 1, 1, 1, 1},
//        {1, 1, 1, 1, 1},
//        {1, 1, 1, 1, 1},
//        {1, 1, 1, 1, 1},
//        {1, 1, 1, 1, 1}};
//    try {
//      myModel.updateModel(myGrid);
//    } catch (NullPointerException e) {
//
//    }
//    for (int row = 0; row < numRows; row++) {
//      System.out.println();
//      for (int col = 0; col < numCols; col++) {
//        System.out.print(" " + myGrid.getCellStateNumber(row, col));
////        assertEquals(expected[row][col], myGrid.getCellState(row, col), row +", "+col);
//      }
//    }
//
  }

}
