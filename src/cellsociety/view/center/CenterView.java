package cellsociety.view.center;

import cellsociety.controller.Controller;
import cellsociety.view.left.CellProperties;
import javafx.scene.Node;

public abstract class CenterView {
  protected static final int CENTER_VIEW_MAX_WIDTH = 300;
  protected static final int CENTER_VIEW_MAX_HEIGHT = 300;
  protected static final int ELEMENT_SPACING = 10;
  protected static final String RESOURCE = "cellsociety.view.center.";
  protected static final String STYLESHEET = "/" + RESOURCE.replace(".", "/") + "CenterView.css";

  public CenterView(CellProperties cellProperties, Controller controller){}

  public abstract void initiateView();

  public abstract void updateView();

  public abstract Node getViewBox();

  public abstract void addViewToCenter();



}
