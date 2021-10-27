package cellsociety.model.cell;

import java.util.ResourceBundle;

public class GameOfLifeCell extends ModelCell {

  private String myStartColors;
  private ResourceBundle myResources;
  private String aliveBlue;
  private String deadName;
  private String aliveName;

  public GameOfLifeCell(int i, int j, String startColors, String parameters, int state) {
    super(i, j, startColors, parameters, state);
    myResources = getMyResources();
    assignConstants();
    myStartColors = startColors;
    assignState(state);
  }

  @Override
  protected void assignConstants() {
    aliveBlue = myResources.getString("AliveBlue");
    deadName = myResources.getString("DeadName");
    aliveName = myResources.getString("AliveName");
  }

  @Override
  protected void assignState(int state) {
    if (myStartColors == null || myStartColors.split(PARAMETER_DELIMINATOR).length != 2) {
      assignTwoCases(state, deadName, DEFAULT_GREY, aliveName, aliveBlue);
    }
    else {
      String stateColors[] = myStartColors.split(PARAMETER_DELIMINATOR);
      assignTwoCases(state, deadName, stateColors[0], aliveName, stateColors[1]);
    }
  }

  @Override
  protected void setParameters(String parameters) {}
}
