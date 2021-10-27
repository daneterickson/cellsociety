package cellsociety.model.cell;

import java.util.ResourceBundle;

public class SpreadingOfFireCell extends ModelCell{

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
    if (myStartColors == null || myStartColors.split(PARAMETER_DELIMINATOR).length != 3) {
      assignThreeCases(state, EMPTY_NAME, emptyColor, treeName, treeColor, burnName, burnColor);
    }
    else {
      String stateColors[] = myStartColors.split(PARAMETER_DELIMINATOR);
      assignThreeCases(state, EMPTY_NAME, stateColors[0], treeName, stateColors[1], burnName, stateColors[2]);
    }
  }

  @Override
  protected void setParameters(String parameters) {
    setCellParameter(probCatchKey, Double.valueOf(parameters.split(PARAMETER_DELIMINATOR)[0]));
  }
}
