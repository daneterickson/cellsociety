package cellsociety.model.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SegregationTest {

  private Model myModel;
  private Grid myGrid;
  private int[][] myStates;
  private int numRows;
  private int numCols;
  private Controller myController;
  private String myStartColors;
  private String myParameters;

  @BeforeEach
  void setUp() {
    myStates = new int[][]{{1, 1, 1, 1, 1},
        {1, 2, 1, 1, 1},
        {1, 1, 1, 1, 1},
        {1, 1, 1, 0, 1},
        {1, 1, 1, 1, 1}};

    numRows = 5;
    numCols = 5;
    String type = "Segregation";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    myModel = new SegregationModel(myController, myGrid);
  }

  @Test
  void test1vMany()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    try {
      myModel.updateModel(myGrid);
    } catch (NullPointerException e) {

    }
    int state;
    for (int row = 0; row < numRows; row++) {
      System.out.println();
      for (int col = 0; col < numCols; col++) {
        state = myGrid.getCellStateNumber(row, col);
        if (row == 1 && col == 1){
          assertEquals(0, state, row + ", " + col + "should be 0. got: " + state);

        }else if (row == 3 && col == 3){
          assertEquals(2, state, row + ", " + col + "should be 2. got: " + state);

        }else{
          assertEquals(1, state, row + ", " + col + "should be 1. got: " + state);
        }
        System.out.print(" " + myGrid.getCellStateNumber(row, col));
      }
    }
  }
}
