package cellsociety.model.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.model.Grid;
import cellsociety.model.model.utils.GridIterator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GridIteratorTest {
  private Grid myGrid;
  private int myStates[][];
  private int numRows;
  private int numCols;
  private String myStartColors;
  private String myParameters;
  private GridIterator myGridIterator;
  private int row;
  private int col;

  @BeforeEach
  void setUp() {
    myGridIterator = new GridIterator();
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
    List<Integer> neighbors = myGridIterator.getSquareComplete(row,col,myGrid,0);

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
    List<Integer> neighbors = myGridIterator.getSquareEdges(row,col,myGrid,0);

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

    List<Integer> neighbors = myGridIterator.getSquareCorners(row,col,myGrid,0);

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

    neighbors = myGridIterator.getSquareCorners(row,col,myGrid,0);

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
