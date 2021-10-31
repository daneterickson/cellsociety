package cellsociety.model.model.utils;

import cellsociety.model.Grid;

public class ToroidalEdgePolicy extends EdgePolicies implements EdgePolicyInterface{
  @Override
  public int policy(int row, int col, Grid grid){
    return toroidal(row,col,grid);
  }
}
