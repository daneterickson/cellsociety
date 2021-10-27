package cellsociety.model.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PercolationTest {

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
  void testGetNearby()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Method getNearby = Model.class.getDeclaredMethod("getNearby", int.class, int.class);
    getNearby.setAccessible(true);

    ArrayList<Integer> neighbors = (ArrayList<Integer>) getNearby.invoke(myModel, 2, 2);

    int burning = 0;
    int tree = 0;
    for (int i : neighbors) {
      if (i == 1) {
        tree += 1;
      } else if (i == 2) {
        burning += 1;
      }
    }
    assertEquals(1, burning, "(2,2) should have 1 burning neighbors. got: " + burning);
    assertEquals(2, tree, "(2,2) should have 2 tree neighbors. got: " + tree);
  }

  @Test
  void testCurrRule()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Method currRule = Model.class.getDeclaredMethod("currRule", int.class, int.class, int.class,
        List.class);
    currRule.setAccessible(true);
//    No test here bc can't test random
//    int newState;
//
//    //stable population
//    newState = (int) currRule.invoke(myModel, 1,new int[]{0,0,1,1,0,0,0,0});
//    assertEquals(1,newState,"New state should be 1. got: " + newState );

  }

  @Test
  void testGameOfLife() {
//    no test here bc can't test random
//    int[][] expected = new int[][]{{0, 0, 0, 0, 0},
//        {0, 0, 1, 0, 0},
//        {0, 0, 1, 0, 0},
//        {0, 0, 1, 0, 0},
//        {0, 0, 0, 0, 0}};
//    try{
//      myModel.updateModel(myGrid);
//    }catch (NullPointerException e){
//
//    }
//    for (int row = 0; row < numRows; row++) {
//      System.out.println("");
//      for (int col = 0; col < numCols; col++) {
//        System.out.print(" "+myGrid.getCellState(row, col));
////        assertEquals(expected[row][col], myGrid.getCellState(row, col), row +", "+col);
//      }
//    }

  }


}
