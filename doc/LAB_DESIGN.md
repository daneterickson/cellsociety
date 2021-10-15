# Cell Society Design Discussion

### Team Number: 8

### Names: Dane Erickson dte12, Aaric Han ah496, Nick Strauch njs35, Albert Yuan aly11

### Discussion Questions

* How does a Cell know what rules to apply for its simulation?
    * Reads Model type from .sim file. This should call a specific simulation model class.

* How does a Cell know about its neighbors?
    * all cell information is stored in model, so we will be able to call the cell's neighbors using
      indexes.

* How can a Cell update itself without affecting its neighbors update?
    * create a new array and then add the new information to it. Then replace the old array.

* What behaviors does the Grid itself have?
    * get and set a value to a cell

* How can a Grid update all the Cells it contains?
    * iterate through all cells and update each cell. And then update everything with the new cells.

* How is configuration information used to set up a simulation?
    * Give us initial starting positions for cells, and tells us which model to call

* How is the GUI updated after all the cells have been updated?
    * the view calls the model to send back updated information.

### Alternate Designs

#### Design Idea #1

* Data Structure #1 and File Format #1
    * store cell objects in grid as 2d arraylist
    * csv (child class of parser)

* Data Structure #2 and File Format #2
    * store cell objects in grid as 2d array
    * tsv (child class of parser)

#### Design Idea #2

* Data Structure #1 and File Format #1
    * store cell objects in grid as hashmap where each cell's x+y*width
    * csv (child class of parser)

* Data Structure #2 and File Format #2
  * store cell objects in grid as hashmap where each cell's x+y*width
  * json - calls json parser (child class of parser)

### CRC Card Classes

This class's purpose is represent the grid of cells:

|grid| |
|---|---|
|void update(x,y, status) | |
|void getStatus(x, y) | |
| | |

```java
public class grid {

  // 
  public update(x, y, status);

  // 
  public getStatus(x, y);

  // creates cell objects
  public initialize();
  // 
  public;
}
 ```

This class's purpose is represent a ViewCell:

|ViewCell| |
|---|---|
| | |
| | |
| | |

```java
public class ViewCell {

  private int x, y;
  private int state;
}
 ```

This class's purpose is represent a cell's data:

|ModelCell| |
|---|---|
| | |
| | |
| | |

```java
public class ModelCell {

  private int x, y;
  private int state;
}
 ```

This class's purpose is represent a parser:

|parser| |
|---|---|
|parse | |
| | |
| | |

```java
public class parser {
  //reads file 
  void parse(file);
}
 ```

This class's purpose is represent the view:

|view| |
|---|---|
|void selectFile() | |
|void sendToController() | |
|void Initialize()| |
|void startAnimation()| |
|void setAnimationSpeed() | |
|void changeCellState() | |

```java
public class view {

  // prompts the user to select a file
  public FILE selectFile();

  // sends information to the controller
  public sendToController();

  // makes javafx stuff, creates the initial grid, etc
  public Initialize();

  // starts the animation
  public startAnimation();

  // starts the animation 
  public setAnimationSpeed();

  // changes the cell's color, on/off, etc
  public changeCellState();

}
 ```

This class's purpose is to represent the model:

|model| |
|---|---|
|void getNeighbors() | |
|void checkChange() | |
|void sendToController()| |
|void main()| |

This class's purpose or value is to represent a customer's order:

```java
public class Model {

  // main function that iterates through the grid
  public step();

  // finds all 8 neighbors of the current cell
  private getNeighbors();

  // checks the current cell for changes
  private checkChange();

  // sends info to Controller
  public updateView();
}
 ```

This class's purpose is to represent the controller:

|controller| |
|---|---|
|void updateModel() | |
|void updateView()| |
| | |

```java
public class controller {

  // sends initial info and view info to model
  public updateModel();

  // passes info from model to view
  public updateView();

}
 ```

### Use Cases

* Apply the rules to a cell: set the next state of a cell to dead by counting its number of
  neighbors using the Game of Life rules for a cell in the middle (i.e., with all of its neighbors)

```java
public class model {

  changedCells =new

  ArrayList();

  void main() {
    newgrid = new Grid();

    for (int x : oldgrid.length) {
      for (int y : oldgrid.length) {
        checkUpdate(x, y);
      }
    }
  }

  void checkUpdate(x, y) {
    int[] neighbors = getNeighbors(x, y);
    //checks rules based on neighbors and model type
    ...
    newGrid.update(x, y, DEAD_STATE);
    update = true;
    ...
    if (update) {
      changedCells.add(x, y);
    }
  }
}
```

* Move to the next generation: update all cells in a simulation from their current state to their
  next state

```java
public class view {

  changedCells =new ArrayList();

  void step() {
    changedCells = model.updateView();
    for (int idx = 0; 0 < changedCells.size(); idx++) {
      x = changedCells[idx];
      y = changedCells[idx + 1];
      ModelCell = controller.getCell(x, y);
      ViewCell = getcell(x, y);
      ViewCell.importCell(ModelCell);
    }
  }
}

public class ViewCell() {

  void importCell(ModelCell) {
    x = modelCell.x;
    ...
  }
}

```

* Switch simulations: load a new simulation from a data file, replacing the current running
  simulation with the newly loaded one

```java
public class view() {

  void selectFile() {
    fileChooser.setTitle("Open Resource File");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("All Files", "*"));
    File selectedFile = fileChooser.showOpenDialog(null); //need to look into this

    controller.sendFile(selectedFile);
  }
}

public class controller() {

  void sendFile(file) {
    model = parser.parse(file);
  }
}

public class parser(){
  model parse(file){
    Scanner sc = new Scanner(file);
    modelType = sc.nextLine();
    sc.close();
    return modeltype;
  }
}
```
