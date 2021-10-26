package cellsociety.model.cell;

import java.util.HashMap;
import java.util.Map;

public abstract class ModelCell {

  public static final String DEFAULT_GREY = "c0c0c0"; // light grey for blank cells
  public static final String EMPTY_NAME = "empty";

  private int myRow;
  private int myCol;
  private Map<String, String> myPropertiesMap;
  private int myStateNumber;
  private String myStateColor;
  private String myStateName;
  private String myStartColors;
  private String myParameters;

  public ModelCell(int i, int j, String stateColors, String parameters, int state) {
    myRow = i;
    myCol = j;
    myPropertiesMap = new HashMap<>();
    myStartColors = stateColors;
    myParameters = parameters;
    myStateNumber = state;
    myPropertiesMap.put("StateNumber", String.valueOf(state));
    assignState(state);
  }

  protected abstract void assignState(int state);

  protected void assignThreeCases (int state, String name0, String color0, String name1, String color1, String name2, String color2) {
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
    myPropertiesMap.put("StateNumber", String.valueOf(newState));
    assignState(newState);
  }

  protected abstract void setParameters (String parameters);

  public void setProperty(String key, String value) {
    myPropertiesMap.put(key, value);
  }

  public String getProperty(String property) { // catch for incorrect property
    return myPropertiesMap.get(property);
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
    myPropertiesMap.put("StateColor", color);
  }

  protected void setStateName(String name) {
    myStateName = name;
    myPropertiesMap.put("StateName", name);
  }

}
