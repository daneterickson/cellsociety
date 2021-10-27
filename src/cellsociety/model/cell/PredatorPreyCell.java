package cellsociety.model.cell;

import java.util.ResourceBundle;

public class PredatorPreyCell extends ModelCell {

  private String myStartColors;
  private ResourceBundle myResources;

  private String fishName;
  private String fishColor;
  private String sharkName;
  private String sharkColor;
  private String defaultParameters;
  private String fishReproductionKey;
  private String sharkReproductionKey;
  private String sharkEnergyStartKey;
  private String sharkEnergyGainKey;

  public PredatorPreyCell(int i, int j, String startColors, String parameters, int state) {
    super(i, j, startColors, parameters, state);
    myResources = getMyResources();
    assignConstants();
    myStartColors = startColors;
    if (parameters == null) {
      parameters = defaultParameters; // default is fish reproduction, shark reproduction, shark energy start, and shark energy gain are all 10
    }
    setParameters(parameters);
    assignState(state);
  }

  @Override
  protected void assignConstants() {
    fishName = myResources.getString("FishName");
    fishColor = myResources.getString("FishColor");
    sharkName = myResources.getString("SharkName");
    sharkColor = myResources.getString("SharkColor");
    defaultParameters = myResources.getString("DefaultPredatorPreyParameters");
    fishReproductionKey = myResources.getString("FishReproduction");
    sharkReproductionKey = myResources.getString("SharkReproduction");
    sharkEnergyStartKey = myResources.getString("SharkEnergyStart");
    sharkEnergyGainKey = myResources.getString("SharkEnergyGain");
  }

  @Override
  protected void assignState(
      int state) { // Assume fish (prey) is 1 and shark (predator) is 2 on initial states
    if (myStartColors == null || myStartColors.split(PARAMETER_DELIMINATOR).length != 3) {
      assignThreeCases(state, EMPTY_NAME, DEFAULT_GREY, fishName, fishColor, sharkName,
          sharkColor);
    } else {
      String stateColors[] = myStartColors.split(PARAMETER_DELIMINATOR);
      assignThreeCases(state, EMPTY_NAME, stateColors[0], fishName, stateColors[1],
          sharkName, stateColors[2]);
    }
  }

  @Override
  protected void setParameters(String parameters) {
    setCellParameter(fishReproductionKey, Double.valueOf(parameters.split(PARAMETER_DELIMINATOR)[0]));
    setCellParameter(sharkReproductionKey, Double.valueOf(parameters.split(PARAMETER_DELIMINATOR)[1]));
    setCellParameter(sharkEnergyStartKey, Double.valueOf(parameters.split(PARAMETER_DELIMINATOR)[2]));
    setCellParameter(sharkEnergyGainKey, Double.valueOf(parameters.split(PARAMETER_DELIMINATOR)[3]));
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
