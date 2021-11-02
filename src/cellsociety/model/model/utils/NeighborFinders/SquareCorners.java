package cellsociety.model.model.utils.NeighborFinders;

import cellsociety.model.Grid;
import java.util.List;

/**
 * subclass of NeighborFinder. Represents the SquareCorners NeighborFinder
 *
 * @Author Albert Yuan
 */
public class SquareCorners extends NeighborFinder implements NeighborFinderInterface {

  /**
   * Overridden method that calls getSquareCorners
   * @param row - the row of the cell that's being examined
   * @param col - the column of the cell that's being examined
   * @param grid - the current grid that is being used
   * @return - an ArrayList that holds (row1,col1,row2,col2,...) for all neighbors
   */
  @Override
  public List<Integer> getNeighbors(int row, int col, Grid grid){
    return getSquareCorners(row,col);
  }

}
