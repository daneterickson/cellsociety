package cellsociety.model.cell;

import cellsociety.model.exceptions.KeyNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Super class for the model cells for each simulation type. Each object stores its position, state,
 * and other properties like color and parameters for the simulation. Methods allow programmer to
 * get and set some properties for the cell.
 */
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

  /**
   * Constructor for a ModelCell object, which is the super class of each simulation cell type
   *
   * @param i           is the row position of the cell being created
   * @param j           is the column position of the cell being created
   * @param stateColors is a comma separated String of the state colors for each state
   * @param parameters  is a comma separated String of the parameters (may be "" for no parameters)
   * @param state       is the state of the cell being created
   */
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
  /**
   * Changes the ModelCell's state based on the inputted value.
   *
   * @param newState is the new state for the cell
   * @throws IndexOutOfBoundsException if the newState is greater than the mas acceptable state
   */
  public void changeState(int newState) throws IndexOutOfBoundsException {
    if (newState >= numCases) {
      throw new IndexOutOfBoundsException();
    }
    myStateNumber = newState;
    myCellProperties.put(STATE_NUMBER_KEY, String.valueOf(newState));
    assignState(newState);
  }

  protected void setStateName(String name) {
    myStateName = name;
    myCellProperties.put(STATE_NAME_KEY, name);
  }

  /**
   * Sets a parameter for the cell based on the inputted key for the parameter map and new value.
   * Parameters include info like probCatch and PredatorPrey energy levels and is only used in back
   * end
   *
   * @param key   is the key for the specific parameter in the parameters map
   * @param value is the new value of the parameter
   */
  public void setCellParameter(String key, Double value) {
    myCellParameters.put(key, value);
  }

  /**
   * Finds the value of a given parameter for the cell, which is only used in back end.
   *
   * @param parameter is the key for the specific parameter in the parameters map
   * @return the Double value for the inputted parameter
   * @throws KeyNotFoundException if there is no matching parameter in the parameters map
   */
  public Double getCellParameter(String parameter) throws KeyNotFoundException {
    if (!myCellParameters.containsKey(parameter)) {
      throw new KeyNotFoundException("Invalid Parameter");
    }
    return myCellParameters.get(parameter);
  }

  /**
   * Finds the String value of a specific property for the cell.
   * Properties include info like state number, state name, etc.
   *
   * @param property is the String key for the specific property in the properties map
   * @return the String value for the inputted property type
   * @throws KeyNotFoundException if there is no matching property in the properties map
   */
  public String getCellProperty(String property) throws KeyNotFoundException {
    if (!myCellProperties.containsKey(property)) {
      throw new KeyNotFoundException("Invalid Property");
    }
    return myCellProperties.get(property);
  }

}
