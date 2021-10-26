package cellsociety.model.cell;

import java.util.Map;

public class PredatorPreyCell extends ModelCell{

  public static final String FISH_NAME = "fish";
  public static final String FISH_COLOR = "00ff00";
  public static final String SHARK_NAME = "shark";
  public static final String SHARK_COLOR = "0000ff";

  private Map<Integer, String> myStartColors;

  public PredatorPreyCell(int i, int j, Map<Integer, String> startColors, int state) {
    super(i, j, startColors, state);
    myStartColors = startColors;
  }

  @Override
  protected void assignState(int state) { // Assume fish (prey) is 1 and shark (predator) is 2 on initial states
    if (myStartColors == null || myStartColors.keySet().size() != 3) {
      assignThreeCases(state, EMPTY_NAME, DEFAULT_GREY, FISH_NAME, FISH_COLOR, SHARK_NAME, SHARK_COLOR);
    }
    else {
      assignThreeCases(state, EMPTY_NAME, myStartColors.get(0), FISH_NAME, myStartColors.get(1), SHARK_NAME, myStartColors.get(2));
    }
  }

}
