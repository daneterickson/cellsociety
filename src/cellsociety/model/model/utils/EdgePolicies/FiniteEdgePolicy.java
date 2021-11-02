package cellsociety.model.model.utils.EdgePolicies;

import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;

public class FiniteEdgePolicy extends EdgePolicies implements EdgePolicyInterface {

  /**
   * Overridden method that calls finite edgepolicy
   */
  @Override
  public int policy(int row, int col, Grid grid){
    return finite(row,col,grid);
  }
}
