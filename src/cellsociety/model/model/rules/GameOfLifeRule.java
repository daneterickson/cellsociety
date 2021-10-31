package cellsociety.model.model.rules;

import static cellsociety.model.cell.GameOfLifeCell.ALIVE_STATE;
import static cellsociety.model.cell.GameOfLifeCell.DEAD_STATE;

import java.util.List;

/**
 * Subclass of Rule that makes a rule for the Game of Life simulation to find a cell's new state
 */
public class GameOfLifeRule extends Rule {

  /**
   * Overridden method to determine the state for a GameOfLifeRule
   *
   * @param currRow is the current row of the cell being evaluated
   * @param currCol is the current column of the cell being evaluated
   * @param state is the current state of the cell being evaluated
   * @param nearby is a list of the states of the nearby cells
   * @return the new state for the cell being evaluated
   */

  @Override
  public int determineState(int currRow, int currCol, int state, List<Integer> nearby) {
    int population = 0;
    for (int i : nearby) {
      if (i == ALIVE_STATE) {
        population += 1;
      }
    }
    if (state == DEAD_STATE && population == 3) {
      return ALIVE_STATE;
    }
    if (state == ALIVE_STATE && (population == 2 || population == 3)) {
      return ALIVE_STATE;
    }
    return DEAD_STATE;
  }
}
