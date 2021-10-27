package cellsociety.model.model;

import static java.lang.Integer.parseInt;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import cellsociety.model.exceptions.KeyNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PredatorPreyModel extends Model {
  //base class variables
  private Grid currGrid;
  private ArrayList<Integer> newUpdates;
  private Controller myController;
  private GridIterator gridIterator;
  private int numUpdates;

  //constants
  private final int EMPTY = 0;
  private final int FISH = 1;
  private final int SHARK = 2;
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

  public PredatorPreyModel(Controller controller, Grid grid) {
    super(controller, grid);
    getBaseInstanceVariables();
    random = new Random();
    numUpdates = 5;
    sharkAttacks = new ArrayList<>();
    numCols = currGrid.getNumCols();
    getBaseParameters();
  }
  private void getBaseInstanceVariables() {
    currGrid = getCurrGrid();
    newUpdates = getNewUpdates();
    myController = getMyController();
    gridIterator = getGridIterator();
    numUpdates = getNumUpdates();
  }
  @Override
  public void updateModel(Grid currGrid) {
    this.currGrid = currGrid;
    iterateSharks(currGrid);
    iterateOthers(currGrid);
    updateGrid();
    myController.setHasUpdate(true);
  }
  private void iterateSharks(Grid currGrid) {
    iterateGrid(row -> col -> {
      String currState = null;
      try {
        currState = currGrid.getCell(row, col).getCellProperty("StateNumber");
      } catch (KeyNotFoundException e) {
        // TODO: handle exception
        System.out.println("Invalid Property");
      }
      int stateAsInt = parseInt(currState);
      if (stateAsInt == SHARK){
        updateCell(row, col, stateAsInt);
      }
    });
  }

  private void iterateOthers(Grid currGrid) {
    iterateGrid(row -> col -> {
      String currState = null;
      try {
        currState = currGrid.getCell(row, col).getCellProperty("StateNumber");
      } catch (KeyNotFoundException e) {
        // TODO: handle exception
        System.out.println("Invalid Property");
      }
      int stateAsInt = parseInt(currState);
      if (stateAsInt != SHARK){
        if (sharkAttacks.contains(row*numCols+col)){
          sharkAttacks.remove(row*numCols+col);
        }else{
          updateCell(row, col, stateAsInt);
        }
      }
    });
  }

  private void getBaseParameters() {
    try {
      fishReproduction = (int) Math.round(
          currGrid.getCell(0, 0).getCellParameter(FishReproduction));
      sharkReproduction = (int) Math.round(
          currGrid.getCell(0, 0).getCellParameter(SharkReproduction));
      sharkEnergy = (int) Math.round(currGrid.getCell(0, 0).getCellParameter(SharkEnergy));
      energyGain = (int) Math.round(currGrid.getCell(0, 0).getCellParameter(SharkEnergyGain));
    } catch (KeyNotFoundException e) {
      // TODO: handle exception
      System.out.println("Invalid Parameter");
    }
  }

  @Override
  protected List<Integer> getNearby(int row, int col) {
    return gridIterator.get4Nearby(row, col, currGrid, EMPTY);
  }

  @Override
  protected void updateCell(int row, int col, int state) {
//    System.out.println("updatecell" + row + " " + col);

    List<Integer> nearby = getNearby(row, col);
    currRule(row, col, state, nearby);
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

  @Override
  protected void updateGrid() {
    int row, col, newState, newReproduction, newEnergy;

    for (int idx = 0; idx < newUpdates.size(); idx += numUpdates) {
      row = newUpdates.get(idx);
      col = newUpdates.get(idx + 1);
      newState = newUpdates.get(idx + 2);
      newReproduction = newUpdates.get(idx + 3);
      newEnergy = newUpdates.get(idx + 4);
//      System.out.println(
//          row + " " + col + " " + newState + " " + newReproduction + " " + newEnergy);
      currGrid.updateCell(row, col, newState);

      if (newState == FISH) {
        currGrid.getCell(row, col).setCellParameter(FishReproduction, (double) newReproduction);
      } else if (newState == SHARK) {
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
    int currReproduction = 0;
    try {
      currReproduction = (int) Math.round(
          currGrid.getCell(currRow, currCol).getCellParameter(FishReproduction));
    } catch (KeyNotFoundException e) {
      // TODO: handle exception
      System.out.println("Invalid Parameter");
    }
    int fishEnergy = -1; //fish don't have energy level

    eligibleSpaces = getEligibleSpaces(currRow, currCol, nearby, EMPTY);
    //update reproduction value
    if (currReproduction > 0) {
      currReproduction--;
    }

    //fish can't move
    if (eligibleSpaces.size() < 1) {
//      System.out.println("fish can't move");
      addNewUpdates(currRow, currCol, FISH, currReproduction, fishEnergy);
      return FISH;
    }

    //reproduce and move
    if (currReproduction == 0) {
//      System.out.println("fish reproduce");
      move(currRow, currCol, eligibleSpaces.get(random.nextInt(eligibleSpaces.size())), state,
          currReproduction, -1, false);
      addNewUpdates(currRow, currCol, FISH, fishReproduction, -1);
      return FISH;
    }

    //move
//    System.out.println("fish move");

    move(currRow, currCol, eligibleSpaces.get(random.nextInt(eligibleSpaces.size())), state,
        currReproduction, -1, false);
    addNewUpdates(currRow, currCol, EMPTY, -1, -1);
    return EMPTY;
  }

  private int sharkRules(int currRow, int currCol, int state, List<Integer> nearby) {
    System.out.println(
        sharkEnergy + " " + sharkReproduction + " " + fishReproduction + " " + energyGain);
    ArrayList<Integer> eligibleSpaces;
    int currReproduction = 0;
    boolean attack = false;
    try {
      currReproduction = (int) Math.round(
          currGrid.getCell(currRow, currCol).getCellParameter(SharkReproduction));
    } catch (KeyNotFoundException e) {
      // TODO: handle exception
      System.out.println("Invalid Parameter");
    }
    int currEnergy = 0;
    try {
      currEnergy = (int) Math.round(
          currGrid.getCell(currRow, currCol).getCellParameter(SharkEnergy));
    } catch (KeyNotFoundException e) {
      // TODO: handle exception
      System.out.println("Invalid Parameter");
    }

    currEnergy--;
    //update reproduction value
    if (currReproduction > 0) {
      currReproduction--;
    }
    //dead
    if (currEnergy <= 0) {
      System.out.println("shark dead");

      addNewUpdates(currRow, currCol, EMPTY, -1, -1);
      return EMPTY;
    }

    eligibleSpaces = getEligibleSpaces(currRow, currCol, nearby, FISH);

    //try to eat
    if (eligibleSpaces.size() >= 1) {
      System.out.println("shark eating");
      currEnergy += energyGain;
      attack = true;
    } else {
      eligibleSpaces = getEligibleSpaces(currRow, currCol, nearby, EMPTY);
    }

    //shark can't move
    if (eligibleSpaces.size() < 1) {
      System.out.println("shark can't move");

//      System.out.println("shark can't move");

      addNewUpdates(currRow, currCol, SHARK, currReproduction, currEnergy);
      return SHARK;
    }
    System.out.println("curr params: "+ currReproduction +" " + currEnergy);

    //move
    System.out.println("shark move");
    move(currRow, currCol, eligibleSpaces.get(random.nextInt(eligibleSpaces.size())), SHARK,
        currReproduction,
        currEnergy, attack);

    if (currReproduction == 0) {
      System.out.println("shark reproduce");
      addNewUpdates(currRow, currCol, SHARK, sharkReproduction, sharkEnergy);
      return SHARK;
    }else{
      addNewUpdates(currRow, currCol, EMPTY, -1, -1);
      return EMPTY;
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
    if (attack){
      sharkAttacks.add(newRow*numCols + newCol);
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
}



