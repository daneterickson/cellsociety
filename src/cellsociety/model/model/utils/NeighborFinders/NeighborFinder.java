package cellsociety.model.model.utils.NeighborFinders;

import static java.lang.Integer.parseInt;

import cellsociety.model.Grid;
import cellsociety.model.exceptions.KeyNotFoundException;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import java.util.ArrayList;
import java.util.List;

public abstract class NeighborFinder {
  private EdgePolicies edgePolicy;
  public NeighborFinder(EdgePolicies edgePolicy){
    this.edgePolicy = edgePolicy;
  }

  public abstract List<Integer> getNeighbors(int row, int col, Grid grid);

  /**
   * finds 4 neighboring cells and returns them as a linear array: [north,south,east,west]
   * if the current point is an edge, it acts as if the edges are edgeValue
   */
  protected List<Integer> getSquareEdges(int row, int col, Grid grid) {
    int[] x = {0, 0, 1, -1};
    int[] y = {-1, 1, 0, 0};
    ArrayList<Integer> neighbors = new ArrayList<>();
    int idx = 0;
    int state;

    while (idx < 4) {
      try {
        state = parseInt(grid.getModelCell(row + y[idx], col + x[idx]).getCellProperty("StateNumber"));
        neighbors.add(idx, state);
      } catch (IndexOutOfBoundsException | KeyNotFoundException e) {
        neighbors.add(idx, edgePolicy.policy(row + y[idx],col + x[idx],grid));
      }
      idx++;
    }

    return neighbors;
  }

  /**
   * finds 4 neighboring cells and returns them as a linear array: [NW,NE,SW,SE]
   * if the current point is an edge, it acts as if the edges are edgeValue
   */
  protected List<Integer> getSquareCorners(int row, int col, Grid grid) {
    int[] x = {-1, 1, -1, 1};
    int[] y = {-1, -1, 1, 1};
    ArrayList<Integer> neighbors = new ArrayList<>();
    int idx = 0;
    int state;

    while (idx < 4) {
      try {
        state = parseInt(grid.getModelCell(row + y[idx], col + x[idx]).getCellProperty("StateNumber"));
        neighbors.add(idx, state);
      } catch (IndexOutOfBoundsException | KeyNotFoundException e) {
        neighbors.add(idx, edgePolicy.policy(row + y[idx],col + x[idx],grid));
      }
      idx++;
    }

    return neighbors;
  }

  /**
   *  finds 8 neighboring cells and returns them as a linear array:
   *  [topLeft,topMid,topRight,midLeft,midRight,botLeft,botMiddle,botRight]
   *  if the current point is an edge, it acts as if the edges are edgeValue
   */
  protected List<Integer> getSquareComplete(int row, int col, Grid grid) {
    int[] dx = {-1, 0, 1};
    int[] dy = {-1, 0, 1};
    ArrayList<Integer> neighbors = new ArrayList<>();
    int idx = 0;
    int state;

    for (int x : dx) {
      for (int y : dy) {
        if (x == 0 && y == 0) {
          continue;
        }
        try {
          state = parseInt(grid.getModelCell(row + y, col + x).getCellProperty("StateNumber"));
          neighbors.add(idx, state);
        } catch (IndexOutOfBoundsException | KeyNotFoundException e) {
          //handles edge cases
          neighbors.add(idx, edgePolicy.policy(row + y,col + x,grid));
        }
        idx++;
      }
    }
    return neighbors;
  }
}
