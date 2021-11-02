package cellsociety.view.center;

import cellsociety.controller.Controller;
import cellsociety.view.left.CellProperties;
import javafx.scene.Node;

public abstract class CenterView {
  protected static final int CENTER_VIEW_MAX_WIDTH = 300;
  protected static final int CENTER_VIEW_MAX_HEIGHT = 300;
  protected static final int ELEMENT_SPACING = 10;
  private boolean gridLinesOn;
  protected static final String RESOURCE = "cellsociety.view.center.";
  protected static final String STYLESHEET = String.format("/%sCenterView.css", RESOURCE.replace(".", "/"));

  public CenterView(CellProperties cellProperties, Controller controller){
    gridLinesOn = true;
  }

  public abstract void initiateView();

  public abstract void updateView();

  public abstract Node getViewBox();

  public abstract void addViewToCenter();

  public void toggleLines(){
    gridLinesOn = !gridLinesOn;
    updateView();
  }

  protected boolean linesAreOn(){
    return gridLinesOn;
  }


}
