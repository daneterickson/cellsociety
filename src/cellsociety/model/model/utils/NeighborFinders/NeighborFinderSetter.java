package cellsociety.model.model.utils.NeighborFinders;


import java.lang.reflect.InvocationTargetException;

/**
 * helper class that takes a string and returns an NeighborFinder. Allows the user to pick from a
 * dropdown list of strings and change the NeighborFinder
 *
 * @Author Albert Yuan
 */
public class NeighborFinderSetter {

  /**
   * Takes a string and uses reflection to instantiate a new NeighborFinder
   *
   * @param type - the NeighborFinder type as a string
   * @return new NeighborFinder object
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
