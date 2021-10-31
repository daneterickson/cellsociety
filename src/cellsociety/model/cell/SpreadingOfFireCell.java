package cellsociety.model.cell;

import java.util.ResourceBundle;

/**
 * Subclass of ModelCell that makes a cell for each spot in Spreading Of Fire simulation
 */
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

  /**
   * Constructor for a SpreadingOfFireCell object, which is extended from the ModelCell super class
   *
   * @param i           is the row position of the cell being created
   * @param j           is the column position of the cell being created
   * @param stateColors is a comma separated String of the state colors for each state
   * @param parameters  is a comma separated String of the parameters (may be "" for no parameters)
   * @param state       is the state of the cell being created
   */
  public SpreadingOfFireCell(int i, int j, String stateColors, String parameters, int state) {
    super(i, j, stateColors, parameters, state);
    myResources = getMyResources();
    assignConstants();
    myStartColors = stateColors;
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
