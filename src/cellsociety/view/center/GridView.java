package cellsociety.view.center;

import cellsociety.controller.Controller;
import cellsociety.view.left.CellProperties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Cell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public abstract class GridView{
  //TODO Make these temporary hardcoded values dependent on the window size or Model values ASAP
  protected static final int GRID_VIEW_MAX_WIDTH = 300;
  protected static final int GRID_VIEW_MAX_HEIGHT = 300;
  protected static final Color GRID_LINE_COLOR = Color.BLACK;
  protected static final Color SELECTED_GRID_COLOR = Color.LIMEGREEN;
  protected static final double GRID_LINE_SIZE = .04;
  protected static final double SELECTED_LINE_SIZE = .25;
  protected static final int GRID_SPACING = 10;
  protected static final String RESOURCE = "cellsociety.view.center.";
  protected static final String STYLESHEET = "/" + RESOURCE.replace(".", "/") + "GridView.css";

  private List<Canvas> myCanvasList;
  private List<Affine> myAffineList;
  private List<Integer> myNumGridColsList;
  private List<Integer> myNumGridRowsList;
  private HBox myGridHolder;
  private CellProperties myCellProperties;
  private Controller myController;
  private double myGridWidth;
  private double myGridHeight;
  private Integer[] myMousePos;


  public GridView(CellProperties cellProps, Controller controller){
    myCanvasList = new ArrayList<>();
    myAffineList = new ArrayList<>();
    myNumGridColsList = new ArrayList<>(Arrays.asList(0));
    myNumGridRowsList = new ArrayList<>(Arrays.asList(0));
    myGridHolder = new HBox();
    myGridHolder.setSpacing(GRID_SPACING);
    myController = controller;
    myCellProperties = cellProps;
    myMousePos = new Integer[2];
    findOptimalGridSizing(myController.getNumGridRows(), myController.getNumGridCols());
    addCanvasToList();
    myGridHolder.getChildren().add(myCanvasList.get(myController.getCurrentGridNumber()));
    setStyles();
  }

  /**
   * Getter method that returns the HBox which holds the canvases (grids).
   * @return HBox node that contains the canvas nodes.
   */
  public HBox getGridBox() {
    return myGridHolder;
  }

  public abstract void updateGrids();

  protected abstract void findOptimalGridSizing(int numRows, int numCols);

  protected abstract void updateCellColors(GraphicsContext gc);

  protected abstract void drawGridLines(GraphicsContext gc);

  protected abstract void getMousePosOnGrid(MouseEvent mouseEvent)
      throws NonInvertibleTransformException;

  /**
   * Sets the initial scaling for the grid view based off the model grid sizing. Then updates the
   * grid view.
   */
  public void initiateGrid(){
    findOptimalGridSizing(myController.getNumGridRows(), myController.getNumGridCols());
    myCanvasList.get(myController.getCurrentGridNumber()).setWidth(myGridWidth);
    myCanvasList.get(myController.getCurrentGridNumber()).setHeight(myGridHeight);
    addAffineToList();
    updateGrids();
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
    //myGridHolder.getChildren().add(myCanvasList.get(myGridNum));
    initiateGrid();
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
    updateGrids();
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
    try{
      myController.setCurrentGridNumber(myCanvasList.indexOf(mouseEvent.getSource()));
      getMousePosOnGrid(mouseEvent);
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
    myGridHolder.getStyleClass().add("root");
    getCanvasFromList(myController.getCurrentGridNumber()).getStyleClass().add("canvas");
  }

  protected void addToCanvasList(Canvas canvas){
    myCanvasList.add(canvas);
  }

  protected void addToCanvasList(int index, Canvas canvas){
    myCanvasList.add(index, canvas);
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

  protected double getGridWidth(){
    return myGridWidth;
  }

  protected double getGridHeight(){
    return myGridHeight;
  }

  protected void setGridWidth(double width){
    myGridWidth = width;
  }

  protected void setGridHeight(double height){
    myGridHeight = height;
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

}
