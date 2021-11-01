package cellsociety.view.center;

import cellsociety.controller.Controller;
import cellsociety.view.left.CellProperties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public abstract class GridView extends CenterView {
  //TODO Make these temporary hardcoded values dependent on the window size or Model values ASAP
  protected static final Color GRID_LINE_COLOR = Color.BLACK;
  protected static final Color SELECTED_GRID_COLOR = Color.LIMEGREEN;
  protected static final double GRID_LINE_SIZE = .04;
  protected static final double SELECTED_LINE_SIZE = .25;
  protected static final double RADIUS = 0.5;

  private List<Canvas> myCanvasList;
  private List<Affine> myAffineList;
  private List<Integer> myNumGridColsList;
  private List<Integer> myNumGridRowsList;
  private HBox myGridHolder;
  private VBox myCenterHolder;
  private CellProperties myCellProperties;
  private Controller myController;
  private double myGridWidth;
  private double myGridHeight;
  private Integer[] myMousePos;
  private double myBlockLength;
  private boolean cursorOverCell;
  private ResourceBundle myResources;


  public GridView(CellProperties cellProps, Controller controller, ResourceBundle resources){
    myResources = resources;
    myCanvasList = new ArrayList<>();
    myAffineList = new ArrayList<>();
    myNumGridColsList = new ArrayList<>(Arrays.asList(0));
    myNumGridRowsList = new ArrayList<>(Arrays.asList(0));
    myGridHolder = new HBox();
    myCenterHolder = new VBox();
    myGridHolder.setSpacing(ELEMENT_SPACING);
    myCenterHolder.setSpacing(ELEMENT_SPACING);
    myCenterHolder.getChildren().addAll(myGridHolder, makeGridChoiceBox());
    myController = controller;
    myCellProperties = cellProps;
    myMousePos = new Integer[2];
    myBlockLength = 0;
    cursorOverCell = true;
    findOptimalGridSizing(myController.getNumGridRows(), myController.getNumGridCols());
    addCanvasToList();
    myGridHolder.getChildren().add(myCanvasList.get(myController.getCurrentGridNumber()));
    setStyles();
  }

  /**
   * Getter method that returns the VBox which holds the canvases (grids) and choice box.
   * @return VBox node that contains the canvas nodes and the change canvas choice box.
   */
  @Override
  public Node getViewBox() {
    return myCenterHolder;
  }

  /**
   * Sets up the initial grid background, gridlines, etc. Also updates the canvas (grid) based off
   * of new changes to the grid model. This should be called after each update of a cell value.
   */
  @Override
  public void updateView() {
    int curGrid = getCurrentGridNum();
    for (int i = 0; i < getCanvasListSize(); i++) {
      setCurrentGridNum(i);
      Canvas curCanvas = getCanvasFromList(i);
      GraphicsContext gc = curCanvas.getGraphicsContext2D();
      gc.clearRect(0, 0, curCanvas.getWidth(), curCanvas.getHeight());
      gc.setTransform(getAffineFromList(i));
      updateCellColors(gc);
      drawGridLines(gc);
    }
    setCurrentGridNum(curGrid);
    drawSelectedGridIndicatorLines();
  }

  protected void findOptimalGridSizing(int numRows, int numCols) {
    removeRowNumForGrid(getCurrentGridNum());
    removeColNumForGrid(getCurrentGridNum());
    addRowNumForGrid(getCurrentGridNum(), numRows);
    addColNumForGrid(getCurrentGridNum(), numCols);
    if (numRows > numCols) {
      myGridHeight = CENTER_VIEW_MAX_HEIGHT;
      if (getCanvasListSize() > 0) {
        myGridHeight = myGridHeight / getCanvasListSize();
      }
      myBlockLength = myGridHeight / numRows;
      myGridWidth = myBlockLength * numCols;
    } else {
      myGridWidth = CENTER_VIEW_MAX_WIDTH;
      if (getCanvasListSize() > 0) {
        myGridWidth = myGridWidth / getCanvasListSize();
      }
      myBlockLength = myGridWidth / numCols;
      myGridHeight = myBlockLength * numRows;
    }
  }

  protected abstract void updateCellColors(GraphicsContext gc);

  protected abstract void drawGridLines(GraphicsContext gc);

  protected abstract void getMousePosOnGrid(MouseEvent mouseEvent)
      throws NonInvertibleTransformException;

  /**
   * Sets the initial scaling for the grid view based off the model grid sizing. Then updates the
   * grid view.
   */
  @Override
  public void initiateView(){
    findOptimalGridSizing(myController.getNumGridRows(), myController.getNumGridCols());
    myCanvasList.get(myController.getCurrentGridNumber()).setWidth(myGridWidth);
    myCanvasList.get(myController.getCurrentGridNumber()).setHeight(myGridHeight);
    addAffineToList();
    updateView();
  }

  public void addGridToCenter(){
    myNumGridRowsList.add(0);
    myNumGridColsList.add(0);
    myController.addDefaultSimPropMap();
    myAffineList.add(new Affine());
    myController.setCurrentGridNumber(myCanvasList.size());
    myController.setCurrentGridNumber(myController.getCurrentGridNumber());
    myController.makeNewDefaultSimulation();
    findOptimalGridSizing(myController.getNumGridRows(), myController.getNumGridCols());
    addCanvasToList();
    initiateView();
    updateOtherGridSizing();
  }

  private void updateOtherGridSizing(){
    int curGridNum = myController.getCurrentGridNumber();
    for (int gridNum = 0; gridNum < curGridNum; gridNum++) {
      myController.setCurrentGridNumber(gridNum);
      findOptimalGridSizing(myController.getNumGridRows(), myController.getNumGridCols());
      myGridHolder.getChildren().remove(myCanvasList.get(gridNum));
      myCanvasList.remove(gridNum);
      addCanvasToList();
    }
    myGridHolder.getChildren().addAll(myCanvasList);
    updateView();
    myController.setCurrentGridNumber(curGridNum);
  }

  //This is the green outline around the selected grid
  protected void drawSelectedGridIndicatorLines(){
    Canvas curCanvas = myCanvasList.get(myController.getCurrentGridNumber());
    GraphicsContext gc = curCanvas.getGraphicsContext2D();
    gc.setTransform(myAffineList.get(myController.getCurrentGridNumber()));
    gc.setStroke(SELECTED_GRID_COLOR);
    gc.setLineWidth(SELECTED_LINE_SIZE);
    int numRows = myNumGridRowsList.get(myController.getCurrentGridNumber());
    int numCols = myNumGridColsList.get(myController.getCurrentGridNumber());
    gc.strokeLine(0,0,numCols, 0); //Draw Top Line
    gc.strokeLine(0,numRows,numCols, numRows); //Draw Bottom Line
    gc.strokeLine(0,0,0, numRows); //Draw Left Line
    gc.strokeLine(numCols,0,numCols, numRows); //Draw Right Line
  }

  private void handleCellClicked(MouseEvent mouseEvent) {
    if(cursorOverCell) {
      try {
        myController.setCurrentGridNumber(myCanvasList.indexOf(mouseEvent.getSource()));
        getMousePosOnGrid(mouseEvent);
        drawSelectedGridIndicatorLines();
      } catch (NonInvertibleTransformException e) {
        e.getMessage();
      }
      int currState = myController.getCellStateNumber(myMousePos[1], myMousePos[0]);
      try {
        myController.setCellState(myMousePos[1], myMousePos[0], currState + 1);
      } catch (IndexOutOfBoundsException e) {
        myController.setCellState(myMousePos[1], myMousePos[0], 0);
      }
      updateView();
    }
  }

  private void handleCellHovered(MouseEvent mouseEvent) {
    try{
      getMousePosOnGrid(mouseEvent);
    }catch(NonInvertibleTransformException e){
      e.getMessage(); //Not possible to get to the exception due to the mouse position being localized to the canvas nodes
    }
    myCellProperties.updateCellCordLabel(myMousePos[0], myMousePos[1]);
  }

  private void addCanvasToList() {
    Canvas tempCanvas = new Canvas(myGridWidth, myGridHeight);
    tempCanvas.setOnMouseClicked(e -> handleCellClicked(e));
    tempCanvas.setOnMouseMoved(e -> handleCellHovered(e));
    tempCanvas.setId("canvas");
    if(myController.getCurrentGridNumber() == myCanvasList.size()) {myCanvasList.add(tempCanvas);}
    else{myCanvasList.add(myController.getCurrentGridNumber(), tempCanvas);}
    addAffineToList();
  }

  private void addAffineToList() {
    Affine tempAffine = new Affine();
    tempAffine.appendScale(myGridWidth / myNumGridColsList.get(myController.getCurrentGridNumber()), myGridHeight / myNumGridRowsList.get(myController.getCurrentGridNumber()));
    if(myAffineList.size()>0){myAffineList.remove(myController.getCurrentGridNumber());}
    myAffineList.add(myController.getCurrentGridNumber(),tempAffine);
  }

  private void setStyles() {
    myGridHolder.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    myGridHolder.getStyleClass().add("root1");
    myCenterHolder.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    myCenterHolder.getStyleClass().add("root");
    getCanvasFromList(myController.getCurrentGridNumber()).getStyleClass().add("canvas");
  }

  /**
   * Not only useful for circular cases, but also a good approximation for cells that are almost
   * circular (hexagons, octagons, decagons, etc.).
   */
  protected void getMousePositionForCircles(MouseEvent mouseEvent){
    double cursorX = mouseEvent.getX();
    double cursorY = mouseEvent.getY();
    setCursorOverCell(false);
    try {
      Point2D modelXY = getAffineFromList(myController.getCurrentGridNumber()).inverseTransform(
          cursorX, cursorY);
      Point2D center = new Point2D(((int) modelXY.getX()) + RADIUS, ((int) modelXY.getY()) + RADIUS);
      double disToCenter = Math.sqrt(Math.pow(modelXY.getX()- center.getX(), 2) + Math.pow(modelXY.getY()- center.getY(), 2));
      if(disToCenter <= RADIUS) {
        setCursorOverCell(true);
      }
      setMosPos(0, (int) modelXY.getX());
      setMosPos(1, (int) modelXY.getY());
    } catch (NonInvertibleTransformException e) {
      e.getMessage();//It should be impossible to enter this catch due to the mouse event being localized to the canvas node dimensions.
    }
  }

  private ChoiceBox makeGridChoiceBox(){
    ChoiceBox gridSelector = new ChoiceBox();
    String typesOfGrid = myResources.getString("GridTypes");
    gridSelector.getItems().addAll(typesOfGrid.split(","));
    gridSelector.setOnAction(e -> updateGridType(gridSelector.getValue()));
    gridSelector.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    gridSelector.getStyleClass().add("gridChoiceBox");
    return gridSelector;
  }

  protected int getCanvasListSize(){
    return myCanvasList.size();
  }

  protected Canvas getCanvasFromList(int index){
    return myCanvasList.get(index);
  }

  protected Affine getAffineFromList(int index){
    return myAffineList.get(index);
  }

  protected int getNumRows(int gridNumber){
    return myNumGridRowsList.get(gridNumber);
  }

  protected int getNumCols(int gridNumber){
    return myNumGridColsList.get(gridNumber);
  }

  protected void removeRowNumForGrid(int gridNumber){
    myNumGridRowsList.remove(gridNumber);
  }

  protected void removeColNumForGrid(int gridNumber){
    myNumGridColsList.remove(gridNumber);
  }

  protected void addRowNumForGrid(int gridNum, int rows){
    myNumGridRowsList.add(gridNum, rows);
  }

  protected void addColNumForGrid(int gridNum, int cols){
    myNumGridColsList.add(gridNum, cols);
  }

  protected double getBlockLength(){
    return myBlockLength;
  }

  protected void setMosPos(int index, int value){
    myMousePos[index] = value;
  }

  protected int getCurrentGridNum(){
    return myController.getCurrentGridNumber();
  }

  protected void setCurrentGridNum(int newGridNumber){
    myController.setCurrentGridNumber(newGridNumber);
  }

  protected String getCellColor(int i, int j){
    return myController.getCellColor(i, j);
  }

  protected void setCursorOverCell(boolean overCell){
    cursorOverCell = overCell;
  }

}
