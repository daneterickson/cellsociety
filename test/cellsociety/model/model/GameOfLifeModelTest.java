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

public class GameOfLifeModelTest {

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
    myStates = new int[][]{{0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0},
        {0, 1, 1, 1, 0},
        {0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0}};

    numRows = 5;
    numCols = 5;
    String type = "GameOfLife";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    myModel = new GameOfLifeModel(myController, myGrid);
  }

  @Test
  void testCurrRule()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Method currRule = Model.class.getDeclaredMethod("currRule", int.class, int.class, int.class,
        List.class);
    currRule.setAccessible(true);
    int newState;
    List<Integer> list;
    int currRow = 2;
    int currCol = 2;
    //stable population
    list = Arrays.asList(0, 0, 1, 1, 0, 0, 0, 0);
    newState = (int) currRule.invoke(myModel, currRow, currCol, 1, list);
    assertEquals(1, newState, "New state should be 1. got: " + newState);

    //overpopulation
    list = Arrays.asList(0, 0, 1, 1, 0, 1, 0, 1);
    newState = (int) currRule.invoke(myModel, currRow, currCol, 1, list);
    assertEquals(0, newState, "New state should be 0. got: " + newState);

    //underpopulation
    list = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 1);
    newState = (int) currRule.invoke(myModel, currRow, currCol, 1, list);
    assertEquals(0, newState, "New state should be 0. got: " + newState);

    //Failed reproduction
    list = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 1);
    newState = (int) currRule.invoke(myModel, currRow, currCol, 0, list);
    assertEquals(0, newState, "New state should be 0. got: " + newState);

    //Failed reproduction
    list = Arrays.asList(0, 0, 1, 1, 1, 0, 0, 1);
    newState = (int) currRule.invoke(myModel, currRow, currCol, 0, list);
    assertEquals(0, newState, "New state should be 0. got: " + newState);

    //Successful reproduction
    list = Arrays.asList(0, 0, 1, 0, 1, 0, 0, 1);
    newState = (int) currRule.invoke(myModel, currRow, currCol, 0, list);
    assertEquals(1, newState, "New state should be 1. got: " + newState);

  }

  @Test
  void testGameOfLife() {
    int[][] expected = new int[][]{{0, 0, 0, 0, 0},
        {0, 0, 1, 0, 0},
        {0, 0, 1, 0, 0},
        {0, 0, 1, 0, 0},
        {0, 0, 0, 0, 0}};
    try {
      myModel.updateModel(myGrid);
    } catch (NullPointerException e) {

    }
    for (int row = 0; row < numRows; row++) {
      System.out.println();
      for (int col = 0; col < numCols; col++) {
        System.out.print(" " + myGrid.getCellStateNumber(row, col));
        assertEquals(expected[row][col], myGrid.getCellStateNumber(row, col), row +", "+col);
      }
    }

  }


}
