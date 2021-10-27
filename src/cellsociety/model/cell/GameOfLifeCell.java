package cellsociety.model.cell;

import java.util.Map;
import java.util.ResourceBundle;

public class GameOfLifeCell extends ModelCell {

  private String myStartColors;
  private ResourceBundle myResources;

  private final String ALIVE_BLUE = myResources.getString("AliveBlue");
  private final String DEAD_NAME = myResources.getString("DeadName");
  private final String ALIVE_NAME = myResources.getString("AliveName");

  public GameOfLifeCell(int i, int j, String startColors, String parameters, int state) {
    super(i, j, startColors, parameters, state);
    myResources = getMyResources();
    myStartColors = startColors;
  }

  @Override
  protected void assignState(int state) {
    if (myStartColors == null || myStartColors.split(PARAMETER_DELIMINATOR).length != 2) {
      assignTwoCases(state, DEAD_NAME, DEFAULT_GREY, ALIVE_NAME, ALIVE_BLUE);
    }
    else {
      String stateColors[] = myStartColors.split(PARAMETER_DELIMINATOR);
      assignTwoCases(state, DEAD_NAME, stateColors[0], ALIVE_NAME, stateColors[1]);
    }
  }

  @Override
  protected void setParameters(String parameters) {}
}
