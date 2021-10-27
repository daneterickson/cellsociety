package cellsociety.model.model.rules;

import java.util.List;

public class PredatorPreyRule extends Rule {

  public PredatorPreyRule(int currRow, int currCol, int state, List<Integer> nearby) {
    super(currRow, currCol, state, nearby);
  }

  @Override
  public int determineState(int currRow, int currCol, int state, List<Integer> nearby) {
    return 0;
  }
}
