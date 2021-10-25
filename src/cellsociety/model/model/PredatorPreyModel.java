package cellsociety.model.model;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class PredatorPreyModel extends Model{

  private final int EMPTY = 0;
  private final int FISH = 1;
  private final int SHARK = 2;
  private Random random;
  private HashMap<Integer, Consumer<Integer>> IdxToDirection;
  public PredatorPreyModel(Controller controller, Grid grid) {
    super(controller,grid);
    random = new Random();
  }

  /**
   * finds 4 neighboring cells and returns them as a linear array: [north,south,east,west]
   * <p>
   * if the current point is an edge, it acts as if the edges are EMPTY
   * @return
   */
  @Override
  protected List<Integer> getNearby(int row, int col) {
    int[] x = {0, 0, 1, -1};
    int[] y = {1, -1, 0, 0};
    ArrayList<Integer> neighbors = new ArrayList<>();
    int idx = 0;

    while(idx<4){
      try {
        neighbors.add(idx,currGrid.getCellStateNumber(row + x[idx], col + y[idx]));
      } catch (IndexOutOfBoundsException e) {
        //handles edge cases
        neighbors.add(idx,EMPTY);
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
    if (state == EMPTY){
      return EMPTY;
    }
    ArrayList<Integer> eligibleSpaces = new ArrayList<>();

    if (state == FISH){
      for(int idx = 0; idx < nearby.size(); idx++){
        if (!inBounds(currRow,currCol,idx)){
          continue;
        }
        if (nearby.get(idx) == EMPTY){
          eligibleSpaces.add(idx);
        }
      }
      if (eligibleSpaces.size() < 1){
        return FISH;
      }
      move(currRow, currCol, random.nextInt(eligibleSpaces.size()), state);
      return EMPTY;

    }else {

    }
    return 0;
  }

  private void move(int currRow, int currCol, int idx, int state) {
    int row = currRow;
    int col = currCol;
    switch (idx) {
      case 0 -> row = currRow - 1;
      case 1 -> row = currRow + 1;
      case 2 -> col = currCol + 1;
      case 3 -> col = currCol - 1;
    }
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



