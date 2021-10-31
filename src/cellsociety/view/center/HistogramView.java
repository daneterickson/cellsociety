package cellsociety.view.center;

import cellsociety.controller.Controller;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
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
  private HBox bars;
  private VBox histogramElements;
  private int numCases;
  private Rectangle bar0;
  private Rectangle bar1;
  private Rectangle bar2;
  private String state0_color;
  private String state1_color;
  private String state2_color;

  public HistogramView (Controller controller) {
    myHistogramHolder = new HBox();
    bars = new HBox(BAR_SPACING);
    histogramElements = new VBox(0);
    myCanvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    myController = controller;
    numCases = getNumCases(myController.getSimPropertiesMap().get("Type"));
    assignStateColors();
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
    bar0 = new Rectangle(BAR_WIDTH, 0);
    bar1 = new Rectangle(BAR_WIDTH, 0);
    bars.getChildren().add(bar0);
    bars.getChildren().add(bar1);
    if (numCases > 2) {
      bar2 = new Rectangle(BAR_WIDTH, 0);
      bars.getChildren().add(bar2);
    }
  }

  public Canvas getCanvas() { return myCanvas; }

}
