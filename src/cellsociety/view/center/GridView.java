package cellsociety.view.center;

import cellsociety.view.left.CellProperties;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.util.Duration;

public class GridView {

  //TODO Make these temporary hardcoded values dependent on the window size or Model values ASAP
  private static final int GRID_VIEW_WIDTH = 300;
  private static final int GRID_VIEW_HEIGHT = 300;
  private static final Color GRID_BACKGROUND_COLOR = Color.LIGHTGREY;
  private static final Color GRID_LINE_COLOR = Color.BLACK;
  private static final double GRID_LINE_SIZE = .04;
  private static final int GRID_MODEL_WIDTH = 10;
  private static final int GRID_MODEL_HEIGHT = 10;
  private String RESOURCE = "cellsociety.view.center.";
  private String STYLESHEET = "/"+RESOURCE.replace(".", "/")+"GridView.css";

  private Canvas myCanvas;
  private Affine myAffine;
  private HBox myGridHolder;
  private CellProperties myCellProperties;


  public GridView(CellProperties cellProps) {
    myGridHolder = new HBox();
    myCanvas = new Canvas(GRID_VIEW_WIDTH, GRID_VIEW_HEIGHT);
    myCanvas.setOnMouseClicked(e -> handleCellClicked(e));
    myCanvas.setOnMouseMoved(e -> handleCellHovered(e));
    myGridHolder.getChildren().add(myCanvas);
    myAffine = new Affine();
    myAffine.appendScale(GRID_VIEW_WIDTH / GRID_MODEL_WIDTH, GRID_VIEW_HEIGHT / GRID_MODEL_HEIGHT);
    myCellProperties = cellProps;
    setStyles();
  }


  /**
   * Getter method that returns the HBox which holds the canvases (grids).
   *
   * @return HBox node that contains the canvas nodes.
   */
  public HBox getGridBox() {
    return myGridHolder;
  }


  /**
   * Sets up the initial grid background, gridlines, etc. Also updates the canvas (grid) based off
   * of new changes to the grid model. This should be called after each update of a cell value.
   */
  public void illustrate() {
    GraphicsContext gc = this.myCanvas.getGraphicsContext2D();
    gc.setTransform(myAffine);
    gc.setFill(GRID_BACKGROUND_COLOR);
    gc.fillRect(0, 0, GRID_VIEW_WIDTH, GRID_VIEW_HEIGHT);

    updateCellColors(gc);
    drawGridLines(gc);

  }


  private void updateCellColors(GraphicsContext gc) {
    for (int i = 0; i < GRID_MODEL_WIDTH; i++) {
      for (int j = 0; j < GRID_MODEL_HEIGHT; j++) {
        //TODO
        /*
        update the [i][j] cell color in the grid based off of current cell values.
        Do something like:
        gc.setFill(getCell(i,j).getColor);
        gc.fillRect(i, j, 1, 1);
         */
      }
    }
  }


  private void drawGridLines(GraphicsContext gc) {
    gc.setStroke(GRID_LINE_COLOR);
    gc.setLineWidth(GRID_LINE_SIZE);
    for (int i = 0; i < GRID_MODEL_WIDTH + 1; i++) {
      gc.strokeLine(i, 0, i, GRID_MODEL_HEIGHT);
    }
    for (int j = 0; j < GRID_VIEW_HEIGHT + 1; j++) {
      gc.strokeLine(0, j, GRID_MODEL_WIDTH, j);
    }
  }


  private void handleCellClicked(MouseEvent mouseEvent) {
    double cursorX = mouseEvent.getX();
    double cursorY = mouseEvent.getY();
    try {
      Point2D modelXY = myAffine.inverseTransform(cursorX, cursorY);
      int modelX = (int) modelXY.getX();
      int modelY = (int) modelXY.getY();
      //System.out.println(modelX + ", " + modelY);
      //TODO Update the modelGrid cell's state. (the clicked cell is cell[X][Y])
      myCellProperties.updateCellCordLabel(modelX, modelY);
      illustrate();
    } catch (NonInvertibleTransformException e) {
      e.getMessage(); //It should be impossible to enter this catch due to the mouse event being localized to the canvas node dimensions.
    }
  }

  //TODO eliminate this duplicated code between handleCellClicked and handleCellCovered
  private void handleCellHovered(MouseEvent mouseEvent) {
    double cursorX = mouseEvent.getX();
    double cursorY = mouseEvent.getY();
    try {
      Point2D modelXY = myAffine.inverseTransform(cursorX, cursorY);
      int modelX = (int) modelXY.getX();
      int modelY = (int) modelXY.getY();
      myCellProperties.updateCellCordLabel(modelX, modelY);
    } catch (NonInvertibleTransformException e) {
      e.getMessage(); //It should be impossible to enter this catch due to the mouse event being localized to the canvas node dimensions.
    }
  }


  private void setStyles() {
    myGridHolder.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    myGridHolder.getStyleClass().add("root");
    myCanvas.getStyleClass().add("canvas");
  }


}
