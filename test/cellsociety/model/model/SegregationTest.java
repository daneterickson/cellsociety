package cellsociety.model.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import cellsociety.model.model.Model;
import cellsociety.model.model.SegregationModel;
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
  private int myStates[][];
  private int numRows;
  private int numCols;
  private Controller myController;
  private Map<Integer, String> myStartColors;

  @BeforeEach
  void setUp() {
    myStates = new int[][]{{0, 0, 0, 0, 0},
        {0, 2, 1, 2, 0},
        {0, 0, 1, 2, 0},
        {0, 1, 1, 1, 0},
        {0, 0, 0, 0, 0}};

    numRows = 5;
    numCols = 5;
    myStartColors = new HashMap<>();
    String type = "Segregation";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, type);
    myModel = new SegregationModel(myController, myGrid);
  }

  @Test
  void testGetNearby()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Method getNearby = Model.class.getDeclaredMethod("getNearby", int.class, int.class);
    getNearby.setAccessible(true);

    ArrayList<Integer> neighbors = (ArrayList<Integer>) getNearby.invoke(myModel, 2,2);

    int race1 = 0;
    int race2 = 0;
    for (int i : neighbors) {
      if (i == 1) {
        race1 += 1;
      }else if (i == 2){
        race2 += 1;
      }
    }
    assertEquals(4,race1,"(2,2) should have 4 race1 neighbors. got: " + race1 );
    assertEquals(3,race2,"(2,2) should have 3 race2 neighbors. got: " + race2 );
  }

  @Test
  void testCurrRule()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Method currRule = Model.class.getDeclaredMethod("currRule", int.class, List.class);
    currRule.setAccessible(true);
    int ret;
    List<Integer> list;
    //empty cell
    list = Arrays.asList(new Integer[]{0,0,0,1,2,0,1,0});
    ret = (int) currRule.invoke(myModel, 0, list);
    assertEquals(0,ret, "empty cell should remain empty(0). got: "+ret);

    //Over threshold
    list = Arrays.asList(new Integer[]{0,0,0,1,2,1,1,0});
    ret = (int) currRule.invoke(myModel, 1, list);
    assertEquals(1,ret, "Cell state should stay the same. got: "+ret);

    //Under threshold
    list = Arrays.asList(new Integer[]{0,0,0,1,2,2,2,0});
    ret = (int) currRule.invoke(myModel, 1, list);
    assertEquals(0,ret, "Cell should be vacated. got: "+ret);
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
