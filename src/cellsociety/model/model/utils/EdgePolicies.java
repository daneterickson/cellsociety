package cellsociety.model.model.utils;

import cellsociety.model.Grid;

public class EdgePolicies {
  public EdgePolicies(){}

  public int finite(int row, int col, Grid grid) {
    return 0;
  }

  public int toroidal(int row, int col, Grid grid){
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

}
