package cellsociety.view.left;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CellProperties {

  private String RESOURCE = "cellsociety.view.left.";
  private String STYLESHEET = "/"+RESOURCE.replace(".", "/")+"CellProp.css";

  private final String DEFAULT_SIM = "Game Of Life";
  private final String STATE_COLOR_TITLE = "Color Key:";
  private int myCurrentX;
  private int myCurrentY;
  private String myCurrentState;
  private Label myCellCoordinatesLabel;
  private Label myCellStateLabel;
  private Label mySimTypeLabel;
  private VBox myCellProperties;
  private ResourceBundle myResource;


  public CellProperties(ResourceBundle resource){
    myResource = resource;
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

  /**
   * Updates the label that displays the current simulation type
   * @param simType The String representing the simulation type
   */
  public void updateSimTypeLabel(String simType){
    mySimTypeLabel.setText(simType);
  }

  private Node makeCellPropLabels(){
    VBox labelBox = new VBox();
    labelBox.getChildren().add(initializeSimTypeLabel());
    labelBox.getChildren().add(makeCellCordTitle());
    labelBox.getChildren().add(initializeCellCordLabel());
    labelBox.getChildren().add(makeStateColorTitle());

    return labelBox;
  }

  private Node initializeCellCordLabel(){
    myCellCoordinatesLabel = new Label("("+myCurrentX+", "+myCurrentY+")");
    return myCellCoordinatesLabel;
  }

  private Node makeCellCordTitle(){
    Label title = new Label(myResource.getString("CoordinateTitle"));
    title.getStyleClass().add("coordinateTitle");
    return title;
  }

  private Node initializeSimTypeLabel(){
    mySimTypeLabel = new Label(DEFAULT_SIM);
    return mySimTypeLabel;
  }

  private Node makeStateColorTitle(){
    Label title = new Label(STATE_COLOR_TITLE);
    title.getStyleClass().add("stateColorsTitle");
    return title;
  }
  /*private Node initializeCellStateLabel(myCurrentState){
    myCellStateLabel = new Label();
    return myCellStateLabel;
  }*/

  private void setStyles() {
    myCellProperties.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    myCellProperties.getStyleClass().add("root");
    myCellCoordinatesLabel.getStyleClass().add("coordinateLabel");
    mySimTypeLabel.getStyleClass().add("simTypeLabel");
  }

  public Node setResource(ResourceBundle bundle) {
    myResource = bundle;
    return makeCellPropLabels();
  }



}
