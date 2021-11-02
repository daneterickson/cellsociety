package cellsociety.model.model.utils.NeighborFinders;

import cellsociety.model.Grid;
import java.util.List;

public class SquareComplete extends NeighborFinder implements NeighborFinderInterface {

  /**
   * Overridden method that calls getSquareComplete
   */
  @Override
  public List<Integer> getNeighbors(int row, int col, Grid grid){
    return getSquareComplete(row,col);
  }


}
