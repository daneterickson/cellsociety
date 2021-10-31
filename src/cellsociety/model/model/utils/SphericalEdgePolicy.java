package cellsociety.model.model.utils;

import cellsociety.model.Grid;

public class SphericalEdgePolicy extends EdgePolicies implements EdgePolicyInterface{
  @Override
  public int policy(int row, int col, Grid grid){
    return spherical(row,col,grid);
  }
}
