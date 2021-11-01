package cellsociety.model.model.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import cellsociety.model.model.utils.EdgePolicies.FiniteEdgePolicy;
import cellsociety.model.model.utils.NeighborFinders.NeighborFinder;
import cellsociety.model.model.utils.NeighborFinders.SquareComplete;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SegregationRuleTest {

  private Rule rule;
  private ArrayList<Integer> empties = new ArrayList<>();
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
        {1, 1, 1, 0, 0},
        {1, 0, 1, 0, 0},
        {1, 1, 1, 0, 0}};
    numRows = 5;
    numCols = 5;
    String type = "Segregation";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    empties.add(5);
    rule = new SegregationRule(0.5,5,empties);
  }

  @Test
  void testRemainEmpty() {
    int row = 3;
    int col = 1;
    nearby = nf.getNeighbors(row,col,myGrid);
    int actual = rule.determineState(row,col,0,nearby,myGrid,edgePolicy);
    assertEquals(0,actual);
  }

  @Test
  void testRemain1() {
    int row = 3;
    int col = 1;
    nearby = nf.getNeighbors(row,col,myGrid);
    int actual = rule.determineState(row,col,1,nearby,myGrid,edgePolicy);
    assertEquals(1,actual);
  }

  @Test
  void testMoveSpots() {
    int row = 3;
    int col = 1;
    nearby = nf.getNeighbors(row,col,myGrid);
    int actual = rule.determineState(row,col,2,nearby,myGrid,edgePolicy);
    assertEquals(0,actual);
  }

}
