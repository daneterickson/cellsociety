package cellsociety.model.model.rules;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import cellsociety.model.exceptions.KeyNotFoundException;
import cellsociety.model.model.Model;
import cellsociety.model.model.PredatorPreyModel;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PredatorPreyRuleTest {

  private Rule myRule;
  private Grid myGrid;
  private int[][] myStates;
  private int numRows;
  private int numCols;
  private Controller myController;
  private String myParameters;
  private String myStartColors;
  private String type;

  @BeforeEach
  void setUp() {
    myStates = new int[][]{{0, 0, 0, 0, 0},
        {0, 2, 1, 2, 0},
        {0, 0, 1, 2, 0},
        {0, 1, 1, 1, 0},
        {0, 0, 0, 0, 0}};
    myParameters = "3,3,3,3";
    numRows = 5;
    numCols = 5;
    type = "PredatorPrey";
    //fishrepro=3, sharkrepro=3, sharkenergy=1
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    int numUpdates = 5;
    int fishReproduction = 3;
    int sharkReproduction = 3;
    int sharkEnergy = 1;
    int energyGain = 1;
    ArrayList<Integer> newUpdates = new ArrayList();
    ArrayList<Integer> sharkAttacks = new ArrayList();
    myRule = new PredatorPreyRule(myGrid, numCols, numUpdates, fishReproduction, sharkReproduction,
        sharkEnergy, energyGain, newUpdates,
        sharkAttacks);
  }

  @Test
  void testEmptyCell() {
    int ret;
    List<Integer> list;
    int currRow = 2;
    int currCol = 2;

    list = Arrays.asList(0, 0, 0, 1);
    ret = myRule.determineState(currRow, currCol, 0, list);
    assertEquals(0, ret, "empty cell should remain empty(0). got: " + ret);
  }
  @Test
  void testFishMoving() {
    int ret;
    List<Integer> list;
    int currRow = 2;
    int currCol = 2;
    //fish moving
    list = Arrays.asList(0, 2, 1, 1);
    ret = myRule.determineState(currRow, currCol, 1, list);
    assertEquals(0, ret, "fish should've moved. got: " + ret);
  }
  @Test
  void testFishCantMove() {
    int ret;
    List<Integer> list;
    int currRow = 2;
    int currCol = 2;
    //fish cant move
    list = Arrays.asList(1, 2, 1, 1);
    ret = myRule.determineState(currRow, currCol, 1, list);
    assertEquals(1, ret, "fish shouldn't be able to move. got: " + ret);
  }
  @Test
  void testSharkMove() {
    int ret;
    List<Integer> list;
    int currRow = 2;
    int currCol = 2;
    //shark moving
    list = Arrays.asList(0, 2, 2, 2);
    ret = myRule.determineState(currRow, currCol, 2, list);
    assertEquals(0, ret, "shark should move. got: " + ret);
  }
  @Test
  void testSharkCantMove() {
    int ret;
    List<Integer> list;
    int currRow = 2;
    int currCol = 2;

    //shark cant move
    list = Arrays.asList(2, 2, 2, 2);
    ret = myRule.determineState(currRow, currCol, 2, list);
    assertEquals(2, ret, "shark shouldn't be able to move. got: " + ret);
  }
  @Test
  void testSharkEating() {
    int ret;
    List<Integer> list;
    int currRow = 2;
    int currCol = 2;
    //shark eating
    list = Arrays.asList(1, 2, 2, 2);
    ret = myRule.determineState(currRow, currCol, 2, list);
    assertEquals(0, ret, "shark should move. got: " + ret);
  }
  @Test
  void testCantMoveEdge() {
    int ret;
    List<Integer> list;
    int currRow = 2;
    int currCol = 2;
    //shark cant move edge
    list = Arrays.asList(0, 2, 2, 2);
    ret = myRule.determineState( 0, currCol, 2, list);
    assertEquals(2, ret, "shark shouldn't be able to move. got: " + ret);

  }
}
