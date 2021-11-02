package cellsociety.view.center;

import cellsociety.controller.Controller;
import cellsociety.view.left.CellProperties;
import java.util.Map;
import javafx.scene.Node;

/**
 * This is the center grid abstract class
 *
 * @author Aaric Han, Nick Strauch
 */

public abstract class CenterView {

  protected static final int CENTER_VIEW_MAX_WIDTH = 300;
  protected static final int CENTER_VIEW_MAX_HEIGHT = 300;
  protected static final int ELEMENT_SPACING = 10;
  private boolean gridLinesOn;
  protected static final String RESOURCE = "cellsociety.view.center.";
  protected static final String STYLESHEET = String.format("/%sCenterView.css",
      RESOURCE.replace(".", "/"));

  /**
   * Default constructor for the CenterView
   *
   * @param cellProperties
   * @param controller
   */
  public CenterView(CellProperties cellProperties, Controller controller) {
    gridLinesOn = true;
  }

  /**
   * Method to start view
   */
  public abstract void initiateView();

  /**
   * Method to update view
   */
  public abstract void updateView();

  /**
   * Method to get the viewbox element
   *
   * @return Node for the view box element
   */
  public abstract Node getViewBox();

  /**
   * Method to add the view to the center of borderpane
   */
  public abstract void addViewToCenter();

  protected double findTotalCells(Map<Integer, Integer> map) {
    int ret = 0;
    for (Integer state : map.keySet()) {
      ret += map.get(state);
    }
    return ret;
  }

  /**
   * Method to toggle grid lines
   */
  public void toggleLines() {
    gridLinesOn = !gridLinesOn;
    updateView();
  }

  protected boolean linesAreOn() {
    return gridLinesOn;
  }


}
