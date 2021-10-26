package cellsociety.model.cell;

import java.util.Map;

public class PredatorPreyCell extends ModelCell {

  public static final String FISH_NAME = "fish";
  public static final String FISH_COLOR = "00ff00";
  public static final String SHARK_NAME = "shark";
  public static final String SHARK_COLOR = "0000ff";

  private double mySharkEnergy;
  private double mySharkReproduction;
  private double myFishReproduction;

  private Map<Integer, String> myStartColors;

  public PredatorPreyCell(int i, int j, Map<Integer, String> startColors, int state) {
    super(i, j, startColors, state);
    myStartColors = startColors;
  }

  @Override
  protected void assignState(
      int state) { // Assume fish (prey) is 1 and shark (predator) is 2 on initial states
    if (myStartColors == null || myStartColors.keySet().size() != 3) {
      assignThreeCases(state, EMPTY_NAME, DEFAULT_GREY, FISH_NAME, FISH_COLOR, SHARK_NAME,
          SHARK_COLOR);
    } else {
      assignThreeCases(state, EMPTY_NAME, myStartColors.get(0), FISH_NAME, myStartColors.get(1),
          SHARK_NAME, myStartColors.get(2));
    }
  }

  /**
   * Method to set the parameters for the sharks beginning energy level as well as the number of
   * iterations before the shark and fish can reproduce. All three parameters are set at once, so
   * they must be inputted in the correct order.
   * Must be set right after cell is created.
   *
   * @param sharkEnergy       is the initial energy level for the shark
   * @param sharkReproduction is the number of iterations before the shark reproduces
   * @param fishReproduction  is the number of iterations before fish reproduces
   */
  public void setParameters(double sharkEnergy, double sharkReproduction, double fishReproduction) {
    mySharkEnergy = sharkEnergy;
    mySharkReproduction = sharkReproduction;
    myFishReproduction = fishReproduction;
  }

  public double getSharkEnergy () {
    return mySharkEnergy;
  }

  public double getSharkReproduction () {
    return mySharkReproduction;
  }

  public double getFishReproduction () {
    return myFishReproduction;
  }
}
