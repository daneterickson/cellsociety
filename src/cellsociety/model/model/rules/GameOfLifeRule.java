package cellsociety.model.model.rules;

import java.util.List;

public class GameOfLifeRule extends Rule {

  private static final int DEAD_STATE = 0;
  private static final int LIVE_STATE = 1;

  public GameOfLifeRule(int currRow, int currCol, int state, List<Integer> nearby) {
    //super(currRow, currCol, state, nearby);
  }

  @Override
  public int determineState(int currRow, int currCol, int state, List<Integer> nearby) {
    int population = 0;
    for (int i : nearby) {
      if (i == LIVE_STATE) {
        population += 1;
      }
    }
    if (state == DEAD_STATE && population == 3) {
      return LIVE_STATE;
    }
    if (state == LIVE_STATE && (population == 2 || population == 3)) {
      return LIVE_STATE;
    }
    return DEAD_STATE;
  }
}
