package cellsociety.model.model;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpreadingOfFireModel extends Model {

  private final int EMPTY = 0;
  private final int TREE = 1;
  private final int BURNING = 2;
  private double probCatch = 0.5;
  private Random random;

  public SpreadingOfFireModel(Controller controller, Grid grid) {
    super(controller, grid);
    random = new Random();
  }

  @Override
  protected List<Integer> getNearby(int row, int col) {
    return gridIterator.get4Nearby(row, col, currGrid, EMPTY);
  }

  /**
   * current rule for Spreading Of Fire. returns EMPTY/TREE/BURNING state
   */
  @Override
  protected Integer currRule(int currRow, int currCol, int state, List<Integer> nearby) {
    if (state == EMPTY || state == BURNING) {
      return EMPTY;
    }

    for (int neighborState : nearby) {
      if (neighborState == BURNING) {
        if (random.nextFloat() < probCatch) {
          return BURNING;
        }
        return TREE;
      }
    }
    return TREE;
  }

}
