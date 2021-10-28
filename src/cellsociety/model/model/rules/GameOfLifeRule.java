package cellsociety.model.model.rules;

import static cellsociety.model.cell.GameOfLifeCell.ALIVE_STATE;
import static cellsociety.model.cell.GameOfLifeCell.DEAD_STATE;

import java.util.List;

public class GameOfLifeRule extends Rule {

  public GameOfLifeRule () {
//    super();
  }

  @Override
  public int determineState(int currRow, int currCol, int state, List<Integer> nearby) {
    int population = 0;
    for (int i : nearby) {
      if (i == ALIVE_STATE) {
        population += 1;
      }
    }
    if (state == DEAD_STATE && population == 3) {
      return ALIVE_STATE;
    }
    if (state == ALIVE_STATE && (population == 2 || population == 3)) {
      return ALIVE_STATE;
    }
    return DEAD_STATE;
  }
}
