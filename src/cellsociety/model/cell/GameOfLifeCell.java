package cellsociety.model.cell;

import java.util.Map;

public class GameOfLifeCell extends ModelCell {

  public static final String ALIVE_BLUE = "0000ff"; // for alive cells in Game of Life
  public static final String DEAD_NAME = "dead";
  public static final String ALIVE_NAME = "alive";

  private String myStartColors;
  private String myParameters;

  public GameOfLifeCell(int i, int j, String startColors, String parameters, int state) {
    super(i, j, startColors, parameters, state);
    myStartColors = startColors;
    myParameters = parameters;
  }

  @Override
  protected void assignState(int state) {
    if (myStartColors == null || myStartColors.split(",").length != 2) {
      assignTwoCases(state, DEAD_NAME, DEFAULT_GREY, ALIVE_NAME, ALIVE_BLUE);
    }
    else {
      String stateColors[] = myStartColors.split(",");
      assignTwoCases(state, DEAD_NAME, stateColors[0], ALIVE_NAME, stateColors[1]);
    }
  }

  @Override
  protected void setParameters(String parameters) {}
}
