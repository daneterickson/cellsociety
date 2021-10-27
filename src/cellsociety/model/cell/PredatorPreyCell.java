package cellsociety.model.cell;

import java.awt.Rectangle;
import java.util.ResourceBundle;

public class PredatorPreyCell extends ModelCell {

  private String myStartColors;
  private ResourceBundle myResources;

  private final String FISH_NAME = myResources.getString("FishName");
  private final String FISH_COLOR = myResources.getString("FishColor");
  private final String SHARK_NAME = myResources.getString("SharkName");
  private final String SHARK_COLOR = myResources.getString("SharkColor");
  private final String DEFAULT_PARAMETERS = myResources.getString("DefaultPredatorPreyParameters");
  private final String FISH_REPRODUCTION_KEY = myResources.getString("FishReproduction");
  private final String SHARK_REPRODUCTION_KEY = myResources.getString("SharkReproduction");
  private final String SHARK_ENERGY_START_KEY = myResources.getString("SharkEnergyStart");
  private final String SHARK_ENERGY_GAIN_KEY = myResources.getString("SharkEnergyGain");

  public PredatorPreyCell(int i, int j, String startColors, String parameters, int state) {
    super(i, j, startColors, parameters, state);
    myResources = getMyResources();
    myStartColors = startColors;
    if (parameters == null) {
      parameters = DEFAULT_PARAMETERS; // default is fish reproduction, shark reproduction, shark energy start, and shark energy gain are all 10
    }
    setParameters(parameters);
  }

  @Override
  protected void assignState(
      int state) { // Assume fish (prey) is 1 and shark (predator) is 2 on initial states
    if (myStartColors == null || myStartColors.split(PARAMETER_DELIMINATOR).length != 3) {
      assignThreeCases(state, EMPTY_NAME, DEFAULT_GREY, FISH_NAME, FISH_COLOR, SHARK_NAME,
          SHARK_COLOR);
    } else {
      String stateColors[] = myStartColors.split(PARAMETER_DELIMINATOR);
      assignThreeCases(state, EMPTY_NAME, stateColors[0], FISH_NAME, stateColors[1],
          SHARK_NAME, stateColors[2]);
    }
  }

  @Override
  protected void setParameters(String parameters) {
    setCellParameter(FISH_REPRODUCTION_KEY, Double.valueOf(parameters.split(PARAMETER_DELIMINATOR)[0]));
    setCellParameter(SHARK_REPRODUCTION_KEY, Double.valueOf(parameters.split(PARAMETER_DELIMINATOR)[1]));
    setCellParameter(SHARK_ENERGY_START_KEY, Double.valueOf(parameters.split(PARAMETER_DELIMINATOR)[2]));
    setCellParameter(SHARK_ENERGY_GAIN_KEY, Double.valueOf(parameters.split(PARAMETER_DELIMINATOR)[3]));
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
