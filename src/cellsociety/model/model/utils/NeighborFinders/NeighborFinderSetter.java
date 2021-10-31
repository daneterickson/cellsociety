package cellsociety.model.model.utils.NeighborFinders;

import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import java.lang.reflect.InvocationTargetException;

public class NeighborFinderSetter {

  public NeighborFinder setNeighborFinder(String type, EdgePolicies currEdgePolicy) {
    String neighborFinderType = String.format("cellsociety.model.model.utils.NeighborFinders.%s",
        type);
    NeighborFinder neighborFinder = null;
    try {
      Class<?> clazz = Class.forName(neighborFinderType);
      neighborFinder = (NeighborFinder) clazz.getDeclaredConstructor(EdgePolicies.class)
          .newInstance(currEdgePolicy);
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
