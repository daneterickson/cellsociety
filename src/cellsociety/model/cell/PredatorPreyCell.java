package cellsociety.model.cell;

import java.util.ResourceBundle;

/**
 * Subclass of ModelCell that makes a cell for each spot in Predator Prey simulation
 */
public class PredatorPreyCell extends ModelCell {

  public static final int FISH_STATE = 1;
  public static final int SHARK_STATE = 2;

  private String myStartColors;
  private ResourceBundle myResources;
  private String fishName;
  private String fishColor;
  private String sharkName;
  private String sharkColor;
  private String defaultParameters;
  private String fishReproductionKey;
  private String sharkReproductionKey;
  private String sharkEnergyStartKey;
  private String sharkEnergyGainKey;

  /**
   * Constructor for a PredatorPreyCell object, which is extended from the ModelCell super class
   *
   * @param i           is the row position of the cell being created
   * @param j           is the column position of the cell being created
   * @param stateColors is a comma separated String of the state colors for each state
   * @param parameters  is a comma separated String of the parameters (may be "" for no parameters)
   * @param state       is the state of the cell being created
   */
  public PredatorPreyCell(int i, int j, String stateColors, String parameters, int state) {
    super(i, j, stateColors, parameters, state);
    myResources = getMyResources();
    assignConstants();
    myStartColors = stateColors;
    if (parameters == null) {
      parameters = defaultParameters; // default is fish reproduction, shark reproduction, shark energy start, and shark energy gain are all 10
    }
    setParameters(parameters);
    assignState(state);
  }

  @Override
  protected void assignConstants() {
    fishName = myResources.getString("FishName");
    fishColor = myResources.getString("FishColor");
    sharkName = myResources.getString("SharkName");
    sharkColor = myResources.getString("SharkColor");
    defaultParameters = myResources.getString("DefaultPredatorPreyParameters");
    fishReproductionKey = myResources.getString("FishReproduction");
    sharkReproductionKey = myResources.getString("SharkReproduction");
    sharkEnergyStartKey = myResources.getString("SharkEnergyStart");
    sharkEnergyGainKey = myResources.getString("SharkEnergyGain");
  }

  @Override
  protected void assignState(
      int state) { // Assume fish (prey) is 1 and shark (predator) is 2 on initial states
    assignThreeCases(state, EMPTY_NAME, DEFAULT_GREY, fishName, fishColor, sharkName,
        sharkColor);
  }

  @Override
  protected void setParameters(String parameters) {
    setCellParameter(fishReproductionKey,
        Double.valueOf(parameters.split(PARAMETER_DELIMINATOR)[0]));
    setCellParameter(sharkReproductionKey,
        Double.valueOf(parameters.split(PARAMETER_DELIMINATOR)[1]));
    setCellParameter(sharkEnergyStartKey,
        Double.valueOf(parameters.split(PARAMETER_DELIMINATOR)[2]));
    setCellParameter(sharkEnergyGainKey,
        Double.valueOf(parameters.split(PARAMETER_DELIMINATOR)[3]));
  }

}
