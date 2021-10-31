package cellsociety.model.model.rules;

import static cellsociety.model.cell.ModelCell.EMPTY_STATE;

import java.util.ArrayList;
import java.util.List;

public class SegregationRule extends Rule {

  private double myThreshold;
  private int myNumCols;
  private ArrayList<Integer> myEmptySpots;
  private boolean relocateCheck = false;

  /**
   * Subclass of Rule that makes a rule for the Segregation simulation to find a cell's new state
   */
  public SegregationRule(double threshold, int numCols, ArrayList<Integer> emptySpots) {
    myThreshold = threshold;
    myNumCols = numCols;
    myEmptySpots = emptySpots;
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
  public int determineState(int currRow, int currCol, int state, List<Integer> nearby) {
    relocateCheck = false;
    if (state == EMPTY_STATE) {
      return EMPTY_STATE;
    }
    double allyPercentage = getAllyPercentage(state, nearby);
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

  private double getAllyPercentage(int state, List<Integer> nearby) {
    double totalNeighbors = 0;
    double allies = 0;
    for (int i : nearby) {
      if (i != EMPTY_STATE) {
        totalNeighbors += 1;
      }
      if (i == state) {
        allies++;
      }
    }
    return allies / totalNeighbors;
  }
}
