package cellsociety.model.model.utils;

import static java.lang.Integer.parseInt;


import cellsociety.model.Grid;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistogramManager {

  private HashMap<Integer, String> cellStates;
  private HashMap<Integer, Integer> histogram;
  private Grid currGrid;

  public HistogramManager(Grid grid) {
    currGrid = grid;
    getCellStateValues();
    initHistogram();
  }

  /**
   * creates the histogram map and initializes each of the cellStates in it
   */
  private void initHistogram() {
    histogram = new HashMap<>();
    String name;
    for (Integer state : cellStates.keySet()) {
      histogram.put(state, 0);
    }
  }

  /**
   * gets all static declared cell state variable names which all end with _STATE
   *
   * @return a list of all cell state variable names
   */
  private List<String> getCellStateNames() {
    List<Field> declaredFields = new ArrayList<>();
    List<Field> childFields = List.of(currGrid.getModelCell(0, 0).getClass().getDeclaredFields());
    List<Field> parentFields = List.of(currGrid.getModelCell(0, 0).getClass().getSuperclass().getDeclaredFields());

    declaredFields.addAll(childFields);
    declaredFields.addAll(parentFields);

    List<String> staticFields = new ArrayList<>();
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
        Field f = currGrid.getModelCell(0, 0).getClass().getField(state);
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
  public HashMap<Integer, Integer> getHistogramManager() {
    return histogram;
  }

  /**
   * adds the given values to the histogram
   */
  public void add(Integer cellStateNum, int amount) {
//    String name = cellStates.get(cellStateNum);
    int total = histogram.get(cellStateNum) + amount;
    histogram.put(cellStateNum, total);
  }

  /**
   * clears the map. this is called everytime the model is updated
   */
  public void clear() {
    for (Integer key : histogram.keySet()){
      histogram.put(key,0);
    }
  }
}
