package cellsociety.view.center;

import cellsociety.controller.Controller;
import cellsociety.view.left.CellProperties;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.NonInvertibleTransformException;

public class TriangleGridView extends GridView {

  public TriangleGridView(CellProperties cellProps, Controller controller) {
    super(cellProps, controller);
  }

  @Override
  protected void findOptimalGridSizing(int numRows, int numCols) {

  }

  @Override
  protected void updateCellColors(GraphicsContext gc) {

  }

  @Override
  protected void drawGridLines(GraphicsContext gc) {
    gc.setStroke(GRID_LINE_COLOR);
    gc.setLineWidth(GRID_LINE_SIZE);
    for (int j = 0; j < getNumRows(getCurrentGridNum()) + 1; j++) {
      gc.strokeLine(0, j, getNumCols(getCurrentGridNum()), j);
    }  }

  @Override
  protected void getMousePosOnGrid(MouseEvent mouseEvent) throws NonInvertibleTransformException {

  }
}
