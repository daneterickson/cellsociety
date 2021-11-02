package cellsociety.model.model;

import static java.lang.Integer.parseInt;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicySetter;
import cellsociety.model.model.utils.NeighborFinders.NeighborFinder;
import cellsociety.model.model.rules.Rule;
import cellsociety.model.model.rules.SpreadingOfFireRule;
import cellsociety.model.model.utils.NeighborFinders.NeighborFinderSetter;
import cellsociety.model.model.utils.NeighborFinders.SquareEdges;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This is a subclass of Model. This model calculates the next states for the Spreading of Fire Simulation
 *
 * @Authors Albert Yuan, Dane Erickson, Aaric Han
 */
public class SpreadingOfFireModel extends Model {
  //base class variables
  private Grid currGrid;
  private ArrayList<Integer> newUpdates;
  private Controller myController;
  private NeighborFinder neighborFinder;
  private EdgePolicies edgePolicy;
  private int numUpdates;
  private Rule myRule;
  private double probCatch;
  private Random random;

  /**
   * This is the constructor for the Spreading Of Fire Model
   * @param controller - the controller that connects the model and view
   * @param grid - the current grid that holds the cells
   */
  public SpreadingOfFireModel(Controller controller, Grid grid) {
    super(controller, grid);
    getBaseInstanceVariables();
    random = new Random();
    try {
      probCatch = currGrid.getModelCell(0, 0).getCellParameter("ProbCatch");
    }catch (Exception e){
      System.out.println("invalid probCatch variable");
      probCatch = 0.5;
    }
    myRule = new SpreadingOfFireRule(probCatch);
  }

  private void getBaseInstanceVariables() {
    currGrid = getCurrGrid();
    newUpdates = getNewUpdates();
    myController = getMyController();
    neighborFinder = getNeighborFinder();
    edgePolicy = getEdgePolicy();
    neighborFinder = new SquareEdges();
    numUpdates = getNumUpdates();
  }
  /**
   * sets the edgepolicy to a new policy type by using reflection and edgepolicysetter class
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
   * sets the neighborfinder to a new neighborfinder type by using reflection and neighborfindersetter class
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
   * current rule for Spreading Of Fire. returns EMPTY/TREE/BURNING state
   */
  @Override
  protected Integer currRule(int currRow, int currCol, int state, List<Integer> nearby) {
    return myRule.determineState(currRow, currCol, state, nearby,currGrid,edgePolicy);
  }

  /**
   * helper method used to set probCatch to a new value
   */
  @Override
  protected void setProb(ArrayList newProb) {
    probCatch = (double) newProb.get(0);
    myRule = new SpreadingOfFireRule(probCatch);
  }

}
