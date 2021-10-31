package cellsociety.model.model.utils.EdgePolicies;

import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;

public class ToroidalEdgePolicy extends EdgePolicies implements EdgePolicyInterface {
  @Override
  public int policy(int row, int col, Grid grid){
    return toroidal(row,col,grid);
  }
}
