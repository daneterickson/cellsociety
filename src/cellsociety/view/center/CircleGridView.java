package cellsociety.view.center;

import cellsociety.controller.Controller;
import cellsociety.view.left.CellProperties;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.NonInvertibleTransformException;

public class CircleGridView extends GridView{
  private double RADIUS = 0.5;

  public CircleGridView(CellProperties cellProps, Controller controller){
    super(cellProps, controller);
  }

  @Override
  protected void updateCellColors(GraphicsContext gc){
    for (int i = 0; i < getNumRows(getCurrentGridNum()); i++) {
      for (int j = 0; j < getNumCols(getCurrentGridNum()); j++) {
        gc.setFill(Color.web("#" + getCellColor(i, j)));
        gc.fillOval(j, i, 1, 1);
      }
    }
  }

  @Override
  protected void drawGridLines(GraphicsContext gc){
    gc.setStroke(GRID_LINE_COLOR);
    gc.setLineWidth(GRID_LINE_SIZE);
    for (int i = 0; i < getNumCols(getCurrentGridNum()) + 1; i++) {
      for (int j = 0; j < getNumRows(getCurrentGridNum()) + 1; j++) {
        gc.strokeOval(i, j, 1, 1);
      }
    }
  }

  @Override
  protected void getMousePosOnGrid(MouseEvent mouseEvent){
    double cursorX = mouseEvent.getX();
    double cursorY = mouseEvent.getY();
    setCursorOverCell(false);
    try {
      Point2D modelXY = getAffineFromList(getCurrentGridNum()).inverseTransform(
          cursorX, cursorY);
      Point2D center = new Point2D(((int) modelXY.getX()) + RADIUS, ((int) modelXY.getY()) + RADIUS);
      double disToCenter = Math.sqrt(Math.pow(modelXY.getX()- center.getX(), 2) + Math.pow(modelXY.getY()- center.getY(), 2));
      System.out.println(disToCenter);
      System.out.println(center);
      if(disToCenter <= RADIUS) {
        setCursorOverCell(true);
      }
      setMosPos(0, (int) modelXY.getX());
      setMosPos(1, (int) modelXY.getY());
    } catch (NonInvertibleTransformException e) {
      e.getMessage();//It should be impossible to enter this catch due to the mouse event being localized to the canvas node dimensions.
    }
  }
}
