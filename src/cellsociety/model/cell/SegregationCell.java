package cellsociety.model.cell;

import java.util.ResourceBundle;

public class SegregationCell extends ModelCell{

  private String myStartColors;
  private ResourceBundle myResources;

  private final String RACE1_COLOR = myResources.getString("Race1Color");
  private final String RACE1_NAME = myResources.getString("Race1Name");
  private final String RACE2_COLOR = myResources.getString("Race2Color");
  private final String RACE2_NAME = myResources.getString("Race2Name");
  private final String DEFAULT_THRESHOLD = myResources.getString("DefaultThreshold");
  private final String SIMILARITY_THRESHOLD_KEY = myResources.getString("Threshold");

  public SegregationCell(int i, int j, String startColors, String parameters, int state) {
    super(i, j, startColors, parameters, state);
    myResources = getMyResources();
    myStartColors = startColors;
    if (parameters == null) {
      parameters = DEFAULT_THRESHOLD; // default 50% similarity threshold
    }
    setParameters(parameters);
  }

  @Override
  protected void assignState(int state) {
    if (myStartColors == null || myStartColors.split(PARAMETER_DELIMINATOR).length != 3) {
      assignThreeCases(state, EMPTY_NAME, DEFAULT_GREY, RACE1_NAME, RACE1_COLOR, RACE2_NAME,
          RACE2_COLOR);
    }
    else {
      String stateColors[] = myStartColors.split(PARAMETER_DELIMINATOR);
      assignThreeCases(state, EMPTY_NAME, stateColors[0], RACE1_NAME, stateColors[1], RACE2_NAME, stateColors[2]);
    }
  }

  @Override
  protected void setParameters(String parameters) {
    setCellParameter(SIMILARITY_THRESHOLD_KEY, Double.valueOf(parameters.split(PARAMETER_DELIMINATOR)[0]));
  }
}
