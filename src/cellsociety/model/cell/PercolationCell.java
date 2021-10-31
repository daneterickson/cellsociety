package cellsociety.model.cell;

import java.util.ResourceBundle;

public class PercolationCell extends ModelCell {

  public static final int WATER_STATE = 1;
  public static final int BARRIER_STATE = 2;

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
    assignState(state);
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
    assignThreeCases(state, EMPTY_NAME, DEFAULT_GREY, waterName, waterColor, barrierName,
        barrierColor);
  }

  @Override
  protected void setParameters(String parameters) {
  }
}
