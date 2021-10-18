package cellsociety.view.center;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class GridView {
  //TODO Make these temporary hardcoded values dependent on the window size or Model values ASAP
  private static final int GRID_VIEW_WIDTH = 300;
  private static final int GRID_VIEW_HEIGHT = 300;
  private static final Color GRID_BACKGROUND_COLOR = Color.LIGHTGREY;
  private static final Color GRID_LINE_COLOR = Color.BLACK;
  private static final double GRID_LINE_SIZE = .04;
  private static final int GRID_MODEL_WIDTH = 25;
  private static final int GRID_MODEL_HEIGHT = 25;

  private Canvas myCanvas;
  private Affine myAffine;

  public GridView() {
    myCanvas = new Canvas(GRID_VIEW_WIDTH, GRID_VIEW_HEIGHT);
    myAffine = new Affine();
    myAffine.appendScale(GRID_VIEW_WIDTH/GRID_MODEL_WIDTH, GRID_VIEW_HEIGHT/GRID_MODEL_HEIGHT);
  }


  /**
   * Getter method that returns the main canvas for the view. This is where the main grid will be illustrated.
   * @return Canvas node that is used for the grid.
   */
  public Canvas getGridCanvas(){
    return myCanvas;
  }


  /**
   * Sets up the initial grid background, gridlines, etc.
   * Also updates the canvas (grid) based off of new changes to the grid model.
   * This should be called after each update of a cell value.
   */
  public void illustrate(){
    GraphicsContext gc = this.myCanvas.getGraphicsContext2D();
    gc.setTransform(myAffine);
    gc.setFill(GRID_BACKGROUND_COLOR);
    gc.fillRect(0, 0, GRID_VIEW_WIDTH, GRID_VIEW_HEIGHT);

    updateCellColors(gc);
    drawGridLines(gc);

  }



  private void updateCellColors(GraphicsContext gc) {
    for (int i = 0; i < GRID_MODEL_WIDTH; i++) {
      for (int j = 0; j < GRID_MODEL_HEIGHT; j++) {
        /*
        update the [i][j] cell color in the grid based off of current cell values.
        Do something like:
        gc.setFill(getCell(i,j).getColor);
        gc.fillRect(i, j, 1, 1);
         */
      }
    }
  }


  private void drawGridLines(GraphicsContext gc){
    gc.setStroke(GRID_LINE_COLOR);
    gc.setLineWidth(GRID_LINE_SIZE);
    for (int i = 0; i < GRID_MODEL_WIDTH+1; i++) {
      gc.strokeLine(i,0, i, GRID_MODEL_HEIGHT);
    }
    for (int j = 0; j < GRID_VIEW_HEIGHT+1; j++) {
      gc.strokeLine(0, j, GRID_MODEL_WIDTH, j);
    }
  }


}