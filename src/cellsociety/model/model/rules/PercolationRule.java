package cellsociety.model.model.rules;

import static cellsociety.model.cell.ModelCell.EMPTY_STATE;
import static cellsociety.model.cell.PercolationCell.WATER_STATE;

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
   * @return the new state for the cell being evaluated
   */

  @Override
  public int determineState(int currRow, int currCol, int state, List<Integer> nearby) {
    if (state == EMPTY_STATE) {
      if (findNearbyWater(nearby)) {
        return WATER_STATE;
      }
      return EMPTY_STATE;
    }
    return state;
  }

  private boolean findNearbyWater(List<Integer> nearby) {
    for (int i : nearby) {
      if (i == WATER_STATE) {
        return true;
      }
    }
    return false;
  }
}
