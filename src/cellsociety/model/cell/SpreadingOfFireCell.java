package cellsociety.model.cell;

import java.util.ResourceBundle;

public class SpreadingOfFireCell extends ModelCell{

  private String myStartColors;
  private ResourceBundle myResources;

  private final String EMPTY_COLOR = myResources.getString("EmptyColor");
  private final String TREE_COLOR = myResources.getString("TreeColor");
  private final String TREE_NAME = myResources.getString("TreeName");
  private final String BURN_COLOR = myResources.getString("BurnColor");
  private final String BURN_NAME = myResources.getString("BurnName");
  private final String DEFAULT_PROB_CATCH = myResources.getString("DefaultProbCatch");
  private final String PROB_CATCH_KEY = myResources.getString("ProbCatch");

  public SpreadingOfFireCell(int i, int j, String startColors, String parameters, int state) {
    super(i, j, startColors, parameters, state);
    myResources = getMyResources();
    myStartColors = startColors;
    if (parameters == null) {
      parameters = DEFAULT_PROB_CATCH; // default 50% probCatch
    }
    setParameters(parameters);  }

  @Override
  protected void assignState(int state) {
    if (myStartColors == null || myStartColors.split(PARAMETER_DELIMINATOR).length != 3) {
      assignThreeCases(state, EMPTY_NAME, EMPTY_COLOR, TREE_NAME, TREE_COLOR, BURN_NAME, BURN_COLOR);
    }
    else {
      String stateColors[] = myStartColors.split(PARAMETER_DELIMINATOR);
      assignThreeCases(state, EMPTY_NAME, stateColors[0], TREE_NAME, stateColors[1], BURN_NAME, stateColors[2]);
    }
  }

  @Override
  protected void setParameters(String parameters) {
    setCellParameter(PROB_CATCH_KEY, Double.valueOf(parameters.split(PARAMETER_DELIMINATOR)[0]));
  }
}
