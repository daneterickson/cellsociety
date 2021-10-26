package cellsociety.model.cell;

public class SegregationCell extends ModelCell{

  public static final String RACE1_RED = "ff0000";
  public static final String RACE1_NAME = "race1";
  public static final String RACE2_BLUE = "0000ff";
  public static final String RACE2_NAME = "race2";

  private String myStartColors;

  public SegregationCell(int i, int j, String startColors, String parameters, int state) {
    super(i, j, startColors, parameters, state);
    myStartColors = startColors;
    if (parameters == null) {
      parameters = "0.5"; // default 50% similarity threshold
    }
    setParameters(parameters);
  }

  @Override
  protected void assignState(int state) {
    if (myStartColors == null || myStartColors.split(",").length != 3) {
      assignThreeCases(state, EMPTY_NAME, DEFAULT_GREY, RACE1_NAME, RACE1_RED, RACE2_NAME, RACE2_BLUE);
    }
    else {
      String stateColors[] = myStartColors.split(",");
      assignThreeCases(state, EMPTY_NAME, stateColors[0], RACE1_NAME, stateColors[1], RACE2_NAME, stateColors[2]);
    }
  }

  @Override
  protected void setParameters(String parameters) {
    setCellParameter("Threshold", Double.valueOf(parameters.split(",")[0]));
  }
}
