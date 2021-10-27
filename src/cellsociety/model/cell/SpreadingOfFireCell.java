package cellsociety.model.cell;

public class SpreadingOfFireCell extends ModelCell{

  public static final String EMPTY_COLOR = "ffff00";
  public static final String TREE_COLOR = "00ff00";
  public static final String TREE_NAME = "tree";
  public static final String BURN_COLOR = "ff0000";
  public static final String BURN_NAME = "burn";

  private String myStartColors;

  public SpreadingOfFireCell(int i, int j, String startColors, String parameters, int state) {
    super(i, j, startColors, parameters, state);
    myStartColors = startColors;
    if (parameters == null) {
      parameters = "0.5"; // default 50% probCatch
    }
    setParameters(parameters);  }

  @Override
  protected void assignState(int state) {
    if (myStartColors == null || myStartColors.split(",").length != 3) {
      assignThreeCases(state, EMPTY_NAME, EMPTY_COLOR, TREE_NAME, TREE_COLOR, BURN_NAME, BURN_COLOR);
    }
    else {
      String stateColors[] = myStartColors.split(",");
      assignThreeCases(state, EMPTY_NAME, stateColors[0], TREE_NAME, stateColors[1], BURN_NAME, stateColors[2]);
    }
  }

  @Override
  protected void setParameters(String parameters) {
    setCellParameter("ProbCatch", Double.valueOf(parameters.split(",")[0]));
  }
}
