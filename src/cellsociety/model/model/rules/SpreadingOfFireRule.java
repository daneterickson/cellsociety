package cellsociety.model.model.rules;

import static cellsociety.model.cell.ModelCell.EMPTY_STATE;
import static cellsociety.model.cell.SpreadingOfFireCell.BURN_STATE;
import static cellsociety.model.cell.SpreadingOfFireCell.TREE_STATE;

import java.util.List;
import java.util.Random;

public class SpreadingOfFireRule extends Rule {

  private double myProbCatch;
  private Random random;

  public SpreadingOfFireRule (double probCatch) {
//    super();
    myProbCatch = probCatch;
    random = new Random();
  }

  @Override
  public int determineState(int currRow, int currCol, int state, List<Integer> nearby) {
    if (state == EMPTY_STATE || state == BURN_STATE) {
      return EMPTY_STATE;
    }

    for (int neighborState : nearby) {
      if (neighborState == BURN_STATE) {
        if (random.nextFloat() < myProbCatch) {
          return BURN_STATE;
        }
        return TREE_STATE;
      }
    }
    return TREE_STATE;  }
}
