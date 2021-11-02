package cellsociety.view.center;

import cellsociety.controller.Controller;
import cellsociety.view.left.CellProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class HistogramView extends CenterView {

  public static final int CANVAS_WIDTH = 700;
  public static final int AXIS_LENGTH = 500;
  public static final int BAR_SPACING = 50;
  public static final int BAR_WIDTH = 100;
  public static final int MAX_BAR_HEIGHT = 400;
  public static final int BOTTOM_SPACING = 15;
  public static final int LABEL_SPACING = BAR_WIDTH + BAR_SPACING / 2;

  private Controller myController;
  private HBox barBox;
  private HBox labelBox;
  private HBox numberBox;
  private VBox histogramElements;
  private List<Rectangle> bars;
  private List<Text> numbers;
  private Map<Integer, Integer> myHistogramMap;
  private Map<Integer, String> myNameColorMap;
  private double totalNumCells;

  public HistogramView(CellProperties cellProps, Controller controller) {
    super(cellProps, controller);
    barBox = new HBox(BAR_SPACING);
    barBox.setAlignment(Pos.BOTTOM_CENTER);
    labelBox = new HBox(LABEL_SPACING);
    labelBox.setAlignment(Pos.TOP_CENTER);
    numberBox = new HBox(LABEL_SPACING);
    numberBox.setAlignment(Pos.TOP_CENTER);
    histogramElements = new VBox(0);
    histogramElements.setAlignment(Pos.BOTTOM_CENTER);
    setStyles();
    myController = controller;
    bars = new ArrayList<>();
    numbers = new ArrayList<>();

  }

  /**
   * Initiates the Histogram after the SIM file has been selected. It creates a VBox and first adds
   * the bars, followed by the axis line, and a buffer. Then it updates the bars once to set the
   * starting values.
   */
  @Override
  public void initiateView() {
    myHistogramMap = myController.getHistogramMap();
    totalNumCells = findTotalCells(myHistogramMap);
    myNameColorMap = myController.getNamesAndColors();
    histogramElements.getChildren().clear();
    makeBars();
    makeAxisLine();
    makeBarLabels();
    makeNumberLabels();
    histogramElements.getChildren()
        .add(new Rectangle(BOTTOM_SPACING, BOTTOM_SPACING, Color.TRANSPARENT));
    updateView();
  }

  private void makeNumberLabels() {
    numbers.clear();
    numberBox.getChildren().clear();
    for (Integer stateNumber : myHistogramMap.keySet()) {
      String percent = String.valueOf(Math.round(Double.valueOf(myHistogramMap.get(stateNumber)) / totalNumCells * 100)) + "%";
      Text num = new Text(percent);
      numbers.add(stateNumber, num);
      numberBox.getChildren().add(num);
    }
    histogramElements.getChildren().add(numberBox);
  }

  private void makeBarLabels() {
    labelBox.getChildren().clear();
    for (Integer state : myHistogramMap.keySet()) {
      String name = myNameColorMap.get(state).split(",")[0];
      Text label = new Text(name);
      labelBox.getChildren().add(label);
    }
    histogramElements.getChildren().add(labelBox);
  }

  private void setStyles() {
    histogramElements.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    histogramElements.getStyleClass().add("barBox");
  }

  private void makeAxisLine() {
    double startX = CANVAS_WIDTH / 2 - AXIS_LENGTH / 2;
    double endX = CANVAS_WIDTH / 2 + AXIS_LENGTH / 2;
    Line xaxis = new Line(startX, 0, endX, 0);
    histogramElements.getChildren().add(xaxis);
  }

  private void makeBars() {
    bars.clear();
    barBox.getChildren().clear();
    for (Integer state : myHistogramMap.keySet()) {
      String color = myNameColorMap.get(state).split(",")[1];
      Rectangle bar = new Rectangle(BAR_WIDTH, 0, Color.web("#" + color));
      bars.add(state, bar);
      barBox.getChildren().add(bar);
    }
    histogramElements.getChildren().add(barBox);
  }

  /**
   * Updates the height of the bars each step of the simulation. The bars are normalized, so they
   * represent percent of the total cells, not absolute values.
   */
  @Override
  public void updateView() {
    if (myHistogramMap == null) initiateView();
    for (Integer stateNumber : myHistogramMap.keySet()) {
      bars.get(stateNumber).setHeight(
          Double.valueOf(myHistogramMap.get(stateNumber)) / totalNumCells * MAX_BAR_HEIGHT);
      numbers.get(stateNumber).setText(String.valueOf(Math.round(Double.valueOf(myHistogramMap.get(stateNumber)) / totalNumCells * 100)) + "%");
    }
  }

  /**
   * Getter method to get the histogram VBox to be displayed on the screen.
   *
   * @return the histogram VBox as a Node to be displayed to the screen.
   */
  @Override
  public Node getViewBox() {
    return histogramElements;
  }

  @Override
  public void addViewToCenter() {}

}
