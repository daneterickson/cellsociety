package cellsociety.model.model;

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
  private Random random;

  public PredatorPreyModel(Controller controller, Grid grid) {
    super(controller, grid);
    random = new Random();
    setReproductionEnergy();
  }

  /**
   * sets the reproduction and energy values in grid for each model cell
   */
  private void setReproductionEnergy() {
    iterateGrid(row -> col -> {
      if (currGrid.getCell(row, col).getStateNumber() == FISH) {
        currGrid.getCell(row, col).setReproduction(fishReproduction);
      } else if (currGrid.getCell(row, col).getStateNumber() == SHARK) {
        currGrid.getCell(row, col).setReproduction(sharkReproduction);
        currGrid.getCell(row, col).setEnergy(sharkEnergy);
      }
    });
  }

  @Override
  protected List<Integer> getNearby(int row, int col) {
    return gridIterator.get4Nearby(row,col,currGrid,EMPTY);
  }

  /**
   * current rule for Spreading Of Fire. returns EMPTY/TREE/BURNING state
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
    int currReproduction;
    currReproduction = currGrid.getCell(currRow, currCol).getReproduction();

    //try to move
    eligibleSpaces = getEligibleSpaces(currRow, currCol, nearby, EMPTY);
    //fish can't move
    if (eligibleSpaces.size() < 1) {
      return FISH;
    }
    //move
    move(currRow, currCol, random.nextInt(eligibleSpaces.size()), state, currReproduction, -1);
    if (currReproduction == 0) {
      currGrid.getCell(currRow, currCol).setReproduction(fishReproduction);
      return FISH;
    }
    setPropertiesNull(currRow,currCol);
    return EMPTY;
  }

  private int sharkRules(int currRow, int currCol, int state, List<Integer> nearby) {
    ArrayList<Integer> eligibleSpaces;
    int currEnergy = currGrid.getCell(currRow, currCol).getEnergy();
    int currReproduction = currGrid.getCell(currRow, currCol).getReproduction();

    currEnergy--;
    if (currEnergy <= 0) {
      setPropertiesNull(currRow,currCol);
      return EMPTY;
    }
    //try to eat fish
    eligibleSpaces = getEligibleSpaces(currRow, currCol, nearby, FISH);
    //try to move
    if (eligibleSpaces.size() >= 1) {
      currEnergy += energyGain;
    } else {
      eligibleSpaces = getEligibleSpaces(currRow, currCol, nearby, EMPTY);
    }
    //shark can't move
    if (eligibleSpaces.size() < 1) {
      return SHARK;
    }
    //move
    move(currRow, currCol, random.nextInt(eligibleSpaces.size()), state, currReproduction,
        currEnergy);
    //reproduce
    if (currReproduction == 0) {
      currGrid.getCell(currRow, currCol).setReproduction(sharkReproduction);
      currGrid.getCell(currRow, currCol).setEnergy(sharkEnergy);
      return SHARK;
    }
    setPropertiesNull(currRow,currCol);
    return EMPTY;
  }

  private void setPropertiesNull(int currRow, int currCol) {
    currGrid.getCell(currRow, currCol).setReproduction(-1);
    currGrid.getCell(currRow, currCol).setReproduction(-1);

  }


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
    currGrid.getCell(newRow, newCol).setReproduction(currReproduction);
    currGrid.getCell(newRow, newCol).setEnergy(currEnergy);

    addNewUpdates(newRow, newCol, state);
  }

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



