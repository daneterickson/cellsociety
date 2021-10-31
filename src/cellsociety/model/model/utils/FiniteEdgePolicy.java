package cellsociety.model.model.utils;

import cellsociety.model.Grid;

public class FiniteEdgePolicy extends EdgePolicies implements EdgePolicyInterface{
  @Override
  public int policy(int row, int col, Grid grid){
    return finite(row,col,grid);
  }
}
