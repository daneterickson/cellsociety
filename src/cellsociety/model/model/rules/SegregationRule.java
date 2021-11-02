package cellsociety.model.model.rules;

import static cellsociety.model.cell.ModelCell.EMPTY_STATE;

import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import java.util.List;

public class SegregationRule extends Rule {

  private double myThreshold;
  private boolean relocateCheck = false;

  /**
   * Subclass of Rule that makes a rule for the Segregation simulation to find a cell's new state
   */
  public SegregationRule(double threshold) {

    myThreshold = threshold;
  }

  /**
   * Overridden method to determine the state for a SegregationRule
   *
   * @param currRow is the current row of the cell being evaluated
   * @param currCol is the current column of the cell being evaluated
   * @param state   is the current state of the cell being evaluated
   * @param nearby  is a list of the states of the nearby cells
   * @return the new state for the cell being evaluated
   */

  @Override
  public int determineState(int currRow, int currCol, int state, List<Integer> nearby, Grid grid,
      EdgePolicies edgePolicy){
    relocateCheck = false;
    if (state == EMPTY_STATE) {
      return EMPTY_STATE;
    }
    double allyPercentage = getAllyPercentage(state, nearby, grid, edgePolicy);
    if (allyPercentage < myThreshold) {
      relocateCheck = true;
      return EMPTY_STATE;
    }
    return state;
  }
  /**
   * Getter method that gets the relocation status to know when to relocate a cell. Relocation
   * Status determined in determineState() method.
   *
   * @return relocateCheck is the boolean of whether to relocate
   */
  public boolean relocationStatus() {
    return relocateCheck;
  }

  private double getAllyPercentage(int state, List<Integer> nearby, Grid grid,
      EdgePolicies edgePolicy) {
    double totalNeighbors = 0;
    double allies = 0;
    for (int i = 0; i<nearby.size();i+=2) {
      int nearbystate = getState(nearby.get(i),nearby.get(i+1), grid, edgePolicy);
      if (nearbystate != EMPTY_STATE) {
        totalNeighbors += 1;
      }
      if(nearbystate == state){
        allies += 1;
      }
    }
    return allies / totalNeighbors;
  }
}
