package cellsociety.view.center;

import cellsociety.controller.Controller;
import cellsociety.view.left.CellProperties;
import java.util.ResourceBundle;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.NonInvertibleTransformException;

public class CircleGridView extends GridView{

  public CircleGridView(CellProperties cellProps, Controller controller, ResourceBundle resources){
    super(cellProps, controller, resources);
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
    getMousePositionForCircles(mouseEvent);
  }
}
