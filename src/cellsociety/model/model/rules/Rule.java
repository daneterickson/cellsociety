package cellsociety.model.model.rules;

import java.util.List;

public abstract class Rule {

  private int myRow;
  private int myCol;
  private int myState;
  private List<Integer> myNeighbors;

  public Rule () {}

  public abstract int determineState(int currRow, int currCol, int state, List<Integer> nearby);

}
