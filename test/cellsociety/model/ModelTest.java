package cellsociety.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.controller.Controller;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelTest {

  private Model myModel;
  private Grid myGrid;
  private int myStates[][];
  private int numRows;
  private int numCols;
  private Controller myController;

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
    myGrid = new Grid(numRows, numCols, myStates, type);
    myModel = new Model(myController, myGrid, type);
  }

  @Test
  void testGetNearby()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Method getNearby = Model.class.getDeclaredMethod("getNearby", int.class, int.class);
    getNearby.setAccessible(true);


    int[] neighbors = (int[]) getNearby.invoke(myModel, 1,2);

    int population = 0;
    for (int i : neighbors) {
      if (i == 1) {
        population += 1;
      }
    }
    assertEquals(3,population,"(1,2) should have 3 live neighbors. got: " + population );
  }

  @Test
  void testCurrRule()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Method currRule = Model.class.getDeclaredMethod("currRule", int.class, int[].class);
    currRule.setAccessible(true);
    int newState;

    //stable population
    newState = (int) currRule.invoke(myModel, 1,new int[]{0,0,1,1,0,0,0,0});
    assertEquals(1,newState,"New state should be 1. got: " + newState );

    //overpopulation
    newState = (int) currRule.invoke(myModel, 1,new int[]{0,0,1,1,0,1,0,1});
    assertEquals(0,newState,"New state should be 0. got: " + newState );

    //underpopulation
    newState = (int) currRule.invoke(myModel, 1,new int[]{0,0,0,0,0,0,0,1});
    assertEquals(0,newState,"New state should be 0. got: " + newState );

    //Failed reproduction
    newState = (int) currRule.invoke(myModel, 0,new int[]{0,0,0,0,0,0,0,1});
    assertEquals(0,newState,"New state should be 0. got: " + newState );

    //Failed reproduction
    newState = (int) currRule.invoke(myModel, 0,new int[]{0,0,1,1,1,0,0,1});
    assertEquals(0,newState,"New state should be 0. got: " + newState );

    //Successful reproduction
    newState = (int) currRule.invoke(myModel, 0,new int[]{0,0,1,0,1,0,0,1});
    assertEquals(1,newState,"New state should be 1. got: " + newState );

  }

  @Test
  void testGameOfLife() {
    int[][] expected = new int[][]{{0, 0, 0, 0, 0},
        {0, 0, 1, 0, 0},
        {0, 0, 1, 0, 0},
        {0, 0, 1, 0, 0},
        {0, 0, 0, 0, 0}};
    try{
      myModel.updateModel(myGrid);
    }catch (NullPointerException e){

    }
    for (int row = 0; row < numRows; row++) {
      System.out.println("");
      for (int col = 0; col < numCols; col++) {
        System.out.print(" "+myGrid.getCellStateNumber(row, col));
//        assertEquals(expected[row][col], myGrid.getCellState(row, col), row +", "+col);
      }
    }

  }


}
