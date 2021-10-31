package cellsociety.model.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import cellsociety.model.model.utils.EdgePolicies.FiniteEdgePolicy;
import cellsociety.model.model.utils.NeighborFinders.NeighborFinder;
import cellsociety.model.model.utils.NeighborFinders.SquareComplete;
import cellsociety.model.model.utils.NeighborFinders.SquareCorners;
import cellsociety.model.model.utils.NeighborFinders.SquareEdges;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NeighborFinderTest {
  private Grid myGrid;
  private int myStates[][];
  private int numRows;
  private int numCols;
  private String myStartColors;
  private String myParameters;
  private NeighborFinder myNeighborFinder;
  private EdgePolicies edgePolicy;
  private int row;
  private int col;

  @BeforeEach
  void setUp() {
    edgePolicy = new FiniteEdgePolicy();
    myStates = new int[][]{{0, 0, 0, 0, 0},
        {0, 2, 1, 2, 0},
        {0, 0, 1, 2, 0},
        {0, 1, 1, 1, 0},
        {0, 0, 0, 0, 0}};
    numRows = 5;
    numCols = 5;
    String type = "SpreadingOfFire";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    row = 2;
    col = 2;
  }

  @Test
  void testSquareComplete(){
    myNeighborFinder = new SquareComplete(edgePolicy);
    List<Integer> neighbors = myNeighborFinder.getNeighbors(row,col,myGrid);

    int population = 0;
    for (int i : neighbors) {
      if (i != 0) {
        population += 1;
      }
    }
    int empty = neighbors.size()-population;
    assertEquals(7, population, "(2,2) should have 7 neighbors. got: " + population);
    assertEquals(1, empty, "(2,2) should have 1 empty. got: " + empty);

  }

  @Test
  void testSquareEdges(){
    myNeighborFinder = new SquareEdges(edgePolicy);
    List<Integer> neighbors = myNeighborFinder.getNeighbors(row,col,myGrid);

    int population = 0;
    for (int i : neighbors) {
      if (i != 0) {
        population += 1;
      }
    }
    int empty = neighbors.size()-population;
    assertEquals(3, population, "(2,2) should have 3 neighbors. got: " + population);
    assertEquals(1, empty, "(2,2) should have 1 empty. got: " + empty);

  }

  @Test
  void testSquareCorners(){
    myNeighborFinder = new SquareCorners(edgePolicy);
    List<Integer> neighbors = myNeighborFinder.getNeighbors(row,col,myGrid);

    int population = 0;
    for (int i : neighbors) {
      if (i != 0) {
        population += 1;
      }
    }
    int empty = neighbors.size()-population;
    assertEquals(4, population, "(2,2) should have 4 neighbors. got: " + population);
    assertEquals(0, empty, "(2,2) should have 0 empty. got: " + empty);

    myStates = new int[][]{{0, 0, 0, 0, 0},
        {0, 2, 1, 0, 0},
        {0, 2, 1, 2, 0},
        {0, 1, 1, 1, 0},
        {0, 0, 0, 0, 0}};
    numRows = 5;
    numCols = 5;
    String type = "SpreadingOfFire";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);

    neighbors = myNeighborFinder.getNeighbors(row,col,myGrid);

    population = 0;
    for (int i : neighbors) {
      if (i != 0) {
        population += 1;
      }
    }
    empty = neighbors.size()-population;
    assertEquals(3, population, "(2,2) should have 3 neighbors. got: " + population);
    assertEquals(1, empty, "(2,2) should have 1 empty. got: " + empty);
  }


}
