package cellsociety.view.center;

import cellsociety.controller.Controller;
import cellsociety.view.left.CellProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This is a subclass of Grid View
 *
 * @author Nick Strauch
 */

public class CircleTimeline extends CenterView {

  private Map<Integer, Integer> myCircleTimelineMap;
  private Controller myController;
  private Map<Integer, String> myNameColorMap;
  private List<HBox> circlesBox;
  private VBox stateLinesBox;

  /**
   * Constructor for a type of centerview
   *
   * @param cellProperties
   * @param controller
   */
  public CircleTimeline(CellProperties cellProperties, Controller controller) {
    super(cellProperties, controller);
    myController = controller;
    circlesBox = new ArrayList<>();
    stateLinesBox = new VBox();
  }

  /**
   * Initiate the timeline
   */
  @Override
  public void initiateView() {
    myCircleTimelineMap = myController.getHistogramMap();
    myNameColorMap = myController.getNamesAndColors();
    makeStartCircles();
  }

  private void makeStartCircles() {
  }

  @Override
  public void updateView() {

  }

  @Override
  public Node getViewBox() {
    return null;
  }

  @Override
  public void addViewToCenter() {

  }
}
