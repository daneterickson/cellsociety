package cellsociety.model.cell;

import java.util.ResourceBundle;

/**
 * Subclass of ModelCell that makes a cell for each spot in Segregation simulation
 *
 * @Author Dane Erickson
 */
public class SegregationCell extends ModelCell {

  public static final int RACE1_STATE = 1;
  public static final int RACE2_STATE = 2;

  private String myStartColors;
  private ResourceBundle myResources;
  private String race1Color;
  private String race1Name;
  private String race2Color;
  private String race2Name;
  private String defaultThreshold;
  private String similarityThresholdKey;

  /**
   * Constructor for a SegregationCell object, which is extended from the ModelCell super class
   *
   * @param i           is the row position of the cell being created
   * @param j           is the column position of the cell being created
   * @param stateColors is a comma separated String of the state colors for each state
   * @param parameters  is a comma separated String of the parameters (may be "" for no parameters)
   * @param state       is the state of the cell being created
   */
  public SegregationCell(int i, int j, String stateColors, String parameters, int state) {
    super(i, j, stateColors, parameters, state);
    myResources = getMyResources();
    assignConstants();
    myStartColors = stateColors;
    if (parameters == null) {
      parameters = defaultThreshold; // default 50% similarity threshold
    }
    setParameters(parameters);
    assignState(state);
  }

  @Override
  protected void assignConstants() {
    race1Color = myResources.getString("Race1Color");
    race1Name = myResources.getString("Race1Name");
    race2Color = myResources.getString("Race2Color");
    race2Name = myResources.getString("Race2Name");
    defaultThreshold = myResources.getString("DefaultThreshold");
    similarityThresholdKey = myResources.getString("Threshold");
  }

  @Override
  protected void assignState(int state) {
    assignThreeCases(state, EMPTY_NAME, DEFAULT_GREY, race1Name, race1Color, race2Name,
        race2Color);
  }

  @Override
  protected void setParameters(String parameters) {
    setCellParameter(similarityThresholdKey,
        Double.valueOf(parameters.split(PARAMETER_DELIMINATOR)[0]));
  }
}
