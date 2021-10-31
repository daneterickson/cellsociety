package cellsociety.model.cell;

import java.util.ResourceBundle;

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

  public SegregationCell(int i, int j, String startColors, String parameters, int state) {
    super(i, j, startColors, parameters, state);
    myResources = getMyResources();
    assignConstants();
    myStartColors = startColors;
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
