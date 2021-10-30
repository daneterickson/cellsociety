package cellsociety.view.center;

import cellsociety.controller.Controller;
import cellsociety.view.left.CellProperties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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
  private static final Color SELECTED_GRID_COLOR = Color.LIMEGREEN;
  private static final double GRID_LINE_SIZE = .04;
  private static final String ADD_GRID_BUTTON_TEXT = "Add Grid";

  private String RESOURCE = "cellsociety.view.center.";
  private String STYLESHEET = "/" + RESOURCE.replace(".", "/") + "GridView.css";
  private double myGridWidth;
  private double myGridHeight;
  private Integer[] myMousePos;
  private int myGridNum;

  //private Canvas myCanvas;
  private List<Integer> myNumGridColsList;
  private List<Integer> myNumGridRowsList;
  private List<Canvas> myCanvasList;
  private List<Affine> myAffineList;
  private HBox myGridHolder;
  private CellProperties myCellProperties;
  private Controller myController;


  public GridView(CellProperties cellProps, Controller controller) {
    myCanvasList = new ArrayList<>();
    myAffineList = new ArrayList<>();
    myNumGridColsList = new ArrayList<>(Arrays.asList(0));
    myNumGridRowsList = new ArrayList<>(Arrays.asList(0));
    myMousePos = new Integer[2];
    myGridHolder = new HBox();
    myController = controller;
    myGridNum = myController.getCurrentGridNumber();
    myCellProperties = cellProps;
    findOptimalGridSizing(myController.getNumGridRows(), myController.getNumGridCols());
    addCanvasToList();
    myGridHolder.getChildren().add(myCanvasList.get(myGridNum));
    //addAffineToList();
    myGridHolder.getChildren().add(makeAddGridButton());
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
  public void updateGrids() {
    for (int i = 0; i < myCanvasList.size(); i++) {
      //GraphicsContext gc = this.myCanvas.getGraphicsContext2D();
      Canvas curCanvas = myCanvasList.get(i);
      GraphicsContext gc = curCanvas.getGraphicsContext2D();
      gc.clearRect(0, 0, curCanvas.getWidth(), curCanvas.getHeight());
      gc.setTransform(myAffineList.get(i));
      updateCellColors(gc);
      drawGridLines(gc);
    }
  }

  /**
   * Sets the initial scaling for the grid view based off the model grid sizing. Then updates the
   * grid view.
   */
  public void initiateGrid(){
    findOptimalGridSizing(myController.getNumGridRows(), myController.getNumGridCols());
    myCanvasList.get(myGridNum).setWidth(myGridWidth);
    myCanvasList.get(myGridNum).setHeight(myGridHeight);
    addAffineToList();
    updateGrids();
  }


  private void updateCellColors(GraphicsContext gc) {
    for (int i = 0; i < myNumGridRowsList.get(myGridNum); i++) {
      for (int j = 0; j < myNumGridColsList.get(myGridNum); j++) {
        gc.setFill(Color.web("#" + myController.getCellColor(i, j)));
        gc.fillRect(j, i, 1, 1);
      }
    }
  }


  private void drawGridLines(GraphicsContext gc) {
    gc.setStroke(GRID_LINE_COLOR);
    gc.setLineWidth(GRID_LINE_SIZE);
    for (int i = 0; i < myNumGridColsList.get(myGridNum) + 1; i++) {
      gc.strokeLine(i, 0, i, myNumGridRowsList.get(myGridNum));
    }
    for (int j = 0; j < myNumGridRowsList.get(myGridNum) + 1; j++) {
      gc.strokeLine(0, j, myNumGridColsList.get(myGridNum), j);
    }
  }

  private void drawSelectedGridIndicatorLines(){
    Canvas curCanvas = myCanvasList.get(myGridNum);
    GraphicsContext gc = curCanvas.getGraphicsContext2D();
    gc.setTransform(myAffineList.get(myGridNum));
    gc.setStroke(SELECTED_GRID_COLOR);
    gc.setLineWidth(GRID_LINE_SIZE);
    int numRows = myNumGridRowsList.get(myGridNum);
    int numCols = myNumGridColsList.get(myGridNum);
    gc.strokeLine(0,0,numCols, 0); //Draw Top Line
    gc.strokeLine(0,numRows,numCols, numRows); //Draw Bottom Line
    gc.strokeLine(0,0,0, numRows); //Draw Left Line
    gc.strokeLine(numCols,0,numCols, numRows); //Draw Right Line
  }


  private void handleCellClicked(MouseEvent mouseEvent) {
    try{
      getMousePosOnGrid(mouseEvent);
      updateGridNumber(myCanvasList.indexOf(mouseEvent.getSource()));
      drawSelectedGridIndicatorLines();
    }catch(NonInvertibleTransformException e){
      e.getMessage();
    }
    int currState = myController.getCellStateNumber(myMousePos[1], myMousePos[0]);
    try {
      myController.setCellState(myMousePos[1], myMousePos[0], currState + 1);
    }catch(IndexOutOfBoundsException e){
      myController.setCellState(myMousePos[1], myMousePos[0], 0);
    }
    updateGrids();
  }

  private void handleCellHovered(MouseEvent mouseEvent) {
    try{
      getMousePosOnGrid(mouseEvent);
    }catch(NonInvertibleTransformException e){
      e.getMessage();
    }
    myCellProperties.updateCellCordLabel(myMousePos[0], myMousePos[1]);
  }

  private void getMousePosOnGrid(MouseEvent mouseEvent)
      throws NonInvertibleTransformException {
    double cursorX = mouseEvent.getX();
    double cursorY = mouseEvent.getY();
    try{
      Point2D modelXY = myAffineList.get(myGridNum).inverseTransform(cursorX, cursorY);
      myMousePos[0] = (int) modelXY.getX();
      myMousePos[1] = (int) modelXY.getY();
    }catch(NonInvertibleTransformException e){
      e.getMessage();//It should be impossible to enter this catch due to the mouse event being localized to the canvas node dimensions.
    }
  }

  private void updateGridNumber(int newGridNumber){
    myController.setCurrentGridNumber(newGridNumber);
    myGridNum = myController.getCurrentGridNumber();
  }


  private void setStyles() {
    myGridHolder.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    myGridHolder.getStyleClass().add("root");
    myCanvasList.get(myGridNum).getStyleClass().add("canvas");
  }


  private void findOptimalGridSizing(int numRows, int numCols){
    myNumGridRowsList.remove(myGridNum);
    myNumGridColsList.remove(myGridNum);
    myNumGridRowsList.add(myGridNum, numRows);
    myNumGridColsList.add(myGridNum, numCols);
    double blockLength;
    if(numRows > numCols){
      myGridHeight = GRID_VIEW_MAX_HEIGHT;
      if(myCanvasList.size()>0){myGridHeight = myGridHeight/myCanvasList.size();}
      blockLength = myGridHeight/numRows;
      myGridWidth = blockLength*numCols;
    }
    else{
      myGridWidth = GRID_VIEW_MAX_WIDTH;
      if(myCanvasList.size()>0){myGridWidth = myGridWidth/myCanvasList.size();}
      blockLength = myGridWidth/numCols;
      myGridHeight = blockLength*numRows;
    }
  }

  private void addAffineToList() {
    Affine tempAffine = new Affine();
    tempAffine.appendScale(myGridWidth / myNumGridColsList.get(myGridNum), myGridHeight / myNumGridRowsList.get(myGridNum));
    if(myAffineList.size()>0){myAffineList.remove(myGridNum);}
    myAffineList.add(myGridNum,tempAffine);
  }

  private void addCanvasToList() {
    Canvas tempCanvas = new Canvas(myGridWidth, myGridHeight);
    tempCanvas.setOnMouseClicked(e -> handleCellClicked(e));
    tempCanvas.setOnMouseMoved(e -> handleCellHovered(e));
    tempCanvas.setId("canvas");
    myCanvasList.add(tempCanvas);
    addAffineToList();
  }

  private Button makeAddGridButton(){
    Button button = new Button(ADD_GRID_BUTTON_TEXT);
    button.setOnAction(e -> addGridToCenter());
    button.getStyleClass().add("addGridButton");
    button.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    return button;
  }

  private void addGridToCenter(){
    myNumGridRowsList.add(0);
    myNumGridColsList.add(0);
    myAffineList.add(new Affine());
    myController.setCurrentGridNumber(myCanvasList.size());
    myGridNum = myController.getCurrentGridNumber();
    myController.makeNewDefaultSimulation();
    findOptimalGridSizing(myController.getNumGridRows(), myController.getNumGridCols());
    addCanvasToList();
    myGridHolder.getChildren().add(myCanvasList.get(myGridNum));
    initiateGrid();
  }


}
