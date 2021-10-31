package cellsociety.view.top;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class TopLoadSave {

  private String RESOURCE = "cellsociety.view.top.";
  private String STYLESHEET = "/" + RESOURCE.replace(".", "/") + "TopLoadSave.css";

  private HBox myTopLoadSave;
  private Stage myStage;
  private Controller myController;
  private ResourceBundle myResources;

  public TopLoadSave(Stage stage, Controller controller, ResourceBundle resources) {
    myStage = stage;
    myTopLoadSave = new HBox();
    myTopLoadSave.getChildren().add(makeLoadSaveButtons());
    setStyles();
    myController = controller;
    myResources = resources;
  }

  private void setStyles() {
    myTopLoadSave.getStyleClass().add("root");
    myTopLoadSave.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
  }

  private Node makeLoadSaveButtons() {
    HBox topLoadSave = new HBox(15);
//    topLoadSave.getChildren().add(makeLoadCSVButton());
//    topLoadSave.getChildren().add(makeSaveCSVButton());
    topLoadSave.getChildren().add(makeLoadSIMButton());
    topLoadSave.getChildren().add(makeSaveSIMButton());
    return topLoadSave;
  }

//  private Node makeLoadCSVButton() {
//    Button loadCSVButton = new Button("Load CSV File");
//    loadCSVButton.setOnAction(e -> loadCSVFile());
//    return setID(loadCSVButton, "LoadCSVButton");
//  }

  private Node makeLoadSIMButton() {
    Button loadSIMButton = new Button("Load Sim File");
    loadSIMButton.setOnAction(e -> loadSIMFile());
    return setID(loadSIMButton, "LoadSIMButton");
  }

//  private Node makeSaveCSVButton() {
//    Button loadButton = new Button("Save CSV File");
//    loadButton.setOnAction(e -> saveCSVFile());
//    return setID(loadButton, "SaveCSVButton");
//  }

  private Node makeSaveSIMButton() {
    Button saveSIMButton = new Button("Save SIM");
    saveSIMButton.setOnAction(e -> saveSIM());
    return setID(saveSIMButton, "SaveSIMButton");
  }

//  private void loadCSVFile() {
//    FileChooser fileChooser = new FileChooser();
//    fileChooser.setTitle("Load CSV File");
//    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV", "*.csv"));
//    File selectedFile = fileChooser.showOpenDialog(myStage);
//    if (selectedFile == null) {
//      return;
//    }
//    myController.openCSVFile(selectedFile);
//  }

  private void loadSIMFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Load SIM File");
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("SIM", "*.sim"));
    File selectedFile = fileChooser.showOpenDialog(myStage);
    if (selectedFile == null) {
      return;
    }
    myController.openSIMFile(selectedFile);
  }

  private void saveCSVFile(File saveFile) {
    File CSV = new File(saveFile.toString() + ".csv");
    Grid tempGrid = myController.getGrid();
    try {
      PrintWriter csvFile = new PrintWriter(CSV);
      csvFile.write(tempGrid.getNumRows() + "," + tempGrid.getNumCols() + "\n");
      for (int i = 0; i < tempGrid.getNumRows(); i++) {
        StringBuilder rowCSV = new StringBuilder();
        for (int j = 0; j < tempGrid.getNumCols(); j++) {
          if (j != tempGrid.getNumCols() - 1) {
            rowCSV.append(tempGrid.getCellStateNumber(i, j) + ",");
          } else {
            rowCSV.append(tempGrid.getCellStateNumber(i, j) + "\n");
          }
        }
        csvFile.write(rowCSV.toString());

      }
      csvFile.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void saveSimFile(File saveFile) {
    File SIM = new File(saveFile.toString() + ".sim");
    Map simValueMap = myController.getSimPropertiesMap();
    try {
      PrintWriter simFile = new PrintWriter(SIM);
      for (Object key : simValueMap.keySet()) {
        simFile.write(key.toString() + "=" + simValueMap.get(key).toString() + "\n");
      }
      simFile.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  private File openSaveFileDialog() {
    FileChooser fileChooser = new FileChooser();
    File savedCSV = fileChooser.showSaveDialog(myStage);
    return savedCSV;
  }
  private void saveSIM() {
    File saveFiles = openSaveFileDialog();
    saveCSVFile(saveFiles);
    // TODO: Add a method to save the SIM file
    saveSimFile(saveFiles);
  }

  private Node setID(Node disp, String id) {
    disp.setId(id);
    return disp;
  }

  public Node getTopLoadSave() {
    return myTopLoadSave;
  }

}
