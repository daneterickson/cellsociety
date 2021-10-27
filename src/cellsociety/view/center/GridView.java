package cellsociety.view.center;

import cellsociety.controller.Controller;
import cellsociety.view.left.CellProperties;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class GridView {

  //TODO Make these temporary hardcoded values dependent on the window size or Model values ASAP
  private static final int GRID_VIEW_MAX_WIDTH = 300;
  private static final int GRID_VIEW_MAX_HEIGHT = 300;
  private static final Color GRID_LINE_COLOR = Color.BLACK;
  private static final double GRID_LINE_SIZE = .04;

  private int myNumGridCols;
  private int myNumGridRows;
  private String RESOURCE = "cellsociety.view.center.";
  private String STYLESHEET = "/" + RESOURCE.replace(".", "/") + "GridView.css";
  private double myGridWidth;
  private double myGridHeight;
  private Integer[] myMousePos;

  private Canvas myCanvas;
  private Affine myAffine;
  private HBox myGridHolder;
  private CellProperties myCellProperties;
  private Controller myController;


  public GridView(CellProperties cellProps, Controller controller) {
    myMousePos = new Integer[2];
    myGridHolder = new HBox();
    myController = controller;
    myCellProperties = cellProps;
    findOptimalGridSizing(myController.getNumGridRows(), myController.getNumGridCols());
    setupCanvas();
    myGridHolder.getChildren().add(myCanvas);
    makeAffine();
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
  public void updateGrid() {
    GraphicsContext gc = this.myCanvas.getGraphicsContext2D();
    gc.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
    gc.setTransform(myAffine);
    updateCellColors(gc);
    drawGridLines(gc);
  }

  /**
   * Sets the initial scaling for the grid view based off the model grid sizing. Then updates the
   * grid view.
   */
  public void initiateGrid(){
    findOptimalGridSizing(myController.getNumGridRows(), myController.getNumGridCols());
    myCanvas.setWidth(myGridWidth);
    myCanvas.setHeight(myGridHeight);
    makeAffine();
    updateGrid();
  }


  private void updateCellColors(GraphicsContext gc) {
    for (int i = 0; i < myNumGridRows; i++) {
      for (int j = 0; j < myNumGridCols; j++) {
        gc.setFill(Color.web("#" + myController.getCellColor(i, j)));
        gc.fillRect(j, i, 1, 1);
      }
    }
  }


  private void drawGridLines(GraphicsContext gc) {
    gc.setStroke(GRID_LINE_COLOR);
    gc.setLineWidth(GRID_LINE_SIZE);
    for (int i = 0; i < myNumGridCols + 1; i++) {
      gc.strokeLine(i, 0, i, myNumGridRows);
    }
    for (int j = 0; j < myNumGridRows + 1; j++) {
      gc.strokeLine(0, j, myNumGridCols, j);
    }
  }


  private void handleCellClicked(MouseEvent mouseEvent) {
    try{
      getMousePosOnGrid(mouseEvent);
    }catch(NonInvertibleTransformException e){
      e.getMessage();
    }
    //TODO May work AFTER exception throwing in backend is added
    int currState = myController.getCellStateNumber(myMousePos[1], myMousePos[0]);
    try {
      myController.setCellState(myMousePos[1], myMousePos[0], currState + 1);
    }catch(IndexOutOfBoundsException e){
      myController.setCellState(myMousePos[1], myMousePos[0], 0);
    }
    updateGrid();
  }

  private void handleCellHovered(MouseEvent mouseEvent) {
    try{
      getMousePosOnGrid(mouseEvent);
    }catch(NonInvertibleTransformException e){
      e.getMessage();
    }
    myCellProperties.updateCellCordLabel(myMousePos[1], myMousePos[0]);
  }

  private void getMousePosOnGrid(MouseEvent mouseEvent)
      throws NonInvertibleTransformException {
    double cursorX = mouseEvent.getX();
    double cursorY = mouseEvent.getY();
    try{
      Point2D modelXY = myAffine.inverseTransform(cursorX, cursorY);
      myMousePos[0] = (int) modelXY.getX();
      myMousePos[1] = (int) modelXY.getY();
    }catch(NonInvertibleTransformException e){
      e.getMessage();//It should be impossible to enter this catch due to the mouse event being localized to the canvas node dimensions.
    }
  }


  private void setStyles() {
    myGridHolder.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    myGridHolder.getStyleClass().add("root");
    myCanvas.getStyleClass().add("canvas");
  }


  private void findOptimalGridSizing(int numRows, int numCols){
    myNumGridRows = numRows;
    myNumGridCols = numCols;
    double blockLength;
    if(numRows > numCols){
      myGridHeight = GRID_VIEW_MAX_HEIGHT;
      blockLength = myGridHeight/numRows;
      myGridWidth = blockLength*numCols;
    }
    else{
      myGridWidth = GRID_VIEW_MAX_WIDTH;
      blockLength = myGridWidth/numCols;
      myGridHeight = blockLength*numRows;
    }
  }

  private void makeAffine() {
    myAffine = new Affine();
    myAffine.appendScale(myGridWidth / myNumGridCols, myGridHeight / myNumGridRows);
  }

  private void setupCanvas() {
    myCanvas = new Canvas(myGridWidth, myGridHeight);
    myCanvas.setOnMouseClicked(e -> handleCellClicked(e));
    myCanvas.setOnMouseMoved(e -> handleCellHovered(e));
  }


}
