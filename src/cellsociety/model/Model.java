package cellsociety.model;

import cellsociety.controller.Controller;
import java.util.ArrayList;

public class Model {

  Grid currGrid;
  ArrayList<ArrayList<Integer>> newGridArray;
  private final int DEAD_STATE = 0;
  private final int LIVE_STATE = 1;
  private Controller myController;
  public Model(Controller controller, Grid grid) {
    newGridArray = new ArrayList<>();
    myController = controller;
    currGrid = grid;
  }

  public void updateModel(Grid currGrid){
    this.currGrid = currGrid;
    iterateGrid();
    buildNewGrid();
  }
  /**
   * iterates through the grid until an exception, which determine when to go the next row/end. each
   * cell in the grid is then processed and then used to call addToNewGrid
   */
  public void iterateGrid() {

    int row = 0;
    int col = 0;
    while (true) {
      try {
        addToNewGrid(row, col, currGrid.getCellState(row, col));
        col += 1;
      } catch (IndexOutOfBoundsException xOutOfBounds) {
        try {
          col = 0;
          row += 1;
          currGrid.getCellState(row, col);
        } catch (IndexOutOfBoundsException yOutOfBounds) {
          break;
        }
      }
    }
  }

  /**
   * finds 8 neighboring cells and returns them as a linear array: [topLeft,topMid,topRight,midLeft,midRight,botLeft,botMiddle,botRight]
   * <p>
   * if the current point is an edge, it acts as if the edges are DEAD_STATES
   */
  private int[] getNearby(int row, int col) {
    int[] dx = {-1, 0, 1};
    int[] dy = {-1, 0, 1};
    int[] neighbors = new int[8];
    int idx = 0;
    for (int x : dx) {
      for (int y : dy) {
        if (x == 0 && y == 0) {
          continue;
        }
        try {
          neighbors[idx] = currGrid.getCellState(row + x, col + y);
        } catch (IndexOutOfBoundsException e) {
          //handles edge cases
          neighbors[idx] = DEAD_STATE;
        }
        idx++;
      }
    }
    return neighbors;
  }

  /**
   * updates the current cell by calling the current Rule
   */
  // checks the rule to see if cell needs to change state
  private Integer updateCell(int row, int col, int state) {
    //nearby: [topLeft,topMid,topRight,midLeft,midRight,botLeft,botMiddle,botRight]
    int[] nearby = getNearby(row, col);
    int newState = currRule(state, nearby);
    return newState;
  }

  /**
   * current rule for Game of life. returns dead/live state
   */
  private Integer currRule(int state, int[] nearby) {
    int population = 0;
    for (int i : nearby) {
      if (i == LIVE_STATE) {
        population += 1;
      }
    }
    if (state == DEAD_STATE && population == 3) {
      return LIVE_STATE;
    }
    if (state == LIVE_STATE && (population == 2 || population == 3)) {
      return LIVE_STATE;
    }
    return DEAD_STATE;
  }

  /**
   * updates the current cell's state. then adds the new state to newGrid arraylist
   */
  private void addToNewGrid(int row, int col, int state) {
    if (newGridArray.size() - 1 < row) {
      newGridArray.add(row, new ArrayList<>());
    }
    state = updateCell(row, col, state);
    newGridArray.get(row).add(col, state);
  }

  /**
   * creates a new grid. If successful, replaces old grid
   */
  private void buildNewGrid() {
    int numRows = newGridArray.size();
    int numCols = newGridArray.get(0).size();
    Grid GridBuffer = new Grid(numRows, numCols, newGridArray);
    currGrid = GridBuffer;
  }

  /**
   * updates the grid in Controller with the current grid in model
   */
  public Grid getGrid() {
    return currGrid;
  }


}
