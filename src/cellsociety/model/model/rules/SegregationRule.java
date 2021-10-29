package cellsociety.model.model.rules;

import java.util.List;

public class SegregationRule extends Rule {

  public SegregationRule(int currRow, int currCol, int state, List<Integer> nearby) {
    //super(currRow, currCol, state, nearby);
  }

  @Override
  public int determineState(int currRow, int currCol, int state, List<Integer> nearby) {
//    if (state == EMPTY) {
//      return EMPTY;
//    }
//
//    double allyPercentage = getAllyPercentage(state, nearby);
//
//    if (allyPercentage < threshold) {
//      relocate(state);
//      emptySpots.add(currRow*numCols + currCol);
//      return EMPTY;
//    }
//    return state;
//  }
    return 0;
  }
}
