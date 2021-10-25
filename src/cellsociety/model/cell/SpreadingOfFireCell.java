package cellsociety.model.cell;

import java.util.Map;

public class SpreadingOfFireCell extends ModelCell{

  public static final String EMPTY_COLOR = "ffff00";
  public static final String TREE_COLOR = "00ff00";
  public static final String TREE_NAME = "tree";
  public static final String BURN_COLOR = "ff0000";
  public static final String BURN_NAME = "burn";

  private Map<Integer, String> myStartColors;

  public SpreadingOfFireCell(int i, int j, Map<Integer, String> startColors, int state) {
    super(i, j, startColors, state);
    myStartColors = startColors;
  }

  @Override
  protected void assignState(int state) {
    if (myStartColors == null || myStartColors.keySet().size() != 3) {
      assignThreeCases(state, EMPTY_NAME, EMPTY_COLOR, TREE_NAME, TREE_COLOR, BURN_NAME, BURN_COLOR);
    }
    else {
      assignThreeCases(state, EMPTY_NAME, myStartColors.get(0), TREE_NAME, myStartColors.get(1), BURN_NAME, myStartColors.get(2));
    }
  }
}
