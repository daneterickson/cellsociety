package cellsociety.model.cell;

public class SpreadingOfFireCell extends ModelCell{

  public static final String EMPTY_COLOR = "ffff00";
  public static final String EMPTY_NAME = "empty";
  public static final String TREE_COLOR = "00ff00";
  public static final String TREE_NAME = "tree";
  public static final String BURN_COLOR = "ff0000";
  public static final String BURN_NAME = "burn";

  public SpreadingOfFireCell(int i, int j, int state) {
    super(i, j, state);
  }

  @Override
  protected void assignState(int state) {
    switch (state) {
      case 0 -> {
        setStateColor(EMPTY_COLOR);
        setStateName(EMPTY_NAME);
      }
      case 1 -> {
        setStateColor(TREE_COLOR);
        setStateName(TREE_NAME);
      }
      case 2 -> {
        setStateColor(BURN_COLOR);
        setStateName(BURN_NAME);
      }
    }
  }
}
