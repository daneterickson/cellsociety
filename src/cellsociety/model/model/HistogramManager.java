package cellsociety.model.model;

import static java.lang.Integer.parseInt;


import cellsociety.model.Grid;
import cellsociety.model.exceptions.KeyNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistogramManager {

  private HashMap<Integer, String> cellStates;
  private HashMap<String, ArrayList<Integer>> histogram;
  private Grid currGrid;

  public HistogramManager(Grid grid) {
    currGrid = grid;
    getCellStateValues();
    initHistogram();
  }

  private void initHistogram() {
    histogram = new HashMap<>();
    String name;
    for (Integer state : cellStates.keySet()) {
      name = cellStates.get(state);
      histogram.put(name, new ArrayList<>());
    }
  }

  /**
   * gets all static declared cell state variable names which all end with _STATE
   *
   * @return a list of all cell state variable names
   */
  private List<String> getCellStateNames() {
    List<Field> declaredFields = new ArrayList<>();
    List<Field> childFields = List.of(currGrid.getCell(0, 0).getClass().getDeclaredFields());
    List<Field> parentFields = List.of(currGrid.getCell(0, 0).getClass().getSuperclass().getDeclaredFields());

    declaredFields.addAll(childFields);
    declaredFields.addAll(parentFields);

    List<String> staticFields = new ArrayList<String>();
    String fieldName;
    for (Field field : declaredFields) {
      if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
        fieldName = field.getName();
        if (fieldName.contains("_STATE")) {
          staticFields.add(fieldName);
        }
      }
    }
    return staticFields;
  }

  /**
   * creates a mapping from the cell state number to the cell state name if no cell state name is
   * specified for 0, the EMPTY_STATE from superclass model is used
   */
  private void getCellStateValues() {
    cellStates = new HashMap<>();
    for (String state : getCellStateNames()) {
      try {
        Field f = currGrid.getCell(0, 0).getClass().getField(state);
        Class<?> t = f.getType();
        if (t == int.class) {
          cellStates.putIfAbsent(f.getInt(null), state);
        }
      } catch (NoSuchFieldException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * @return the histogram as a map of cell state names to arrays of the amounts of each type of
   * cell state per round
   */
  public HashMap<String, ArrayList<Integer>> getHistogram() {
    return histogram;
  }

  /**
   * @return a blank map with preinputted keys of the cell state values
   */
  public HashMap makeBlankMap() {
    HashMap<Integer, Integer> temp = new HashMap();
    for (Integer state : cellStates.keySet()) {
      temp.put(state, 0);
    }
    return temp;
  }

  /**
   * adds the given values to the histogram
   * @param temp - a map of cell state numbers to the frequency of them in the simulation
   */
  public void addPlotPoints(HashMap<Integer, Integer> temp) {
    String name;
    Integer value;
    for (Integer state : temp.keySet()) {
      name = cellStates.get(state);
      value = temp.get(state);
//      System.out.println(name + " : " + value);
      histogram.get(name).add(value);
    }
  }
}
