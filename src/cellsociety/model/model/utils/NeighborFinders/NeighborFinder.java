package cellsociety.model.model.utils.NeighborFinders;

import cellsociety.model.Grid;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that represents the NeighborFinder. A NeighborFinder class takes a coordinate
 * and returns a list of all the neighbors' coordinates
 *
 * @Author Albert Yuan
 */
public abstract class NeighborFinder {

  public NeighborFinder() {}

  /**
   * abstract class that will be called in each NeighborFinder. Calls a method below.
   * @param row - the row of the out of bounds coordinate
   * @param col - the column of the out of bounds coordinate
   * @param grid - the current grid that's holding all the cells
   * @return a List of (row1, col1, ...) for each neighbor
   */
  public abstract List<Integer> getNeighbors(int row, int col, Grid grid);

  /**
   * finds 4 neighboring cells and returns them as a linear array: [north,south,east,west]
   */
  protected List<Integer> getSquareEdges(int row, int col) {
    int[] x = {0, 0, 1, -1};
    int[] y = {-1, 1, 0, 0};
    ArrayList<Integer> neighbors = new ArrayList<>();
    addSquareNeighbors(row, col, x, y, neighbors);

    return neighbors;
  }

  /**
   * finds 4 neighboring cells and returns them as a linear array: [NW,NE,SW,SE]
   */
  protected List<Integer> getSquareCorners(int row, int col) {
    int[] x = {-1, 1, -1, 1};
    int[] y = {-1, -1, 1, 1};
    ArrayList<Integer> neighbors = new ArrayList<>();
    addSquareNeighbors(row, col, x, y, neighbors);

    return neighbors;
  }

  /**
   * finds 8 neighboring cells and returns them as a linear array: [topLeft,topMid,topRight,midLeft,midRight,botLeft,botMiddle,botRight]
   */
  protected List<Integer> getSquareComplete(int row, int col) {
    int[] x = {-1, 0, 1, -1, 1, -1, 0, 1};
    int[] y = {-1, -1, -1, 0, 0, 1, 1, 1};
    ArrayList<Integer> neighbors = new ArrayList<>();
    addSquareNeighbors(row, col, x, y, neighbors);

    return neighbors;
  }

  /**
   * finds 12 neighboring cells and returns them as a linear array: [(top left)top row... middle
   * row... bottom row (bottom right)]
   */
  protected List<Integer> getTriangleComplete(int row, int col) {
    //row 0 has upside down triangles. odd rows have 5,4,3. even have 3,4,5
    ArrayList<Integer> neighbors = new ArrayList<>();
    int[] dx = {-2, -1, 0, 1, 2, -2, -1, 1, 2, -1, 0, 1};
    int[] dy = {-1, -1, -1, -1, -1, 0, 0, 0, 0, 1, 1, 1};

    addTriangleNeighbors(row, col, neighbors, dx, dy);
    return neighbors;
  }

  /**
   * finds 3 neighboring cells that share an edge and returns them as a linear array: [(top left)top
   * row... middlerow... bottom row (bottom right)]
   */
  protected List<Integer> getTriangleEdges(int row, int col) {
    ArrayList<Integer> neighbors = new ArrayList<>();
    int[] dx = {0, -1, 1};
    int[] dy = {-1, 0, 0};

    addTriangleNeighbors(row, col, neighbors, dx, dy);
    return neighbors;
  }

  /**
   * finds 9 neighboring cells that share a vertex and returns them as a linear array: [(top
   * left)top row... middlerow... bottom row (bottom right)]
   */
  protected List<Integer> getTriangleVertices(int row, int col) {
    ArrayList<Integer> neighbors = new ArrayList<>();
    int[] dx = {-2, -1, 1, 2, -2, 2, -1, 0, 1};
    int[] dy = {-1, -1, -1, -1, 0, 0, 1, 1, 1};

    addTriangleNeighbors(row, col, neighbors, dx, dy);
    return neighbors;
  }

  /**
   * adds the neighbors to the neighbors arraylist for square neighborfinders
   */
  private void addSquareNeighbors(int row, int col, int[] x, int[] y,
      ArrayList<Integer> neighbors) {
    int idx = 0;
    while (idx < x.length) {
      neighbors.add(row + y[idx]);
      neighbors.add(col + x[idx]);
      idx++;
    }
  }

  /**
   * adds the neighbors to the neighbors arraylist for triangle neighborfinders
   */
  private void addTriangleNeighbors(int row, int col, ArrayList<Integer> neighbors, int[] dx,
      int[] dy) {
    for (int idx = 0; idx < dx.length; idx++) {
      if ((col + row) % 2 == 1) {
        neighbors.add(row + dy[idx]);
        neighbors.add(col + dx[idx]);
      } else {
        neighbors.add(row - dy[idx]);
        neighbors.add(col + dx[idx]);
      }
    }
  }


}
