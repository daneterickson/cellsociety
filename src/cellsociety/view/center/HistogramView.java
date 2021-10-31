package cellsociety.view.center;

import cellsociety.controller.Controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class HistogramView extends CenterView {

  public static final int CANVAS_WIDTH = 700;
  public static final int CANVAS_HEIGHT = 500;
  public static final int AXIS_LENGTH = 500;
  public static final int BAR_SPACING = 50;
  public static final int BAR_WIDTH = 100;

  private HBox myHistogramHolder;
  private Canvas myCanvas;
  private Controller myController;
  private HBox barBox;
  private VBox histogramElements;
  private int numCases;
  private List<Rectangle> bars;
  private String state0_color;
  private String state1_color;
  private String state2_color;
  private Map<Integer, Integer> myHistogramMap;
  private GraphicsContext gc;

  public HistogramView (Controller controller) {
    myHistogramHolder = new HBox();
    barBox = new HBox(BAR_SPACING);
    histogramElements = new VBox(0);
    myCanvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    gc = myCanvas.getGraphicsContext2D();
    myController = controller;
    myHistogramMap = myController.getHistogramMap();
    bars = new ArrayList<>();
//    numCases = getNumCases(myController.getSimPropertiesMap().get("Type"));
    assignStateColors();
    makeBars();
    makeAxisLine();
  }

  private void assignStateColors() {
  }

  private int getNumCases(String type) {
    if (type.equals("GameOfLife")) return 2;
    return 3;
  }

  private void makeAxisLine() {
    double startX = CANVAS_WIDTH / 2 - AXIS_LENGTH / 2;
    double endX = CANVAS_WIDTH / 2 + AXIS_LENGTH / 2;
    Line xaxis = new Line(startX, 0, endX, 0);
    histogramElements.getChildren().add(xaxis);
  }

  private void makeBars() {
    for (Integer state : myHistogramMap.keySet()) {
      Rectangle bar = new Rectangle(BAR_WIDTH, 0);
      bars.add(bar);
      barBox.getChildren().add(bar);
    }
    histogramElements.getChildren().add(barBox);
  }

  public void updateBars() {
    for (Integer stateNumber : myHistogramMap.keySet()) {
      bars.get(stateNumber).setY(myHistogramMap.get(stateNumber));
    }
  }

  public Node getHistogramBox() {
    return histogramElements;
  }

}
