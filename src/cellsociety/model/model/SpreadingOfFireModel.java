package cellsociety.model.model;

import static java.lang.Integer.parseInt;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import cellsociety.model.model.utils.NeighborFinders.NeighborFinder;
import cellsociety.model.model.rules.Rule;
import cellsociety.model.model.rules.SpreadingOfFireRule;
import cellsociety.model.model.utils.NeighborFinders.SquareEdges;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

  public SpreadingOfFireModel(Controller controller, Grid grid) {
    super(controller, grid);
    getBaseInstanceVariables();
    random = new Random();
    try {
      probCatch = currGrid.getCell(0, 0).getCellParameter("ProbCatch");
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
    neighborFinder = setNeighborFinder();
    edgePolicy = getEdgePolicy();
    neighborFinder = new SquareEdges(edgePolicy);
    numUpdates = getNumUpdates();
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
    return myRule.determineState(currRow, currCol, state, nearby);
  }

  @Override
  protected void setProb(ArrayList newProb) {
    probCatch = (double) newProb.get(0);
    myRule = new SpreadingOfFireRule(probCatch);
  }

  @Override
  public void changeSettings(ArrayList newProb) {
    setProb(newProb);
  }

}
