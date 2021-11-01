package cellsociety.model.model.rules;

import static cellsociety.model.cell.ModelCell.EMPTY_STATE;
import static cellsociety.model.cell.PredatorPreyCell.FISH_STATE;
import static cellsociety.model.cell.PredatorPreyCell.SHARK_STATE;

import cellsociety.model.Grid;
import cellsociety.model.exceptions.KeyNotFoundException;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Subclass of Rule that makes a rule for the Predator Prey simulation to find a cell's new state
 */
public class PredatorPreyRule extends Rule {

  private final Random random;
  private final String FishReproduction = "FishReproduction";
  private final String SharkReproduction = "SharkReproduction";
  private final String SharkEnergy = "SharkEnergyStart";
  private final String SharkEnergyGain = "SharkEnergyGain";

  private int numCols;
  private int numUpdates;
  private Grid currGrid;
  private ArrayList<Integer> newUpdates;
  private ArrayList<Integer> sharkAttacks;
  private int fishReproduction;
  private int sharkReproduction;
  private int sharkEnergy;
  private int fishEnergy = -1; //fish don't have energy level
  private int energyGain;

  /**
   * Constructor to make a new PredatorPreyRule, which is extended from the Rule super class
   *
   * @param currGrid          is the current Grid with the cell being evaluated
   * @param numCols           is the number of columns in teh current Grid
   * @param numUpdates        is the number of items used to update a cell. Usually 3 since row,
   *                          col, new state
   * @param fishReproduction  is the number of steps before the fish reproduce
   * @param sharkReproduction is the number of steps before the sharks reproduce
   * @param sharkEnergy       is the initial energy of a shark
   * @param energyGain        is how much a shark gains by eating a fish
   * @param newUpdates        is a list of the new positions and states to be updated
   * @param sharkAttacks      is a list to store where shark attacks occur to know which fish to
   *                          remove
   */
  public PredatorPreyRule(Grid currGrid, int numCols, int numUpdates, int fishReproduction,
      int sharkReproduction, int sharkEnergy, int energyGain, ArrayList<Integer> newUpdates,
      ArrayList<Integer> sharkAttacks) {
    super();
    this.currGrid = currGrid;
    this.numCols = numCols;
    this.numUpdates = numUpdates;
    this.fishReproduction = fishReproduction;
    this.sharkReproduction = sharkReproduction;
    this.sharkEnergy = sharkEnergy;
    this.energyGain = energyGain;
    this.newUpdates = newUpdates;
    this.sharkAttacks = sharkAttacks;
    random = new Random();
  }

  @Override
  public int determineState(int currRow, int currCol, int state, List<Integer> nearby, Grid grid,
      EdgePolicies edgePolicy) {
    if (state == EMPTY_STATE) {
      return EMPTY_STATE;
    }
    try {
      if (state == FISH_STATE) {
        return fishRules(currRow, currCol, state, nearby, grid,edgePolicy );
      } else {
        return sharkRules(currRow, currCol, state, nearby,grid,edgePolicy );
      }
    } catch (KeyNotFoundException e) {
      e.printStackTrace();
    }
    return EMPTY_STATE;
  }

  /**
   * Overridden method to determine the state for a PredatorPreyRule
   *
   * @param currRow is the current row of the cell being evaluated
   * @param currCol is the current column of the cell being evaluated
   * @param state   is the current state of the cell being evaluated
   * @param nearby  is a list of the states of the nearby cells
   * @return the new state for the cell being evaluated
   */
  @Override
  public int determineState(int currRow, int currCol, int state, List<Integer> nearby) {
    return 0;
  }

  /**
   * determines the cell state changes for a fish cell. if the fish can move, it sets the current
   * cell to empty and adds the current fish states to newUpdates if reproduction = 0, it sets the
   * current cell to fish, and adds the current fish states to newUpdates
   */
  private int fishRules(int currRow, int currCol, int state, List<Integer> nearby,
      Grid grid, EdgePolicies edgePolicy)
      throws KeyNotFoundException {
    ArrayList<Integer> eligibleSpaces;
    int currReproduction = (int) Math.round(
        currGrid.getCell(currRow, currCol).getCellParameter(FishReproduction));

    eligibleSpaces = getEligibleSpaces(nearby, EMPTY_STATE);
    //update reproduction value
    if (currReproduction > 0) {
      currReproduction--;
    }

    //fish can't move
    if (eligibleSpaces.size() < 1) {
//      System.out.println("fish can't move");
      addNewUpdates(currRow, currCol, FISH_STATE, currReproduction, fishEnergy);
      return FISH_STATE;
    }

    //reproduce and move
    if (currReproduction == 0) {
//      System.out.println("fish reproduce");
      move(eligibleSpaces,state, currReproduction, -1, false);
      addNewUpdates(currRow, currCol, FISH_STATE, fishReproduction, -1);
      return FISH_STATE;
    }

    //move
//    System.out.println("fish move");

    move(eligibleSpaces, state, currReproduction, -1, false);
    addNewUpdates(currRow, currCol, EMPTY_STATE, -1, -1);
    return EMPTY_STATE;
  }

  /**
   * determines the cell state changes for a shark cell. if energy = 0; the cell becomes empty if
   * the shark can move, it sets the current cell to empty and adds the current shark states to
   * newUpdates if the shark can eat, it sets the current cell to empty, sets attacked = true, and
   * adds the current shark states to newUpdates if reproduction = 0, it sets the current cell to
   * shark, and adds the current shark states to newUpdates
   */
  private int sharkRules(int currRow, int currCol, int state, List<Integer> nearby,
      Grid grid, EdgePolicies edgePolicy)
      throws KeyNotFoundException {
    ArrayList<Integer> eligibleSpaces;
    boolean attack = false;
    int currReproduction = (int) Math.round(
        currGrid.getCell(currRow, currCol).getCellParameter(SharkReproduction));
    int currEnergy = (int) Math.round(
        currGrid.getCell(currRow, currCol).getCellParameter(SharkEnergy));

    currEnergy--;
    //update reproduction value
    if (currReproduction > 0) {
      currReproduction--;
    }
    System.out.println("curr params: " + currReproduction + " " + currEnergy);

    //dead
    if (currEnergy <= 0) {
      System.out.println("shark dead");

      addNewUpdates(currRow, currCol, EMPTY_STATE, sharkReproduction, sharkEnergy);
      return EMPTY_STATE;
    }

    eligibleSpaces = getEligibleSpaces(nearby, FISH_STATE);

    //try to eat
    if (eligibleSpaces.size() >= 2) {
      System.out.println("shark eating");
      currEnergy += energyGain;
      attack = true;
    } else {
      eligibleSpaces = getEligibleSpaces(nearby, EMPTY_STATE);
    }

    //shark can't move
    if (eligibleSpaces.size() < 2) {
      System.out.println("shark can't move");
      addNewUpdates(currRow, currCol, SHARK_STATE, currReproduction, currEnergy);
      return SHARK_STATE;
    }

    //move
    System.out.println("shark move");
    move(eligibleSpaces, SHARK_STATE, currReproduction, currEnergy, attack);

    if (currReproduction == 0) {
      System.out.println("shark reproduce");
      addNewUpdates(currRow, currCol, SHARK_STATE, sharkReproduction, sharkEnergy);
      return SHARK_STATE;
    } else {
      addNewUpdates(currRow, currCol, EMPTY_STATE, sharkReproduction, sharkEnergy);
      return EMPTY_STATE;
    }


  }

  /**
   * finds eligible spaces that the current cell can move to based on the allowed 'eligible' cell
   * state.
   */
  private ArrayList<Integer> getEligibleSpaces(List<Integer> nearby,
      int eligible) {
    ArrayList<Integer> ret = new ArrayList<>();
    int row, col, state;

    for (int idx = 0; idx < nearby.size(); idx += 2) {
      row = nearby.get(idx);
      col = nearby.get(idx + 1);
      if (!inBounds(row, col) || occupiedSpace(row, col)) {
        continue;
      }
      state = currGrid.getCellStateNumber(row, col);
      if (state == eligible) {
        ret.add(row);
        ret.add(col);
      }
    }
    return ret;
  }

  /**
   * moves the current cell to a new location and then adds the new properties to newUpdates
   */
  private void move(List<Integer> eligibleSpaces, int state,
      int currReproduction,
      int currEnergy, boolean attack) {
    if (currReproduction == 0) {
      currReproduction = sharkReproduction;
    }
    int idx = random.nextInt(eligibleSpaces.size()/2);
    idx = idx * 2;
    int newRow = eligibleSpaces.get(idx);
    int newCol = eligibleSpaces.get(idx+1);

    if (attack) {
      sharkAttacks.add(newRow * numCols + newCol);
    }
    addNewUpdates(newRow, newCol, state, currReproduction, currEnergy);
  }

  /**
   * checks that a possible move is in bounds.
   */
  private boolean inBounds(int row, int col) {
    if (row < 0 || row >= currGrid.getNumRows()) {
      return false;
    }
    if (col < 0 || col >= currGrid.getNumCols()) {
      return false;
    }
    return true;
  }

  /**
   * determines if the specified row and col have been moved to
   *
   * @param row
   * @param col
   */
  private boolean occupiedSpace(int row, int col) {
    for (int i = 0; i < newUpdates.size(); i += numUpdates) {
      if (newUpdates.get(i) == row && newUpdates.get(i + 1) == col) {
        return true;
      }
    }
    return false;
  }

  /**
   * adds changed cells' states to a queue that's to be used in updateGrid
   */
  private void addNewUpdates(int row, int col, int newState, int reproduction, int energy) {
//    System.out.println(
//        "new UPDATE: " + row + " " + col + " " + newState + " " + reproduction + " " + energy);

    newUpdates.add(row);
    newUpdates.add(col);
    newUpdates.add(newState);
    newUpdates.add(reproduction);
    newUpdates.add(energy);
  }
}
