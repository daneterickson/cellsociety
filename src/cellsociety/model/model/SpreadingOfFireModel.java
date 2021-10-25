package cellsociety.model.model;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpreadingOfFireModel extends Model{

  private final int EMPTY = 0;
  private final int TREE = 1;
  private final int BURNING = 2;
  private double probCatch = 0.5;
  private Random random;

  public SpreadingOfFireModel(Controller controller, Grid grid) {
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
        neighbors.add(idx, currGrid.getCellStateNumber(row + x[idx], col + y[idx]));
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
  protected Integer currRule(int state, List<Integer> nearby) {
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
