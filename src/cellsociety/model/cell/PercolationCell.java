package cellsociety.model.cell;

import java.util.Map;
import java.util.ResourceBundle;

public class PercolationCell extends ModelCell{

  private String myStartColors;
  private String myParameters;
  private ResourceBundle myResources;

  private final String WATER_NAME = myResources.getString("WaterName");
  private final String WATER_COLOR = myResources.getString("WaterColor");
  private final String BARRIER_NAME = myResources.getString("BarrierName");
  private final String BARRIER_COLOR = myResources.getString("BarrierColor");

  public PercolationCell(int i, int j, String startColors, String parameters, int state) {
    super(i, j, startColors, parameters, state);
    myResources = getMyResources();
    myStartColors = startColors;
    myParameters = parameters;
  }

  @Override
  protected void assignState(int state) {
    if (myStartColors == null || myStartColors.split(PARAMETER_DELIMINATOR).length != 3) {
      assignThreeCases(state, EMPTY_NAME, DEFAULT_GREY, WATER_NAME, WATER_COLOR, BARRIER_NAME, BARRIER_COLOR);
    }
    else {
      String stateColors[] = myStartColors.split(PARAMETER_DELIMINATOR);
      assignThreeCases(state, EMPTY_NAME, stateColors[0], WATER_NAME, stateColors[1], BARRIER_NAME, stateColors[2]);
    }
  }

  @Override
  protected void setParameters(String parameters) {}
}
