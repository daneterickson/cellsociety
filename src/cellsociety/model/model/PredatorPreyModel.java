package cellsociety.model.model;

import static cellsociety.model.cell.PredatorPreyCell.FISH_STATE;
import static cellsociety.model.cell.PredatorPreyCell.SHARK_STATE;
import static java.lang.Integer.parseInt;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import cellsociety.model.exceptions.KeyNotFoundException;
import cellsociety.model.model.rules.PredatorPreyRule;
import cellsociety.model.model.rules.Rule;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicySetter;
import cellsociety.model.model.utils.NeighborFinders.NeighborFinder;
import cellsociety.model.model.utils.NeighborFinders.NeighborFinderSetter;
import cellsociety.model.model.utils.NeighborFinders.SquareEdges;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
/**
 * This is a subclass of Model. This model calculates the next states for the Predator Prey Simulation
 * *
 * @Authors Albert Yuan, Dane Erickson, Aaric Han
 */
public class PredatorPreyModel extends Model {

  //base class variables
  private Grid currGrid;
  private ArrayList<Integer> newUpdates;
  private Controller myController;
  private NeighborFinder neighborFinder;
  private EdgePolicies edgePolicy;
  private int numUpdates;

  private final Random random;
  private final String FishReproduction = "FishReproduction";
  private final String SharkReproduction = "SharkReproduction";
  private final String SharkEnergy = "SharkEnergyStart";
  private final String SharkEnergyGain = "SharkEnergyGain";

  //runtime variables
  private int fishReproduction;
  private int sharkReproduction;
  private int sharkEnergy;
  private int energyGain;
  private ArrayList<Integer> sharkAttacks;
  private int numCols;
  private Rule myRule;

  /**
   * This is the constructor for the Predator Prey Model
   * @param controller - the controller that connects the model and view
   * @param grid - the current grid that holds the cells
   */
  public PredatorPreyModel(Controller controller, Grid grid) {
    super(controller, grid);
    getBaseInstanceVariables();
    random = new Random();
    numUpdates = 5;
    sharkAttacks = new ArrayList<>();
    numCols = currGrid.getNumCols();
    getBaseParameters();
    myRule = new PredatorPreyRule(currGrid, numCols, numUpdates, fishReproduction, sharkReproduction,
        sharkEnergy, energyGain, newUpdates,
        sharkAttacks);

  }

  private void getBaseInstanceVariables() {
    currGrid = getCurrGrid();
    newUpdates = getNewUpdates();
    myController = getMyController();
    numUpdates = getNumUpdates();
    edgePolicy = getEdgePolicy();
    neighborFinder = new SquareEdges();
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
  /**
   * overridden method that is called every step.
   * first iterates through the grid for sharks since sharks can eat fish before they move
   * then iterates through the grid for NOT sharks
   */
  @Override
  public void updateModel(Grid currGrid) {
    newUpdates.clear();
    this.currGrid = currGrid;
    iterateGrid(iterateGridLambda(currGrid, row -> col -> state -> iterateSharks(row, col, state)));
    iterateGrid(iterateGridLambda(currGrid, row -> col -> state -> iterateOthers(row, col, state)));
    updateGrid();
    updateHistogram();
    myController.setHasUpdate(true);
  }

  /**
   * returns a lambda that iterates through the grid while only considering a certain set of targets
   */
  private Function<Integer, Consumer<Integer>> iterateGridLambda(Grid currGrid,
      Function<Integer, Function<Integer, Consumer<Integer>>> iteratorTarget) {
    return row -> col -> {
      try {
        String currState = currGrid.getModelCell(row, col).getCellProperty("StateNumber");
        int stateAsInt = parseInt(currState);
        iteratorTarget.apply(row).apply(col).accept(stateAsInt);
      } catch (KeyNotFoundException e) {
        // TODO: handle exception
      }
    };
  }
  /**
   * iterates thru the grid and only considers shark cells
   */
  private void iterateSharks(Integer row, Integer col, int stateAsInt) {
    if (stateAsInt == SHARK_STATE) {
      updateCell(row, col, stateAsInt);
    }
  }

  /**
   * iterates thru the grid and only considers NOT shark cells
   */
  private void iterateOthers(Integer row, Integer col, int stateAsInt) {
    if (stateAsInt != SHARK_STATE) {
      if (sharkAttacks.contains(row * numCols + col)) {
        sharkAttacks.remove(row * numCols + col);
      } else {
        updateCell(row, col, stateAsInt);
      }
    }
  }

  /**
   * retrieves the set values of fish/shark reproduction, shark energy, and energy gain
   */
  private void getBaseParameters() {
    try {
      fishReproduction = (int) Math.round(
          currGrid.getModelCell(0, 0).getCellParameter(FishReproduction));
      sharkReproduction = (int) Math.round(
          currGrid.getModelCell(0, 0).getCellParameter(SharkReproduction));
      sharkEnergy = (int) Math.round(currGrid.getModelCell(0, 0).getCellParameter(SharkEnergy));
      energyGain = (int) Math.round(currGrid.getModelCell(0, 0).getCellParameter(SharkEnergyGain));
    } catch (KeyNotFoundException e) {
      // TODO: handle exception
    }
  }

  @Override
  protected List<Integer> getNearby(int row, int col) {
    return neighborFinder.getNeighbors(row, col, currGrid);
  }

  /**
   * overridden method that only calls getnearby and currrule.
   * pred/prey doesn't check the return value of currRule
   */
  @Override
  protected void updateCell(int row, int col, int state) {
    List<Integer> nearby = getNearby(row, col);
    currRule(row, col, state, nearby);
  }

  /**
   * Overridden method that updates the currGrid with new values
   * First sets the cells at (row,col) to new states
   * Then uses cell.setCellParameter to update fish/shark reproduction, etc
   */
  @Override
  protected void updateGrid() {
    int row, col, newState, newReproduction, newEnergy;

    for (int idx = 0; idx < newUpdates.size(); idx += numUpdates) {
      row = newUpdates.get(idx);
      col = newUpdates.get(idx + 1);
      newState = newUpdates.get(idx + 2);
      newReproduction = newUpdates.get(idx + 3);
      newEnergy = newUpdates.get(idx + 4);
      currGrid.updateCell(row, col, newState);

      if (newState == FISH_STATE) {
        currGrid.getModelCell(row, col).setCellParameter(FishReproduction, (double) newReproduction);
      } else if (newState == SHARK_STATE) {
        currGrid.getModelCell(row, col).setCellParameter(SharkReproduction, (double) newReproduction);
        currGrid.getModelCell(row, col).setCellParameter(SharkEnergy, (double) newEnergy);
      }
    }
  }

  /**
   * current rule for Predator/Prey. returns EMPTY/FISH/SHARK state
   */
  @Override
  protected Integer currRule(int currRow, int currCol, int state, List<Integer> nearby) {
    return myRule.determineState(currRow, currCol, state, nearby,currGrid,edgePolicy);
  }

  /**
   * Overridden method to set the game states to new values
   */
  @Override
  protected void setProb(ArrayList newProb) {
    fishReproduction = (int) newProb.get(0);
    sharkReproduction = (int) newProb.get(1);
    sharkEnergy = (int) newProb.get(2);
    energyGain = (int) newProb.get(3);

    myRule = new PredatorPreyRule(currGrid, numCols, numUpdates, fishReproduction, sharkReproduction,
        sharkEnergy, energyGain, newUpdates,
        sharkAttacks);
  }


}



