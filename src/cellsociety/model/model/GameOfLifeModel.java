package cellsociety.model.model;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicySetter;
import cellsociety.model.model.utils.NeighborFinders.NeighborFinder;
import cellsociety.model.model.rules.GameOfLifeRule;
import cellsociety.model.model.rules.Rule;
import cellsociety.model.model.utils.NeighborFinders.NeighborFinderSetter;
import cellsociety.model.model.utils.NeighborFinders.SquareComplete;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a subclass of Model. This model calculates the next states for the Game of Life
 * Simulation
 *
 * @Authors Albert Yuan, Dane Erickson, Aaric Han
 */
public class GameOfLifeModel extends Model {

  //base class variables
  private Grid currGrid;
  private ArrayList<Integer> newUpdates;
  private Controller myController;
  private NeighborFinder neighborFinder;
  private EdgePolicies edgePolicy;
  private int numUpdates;
  private Rule myRule;

  /**
   * @param controller - controller that communicates between model and view
   * @param grid       - the grid that holds the current cells
   */
  public GameOfLifeModel(Controller controller, Grid grid) {
    super(controller, grid);
    getBaseInstanceVariables();
    myRule = new GameOfLifeRule();
  }

  private void getBaseInstanceVariables() {
    currGrid = getCurrGrid();
    newUpdates = getNewUpdates();
    myController = getMyController();
    neighborFinder = getNeighborFinder();
    edgePolicy = getEdgePolicy();
    neighborFinder = new SquareComplete();
    numUpdates = getNumUpdates();
  }

  /**
   * sets the edgepolicy to a new policy type by using reflection and edgepolicysetter class
   *
   * @param type - the type of edgepolicy as a string
   */
  @Override
  public void setEdgePolicy(String type) {
    EdgePolicySetter eps = new EdgePolicySetter();
    edgePolicy = eps.setEdgePolicy(type);
  }

  /**
   * @return the current edgepolicy type as a string
   */
  @Override
  public String getEdgePolicyType() {
    return edgePolicy.getClass().toString();
  }

  /**
   * sets the neighborfinder to a new neighborfinder type by using reflection and
   * neighborfindersetter class
   *
   * @param type - the type of neighborfinder as a string
   */
  @Override
  public void setNeighborFinder(String type) {
    NeighborFinderSetter nfs = new NeighborFinderSetter();
    neighborFinder = nfs.setNeighborFinder(type);
  }

  /**
   * @return the current neighborfinder type as a string
   */
  @Override
  public String getNeighborFinderType() {
    return neighborFinder.getClass().toString();
  }

  @Override
  protected List<Integer> getNearby(int row, int col) {
    return neighborFinder.getNeighbors(row, col, currGrid);
  }

  /**
   * current rule for Game of life. returns dead/live state
   */
  @Override
  protected Integer currRule(int currRow, int currCol, int state, List<Integer> nearby) {
    return myRule.determineState(currRow, currCol, state, nearby, currGrid, edgePolicy);
  }

}
