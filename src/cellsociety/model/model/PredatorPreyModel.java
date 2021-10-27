package cellsociety.model.model;

import static java.lang.Integer.parseInt;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PredatorPreyModel extends Model {

  private final int EMPTY = 0;
  private final int FISH = 1;
  private final int SHARK = 2;
  private int fishReproduction;
  private int sharkReproduction;
  private int sharkEnergy;
  private int energyGain;
  private final Random random;
  private final String FishReproduction = "FishReproduction";
  private final String SharkReproduction = "SharkReproduction";
  private final String SharkEnergy = "SharkEnergy";
  private final String SharkEnergyGain = "SharkEnergyGain";

  public PredatorPreyModel(Controller controller, Grid grid) {
    super(controller, grid);
    random = new Random();
//    setReproductionEnergy();
    numUpdates = 5;
    getBaseParameters();
  }

  private void getBaseParameters() {
    fishReproduction = (int) Math.round(
        currGrid.getCell(0, 0).getCellParameter(FishReproduction));
    sharkReproduction = (int) Math.round(
        currGrid.getCell(0, 0).getCellParameter(SharkReproduction));
    sharkEnergy = (int) Math.round(currGrid.getCell(0, 0).getCellParameter(SharkEnergy));
    energyGain = 10;
//    energyGain = (int) Math.round(currGrid.getCell(0, 0).getCellParameter(SharkEnergyGain));
  }

  @Override
  protected List<Integer> getNearby(int row, int col) {
    return gridIterator.get4Nearby(row, col, currGrid, EMPTY);
  }

  @Override
  protected void updateCell(int row, int col, int state) {
    List<Integer> nearby = getNearby(row, col);
    currRule(row, col, state, nearby);
  }

  private void addNewUpdates(int row, int col, int newState, int reproduction, int energy) {
    newUpdates.add(row);
    newUpdates.add(col);
    newUpdates.add(newState);
    newUpdates.add(reproduction);
    newUpdates.add(energy);
  }

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

      if(newState == FISH){
        currGrid.getCell(row, col).setCellParameter(FishReproduction, (double) newReproduction);
      }else if (newState == SHARK){
        currGrid.getCell(row, col).setCellParameter(SharkReproduction, (double) newReproduction);
        currGrid.getCell(row, col).setCellParameter(SharkEnergy, (double) newEnergy);
      }
    }
  }

  /**
   * current rule for Predator/Prey. returns EMPTY/FISH/SHARK state
   */
  @Override
  protected Integer currRule(int currRow, int currCol, int state, List<Integer> nearby) {
    if (state == EMPTY) {
      return EMPTY;
    }
    if (state == FISH) {
      return fishRules(currRow, currCol, state, nearby);
    } else {
      return sharkRules(currRow, currCol, state, nearby);
    }
  }

  private int fishRules(int currRow, int currCol, int state, List<Integer> nearby) {
    ArrayList<Integer> eligibleSpaces;
    int currReproduction = (int) Math.round(
        currGrid.getCell(currRow, currCol).getCellParameter(FishReproduction));
    int fishEnergy = -1; //fish don't have energy level

    eligibleSpaces = getEligibleSpaces(currRow, currCol, nearby, EMPTY);

    //update reproduction value
    if (currReproduction > 0) {
      currReproduction--;
    }

    //fish can't move
    if (eligibleSpaces.size() < 1) {
      addNewUpdates(currRow, currCol, FISH, currReproduction, fishEnergy);
      return FISH;
    }

    //reproduce and move
    if (currReproduction == 0) {
      addNewUpdates(currRow, currCol, FISH, fishReproduction, -1);
      move(currRow, currCol, random.nextInt(eligibleSpaces.size()), state, currReproduction, -1);
      return FISH;
    }

    //move
    move(currRow, currCol, random.nextInt(eligibleSpaces.size()), state, currReproduction, -1);
    addNewUpdates(currRow, currCol, EMPTY, -1, -1);
    return EMPTY;
  }

  private int sharkRules(int currRow, int currCol, int state, List<Integer> nearby) {
    ArrayList<Integer> eligibleSpaces;
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
      addNewUpdates(currRow, currCol, EMPTY, -1, -1);
      return EMPTY;
    }

    eligibleSpaces = getEligibleSpaces(currRow, currCol, nearby, FISH);

    //try to eat
    if (eligibleSpaces.size() >= 1) {
      currEnergy += energyGain;
    } else {
      eligibleSpaces = getEligibleSpaces(currRow, currCol, nearby, EMPTY);
    }

    //shark can't move
    if (eligibleSpaces.size() < 1) {
      addNewUpdates(currRow, currCol, SHARK, currReproduction, currEnergy);
      return SHARK;
    }

    //reproduce and move
    if (currReproduction == 0) {
      move(currRow, currCol, random.nextInt(eligibleSpaces.size()), state, currReproduction,
          currEnergy);
      addNewUpdates(currRow, currCol, SHARK, sharkReproduction, sharkEnergy);
      return SHARK;
    }

    //move
    addNewUpdates(currRow, currCol, EMPTY, -1, -1);
    return EMPTY;
  }

  /**
   * finds eligible spaces that the current cell can move to based on the allowed 'eligible' cell
   * state.
   */
  private ArrayList<Integer> getEligibleSpaces(int currRow, int currCol, List<Integer> nearby,
      int eligible) {
    ArrayList<Integer> ret = new ArrayList<>();
    for (int idx = 0; idx < nearby.size(); idx++) {
      if (!inBounds(currRow, currCol, idx)) {
        continue;
      }
      if (nearby.get(idx) == eligible) {
        ret.add(idx);
      }
    }
    return ret;
  }

  /**
   * moves the current cell to a new location and then adds the new properties to newUpdates
   */
  private void move(int currRow, int currCol, int idx, int state, int currReproduction,
      int currEnergy) {
    if (currReproduction == 0) {
      currReproduction = sharkReproduction;
    }

    int newRow = currRow;
    int newCol = currCol;
    switch (idx) {
      case 0 -> newRow = currRow - 1;
      case 1 -> newRow = currRow + 1;
      case 2 -> newCol = currCol + 1;
      case 3 -> newCol = currCol - 1;
    }

    addNewUpdates(newRow, newCol, state, currReproduction, currEnergy);
  }

  /**
   * checks that a possible move is in bounds.
   */
  private boolean inBounds(int currRow, int currCol, int idx) {
    //nearby = [north,south,east,west]
    return switch (idx) {
      case 0 -> currRow - 1 >= 0;
      case 1 -> currRow + 1 < currGrid.getNumRows();
      case 2 -> currCol + 1 < currGrid.getNumCols();
      case 3 -> currCol - 1 >= 0;
      default -> false;
    };
  }
}



