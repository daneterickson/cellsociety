package cellsociety.model.model.utils.EdgePolicies;

import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;

/**
 * subclass of EdgePolicies. Represents the Toroidal EdgePolicy
 *
 * @Author Albert Yuan
 */
public class ToroidalEdgePolicy extends EdgePolicies implements EdgePolicyInterface {

  /**
   * Overridden method that calls toroidal edgepolicy
   *
   * @param row  - the row of the cell that's being examined
   * @param col  - the column of the cell that's being examined
   * @param grid - the current grid that is being used
   * @return - an integer that represents the state of the outOfBounds coordinate
   */
  @Override
  public int policy(int row, int col, Grid grid) {
    return toroidal(row, col, grid);
  }
}
