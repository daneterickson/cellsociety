package cellsociety.model.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies;
import cellsociety.model.model.utils.GridIterator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EdgePoliciesTest {

  private Grid myGrid;
  private int myStates[][];
  private int numRows;
  private int numCols;
  private String myStartColors;
  private String myParameters;
  private EdgePolicies myEdgePolicies;
  private int row;
  private int col;

  @BeforeEach
  void setUp() {
    myEdgePolicies = new EdgePolicies();
    myStates = new int[][]{{1, 1, 1, 1, 1},
        {2, 0, 0, 0, 1},
        {2, 0, 0, 0, 1},
        {2, 0, 0, 0, 1},
        {2, 2, 2, 2, 2}};
    numRows = 5;
    numCols = 5;
    String type = "SpreadingOfFire";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    row = 2;
    col = 2;
  }

  @Test
  void testFinite() {
    int result = myEdgePolicies.finite(row, col, myGrid);

    assertEquals(0, result, "finite should return 0: " + result);
  }

  @Test
  void testToroidal() {
//     {{1, 1, 1, 1, 1},
//      {2, 0, 0, 0, 1},
//      {2, 0, 0, 0, 1},
//      {2, 0, 0, 0, 1},
//      {2, 2, 2, 2, 2}};
    int result;
    row = -1;
    col = 0;
    result = myEdgePolicies.toroidal(row, col, myGrid);

    assertEquals(2, result, "(0,-1) should go to (0,4) = 2. got:" + result);

    row = 5;
    col = 0;
    result = myEdgePolicies.toroidal(row, col, myGrid);

    assertEquals(1, result, "(0,5) should go to (0,1) = 1. got:" + result);

    row = 5;
    col = -1;
    result = myEdgePolicies.toroidal(row, col, myGrid);

    assertEquals(1, result, "(-1,5) should go to (4,1) = 1. got:" + result);

    row = -1;
    col = -1;
    result = myEdgePolicies.toroidal(row, col, myGrid);

    assertEquals(2, result, "(-1,-1) should go to (4,4) = 2. got:" + result);
  }

}
