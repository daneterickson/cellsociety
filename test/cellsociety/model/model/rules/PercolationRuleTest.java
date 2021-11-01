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

public class PercolationRuleTest {

  private Rule rule = new PercolationRule();
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
        {2, 0, 0, 0, 0},
        {1, 2, 0, 0, 0}};
    numRows = 5;
    numCols = 5;
    String type = "Percolation";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
  }

  @Test
  void testPercolate() {
    int row = 3;
    int col = 1;
    nearby = nf.getNeighbors(row,col,myGrid);
    int actual = rule.determineState(0,0,0,nearby,myGrid,edgePolicy);
    assertEquals(1,actual);
    actual = rule.determineState(0,0,1,nearby,myGrid,edgePolicy);
    assertEquals(1,actual);
  }

  @Test
  void testNoPercolate() {
    int row = 3;
    int col = 2;
    nearby = nf.getNeighbors(row,col,myGrid);
    int actual = rule.determineState(0,0,0,nearby,myGrid,edgePolicy);
    assertEquals(0,actual);
    actual = rule.determineState(0,0,1,nearby,myGrid,edgePolicy);
    assertEquals(1,actual);
  }
}
