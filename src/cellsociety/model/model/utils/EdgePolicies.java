package cellsociety.model.model.utils;

import cellsociety.model.Grid;

public class EdgePolicies {
  public EdgePolicies(){}

  public int policy(int row, int col, Grid grid){return 0;}

  protected int finite(int row, int col, Grid grid) {
    return 0;
  }

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

//    System.out.println(newCol + ", " + newRow);
    return grid.getCellStateNumber(newRow,newCol);
  }

}
