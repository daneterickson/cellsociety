package cellsociety.model.model.utils.EdgePolicies;

import cellsociety.model.Grid;

/**
 * Abstract class that represents the edgepolicy. an edgepolicy class takes an out of bounds coordinate
 * and return a state based on the policy
 *
 * @Author Albert Yuan
 */
public abstract class EdgePolicies {
  public EdgePolicies(){}

  /**
   * abstract class that will be called in each policy. Calls a method below.
   * @param row - the row of the out of bounds coordinate
   * @param col - the column of the out of bounds coordinate
   * @param grid - the current grid that's holding all the cells
   * @return an integer that corresponds to a cell state
   */
  public abstract int policy(int row, int col, Grid grid);

  /**
   * edge policy that assumes edges as empty cells
   */
  protected int finite(int row, int col, Grid grid) {
    return 0;
  }

  /**
   * edge policy that assumes the edges loop to the opposite edge
   */
  protected int toroidal(int row, int col, Grid grid){
    int numRows = grid.getNumRows();
    int numCols = grid.getNumCols();

    if (row >= numRows || row < 0){
      row += numRows;
      row = row % numRows;
    }
    if (col >= numCols || col < 0){
      col += numCols;
      col = col % numCols;
    }
    return grid.getCellStateNumber(row,col);
  }

  /**
   * edge policy that assumes the top and left edges connect and the bottom and right edges connect
   */
  protected int spherical(int row, int col, Grid grid){
    int numRows = grid.getNumRows();
    int numCols = grid.getNumCols();
    int newRow, newCol;
    if (row >= numRows){
      newCol = row - 1;
    }else if(row < 0) {
      newCol = row + 1;
    }else{
      newCol = row;
    }
    if (col >= numCols){
      newRow = col - 1;
    }else if(col < 0) {
      newRow = col + 1;
    }else{
      newRow = col;
    }
    return grid.getCellStateNumber(newRow,newCol);
  }

}
