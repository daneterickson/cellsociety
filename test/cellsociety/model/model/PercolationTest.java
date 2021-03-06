package cellsociety.model.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
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
  private String type;

  @BeforeEach
  void setUp() {
    myStates = new int[][]{{0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0},
        {1, 2, 0, 0, 0}};

    numRows = 5;
    numCols = 5;
    type = "Percolation";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    myModel = new PercolationModel(myController, myGrid);
  }

  @Test
  void testFindEndEdge()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Method getEndEdge = myModel.getClass().getDeclaredMethod("getEndEdge");
    getEndEdge.setAccessible(true);
    String ret;

    myStates = new int[][]{{0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0},
        {1, 0, 0, 0, 0},
        {0, 0, 0, 0, 0},
        {0, 2, 0, 0, 0}};
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    myModel = new PercolationModel(myController, myGrid);
    ret = (String) getEndEdge.invoke(myModel);
    assertEquals("right", ret, "end edge should be right. got: " + ret);

    myStates = new int[][]{{0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0},
        {0, 2, 1, 0, 0}};
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    myModel = new PercolationModel(myController, myGrid);
    ret = (String) getEndEdge.invoke(myModel);
    assertEquals("top", ret, "end edge should be top. got: " + ret);
  }

  @Test
  void testEndGame()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Method checkEnd = myModel.getClass().getDeclaredMethod("checkEnd");
    checkEnd.setAccessible(true);
    boolean ret;

    myStates = new int[][]{{0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0},
        {1, 0, 0, 0, 0},
        {0, 0, 0, 0, 0},
        {0, 2, 0, 1, 0}};
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    myModel = new PercolationModel(myController, myGrid);
    ret = (boolean) checkEnd.invoke(myModel);
    assertEquals(false, ret, "game should not be over. got: " + ret);

    myStates = new int[][]{{1, 0, 0, 0, 0},
        {0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0},
        {0, 2, 1, 0, 0}};
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    myModel = new PercolationModel(myController, myGrid);
    ret = (boolean) checkEnd.invoke(myModel);
    assertEquals(true, ret, "game SHOULD be over. got: " + ret);
  }
}
