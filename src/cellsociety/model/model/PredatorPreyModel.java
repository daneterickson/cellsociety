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
  }

  /**
   * finds 4 neighboring cells and returns them as a linear array: [north,south,east,west]
   * <p>
   * if the current point is an edge, it acts as if the edges are EMPTY
   *
   * @return
   */
  @Override
  protected List<Integer> getNearby(int row, int col) {
    int[] x = {0, 0, 1, -1};
    int[] y = {1, -1, 0, 0};
    ArrayList<Integer> neighbors = new ArrayList<>();
    int idx = 0;

    while (idx < 4) {
      try {
        neighbors.add(idx, currGrid.getCellStateNumber(row + x[idx], col + y[idx]));
      } catch (IndexOutOfBoundsException e) {
        //handles edge cases
        neighbors.add(idx, EMPTY);
      }
      idx++;
    }
    return neighbors;
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

  private int sharkRules(int currRow, int currCol, int state, List<Integer> nearby) {
    ArrayList<Integer> eligibleSpaces;
    int currEnergy = currGrid.getCell(currRow, currCol).getEnergy();
    int currReproduction = currGrid.getCell(currRow, currCol).getReproduction();

    currEnergy--;
    if (currEnergy <= 0){
      return EMPTY;
    }
    //try to eat fish
    eligibleSpaces = getEligibleSpaces(currRow, currCol, nearby, FISH);
    //try to move
    if (eligibleSpaces.size() >= 1) {
      currEnergy += energyGain;
    }else{
      eligibleSpaces = getEligibleSpaces(currRow, currCol, nearby, EMPTY);
    }
    //shark can't move
    if (eligibleSpaces.size() < 1) {
      return SHARK;
    }
    //move
    move(currRow, currCol, random.nextInt(eligibleSpaces.size()), state, currReproduction, currEnergy);
    //reproduce
    if (currReproduction == 0){
      currGrid.getCell(currRow, currCol).setReproduction(sharkReproduction);
      currGrid.getCell(currRow, currCol).setEnergy(sharkEnergy);
      return SHARK;
    }
    return EMPTY;
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
    if (currReproduction == 0){
      currGrid.getCell(currRow, currCol).setReproduction(fishReproduction);
      return FISH;
    }
    return EMPTY;
  }

  private ArrayList<Integer> getEligibleSpaces(int currRow, int currCol, List<Integer> nearby,int eligible) {
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

  private void move(int currRow, int currCol, int idx, int state, int currReproduction, int currEnergy) {
    if (currReproduction == 0){
      currReproduction = sharkReproduction;
    }
    int row = currRow;
    int col = currCol;
    switch (idx) {
      case 0 -> row = currRow - 1;
      case 1 -> row = currRow + 1;
      case 2 -> col = currCol + 1;
      case 3 -> col = currCol - 1;
    }
    currGrid.getCell(row,col).setReproduction(currReproduction);
    currGrid.getCell(row,col).setEnergy(currEnergy);

    addNewUpdates(row, col, state);
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



