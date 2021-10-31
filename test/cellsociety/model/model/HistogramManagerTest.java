package cellsociety.model.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HistogramManagerTest {

  private Model myModel;
  private Grid myGrid;
  private int myStates[][];
  private int numRows;
  private int numCols;
  private String myStartColors;
  private String myParameters;
  private EdgePolicies myEdgePolicies;
  private int row;
  private int col;
  private String type;
  private Controller myController;
//  private HistogramManager histogram;

  @BeforeEach
  void setUp() {
    myStates = new int[][]{{1, 1, 1, 1, 1},
        {0, 0, 0, 0, 1},
        {0, 0, 1, 0, 1},
        {0, 0, 0, 1, 1},
        {0, 0, 0, 0, 0}};
    numRows = 5;
    numCols = 5;
    type = "GameOfLife";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    myModel = new GameOfLifeModel(myController,myGrid);
//    myController = new Controller(new Stage());
//    histogram = new HistogramManager(myGrid);

  }

  @Test
  void testInit() {
    Set<String> expected;
    StringBuilder setString = new StringBuilder();

    type = "GameOfLife";
    HashMap test = myModel.getHistogram();
    Set<String> golKeys = test.keySet();
    for (String key: golKeys){
      setString.append(key + " ");
    }
    expected = Set.of("DEAD_STATE", "ALIVE_STATE");
    assertEquals(expected, golKeys, "game of life keys should be dead and alive state. got: " + setString.toString());
  }

  @Test
  void testAddRound() {
    Set<String> expected;
    StringBuilder setString = new StringBuilder();
    HashMap<String, Integer> test;

    myStates = new int[][]{{0, 1, 1, 1, 1},
        {2, 0, 0, 0, 1},
        {2, 0, 0, 0, 1},
        {2, 0, 0, 0, 1},
        {2, 2, 2, 2, 0}};
    numRows = 5;
    numCols = 5;
    type = "SpreadingOfFire";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    myModel = new SpreadingOfFireModel(myController,myGrid);

    test = myModel.getHistogram();
    Set<String> sofKeys = test.keySet();
    for (String key: sofKeys){
      setString.append(key + " ");
    }

    expected = Set.of("BURN_STATE","TREE_STATE","EMPTY_STATE");
    assertEquals(expected, sofKeys, "spreading of fire keys should be burn and tree state. got: " + setString.toString());
    int burning = test.get("BURN_STATE");
    int tree =  test.get("TREE_STATE");
    assertEquals(7,burning,"should start of with 7 burning. got: " + burning);
    assertEquals(7,tree,"should start of with 7 tree. got: " + tree);

    try {
      myModel.updateModel(myGrid);
    }catch (NullPointerException e){

    }

    test = myModel.getHistogram();
    burning =  test.get("BURN_STATE");
    tree =  test.get("TREE_STATE");
    assertEquals(0,burning,"should have 0 burning. got: " + burning);
    assertEquals(7,tree,"should still have 7 tree. got: " + tree);
  }
}
