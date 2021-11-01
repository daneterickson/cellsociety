package cellsociety.model.model.rules;

import static cellsociety.model.cell.ModelCell.EMPTY_STATE;
import static cellsociety.model.cell.PercolationCell.WATER_STATE;

import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import java.util.List;

/**
 * Subclass of Rule that makes a rule for the Percolation simulation to find a cell's new state
 */
public class PercolationRule extends Rule {

  /**
   * Overridden method to determine the state for a PercolationRule
   *
   * @param currRow is the current row of the cell being evaluated
   * @param currCol is the current column of the cell being evaluated
   * @param state   is the current state of the cell being evaluated
   * @param nearby  is a list of the states of the nearby cells
   * @param edgePolicy
   * @return the new state for the cell being evaluated
   */
  @Override
  public int determineState(int currRow, int currCol, int state, List<Integer> nearby, Grid grid,
      EdgePolicies edgePolicy){
    if (state == EMPTY_STATE) {
      if (findNearbyWater(nearby, grid, edgePolicy)) {
        return WATER_STATE;
      }
      return EMPTY_STATE;
    }
    return state;
  }

  private boolean findNearbyWater(List<Integer> nearby, Grid grid,
      EdgePolicies edgePolicy) {
    for (int i = 0; i<nearby.size();i+=2) {
      int nearbystate = getState(nearby.get(i),nearby.get(i+1), grid, edgePolicy);
      if (nearbystate == WATER_STATE) {
        return true;
      }
    }
    return false;
  }
}
