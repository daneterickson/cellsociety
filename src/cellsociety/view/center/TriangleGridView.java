package cellsociety.view.center;

import cellsociety.controller.Controller;
import cellsociety.view.left.CellProperties;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.NonInvertibleTransformException;

public class TriangleGridView extends GridView {

  public TriangleGridView(CellProperties cellProps, Controller controller) {
    super(cellProps, controller);
  }

  @Override
  protected void updateCellColors(GraphicsContext gc) {

  }

  @Override
  protected void drawGridLines(GraphicsContext gc) {
    gc.setStroke(GRID_LINE_COLOR);
    gc.setLineWidth(GRID_LINE_SIZE);
    int numRows = getNumRows(getCurrentGridNum());
    int numCols = getNumCols(getCurrentGridNum());
    for (int j = 0; j < numRows + 1; j++) {
      gc.strokeLine(0, j, numCols, j);
    }
    for (int i = -numCols / 2; i < numCols / 2; i++) {
      gc.strokeLine(2 * i, 0, 2 * numCols, 2 * (numCols - i));
    }
    for (int i = 0; i < numCols + 1; i++) {
      gc.strokeLine(2 * i, 0, 0, 2 * i);
    }
    gc.strokeLine(0, 0, 0, numRows);
    gc.strokeLine(numCols, 0, numCols, numRows);
  }

  @Override
  protected void getMousePosOnGrid(MouseEvent mouseEvent) throws NonInvertibleTransformException {
    double cursorX = mouseEvent.getX();
    double cursorY = mouseEvent.getY();
    try {
      Point2D modelAffineXY = getAffineFromList(getCurrentGridNum()).inverseTransform(
          cursorX, cursorY);
      mapToTriangles(cursorX, cursorY, modelAffineXY);
      setMosPos(1, (int) modelAffineXY.getY());
    } catch (NonInvertibleTransformException e) {
      e.getMessage();//It should be impossible to enter this catch due to the mouse event being localized to the canvas node dimensions.
    }
  }

  private void mapToTriangles(double cursorX, double cursorY, Point2D modelAffineXY) {
    double triLength = getBlockLength();
    double correctedX = cursorX - ((int) modelAffineXY.getX()) * triLength;
    double correctedY = cursorY - ((int) modelAffineXY.getY()) * triLength;
    selectCorrectTriangle(modelAffineXY, correctedX, correctedY, triLength);
  }

  private void selectCorrectTriangle(Point2D modelAffineXY, double correctedX, double correctedY, double triLength) {
    if (((int) modelAffineXY.getX() % 2 == 0 && (int)modelAffineXY.getY() % 2 == 0) ||
        ((int) modelAffineXY.getX() % 2 != 0 && (int)modelAffineXY.getY() % 2 != 0)) { //Top to bottom diagonal
      if (correctedX >= correctedY) {
        setMosPos(0, (int) modelAffineXY.getX() + 1);
      } else {
        setMosPos(0, (int) modelAffineXY.getX());
      }
    } else {
      if (correctedX > triLength - correctedY) {
        setMosPos(0, (int) modelAffineXY.getX() + 1);
      } else {
        setMosPos(0, (int) modelAffineXY.getX());
      }
    }
  }
}
