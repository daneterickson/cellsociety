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
    myStates = new int[][]{{0, 0, 0, 0, 0},
        {0, 2, 1, 2, 0},
        {0, 0, 1, 2, 0},
        {0, 1, 1, 1, 0},
        {0, 0, 0, 0, 0}};

    numRows = 5;
    numCols = 5;
    String type = "Segregation";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    myModel = new SegregationModel(myController, myGrid);
  }

  @Test
  void testGetNearby()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Method getNearby = Model.class.getDeclaredMethod("getNearby", int.class, int.class);
    getNearby.setAccessible(true);

    ArrayList<Integer> neighbors = (ArrayList<Integer>) getNearby.invoke(myModel, 2, 2);

    int race1 = 0;
    int race2 = 0;
    for (int i : neighbors) {
      if (i == 1) {
        race1 += 1;
      } else if (i == 2) {
        race2 += 1;
      }
    }
    assertEquals(4, race1, "(2,2) should have 4 race1 neighbors. got: " + race1);
    assertEquals(3, race2, "(2,2) should have 3 race2 neighbors. got: " + race2);
  }

  @Test
  void testCurrRule()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Method currRule = Model.class.getDeclaredMethod("currRule", int.class, int.class, int.class,
        List.class);
    currRule.setAccessible(true);
    int ret;
    List<Integer> list;
    int currRow = 2;
    int currCol = 2;

    //empty cell
    list = Arrays.asList(0, 0, 0, 1, 2, 0, 1, 0);
    ret = (int) currRule.invoke(myModel, currRow, currCol, 0, list);
    assertEquals(0, ret, "empty cell should remain empty(0). got: " + ret);

    //Over threshold
    list = Arrays.asList(0, 0, 0, 1, 2, 1, 1, 0);
    ret = (int) currRule.invoke(myModel, currRow, currCol, 1, list);
    assertEquals(1, ret, "Cell state should stay the same. got: " + ret);

    //Under threshold
    list = Arrays.asList(0, 0, 0, 1, 2, 2, 2, 0);
    ret = (int) currRule.invoke(myModel, currRow, currCol, 1, list);
    assertEquals(0, ret, "Cell should be vacated. got: " + ret);
  }
}
