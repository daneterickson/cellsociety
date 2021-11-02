package cellsociety.model.model.utils.NeighborFinders;

import cellsociety.model.Grid;
import java.util.List;

public class SquareEdges extends NeighborFinder implements NeighborFinderInterface {

  /**
   * Overridden method that calls getTriangleEdges
   */
  @Override
  public List<Integer> getNeighbors(int row, int col, Grid grid){
    return getSquareEdges(row,col);
  }
}
