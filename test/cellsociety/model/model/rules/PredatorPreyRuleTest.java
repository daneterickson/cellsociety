package cellsociety.model.model.rules;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import cellsociety.model.model.utils.EdgePolicies.FiniteEdgePolicy;
import cellsociety.model.model.utils.NeighborFinders.NeighborFinder;
import cellsociety.model.model.utils.NeighborFinders.SquareEdges;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PredatorPreyRuleTest {

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
    myStates = new int[][]{{0, 2, 0, 0, 2},
        {2, 0, 2, 2, 0},
        {0, 2, 1, 0, 2},
        {1, 0, 0, 1, 0},
        {1, 1, 1, 1, 0}};
    numRows = 5;
    numCols = 5;
    String type = "PredatorPrey";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    int numUpdates = 5;
    int fishReproduction = 3;
    int sharkReproduction = 3;
    int sharkEnergy = 1;
    int energyGain = 1;
    ArrayList<Integer> newUpdates = new ArrayList();
    ArrayList<Integer> sharkAttacks = new ArrayList();
    rule = new PredatorPreyRule(myGrid, numCols, numUpdates, fishReproduction, sharkReproduction,
        sharkEnergy, energyGain, newUpdates,
        sharkAttacks);
  }

  @Test
  void testEmptyCell() {
    int row = 3;
    int col = 1;
    nearby = nf.getNeighbors(row,col,myGrid);
    int actual = rule.determineState(row, col, 0, nearby, myGrid, edgePolicy);
    assertEquals(0, actual, "empty cell should remain empty(0). got: " + actual);
  }
  @Test
  void testFishMoving() {
    int row = 3;
    int col = 1;
    nearby = nf.getNeighbors(row,col,myGrid);
    int actual = rule.determineState(row, col, 1, nearby, myGrid, edgePolicy);
    assertEquals(0, actual, "fish should've moved. got: " + actual);
  }
  @Test
  void testFishCantMove() {
    int row = 2;
    int col = 3;
    nearby = nf.getNeighbors(row,col,myGrid);
    int actual = rule.determineState(row, col, 1, nearby, myGrid, edgePolicy);
    assertEquals(1, actual, "fish shouldn't be able to move. got: " + actual);
  }
  @Test
  void testSharkMove() {
    int row = 3;
    int col = 1;
    nearby = nf.getNeighbors(row,col,myGrid);
    int actual = rule.determineState(row, col, 2, nearby, myGrid, edgePolicy);
    assertEquals(0, actual, "shark should move. got: " + actual);
  }
  @Test
  void testSharkCantMoveEdge() {
    int row = 1;
    int col = 4;
    nearby = nf.getNeighbors(row,col,myGrid);
    int actual = rule.determineState(row, col, 2, nearby, myGrid, edgePolicy);
    assertEquals(2, actual, "shark shouldn't be able to move. got: " + actual);
  }
  @Test
  void testSharkEating() {
    int row = 2;
    int col = 3;
    nearby = nf.getNeighbors(row,col,myGrid);
    int actual = rule.determineState(row, col, 2, nearby, myGrid, edgePolicy);
    assertEquals(0, actual, "shark should move. got: " + actual);
  }
  @Test
  void testSharkCantMove() {
    int row = 1;
    int col = 1;
    nearby = nf.getNeighbors(row,col,myGrid);
    int actual = rule.determineState(row, col, 2, nearby, myGrid, edgePolicy);
    assertEquals(2, actual, "shark shouldn't be able to move. got: " + actual);

  }
}
