package cellsociety.view.center;

import cellsociety.controller.Controller;
import cellsociety.view.left.CellProperties;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * This is a subclass of Grid View
 *
 * @author Nick Strauch
 */
public class HexagonGridView extends GridView {

  private int NUM_VERTEXES = 6;

  /**
   * Constructor for a type of grid view
   *
   * @param cellProperties
   * @param controller
   */
  public HexagonGridView(CellProperties cellProperties, Controller controller) {
    super(cellProperties, controller);
  }

  @Override
  protected void updateCellColors(GraphicsContext gc) {
    for (int i = 0; i < getNumCols(getCurrentGridNum()); i++) {
      for (int j = 0; j < getNumRows(getCurrentGridNum()); j++) {
        gc.setFill(Color.web("#" + getCellColor(i, j)));
        gc.fillPolygon(getHexCoords(j, i)[0], getHexCoords(j, i)[1], NUM_VERTEXES);
      }
    }
  }

  @Override
  protected void drawGridLines(GraphicsContext gc) {
    gc.setStroke(GRID_LINE_COLOR);
    gc.setLineWidth(GRID_LINE_SIZE);
    for (int i = 0; i < getNumCols(getCurrentGridNum()) + 1; i++) {
      for (int j = 0; j < getNumRows(getCurrentGridNum()) + 1; j++) {
        gc.strokePolygon(getHexCoords(i, j)[0], getHexCoords(i, j)[1], NUM_VERTEXES);
      }
    }
  }

  @Override
  protected void getMousePosOnGrid(MouseEvent mouseEvent) {
    getMousePositionForCircles(
        mouseEvent); //Good approximation. The perfect values could be computed if we have time.
  }

  private double[][] getHexCoords(int i, int j) {
    double[][] coords = {
        {i, i + RADIUS / 2, i + 3 * RADIUS / 2, i + 1, i + 3 * RADIUS / 2, i + RADIUS / 2, i},
        {j + RADIUS, j, j, j + RADIUS, j + 1, j + 1, j + RADIUS}};
    return coords;
  }
}
