package cellsociety.model.model;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PercolationModel extends Model {

  private final int EMPTY = 0;
  private final int WATER = 1;
  private final int BARRIER = 2;
  private int probCatch;
  private Random random;

  public PercolationModel(Controller controller, Grid grid) {
    super(controller, grid);
    random = new Random();
  }

  @Override
  public void updateModel(Grid currGrid) {
    super.updateModel(currGrid);
    //iterate find start edge
    //check that opposite edge is percolated
  }

  @Override
  protected List<Integer> getNearby(int row, int col) {
    return gridIterator.get8Nearby(row, col, currGrid, EMPTY);
  }

  /**
   * current rule for Percolation. returns EMPTY/WATER/WALL state
   */
  @Override
  protected Integer currRule(int currRow, int currCol, int state, List<Integer> nearby) {
    if (state == EMPTY) {
      if (findNearbyWater(nearby)) {
        return WATER;
      }
      return EMPTY;
    }
    return state;
  }

  private boolean findNearbyWater(List<Integer> nearby) {
    for (int i : nearby) {
      if (i == WATER) {
        return true;
      }
    }
    return false;
  }

}
