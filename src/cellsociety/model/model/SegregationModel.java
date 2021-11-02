package cellsociety.model.model;

import static cellsociety.model.cell.ModelCell.EMPTY_STATE;
import static java.lang.Integer.parseInt;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import cellsociety.model.exceptions.KeyNotFoundException;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicySetter;
import cellsociety.model.model.utils.NeighborFinders.NeighborFinder;
import cellsociety.model.model.rules.SegregationRule;
import cellsociety.model.model.utils.NeighborFinders.NeighborFinderSetter;
import cellsociety.model.model.utils.NeighborFinders.SquareComplete;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This is a subclass of Model. This model calculates the next states for the Segregation Simulation
 *
 * @Authors Albert Yuan, Dane Erickson, Aaric Han
 */
public class SegregationModel extends Model {
  //base class variables
  private Grid currGrid;
  private ArrayList<Integer> newUpdates;
  private Controller myController;
  private NeighborFinder neighborFinder;
  private EdgePolicies edgePolicy;
  private int numUpdates;
  private SegregationRule myRule;

  private double threshold;
  private ArrayList<Integer> emptySpots;
  private Random random;
  private int numCols;

  /**
   * This is the constructor for the Segregation Simulation
   * @param controller - the controller that connects the view and model
   * @param grid - the current grid that holds the cells
   */
  public SegregationModel(Controller controller, Grid grid) {
    super(controller, grid);
    getBaseInstanceVariables();

    numCols = grid.getNumCols();
    random = new Random();
    emptySpots = new ArrayList<>();
    findEmptyCells(grid);
    try{
      threshold = currGrid.getModelCell(0,0).getCellParameter("Threshold");
    } catch(Exception e){
      // TODO: catch threshold exception
      threshold = 0.5;
    }
    myRule = new SegregationRule(threshold);
  }

  private void findEmptyCells(Grid grid) {
    iterateGrid(row -> col -> {
      int state = 0;
      try {
        state = parseInt(grid.getModelCell(row, col).getCellProperty("StateNumber"));
      } catch (KeyNotFoundException e) {
        // TODO: handle exception - invalid properties
      }
      if (state == EMPTY_STATE) {
        emptySpots.add(row * numCols + col);
      }
    });
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
   * current rule for Segregation. returns EMPTY/RACE1/RACE2 state
   * relocates the state to an empty cell by calling relocate
   */
  @Override
  protected Integer currRule(int currRow, int currCol, int state, List<Integer> nearby) {
    int newState = myRule.determineState(currRow,currCol,state,nearby,currGrid,edgePolicy);
    if (myRule.relocationStatus()) {
      relocate(state);
      emptySpots.add(currRow * numCols + currCol);
    }
    return newState;
  }

  /**
   * takes a random empty cell from emptyspots and sets its state to state
   */
  private void relocate(int state) {
    int idx = random.nextInt(emptySpots.size());
    int r = emptySpots.get(idx) / numCols;
    int c = emptySpots.get(idx) % numCols;
    emptySpots.remove(idx);
    addNewUpdates(r, c, state);
  }

  @Override
  protected void setProb(ArrayList newProb) {
    threshold = (double) newProb.get(0);
    myRule = new SegregationRule(threshold);
  }


}
