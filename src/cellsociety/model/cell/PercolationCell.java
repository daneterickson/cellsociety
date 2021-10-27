package cellsociety.model.cell;

import java.util.ResourceBundle;

public class PercolationCell extends ModelCell{

  private String myStartColors;
  private String myParameters;
  private ResourceBundle myResources;

  private String waterName;
  private String waterColor;
  private String barrierName;
  private String barrierColor;

  public PercolationCell(int i, int j, String startColors, String parameters, int state) {
    super(i, j, startColors, parameters, state);
    myResources = getMyResources();
    assignConstants();
    myStartColors = startColors;
    myParameters = parameters;
  }

  @Override
  protected void assignConstants() {
    waterName = myResources.getString("WaterName");
    waterColor = myResources.getString("WaterColor");
    barrierName = myResources.getString("BarrierName");
    barrierColor = myResources.getString("BarrierColor");
  }

  @Override
  protected void assignState(int state) {
    if (myStartColors == null || myStartColors.split(PARAMETER_DELIMINATOR).length != 3) {
      assignThreeCases(state, EMPTY_NAME, DEFAULT_GREY, waterName, waterColor, barrierName,
          barrierColor);
    }
    else {
      String stateColors[] = myStartColors.split(PARAMETER_DELIMINATOR);
      assignThreeCases(state, EMPTY_NAME, stateColors[0], waterName, stateColors[1], barrierName, stateColors[2]);
    }
  }

  @Override
  protected void setParameters(String parameters) {}
}
