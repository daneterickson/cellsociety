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
    for (int i = -5; i < numCols/2; i++) {
      gc.strokeLine(2*i, 0, 2*numCols, 2*(numCols-i));
    }
    for (int i = 0; i < numCols + 1; i++) {
      gc.strokeLine(2*i, 0, 0, 2*i);
    }
  }

  @Override
  protected void getMousePosOnGrid(MouseEvent mouseEvent) throws NonInvertibleTransformException {

  }
}
