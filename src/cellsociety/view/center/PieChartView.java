package cellsociety.view.center;

import cellsociety.controller.Controller;
import cellsociety.view.left.CellProperties;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

public class PieChartView extends CenterView {

  private Map<Integer, Integer> myPieChartMap;
  private Controller myController;
  private Map<Integer, String> myNameColorMap;
  private PieChart myChart;

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

  @Override
  public void updateView() {
    initiateView();
  }

  @Override
  public Node getViewBox() {
    return myChart;
  }

  @Override
  public void addViewToCenter() {

  }
}
