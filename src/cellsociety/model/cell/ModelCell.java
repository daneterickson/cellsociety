package cellsociety.model.cell;

import cellsociety.model.exceptions.KeyNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class ModelCell implements ModelCellInterface, ViewCellInterface {

  private int myRow;
  private int myCol;
  private Map<String, String> myCellProperties;
  private Map<String, Double> myCellParameters;
  private int myStateNumber;
  private String myStateColor;
  private String myStateName;
  private String myStartColors;
  private int numCases;
  private ResourceBundle myResources = ResourceBundle.getBundle(
      String.format("%s%s", DEFAULT_RESOURCES, RESOURCES_LANGUAGE));

  private static final String THREE_CASES = "3";
  private static final String TWO_CASES = "2";
  private static final String DEFAULT_RESOURCES = "cellsociety.model.resources.";
  private static final String RESOURCES_LANGUAGE = "English";
  private final String STATE_NUMBER_KEY = myResources.getString("StateNumber");
  private final String STATE_NAME_KEY = myResources.getString("StateName");
  private final String STATE_COLOR_KEY = myResources.getString("StateColor");
  private final String NUMBER_CASES_KEY = myResources.getString("NumCases");
  public final String DEFAULT_GREY = myResources.getString("DefaultGrey");
  public final String EMPTY_NAME = myResources.getString("EmptyName");
  public final String PARAMETER_DELIMINATOR = myResources.getString("ParameterDeliminator");
  public static final int EMPTY_STATE = 0;


  public ModelCell(int i, int j, String stateColors, String parameters, int state) {
    myRow = i;
    myCol = j;
    myCellProperties = new HashMap<>();
    myCellParameters = new HashMap<>();
    myStartColors = stateColors;
    myStateNumber = state;
    myCellProperties.put(STATE_NUMBER_KEY, String.valueOf(state));
  }

  protected abstract void assignConstants();

  protected ResourceBundle getMyResources() {
    return myResources;
  }

  protected abstract void assignState(int state);

  protected void assignThreeCases(int state, String name0, String color0, String name1,
      String color1, String name2, String color2) {
    numCases = 3;
    myCellProperties.put(NUMBER_CASES_KEY, THREE_CASES);
    if (myStartColors == null || myStartColors.split(PARAMETER_DELIMINATOR).length != 3) {
      switchThreeCases(state, name0, color0, name1, color1, name2, color2);
    } else {
      String stateColors[] = myStartColors.split(PARAMETER_DELIMINATOR);
      switchThreeCases(state, name0, stateColors[0], name1, stateColors[1], name2, stateColors[2]);
    }
  }

  private void switchThreeCases(int state, String name0, String color0, String name1, String color1,
      String name2, String color2) {
    switch (state) {
      case EMPTY_STATE -> {
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

  protected void assignTwoCases(int state, String name0, String color0, String name1,
      String color1) {
    numCases = 2;
    myCellProperties.put(NUMBER_CASES_KEY, TWO_CASES);
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

  protected abstract void setParameters(String parameters);

  protected void setStateColor(String color) {
    myStateColor = color;
    myCellProperties.put(STATE_COLOR_KEY, color);
  }

  protected void setStateName(String name) {
    myStateName = name;
    myCellProperties.put(STATE_NAME_KEY, name);
  }

  public void setCellParameter(String key, Double value) {
    myCellParameters.put(key, value);
  }

  public Double getCellParameter(String parameter) throws KeyNotFoundException {
    if (!myCellParameters.containsKey(parameter)) {
      throw new KeyNotFoundException("Invalid Parameter");
    }
    return myCellParameters.get(parameter);
  }

  public String getCellProperty(String property) throws KeyNotFoundException {
    if (!myCellProperties.containsKey(property)) {
      throw new KeyNotFoundException("Invalid Property");
    }
    return myCellProperties.get(property);
  }

  public void changeState(int newState) {
    if (newState >= numCases) {
      throw new IndexOutOfBoundsException();
    }
    myStateNumber = newState;
    myCellProperties.put(STATE_NUMBER_KEY, String.valueOf(newState));
    assignState(newState);
  }

}
