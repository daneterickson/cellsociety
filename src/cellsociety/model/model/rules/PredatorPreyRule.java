package cellsociety.model.model.rules;

import static cellsociety.model.cell.ModelCell.EMPTY_STATE;
import static cellsociety.model.cell.PredatorPreyCell.FISH_STATE;
import static cellsociety.model.cell.PredatorPreyCell.SHARK_STATE;

import cellsociety.model.Grid;
import cellsociety.model.exceptions.KeyNotFoundException;
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
    if (state == EMPTY_STATE) {
      return EMPTY_STATE;
    }
    try {
      if (state == FISH_STATE) {
        return fishRules(currRow, currCol, state, nearby);
      } else {
        return sharkRules(currRow, currCol, state, nearby);
      }
    } catch (KeyNotFoundException e) {
      e.printStackTrace();
    }
  return EMPTY_STATE;
  }

  private int fishRules(int currRow, int currCol, int state, List<Integer> nearby)
      throws KeyNotFoundException {
    ArrayList<Integer> eligibleSpaces;
    int currReproduction = 0;

    currReproduction = (int) Math.round(
        currGrid.getCell(currRow, currCol).getCellParameter(FishReproduction));

    eligibleSpaces = getEligibleSpaces(currRow, currCol, nearby, EMPTY_STATE);
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
      move(currRow, currCol, eligibleSpaces.get(random.nextInt(eligibleSpaces.size())), state,
          currReproduction, -1, false);
      addNewUpdates(currRow, currCol, FISH_STATE, fishReproduction, -1);
      return FISH_STATE;
    }

    //move
//    System.out.println("fish move");

    move(currRow, currCol, eligibleSpaces.get(random.nextInt(eligibleSpaces.size())), state,
        currReproduction, -1, false);
    addNewUpdates(currRow, currCol, EMPTY_STATE, -1, -1);
    return EMPTY_STATE;
  }

  private int sharkRules(int currRow, int currCol, int state, List<Integer> nearby)
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
    //dead
    if (currEnergy <= 0) {
      System.out.println("shark dead");

      addNewUpdates(currRow, currCol, EMPTY_STATE, sharkReproduction, sharkEnergy);
      return EMPTY_STATE;
    }

    eligibleSpaces = getEligibleSpaces(currRow, currCol, nearby, FISH_STATE);

    //try to eat
    if (eligibleSpaces.size() >= 1) {
      System.out.println("shark eating");
      currEnergy += energyGain;
      attack = true;
    } else {
      eligibleSpaces = getEligibleSpaces(currRow, currCol, nearby, EMPTY_STATE);
    }

    //shark can't move
    if (eligibleSpaces.size() < 1) {
      System.out.println("shark can't move");

//      System.out.println("shark can't move");

      addNewUpdates(currRow, currCol, SHARK_STATE, currReproduction, currEnergy);
      return SHARK_STATE;
    }
    System.out.println("curr params: " + currReproduction + " " + currEnergy);

    //move
    System.out.println("shark move");
    move(currRow, currCol, eligibleSpaces.get(random.nextInt(eligibleSpaces.size())), SHARK_STATE,
        currReproduction,
        currEnergy, attack);

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
  private ArrayList<Integer> getEligibleSpaces(int currRow, int currCol, List<Integer> nearby,
      int eligible) {
    ArrayList<Integer> ret = new ArrayList<>();
    for (int idx = 0; idx < nearby.size(); idx++) {
      if (!inBounds(currRow, currCol, idx) || occupiedSpace(currRow, currCol, idx)) {
        continue;
      }

      if (nearby.get(idx) == eligible) {
//        System.out.println("geteligible  "+currRow + " " + currCol + " " + idx);
        ret.add(idx);
      }
    }
    return ret;
  }

  /**
   * moves the current cell to a new location and then adds the new properties to newUpdates
   */
  private void move(int currRow, int currCol, int idx, int state, int currReproduction,
      int currEnergy, boolean attack) {
    if (currReproduction == 0) {
      currReproduction = sharkReproduction;
    }
    int newRow = currRow;
    int newCol = currCol;
    switch (idx) {
      case 0 -> newRow -= 1;
      case 1 -> newRow += 1;
      case 2 -> newCol += 1;
      case 3 -> newCol -= 1;
    }
    if (attack) {
      sharkAttacks.add(newRow * numCols + newCol);
    }
    addNewUpdates(newRow, newCol, state, currReproduction, currEnergy);
  }

  /**
   * checks that a possible move is in bounds.
   */
  private boolean inBounds(int currRow, int currCol, int idx) {
    //nearby = [north,south,east,west]
    boolean ret = switch (idx) {
      case 0 -> currRow - 1 >= 0;
      case 1 -> currRow + 1 < currGrid.getNumRows();
      case 2 -> currCol + 1 < currGrid.getNumCols();
      case 3 -> currCol - 1 >= 0;
      default -> false;
    };
//    if (ret) System.out.println("in bounds "+currRow+" "+currCol+" "+idx);
    return ret;
  }

  private boolean occupiedSpace(int currRow, int currCol, int idx) {
    //nearby = [north,south,east,west]
    switch (idx) {
      case 0 -> currRow--;
      case 1 -> currRow++;
      case 2 -> currCol++;
      case 3 -> currCol--;
      default -> throw new IllegalStateException("Unexpected value: " + idx);
    }

    for (int i = 0; i < newUpdates.size(); i += numUpdates) {
      if (newUpdates.get(i) == currRow && newUpdates.get(i + 1) == currCol) {
        return false;
      }
    }
    return false;
  }

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
