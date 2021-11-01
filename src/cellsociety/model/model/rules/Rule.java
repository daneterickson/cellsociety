package cellsociety.model.model.rules;

import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import java.util.List;

/**
 * Super class for a Rule object, which is used to determine the new state of a particular cell in
 * each of the simulations.
 */
public abstract class Rule {

  /**
   * Determines the new state for a particular cell in any of the simulation types. This method is
   * overridden for each simulation type and called by the model to find the new state of a cell.
   *
   * @param currRow is the current row of the cell being evaluated
   * @param currCol is the current column of the cell being evaluated
   * @param state   is the current state of the cell being evaluated
   * @param nearby  is a list of the states of the nearby cells
   * @return the new state for the cell being evaluated
   */
  public abstract int determineState(int currRow, int currCol, int state, List<Integer> nearby,
      Grid grid, EdgePolicies edgePolicy);

  protected int getState(int row, int col, Grid grid,
      EdgePolicies edgePolicy){
    try{
      return grid.getCellStateNumber(row,col);
    }catch (IndexOutOfBoundsException e){

    }
    return edgePolicy.policy(row,col,grid);

  }

}
