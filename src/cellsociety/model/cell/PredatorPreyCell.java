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

  private String myStartColors;
  private String myParameters;

  public PredatorPreyCell(int i, int j, String startColors, String parameters, int state) {
    super(i, j, startColors, parameters, state);
    myStartColors = startColors;
    myParameters = parameters;
    if (parameters != null) setParameters(parameters);
  }

  @Override
  protected void assignState(
      int state) { // Assume fish (prey) is 1 and shark (predator) is 2 on initial states
    if (myStartColors == null || myStartColors.split(",").length != 3) {
      assignThreeCases(state, EMPTY_NAME, DEFAULT_GREY, FISH_NAME, FISH_COLOR, SHARK_NAME,
          SHARK_COLOR);
    } else {
      String stateColors[] = myStartColors.split(",");
      assignThreeCases(state, EMPTY_NAME, stateColors[0], FISH_NAME, stateColors[1],
          SHARK_NAME, stateColors[2]);
    }
  }

  @Override
  protected void setParameters(String parameters) {
    setProperty("FishReproduction", parameters.split(",")[0]);
    setProperty("SharkReproduction", parameters.split(",")[1]);
    setProperty("SharkEnergy", parameters.split(",")[2]);
  }

//  public double getSharkEnergy () {
//    return mySharkEnergy;
//  }
//
//  public double getSharkReproduction () {
//    return mySharkReproduction;
//  }
//
//  public double getFishReproduction () {
//    return myFishReproduction;
//  }
}
