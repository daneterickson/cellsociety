package cellsociety.view.center;

import static cellsociety.view.center.HistogramView.CANVAS_WIDTH;

import cellsociety.controller.Controller;
import cellsociety.view.left.CellProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class CircleTimelineView extends CenterView {

  public static final int MAX_CIRCLE_RADIUS = 20;
  public static final int LINE_SPACING = 30;

  private Map<Integer, Integer> myCircleTimelineMap;
  private Controller myController;
  private Map<Integer, String> myNameColorMap;
  private Map<Integer, List<Circle>> circles;
  private List<HBox> circleBoxes;
  private VBox lineElements;
  private double totalNumCells;
  private boolean first = true;

  public CircleTimelineView(CellProperties cellProperties, Controller controller) {
    super(cellProperties, controller);
    myController = controller;
    circles = new TreeMap<>();
    circleBoxes = new ArrayList<>();
    lineElements = new VBox();
    lineElements.setAlignment(Pos.CENTER);
    lineElements.setSpacing(LINE_SPACING);
  }

  @Override
  public void initiateView() {
    myCircleTimelineMap = myController.getHistogramMap();
    totalNumCells = findTotalCells(myCircleTimelineMap);
    myNameColorMap = myController.getNamesAndColors();
    lineElements.getChildren().clear();
    makeStartCircles();
    updateView();
  }

  private void makeStartCircles() {
    circles.clear();
    for (Integer state : myCircleTimelineMap.keySet()) {
      circleBoxes.add(state, new HBox());
      circleBoxes.get(state).getChildren().clear();
      String name = myNameColorMap.get(state).split(",")[0];
      Text label = new Text(String.format("%s   ", name));
      circleBoxes.get(state).getChildren().add(label);
      String color = myNameColorMap.get(state).split(",")[1];
      Circle circle = new Circle(0, Color.web("#" + color));
      circles.put(state, new ArrayList<>());
      circles.get(state).add(circle);
      circleBoxes.get(state).getChildren().add(circle);
      lineElements.getChildren().add(circleBoxes.get(state));
    }
  }

  @Override
  public void updateView() {
    if (myCircleTimelineMap == null) {
      initiateView();
    }
    for (Integer stateNumber : myCircleTimelineMap.keySet()) {
      String color = myNameColorMap.get(stateNumber).split(",")[1];
      Circle newCircle = new Circle(
          Double.valueOf(myCircleTimelineMap.get(stateNumber)) / totalNumCells * MAX_CIRCLE_RADIUS,
          Color.web("#" + color));
      double totalWidth = calculateWidth(stateNumber);
      while (totalWidth > CANVAS_WIDTH - 50 - 2 * newCircle.getRadius()) {
        circles.get(stateNumber).remove(0);
        circleBoxes.get(stateNumber).getChildren().remove(1);
        totalWidth = calculateWidth(stateNumber);
      }
      circles.get(stateNumber).add(newCircle);
      circleBoxes.get(stateNumber).getChildren().add(newCircle);
    }
  }

  private double calculateWidth(int stateNumber) {
    double totalWidth = 0;
    for (Circle c : circles.get(stateNumber)) {
      totalWidth += 2 * c.getRadius();
    }
    return totalWidth;
  }

  @Override
  public Node getViewBox() {
    return lineElements;
  }

  @Override
  public void addViewToCenter() {

  }
}
