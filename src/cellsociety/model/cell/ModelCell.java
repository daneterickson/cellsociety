package cellsociety.model.cell;

import java.util.HashMap;
import java.util.Map;

public abstract class ModelCell {

  public static final String DEFAULT_GREY = "c0c0c0"; // light grey for blank cells
  public static final String EMPTY_NAME = "empty";

  private int myRow;
  private int myCol;
  private Map<String, String> myCellProperties;
  private Map<String, Double> myCellParameters;
  private int myStateNumber;
  private String myStateColor;
  private String myStateName;
  private String myStartColors;

  public ModelCell(int i, int j, String stateColors, String parameters, int state) {
    myRow = i;
    myCol = j;
    myCellProperties = new HashMap<>();
    myCellParameters = new HashMap<>();
    myStartColors = stateColors;
    myStateNumber = state;
    myCellProperties.put("StateNumber", String.valueOf(state));
    assignState(state);
  }

  protected abstract void assignState(int state);

  protected void assignThreeCases (int state, String name0, String color0, String name1, String color1, String name2, String color2) {
    myCellProperties.put("NumCases", "3");
    switch (state) {
      case 0 -> {
        setStateColor(color0);
        setStateName(name0);
      }
      case 1 -> {
        setStateColor(color1);
        setStateName(name1);
      }
      case 2 -> {
        setStateColor(color2);
        setStateName(name2);
      }
    }
  }

  protected void assignTwoCases (int state, String name0, String color0, String name1, String color1) {
    myCellProperties.put("NumCases", "2");
    switch (state) {
      case 0 -> {
        setStateColor(color0);
        setStateName(name0);
      }
      case 1 -> {
        setStateColor(color1);
        setStateName(name1);
      }
    }
  }

  public void changeState(int newState) {
    myStateNumber = newState;
    myCellProperties.put("StateNumber", String.valueOf(newState));
    assignState(newState);
  }

  protected abstract void setParameters (String parameters);

  public void setCellParameter(String key, Double value) {
    myCellParameters.put(key, value);
  }

  public Double getCellParameter(String parameter) { // catch for incorrect property
    return myCellParameters.get(parameter);
  }

  public String getCellProperty(String property) { // catch for incorrect property
    return myCellProperties.get(property);
  }

//  public int getStateNumber() {
//    return myStateNumber;
//  }

//  public String getStateName() {
//    return myStateName;
//  }

//  public String getStateColor() {
//    return myStateColor;
//  }

  protected void setStateColor(String color) {
    myStateColor = color;
    myCellProperties.put("StateColor", color);
  }

  protected void setStateName(String name) {
    myStateName = name;
    myCellProperties.put("StateName", name);
  }

}
