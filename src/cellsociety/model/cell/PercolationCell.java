package cellsociety.model.cell;

import java.util.ResourceBundle;

/**
 * Subclass of ModelCell that makes a cell for each spot in Percolation simulation
 *
 * @Author Dane Erickson
 */
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

  /**
   * Constructor for a PercolationCell object, which is extended from the ModelCell super class
   *
   * @param i           is the row position of the cell being created
   * @param j           is the column position of the cell being created
   * @param stateColors is a comma separated String of the state colors for each state
   * @param parameters  is a comma separated String of the parameters (may be "" for no parameters)
   * @param state       is the state of the cell being created
   */
  public PercolationCell(int i, int j, String stateColors, String parameters, int state) {
    super(i, j, stateColors, parameters, state);
    myResources = getMyResources();
    assignConstants();
    myStartColors = stateColors;
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
