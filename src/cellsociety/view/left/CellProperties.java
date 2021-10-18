package cellsociety.view.left;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CellProperties {

  private String RESOURCE = "cellsociety.view.left.";
  private String STYLESHEET = "/"+RESOURCE.replace(".", "/")+"CellProp.css";
  private int myCurrentX;
  private int myCurrentY;
  //private String COORD_TEXT = "("+myCurrentX+", "+myCurrentY+")";
  private Label myCellCoordinatesLabel;
  private VBox myCellProperties;


  public CellProperties(){
    myCellProperties = new VBox();
    myCellProperties.getChildren().add(makeCellPropLabels());
    setStyles();
  }

  /**
   * Getter method that returns the CellProperties VBox. This contains all the displayed cell info.
   * @return Node that contains the displayed cell properties.
   */
  public Node getCellProperties(){
    return myCellProperties;
  }

  /**
   * Updates the label that displays the current cell that is hovered over.
   * @param currentX X coordinate of current cell.
   * @param currentY Y coordinate of current cell.
   */
  public void updateCellCordLabel(int currentX, int currentY){
    myCurrentX = currentX;
    myCurrentY = currentY;
    myCellCoordinatesLabel.setText("("+myCurrentX+", "+myCurrentY+")");
  }

  private Node makeCellPropLabels(){
    VBox labelBox = new VBox();

    labelBox.getChildren().add(initializeCellCordLabel());

    return labelBox;
  }
  /*private Label makeCellPropLabel(){
    Label retLabel = new Label()
  }*/

  private Node initializeCellCordLabel(){
    myCellCoordinatesLabel = new Label("("+myCurrentX+", "+myCurrentY+")");
    return myCellCoordinatesLabel;
  }

  private void setStyles() {
    myCellProperties.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    myCellProperties.getStyleClass().add("root");
  }



}
