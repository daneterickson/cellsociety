package cellsociety.model.model.utils.EdgePolicies;

import cellsociety.model.model.utils.NeighborFinders.NeighborFinder;
import java.lang.reflect.InvocationTargetException;

public class EdgePolicySetter {

  /**
   * Takes a string and uses reflection to instantiate a new edgepolicy
   * returns the new edgepolicy object
   */
  public EdgePolicies setEdgePolicy(String type) {
    String edgePolicyType = String.format("cellsociety.model.model.utils.EdgePolicies.%sEdgePolicy", type);
    EdgePolicies edgePolicy = null;
    try {
      Class<?> EdgePolicyclazz = Class.forName(edgePolicyType);
      edgePolicy = (EdgePolicies) EdgePolicyclazz.getDeclaredConstructor()
          .newInstance();
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
      System.out.println("Method Not Found");
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      System.out.println("Class Not Found");
      e.printStackTrace();
    }
    return edgePolicy;
  }
}
