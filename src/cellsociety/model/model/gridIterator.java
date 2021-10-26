package cellsociety.model.model;

import cellsociety.model.Grid;
import java.util.ArrayList;
import java.util.List;

public class gridIterator {

  public gridIterator(){}

  /**
   * finds 4 neighboring cells and returns them as a linear array: [north,south,east,west]
   * if the current point is an edge, it acts as if the edges are edgeValue
   */
  public List<Integer> get4Nearby(int row, int col, Grid grid, int edgeValue) {
    int[] x = {0, 0, 1, -1};
    int[] y = {1, -1, 0, 0};
    ArrayList<Integer> neighbors = new ArrayList<>();
    int idx = 0;

    while (idx < 4) {
      try {
        neighbors.add(idx, grid.getCellStateNumber(row + x[idx], col + y[idx]));
      } catch (IndexOutOfBoundsException e) {
        neighbors.add(idx, edgeValue);
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
  protected List<Integer> get8Nearby(int row, int col, Grid grid, int edgeValue) {
    int[] dx = {-1, 0, 1};
    int[] dy = {-1, 0, 1};
    ArrayList<Integer> neighbors = new ArrayList<>();
    int idx = 0;

    for (int x : dx) {
      for (int y : dy) {
        if (x == 0 && y == 0) {
          continue;
        }
        try {
          neighbors.add(idx, grid.getCellStateNumber(row + x, col + y));
        } catch (IndexOutOfBoundsException e) {
          //handles edge cases
          neighbors.add(idx, edgeValue);
        }
        idx++;
      }
    }
    return neighbors;
  }
}
