package cellsociety.model.cell;

import java.util.ResourceBundle;

public class SpreadingOfFireCell extends ModelCell {

  public static final int TREE_STATE = 1;
  public static final int BURN_STATE = 2;

  private String myStartColors;
  private ResourceBundle myResources;
  private String emptyColor;
  private String treeColor;
  private String treeName;
  private String burnColor;
  private String burnName;
  private String defaultProbCatch;
  private String probCatchKey;

  public SpreadingOfFireCell(int i, int j, String startColors, String parameters, int state) {
    super(i, j, startColors, parameters, state);
    myResources = getMyResources();
    assignConstants();
    myStartColors = startColors;
    if (parameters == null) {
      parameters = defaultProbCatch; // default 50% probCatch
    }
    setParameters(parameters);
    assignState(state);
  }

  @Override
  protected void assignConstants() {
    emptyColor = myResources.getString("EmptyColor");
    treeColor = myResources.getString("TreeColor");
    treeName = myResources.getString("TreeName");
    burnColor = myResources.getString("BurnColor");
    burnName = myResources.getString("BurnName");
    defaultProbCatch = myResources.getString("DefaultProbCatch");
    probCatchKey = myResources.getString("ProbCatch");
  }

  @Override
  protected void assignState(int state) {
    assignThreeCases(state, EMPTY_NAME, emptyColor, treeName, treeColor, burnName, burnColor);
  }

  @Override
  protected void setParameters(String parameters) {
    setCellParameter(probCatchKey, Double.valueOf(parameters.split(PARAMETER_DELIMINATOR)[0]));
  }
}
