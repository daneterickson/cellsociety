package cellsociety.model.model.rules;

import java.util.List;
import java.util.Random;

public class SpreadingOfFireRule extends Rule {

  private static final int EMPTY_STATE = 0;
  private static final int TREE_STATE = 1;
  private static final int BURNING_STATE = 2;
  private double probCatch;
  private Random random;

  public SpreadingOfFireRule(int currRow, int currCol, int state, List<Integer> nearby) {
//    super(currRow, currCol, state, nearby);
  }

  @Override
  public int determineState(int currRow, int currCol, int state, List<Integer> nearby) {
    if (state == EMPTY_STATE || state == BURNING_STATE) {
      return EMPTY_STATE;
    }

    for (int neighborState : nearby) {
      if (neighborState == BURNING_STATE) {
        if (random.nextFloat() < probCatch) {
          return BURNING_STATE;
        }
        return TREE_STATE;
      }
    }
    return TREE_STATE;  }
}
