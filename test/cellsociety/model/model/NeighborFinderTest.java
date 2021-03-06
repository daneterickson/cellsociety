package cellsociety.model.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import cellsociety.model.model.utils.EdgePolicies.FiniteEdgePolicy;
import cellsociety.model.model.utils.NeighborFinders.NeighborFinder;
import cellsociety.model.model.utils.NeighborFinders.SquareComplete;
import cellsociety.model.model.utils.NeighborFinders.SquareCorners;
import cellsociety.model.model.utils.NeighborFinders.SquareEdges;
import cellsociety.model.model.utils.NeighborFinders.TriangleComplete;
import cellsociety.model.model.utils.NeighborFinders.TriangleEdges;
import cellsociety.model.model.utils.NeighborFinders.TriangleVertices;
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
  private int currRow;
  private int currCol;

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
    currRow = 2;
    currCol = 2;
  }

  @Test
  void testSquareComplete() {
    myNeighborFinder = new SquareComplete();
    List<Integer> neighbors = myNeighborFinder.getNeighbors(currRow, currCol, myGrid);

    int population = 0;
    int row, col, state;
    for (int i = 0; i < neighbors.size(); i += 2) {
      row = neighbors.get(i);
      col = neighbors.get(i + 1);
      state = myGrid.getCellStateNumber(row, col);
      if (state != 0) {
        population += 1;
      }
    }
    int empty = (neighbors.size() / 2) - population;
    assertEquals(7, population, "(2,2) should have 7 neighbors. got: " + population);
    assertEquals(1, empty, "(2,2) should have 1 empty. got: " + empty);

  }

  @Test
  void testSquareEdges() {
    myNeighborFinder = new SquareEdges();
    List<Integer> neighbors = myNeighborFinder.getNeighbors(currRow, currCol, myGrid);

    int population = 0;
    int row, col, state;
    for (int i = 0; i < neighbors.size(); i += 2) {
      row = neighbors.get(i);
      col = neighbors.get(i + 1);
      state = myGrid.getCellStateNumber(row, col);
      if (state != 0) {
        population += 1;
      }
    }
    int empty = (neighbors.size() / 2) - population;
    assertEquals(3, population, "(2,2) should have 3 neighbors. got: " + population);
    assertEquals(1, empty, "(2,2) should have 1 empty. got: " + empty);

  }

  @Test
  void testSquareCorners() {
    myNeighborFinder = new SquareCorners();
    List<Integer> neighbors = myNeighborFinder.getNeighbors(currRow, currCol, myGrid);

    int population = 0;
    int row, col, state;
    for (int i = 0; i < neighbors.size(); i += 2) {
      row = neighbors.get(i);
      col = neighbors.get(i + 1);
      state = myGrid.getCellStateNumber(row, col);
      if (state != 0) {
        population += 1;
      }
    }
    int empty = (neighbors.size() / 2) - population;
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

    neighbors = myNeighborFinder.getNeighbors(currRow, currCol, myGrid);

    population = 0;
    for (int i = 0; i < neighbors.size(); i += 2) {
      row = neighbors.get(i);
      col = neighbors.get(i + 1);
      state = myGrid.getCellStateNumber(row, col);
      if (state != 0) {
        population += 1;
      }
    }
    empty = (neighbors.size() / 2) - population;
    assertEquals(3, population, "(2,2) should have 3 neighbors. got: " + population);
    assertEquals(1, empty, "(2,2) should have 1 empty. got: " + empty);
  }

  @Test
  void testTriangleComplete() {
    myStates = new int[][]{
        {2, 2, 1, 2, 0},
        {1, 0, 1, 2, 0},
        {0, 1, 1, 1, 0},
        {0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0}};
    numRows = 5;
    numCols = 5;
    String type = "SpreadingOfFire";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    myNeighborFinder = new TriangleComplete();

    currRow = 1;
    currCol = 2;
    List<Integer> neighbors = myNeighborFinder.getNeighbors(currRow, currCol, myGrid);
    System.out.println(neighbors);
    int burning = 0;
    int tree = 0;
    int row, col, state;
    for (int i = 0; i < neighbors.size(); i += 2) {
      row = neighbors.get(i);
      col = neighbors.get(i + 1);
      state = myGrid.getCellStateNumber(row, col);
      if (state == 1) {
        tree += 1;
      } else if (state == 2) {
        burning += 1;
      }
    }
    assertEquals(5, tree, "(2,1) should have 5 tree. got: " + tree);
    assertEquals(4, burning, "(2,1) should have 4 burning. got: " + burning);

  }

  @Test
  void testTriangleEdges() {
    myStates = new int[][]{
        {2, 2, 1, 2, 0},
        {1, 0, 1, 2, 0},
        {0, 1, 1, 1, 0},
        {0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0}};
    numRows = 5;
    numCols = 5;
    String type = "SpreadingOfFire";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    myNeighborFinder = new TriangleEdges();

    currRow = 1;
    currCol = 2;
    List<Integer> neighbors = myNeighborFinder.getNeighbors(currRow, currCol, myGrid);
    System.out.println(neighbors);
    int burning = 0;
    int tree = 0;
    int row, col, state;
    for (int i = 0; i < neighbors.size(); i += 2) {
      row = neighbors.get(i);
      col = neighbors.get(i + 1);
      state = myGrid.getCellStateNumber(row, col);
      if (state == 1) {
        tree += 1;
      } else if (state == 2) {
        burning += 1;
      }
    }
    assertEquals(1, tree, "(2,1) should have 1 tree. got: " + tree);
    assertEquals(1, burning, "(2,1) should have 1 burning. got: " + burning);
  }

  @Test
  void testTriangleVertices() {
    myStates = new int[][]{
        {2, 2, 1, 2, 0},
        {1, 0, 1, 2, 0},
        {0, 1, 1, 1, 0},
        {0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0}};
    numRows = 5;
    numCols = 5;
    String type = "SpreadingOfFire";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    myNeighborFinder = new TriangleVertices();

    currRow = 1;
    currCol = 2;
    List<Integer> neighbors = myNeighborFinder.getNeighbors(currRow, currCol, myGrid);
    System.out.println(neighbors);
    int burning = 0;
    int tree = 0;
    int row, col, state;
    for (int i = 0; i < neighbors.size(); i += 2) {
      row = neighbors.get(i);
      col = neighbors.get(i + 1);
      state = myGrid.getCellStateNumber(row, col);
      if (state == 1) {
        tree += 1;
      } else if (state == 2) {
        burning += 1;
      }
    }
    assertEquals(4, tree, "(2,1) should have 4 tree. got: " + tree);
    assertEquals(3, burning, "(2,1) should have 3 burning. got: " + burning);

  }
}
