package cellsociety.view.center;

import cellsociety.controller.Controller;
import cellsociety.view.left.CellProperties;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

/**
 * This is a subclass of Centerview
 *
 * @author Dane Erickson
 */
public class PieChartView extends CenterView {

  private Map<Integer, Integer> myPieChartMap;
  private Controller myController;
  private Map<Integer, String> myNameColorMap;
  private PieChart myChart;

  /**
   * Constructor for a type of Centerview
   *
   * @param cellProperties
   * @param controller
   */
  public PieChartView(CellProperties cellProperties, Controller controller) {
    super(cellProperties, controller);
    myController = controller;
  }

  @Override
  public void initiateView() {
    myPieChartMap = myController.getHistogramMap();
    myNameColorMap = myController.getNamesAndColors();
    ObservableList<Data> pieChartData = FXCollections.observableArrayList();
    for (Integer state : myPieChartMap.keySet()) {
      String name = myNameColorMap.get(state).split(",")[0];
      int data = myPieChartMap.get(state);
      pieChartData.add(new PieChart.Data(name, data));
    }
    myChart = new PieChart(pieChartData);
  }

  /**
   * Updates view by initating view
   */
  @Override
  public void updateView() {
    initiateView();
  }

  /**
   * Return the view box which is a chart
   *
   * @return myChart
   */
  @Override
  public Node getViewBox() {
    return myChart;
  }

  /**
   * Does nothing
   */
  @Override
  public void addViewToCenter() {

  }
}
