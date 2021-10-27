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

public class PredatorPreyTest {

  private Model myModel;
  private Grid myGrid;
  private int[][] myStates;
  private int numRows;
  private int numCols;
  private Controller myController;
  private String myParameters;
  private String myStartColors;

  @BeforeEach
  void setUp() {
    myStates = new int[][]{{0, 0, 0, 0, 0},
        {0, 2, 1, 2, 0},
        {0, 0, 1, 2, 0},
        {0, 1, 1, 1, 0},
        {0, 0, 0, 0, 0}};

    numRows = 5;
    numCols = 5;
    String type = "PredatorPrey";
    //fishrepro=3, sharkrepro=3, sharkenergy=1
    myParameters = "3,3,1";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    myModel = new PredatorPreyModel(myController, myGrid);
  }

  @Test
  void testGetNearby()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Method getNearby = Model.class.getDeclaredMethod("getNearby", int.class, int.class);
    getNearby.setAccessible(true);

    ArrayList<Integer> neighbors = (ArrayList<Integer>) getNearby.invoke(myModel, 2, 2);

    int empty = 0;
    int fish = 0;
    for (int i : neighbors) {
      if (i == 0) {
        empty += 1;
      } else if (i == 1) {
        fish += 1;
      }
    }
    assertEquals(1, empty, "(2,2) should have 1 empty neighbors. got: " + empty);
    assertEquals(2, fish, "(2,2) should have 2 fish neighbors. got: " + fish);
  }

  @Test
  void testCurrRuleMovement()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Method currRule = Model.class.getDeclaredMethod("currRule", int.class, int.class, int.class,
        List.class);
    currRule.setAccessible(true);
    int ret;
    List<Integer> list;
    int currRow = 2;
    int currCol = 2;
    //empty cell
    list = Arrays.asList(0, 0, 0, 1);
    ret = (int) currRule.invoke(myModel, currRow, currCol, 0, list);
    assertEquals(0, ret, "empty cell should remain empty(0). got: " + ret);

    //fish moving
    list = Arrays.asList(0, 2, 1, 1);
    ret = (int) currRule.invoke(myModel, currRow, currCol, 1, list);
    assertEquals(0, ret, "fish should've moved. got: " + ret);

    //fish cant move
    list = Arrays.asList(1, 2, 1, 1);
    ret = (int) currRule.invoke(myModel, currRow, currCol, 1, list);
    assertEquals(1, ret, "fish shouldn't be able to move. got: " + ret);

    //shark moving
    list = Arrays.asList(0, 2, 2, 2);
    ret = (int) currRule.invoke(myModel, currRow, currCol, 2, list);
    assertEquals(0, ret, "shark should move. got: " + ret);

    //shark eating
    list = Arrays.asList(1, 2, 2, 2);
    ret = (int) currRule.invoke(myModel, currRow, currCol, 2, list);
    assertEquals(0, ret, "shark should move. got: " + ret);

    //shark cant move
    list = Arrays.asList(2, 2, 2, 2);
    ret = (int) currRule.invoke(myModel, currRow, currCol, 2, list);
    assertEquals(2, ret, "shark shouldn't be able to move. got: " + ret);

    //shark cant move edge
    list = Arrays.asList(0, 2, 2, 2);
    ret = (int) currRule.invoke(myModel, 0, currCol, 2, list);
    assertEquals(2, ret, "shark shouldn't be able to move. got: " + ret);

  }

  @Test
  void testCurrRuleReproducing()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Method currRule = Model.class.getDeclaredMethod("currRule", int.class, int.class, int.class,
        List.class);
    currRule.setAccessible(true);

    int ret;
    List<Integer> list;
    int currRow = 2;
    int currCol = 2;

    list = Arrays.asList(0, 0, 0, 1);
    ret = (int) currRule.invoke(myModel, currRow, currCol, 0, list);
    assertEquals(0, ret, "empty cell should remain empty(0). got: " + ret);

    //fish moving
    list = Arrays.asList(0, 2, 1, 1);
    ret = (int) currRule.invoke(myModel, currRow, currCol, 1, list);
    assertEquals(0, ret, "fish should've moved. got: " + ret);

    //fish cant move
    list = Arrays.asList(1, 2, 1, 1);
    ret = (int) currRule.invoke(myModel, currRow, currCol, 1, list);
    assertEquals(1, ret, "fish shouldn't be able to move. got: " + ret);

    //shark moving
    list = Arrays.asList(0, 2, 2, 2);
    ret = (int) currRule.invoke(myModel, currRow, currCol, 2, list);
    assertEquals(0, ret, "shark should move. got: " + ret);

    //shark eating
    list = Arrays.asList(1, 2, 2, 2);
    ret = (int) currRule.invoke(myModel, currRow, currCol, 2, list);
    assertEquals(0, ret, "shark should move. got: " + ret);

    //shark cant move
    list = Arrays.asList(2, 2, 2, 2);
    ret = (int) currRule.invoke(myModel, currRow, currCol, 2, list);
    assertEquals(2, ret, "shark shouldn't be able to move. got: " + ret);

    //shark cant move edge
    list = Arrays.asList(0, 2, 2, 2);
    ret = (int) currRule.invoke(myModel, 0, currCol, 2, list);
    assertEquals(2, ret, "shark shouldn't be able to move. got: " + ret);

  }



}
