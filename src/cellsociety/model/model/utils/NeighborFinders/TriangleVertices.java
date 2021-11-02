package cellsociety.model.model.utils.NeighborFinders;

import cellsociety.model.Grid;
import java.util.List;

public class TriangleVertices extends NeighborFinder implements NeighborFinderInterface {

  /**
   * Overridden method that calls getTriangleVertices
   */
  @Override
  public List<Integer> getNeighbors(int row, int col, Grid grid){
    return getTriangleVertices(row,col);
  }

}
