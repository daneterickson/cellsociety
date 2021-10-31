package cellsociety.model.model.rules;

import static cellsociety.model.cell.ModelCell.EMPTY_STATE;
import static cellsociety.model.cell.PercolationCell.WATER_STATE;

import java.util.List;

public class PercolationRule extends Rule {

  public PercolationRule () {}
  @Override
  public int determineState(int currRow, int currCol, int state, List<Integer> nearby) {
    if (state == EMPTY_STATE) {
      if (findNearbyWater(nearby)) {
        return WATER_STATE;
      }
      return EMPTY_STATE;
    }
    return state;  }

  private boolean findNearbyWater(List<Integer> nearby) {
    for (int i : nearby) {
      if (i == WATER_STATE) {
        return true;
      }
    }
    return false;
  }
}
