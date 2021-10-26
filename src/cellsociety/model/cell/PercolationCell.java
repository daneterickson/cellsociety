package cellsociety.model.cell;

import java.util.Map;

public class PercolationCell extends ModelCell{

  public static final String WATER_NAME = "water";
  public static final String WATER_COLOR = "0000ff";
  public static final String BARRIER_NAME = "barrier";
  public static final String BARRIER_COLOR = "000000";

  private String myStartColors;
  private String myParameters;

  public PercolationCell(int i, int j, String startColors, String parameters, int state) {
    super(i, j, startColors, parameters, state);
    myStartColors = startColors;
    myParameters = parameters;
  }

  @Override
  protected void assignState(int state) {
    if (myStartColors == null || myStartColors.split(",").length != 3) {
      assignThreeCases(state, EMPTY_NAME, DEFAULT_GREY, WATER_NAME, WATER_COLOR, BARRIER_NAME, BARRIER_COLOR);
    }
    else {
      String stateColors[] = myStartColors.split(",");
      assignThreeCases(state, EMPTY_NAME, stateColors[0], WATER_NAME, stateColors[1], BARRIER_NAME, stateColors[2]);
    }
  }

  @Override
  protected void setParameters(String parameters) {}
}
