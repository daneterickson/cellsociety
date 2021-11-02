package cellsociety.model.model.utils.NeighborFinders;


import java.lang.reflect.InvocationTargetException;

public class NeighborFinderSetter {

  /**
   * Takes a string and uses reflection to instantiate a new neighborfinder
   * returns the new neighborfinder object
   */
  public NeighborFinder setNeighborFinder(String type) {
    String neighborFinderType = String.format("cellsociety.model.model.utils.NeighborFinders.%s",
        type);
    NeighborFinder neighborFinder = null;
    try {
      Class<?> clazz = Class.forName(neighborFinderType);
      neighborFinder = (NeighborFinder) clazz.getDeclaredConstructor()
          .newInstance();
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
      System.out.println("Method Not Found");
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      System.out.println("Class Not Found");
      e.printStackTrace();
    }
    return neighborFinder;
  }
}
