package cellsociety.model.model.rules;

import static cellsociety.model.cell.ModelCell.EMPTY_STATE;
import static cellsociety.model.cell.SpreadingOfFireCell.BURN_STATE;
import static cellsociety.model.cell.SpreadingOfFireCell.TREE_STATE;

import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import java.util.List;
import java.util.Random;

/**
 * Subclass of Rule that makes a rule for the Spreading of Fire simulation to find a cell's new
 * state
 */
public class SpreadingOfFireRule extends Rule {

  private double myProbCatch;
  private Random random;

  /**
   * Constructor to create the SpreadingOfFireRule object, which is called by SpreadingOfFireModel
   * to determine the new state for a particular cell.
   *
   * @param probCatch is the probability that a tree cell catches on fire from an adjacent burning
   *                  cell
   */
  public SpreadingOfFireRule(double probCatch) {
    myProbCatch = probCatch;
    random = new Random();
  }
  /**
   * Overridden method to determine the state for a SpreadingOfFireRule
   *
   * @param currRow is the current row of the cell being evaluated
   * @param currCol is the current column of the cell being evaluated
   * @param state   is the current state of the cell being evaluated
   * @param nearby  is a list of the states of the nearby cells
   * @return the new state for the cell being evaluated
   */

  @Override
  public int determineState(int currRow, int currCol, int state, List<Integer> nearby, Grid grid,
      EdgePolicies edgePolicy) {
    if (state == EMPTY_STATE || state == BURN_STATE) {
      return EMPTY_STATE;
    }

    for (int i = 0; i<nearby.size();i+=2) {
      int nearbystate = getState(nearby.get(i),nearby.get(i+1), grid, edgePolicy);
      if (nearbystate == BURN_STATE) {
        if (random.nextFloat() < myProbCatch) {
          return BURN_STATE;
        }
        return TREE_STATE;
      }
    }
    return TREE_STATE;

  }

}
