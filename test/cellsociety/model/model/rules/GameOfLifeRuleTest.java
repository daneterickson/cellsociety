package cellsociety.model.model.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import cellsociety.model.model.utils.EdgePolicies.FiniteEdgePolicy;
import cellsociety.model.model.utils.NeighborFinders.NeighborFinder;
import cellsociety.model.model.utils.NeighborFinders.SquareComplete;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameOfLifeRuleTest {

  private Rule rule = new GameOfLifeRule();
  private Grid myGrid;
  private int myStates[][];
  private int numRows;
  private int numCols;
  private List nearby;
  private String myStartColors;
  private String myParameters;
  private NeighborFinder nf;
  private EdgePolicies edgePolicy;

  @BeforeEach
  void setUp() {
    edgePolicy = new FiniteEdgePolicy();
    nf = new SquareComplete(edgePolicy);
    myStates = new int[][]{{0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0},
        {1, 0, 0, 0, 0},
        {1, 1, 1, 1, 0}};
    numRows = 5;
    numCols = 5;
    String type = "GameOfLife";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
  }

  @Test
  void testAllDead() {
    int row = 3;
    int col = 4;
    nearby = nf.getNeighbors(row,col,myGrid);
    int actual = rule.determineState(row, col, 0, nearby, myGrid, edgePolicy);
    assertEquals(0, actual);
    actual = rule.determineState(row, col, 1, nearby, myGrid, edgePolicy);
    assertEquals(0, actual);

  }

  @Test
  void testStableCell() {
    int row = 3;
    int  col = 3;
    nearby = nf.getNeighbors(row,col,myGrid);
    int actual = rule.determineState(row, col, 0, nearby, myGrid, edgePolicy);
    assertEquals(0, actual);
    actual = rule.determineState(row, col, 1, nearby, myGrid, edgePolicy);
    assertEquals(1,actual);
  }

  @Test
  void testGenerateCell() {
    int row = 3;
    int col = 2;
    nearby = nf.getNeighbors(row,col,myGrid);
    int actual = rule.determineState(row, col, 0, nearby, myGrid, edgePolicy);
    assertEquals(1, actual);
    actual = rule.determineState(row, col, 1, nearby, myGrid, edgePolicy);
    assertEquals(1,actual);
  }

  @Test
  void testRemoveCell() {
    int row = 3;
    int col = 1;
    nearby = nf.getNeighbors(row,col,myGrid);
    int actual = rule.determineState(row, col, 0, nearby, myGrid, edgePolicy);
    assertEquals(0, actual);
    actual = rule.determineState(row, col, 1, nearby, myGrid, edgePolicy);
    assertEquals(0,actual);
  }
}
