package cellsociety.view.center;

import cellsociety.controller.Controller;
import cellsociety.view.left.CellProperties;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.NonInvertibleTransformException;

public class SquareGridView extends GridView {


  public SquareGridView(CellProperties cellProps, Controller controller) {
    super(cellProps, controller);
  }

  /**
   * Sets up the initial grid background, gridlines, etc. Also updates the canvas (grid) based off
   * of new changes to the grid model. This should be called after each update of a cell value.
   */
  @Override
  public void updateGrids() {
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

  @Override
  protected void updateCellColors(GraphicsContext gc) {
    for (int i = 0; i < getNumRows(getCurrentGridNum()); i++) {
      for (int j = 0; j < getNumCols(getCurrentGridNum()); j++) {
        gc.setFill(Color.web("#" + getCellColor(i, j)));
        gc.fillRect(j, i, 1, 1);
      }
    }
  }

  @Override
  protected void drawGridLines(GraphicsContext gc) {
    gc.setStroke(GRID_LINE_COLOR);
    gc.setLineWidth(GRID_LINE_SIZE);
    for (int i = 0; i < getNumCols(getCurrentGridNum()) + 1; i++) {
      gc.strokeLine(i, 0, i, getNumRows(getCurrentGridNum()));
    }
    for (int j = 0; j < getNumRows(getCurrentGridNum()) + 1; j++) {
      gc.strokeLine(0, j, getNumCols(getCurrentGridNum()), j);
    }
  }

  @Override
  protected void getMousePosOnGrid(MouseEvent mouseEvent)
      throws NonInvertibleTransformException {
    double cursorX = mouseEvent.getX();
    double cursorY = mouseEvent.getY();
    try {
      Point2D modelXY = getAffineFromList(getCurrentGridNum()).inverseTransform(
          cursorX, cursorY);
      setMosPos(0, (int) modelXY.getX());
      setMosPos(1, (int) modelXY.getY());
    } catch (NonInvertibleTransformException e) {
      e.getMessage();//It should be impossible to enter this catch due to the mouse event being localized to the canvas node dimensions.
    }
  }

  @Override
  protected void findOptimalGridSizing(int numRows, int numCols) {
    removeRowNumForGrid(getCurrentGridNum());
    removeColNumForGrid(getCurrentGridNum());
    addRowNumForGrid(getCurrentGridNum(), numRows);
    addColNumForGrid(getCurrentGridNum(), numCols);
    double blockLength;
    if (numRows > numCols) {
      setGridHeight(CENTER_VIEW_MAX_HEIGHT);
      if (getCanvasListSize() > 0) {
        setGridHeight(getGridHeight() / getCanvasListSize());
      }
      blockLength = getGridHeight() / numRows;
      setGridWidth(blockLength * numCols);
    } else {
      setGridWidth(CENTER_VIEW_MAX_WIDTH);
      if (getCanvasListSize() > 0) {
        setGridWidth(getGridWidth() / getCanvasListSize());
      }
      blockLength = getGridWidth() / numCols;
      setGridHeight(blockLength * numRows);
    }
  }

}
