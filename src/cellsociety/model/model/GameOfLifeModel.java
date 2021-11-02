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

public class GameOfLifeModel extends Model {
  //base class variables
  private Grid currGrid;
  private ArrayList<Integer> newUpdates;
  private Controller myController;
  private NeighborFinder neighborFinder;
  private EdgePolicies edgePolicy;
  private int numUpdates;
  private Rule myRule;

  public GameOfLifeModel(Controller controller, Grid grid) {
    super(controller, grid);
    getBaseInstanceVariables();
    myRule = new GameOfLifeRule();
//    setRule(new GameOfLifeRule());
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

  @Override
  public void setEdgePolicy(String type){
    EdgePolicySetter eps = new EdgePolicySetter();
    edgePolicy = eps.setEdgePolicy(type);
  }
  @Override
  public String getEdgePolicyType(){
    return edgePolicy.getClass().toString();
  }
  @Override
  public void setNeighborFinder(String type){
    NeighborFinderSetter nfs = new NeighborFinderSetter();
    neighborFinder = nfs.setNeighborFinder(type);
  }
  @Override
  public String getNeighborFinderType(){
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
    return myRule.determineState(currRow, currCol, state, nearby,currGrid, edgePolicy);
  }

}
