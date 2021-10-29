package cellsociety.model.model.rules;

import java.util.List;

public class PercolationRule extends Rule {


  public PercolationRule () {
//    super();

  }
  @Override
  public int determineState(int currRow, int currCol, int state, List<Integer> nearby) {
    return 0;
  }
}
