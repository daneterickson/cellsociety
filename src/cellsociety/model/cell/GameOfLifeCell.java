package cellsociety.model.cell;

import java.util.Map;

public class GameOfLifeCell extends ModelCell {

  public static final String ALIVE_BLUE = "0000ff"; // for alive cells in Game of Life
  public static final String DEAD_NAME = "dead";
  public static final String ALIVE_NAME = "alive";

  private Map<Integer, String> myStartColors;

  public GameOfLifeCell(int i, int j, Map<Integer, String> startColors, int state) {
    super(i, j, startColors, state);
    myStartColors = startColors;
  }

  @Override
  protected void assignState(int state) {
    if (myStartColors == null || myStartColors.keySet().size() != 2) {
      assignTwoCases(state, DEAD_NAME, DEFAULT_GREY, ALIVE_NAME, ALIVE_BLUE);
    }
    else {
      assignTwoCases(state, DEAD_NAME, myStartColors.get(0), ALIVE_NAME, myStartColors.get(1));
    }
  }
}
