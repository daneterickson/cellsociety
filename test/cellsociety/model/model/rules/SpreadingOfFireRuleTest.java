package cellsociety.model.model.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import cellsociety.model.model.utils.EdgePolicies.FiniteEdgePolicy;
import cellsociety.model.model.utils.NeighborFinders.NeighborFinder;
import cellsociety.model.model.utils.NeighborFinders.SquareEdges;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpreadingOfFireRuleTest {

  private Rule rule;
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
    nf = new SquareEdges();
    myStates = new int[][]{{0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0},
        {2, 2, 2, 1, 1, 1},
        {2, 1, 2, 1, 1, 1},
        {2, 2, 2, 1, 1, 1}};
    numRows = 5;
    numCols = 6;
    String type = "SpreadingOfFire";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
  }

  @Test
  void testAllFireNoCatch() {
    rule = new SpreadingOfFireRule(0);
    int row = 3;
    int col = 1;
    nearby = nf.getNeighbors(row, col, myGrid);
    int actual = rule.determineState(row, col, 0, nearby, myGrid, edgePolicy);
    assertEquals(0, actual);
    actual = rule.determineState(row, col, 1, nearby, myGrid, edgePolicy);
    assertEquals(1, actual);
    actual = rule.determineState(row, col, 2, nearby, myGrid, edgePolicy);
    assertEquals(0, actual);
  }

  @Test
  void testAllFireCatch() {
    rule = new SpreadingOfFireRule(1);
    int row = 3;
    int col = 1;
    nearby = nf.getNeighbors(row, col, myGrid);
    int actual = rule.determineState(row, col, 0, nearby, myGrid, edgePolicy);
    assertEquals(0, actual);
    actual = rule.determineState(row, col, 1, nearby, myGrid, edgePolicy);
    assertEquals(2, actual);
    actual = rule.determineState(row, col, 2, nearby, myGrid, edgePolicy);
    assertEquals(0, actual);
  }

  @Test
  void testAllTrees() {
    rule = new SpreadingOfFireRule(0.5);
    int row = 3;
    int col = 4;
    nearby = nf.getNeighbors(row, col, myGrid);
    int actual = rule.determineState(row, col, 0, nearby, myGrid, edgePolicy);
    assertEquals(0, actual);
    actual = rule.determineState(row, col, 1, nearby, myGrid, edgePolicy);
    assertEquals(1, actual);
    actual = rule.determineState(row, col, 2, nearby, myGrid, edgePolicy);
    assertEquals(0, actual);
  }
}
