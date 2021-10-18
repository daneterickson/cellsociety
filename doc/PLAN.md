# Cell Society Design Plan
### Team Number
8
### Names
 Dane Erickson dte12, Aaric Han ah496, Nick Strauch njs35, Albert Yuan aly11


## Design Overview
We are separating the program into a model, view, and controller. The model will be divided up into an anstract class hierarchy. Each subclass will have different rules depending on the applicatoin type. The model will also accept a file from the view and then parse said file into useful data. 
The view will call the model for every animation step and get all of the updated cell states. The view is primarily divided into the different visual components of the scene. Specifically, the view was divided into a top, bottom, left, right, and center. The top will contain file realated features such as save and load. The left will have the current cell properties. The right will have modifications that can be made to the simulation. The bottom will have animation specific tools such as pause and play. And the center will have the displayed grid(s). We will also have a controller that will be the middle-man for the view and the model. Whenever the view needs to get new data from the model, the model will first send the data to the controller. The controller will then modify the data before sending it to the view to ensure that the view only has as much access as it needs to the model data.

## Design Details

Here is a graphical look at my design:

![This is cool, too bad you can't see it](images/online-shopping-uml-example.png "An initial UI")

made from [a tool that generates UML from existing code](http://staruml.io/).


## Design Considerations

#### Design Issue #1
Communication between the model grid and the view grid.

 * Alernative #1: Send an entire grid object from the model to the controller to the view.

 * Alernative #2: Send each cell individually from the model to the controller to the view.

 * Trade-offs : Alternative #1 would only require one transfer from the model to the view, but would also be sending more data than the view may need. Alternative #2 would require multiple transfers of cell objects from the model to the view, but this would allow the model to selectively send only the cells that underwent an update in state.


#### Design Issue #2
Construction of the grid view.

 * Alernative #1: Create the grid view by drawing in a canvas and assign each canvas coordinate to a cell object in the model.

 * Alernative #2: Create the grid view by creating a grid of rectangle Shape nodes. Each of these Shape objects would be stored in a cell object.

 * Trade-offs: Alternative #1 would give more freedom in the grid shape and structure. Alternative #2 would simplify the process of updating the grid view given updated cell objects from the grid model.



## User Interface

Here is our amazing UI:

![This is cool, too bad you can't see it](images/29-sketched-ui-wireframe.jpg "An alternate design")

taken from [Brilliant Examples of Sketched UI Wireframes and Mock-Ups](https://onextrapixel.com/40-brilliant-examples-of-sketched-ui-wireframes-and-mock-ups/).


## Team Responsibilities

 * Dane Erickson
   * Backend responsibilities: Grid model and Cell model.


 * Albert Yuan
   * Backend responsibilities: Application models. (Game of life, segregation, etc.).


 * Nick Strauch
   * Frontend responsibilities: Grid view, Cell properties, and Animation.


 * Aaric Han
   * Frontend responsibilities: File loading/saving, animation start/stop/step, simulation modification inputs.


#### Proposed Schedule

* Test implementation done by October 19th, 10:00pm.

* Game of Life implementation done by October 22nd, 11:00pm.

* Speading of Fire implementation done by October 24th, 10:00pm.

* Segregation, Predator-Prey, and Percolation done by October 26th, 10:00pm.

* Duvall's required modifications finished by October 30th, 10:00pm.

* Refactoring, bugfixing, code-cleanup by 10:00pm October, 31st 10:00pm.



