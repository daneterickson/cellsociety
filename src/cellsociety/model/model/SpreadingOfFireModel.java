package cellsociety.model.model;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import java.util.Random;

public class SpreadingOfFireModel extends Model{

  private final int EMPTY = 0;
  private final int TREE = 1;
  private final int BURNING = 1;
  private int probCatch;
  private Random random;

  public SpreadingOfFireModel(Controller controller, Grid grid) {
    super(controller,grid);
    random = new Random();
  }

  /**
   * finds 4 neighboring cells and returns them as a linear array: [north,south,east,west]
   * <p>
   * if the current point is an edge, it acts as if the edges are EMPTY
   */
  @Override
  protected int[] getNearby(int row, int col) {
    int[] x = {0, 0, 1, -1};
    int[] y = {1, -1, 0, 0};
    int[] neighbors = new int[4];
    int idx = 0;

    while(idx<4){
      try {
        neighbors[idx] = currGrid.getCellStateNumber(row + x[idx], col + y[idx]);
      } catch (IndexOutOfBoundsException e) {
        //handles edge cases
        neighbors[idx] = EMPTY;
      }
      idx++;
    }
    return neighbors;
  }

  /**
   * current rule for Spreading Of Fire. returns EMPTY/TREE/BURNING state
   */
  @Override
  protected Integer currRule(int state, int[] nearby) {
    if (state == EMPTY || state == BURNING){
      return EMPTY;
    }

    for (int neighborState : nearby){
      if (neighborState == BURNING){
        if (random.nextFloat() < probCatch){
          return BURNING;
        }
        return TREE;
      }
    }
    return TREE;
  }

}
