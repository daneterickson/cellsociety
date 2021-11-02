package cellsociety.model.model.utils.NeighborFinders;

import static java.lang.Integer.parseInt;

import cellsociety.model.Grid;
import cellsociety.model.exceptions.KeyNotFoundException;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import java.util.ArrayList;
import java.util.List;

public abstract class NeighborFinder {

  private EdgePolicies edgePolicy;

  public NeighborFinder(EdgePolicies edgePolicy) {
    this.edgePolicy = edgePolicy;
  }

  public abstract List<Integer> getNeighbors(int row, int col, Grid grid);

  /**
   * finds 4 neighboring cells and returns them as a linear array: [north,south,east,west]
   */
  protected List<Integer> getSquareEdges(int row, int col, Grid grid) {
    int[] x = {0, 0, 1, -1};
    int[] y = {-1, 1, 0, 0};
    ArrayList<Integer> neighbors = new ArrayList<>();
    int idx = 0;

    while (idx < 4) {
      neighbors.add(row + y[idx]);
      neighbors.add(col + x[idx]);
      idx++;
    }

    return neighbors;
  }

  /**
   * finds 4 neighboring cells and returns them as a linear array: [NW,NE,SW,SE]
   */
  protected List<Integer> getSquareCorners(int row, int col, Grid grid) {
    int[] x = {-1, 1, -1, 1};
    int[] y = {-1, -1, 1, 1};
    ArrayList<Integer> neighbors = new ArrayList<>();
    int idx = 0;

    while (idx < 4) {
      neighbors.add(row + y[idx]);
      neighbors.add(col + x[idx]);
      idx++;
    }

    return neighbors;
  }

  /**
   * finds 8 neighboring cells and returns them as a linear array: [topLeft,topMid,topRight,midLeft,midRight,botLeft,botMiddle,botRight]
   */
  protected List<Integer> getSquareComplete(int row, int col, Grid grid) {
    int[] dx = {-1, 0, 1};
    int[] dy = {-1, 0, 1};
    ArrayList<Integer> neighbors = new ArrayList<>();
    int numCols = grid.getNumCols();

    for (int y : dx) {
      for (int x : dy) {
        if (x == 0 && y == 0) {
          continue;
        }
        neighbors.add(row + y);
        neighbors.add(col + x);
      }
    }
    return neighbors;
  }

  /**
   * finds 12 neighboring cells and returns them as a linear array: [(top left)top row... middle
   * row... bottom row (bottom right)]
   */
  protected List<Integer> getTriangleComplete(int row, int col, Grid grid) {
    //row 0 has upside down triangles. odd rows have 5,4,3. even have 3,4,5
    ArrayList<Integer> neighbors = new ArrayList<>();
    int[] dx = {-2, -1, 0, 1, 2, -2, -1, 1, 2, -1, 0, 1};
    int[] dy = {-1, -1, -1, -1, -1, 0, 0, 0, 0, 1, 1, 1};

    for (int idx = 0; idx < dx.length; idx++) {
      if ((col+row) % 2 == 1) {
        neighbors.add(row + dy[idx]);
        neighbors.add(col + dx[idx]);
      } else {
        neighbors.add(row - dy[idx]);
        neighbors.add(col + dx[idx]);
      }
    }

    return neighbors;
  }
}
