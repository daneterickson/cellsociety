# Cell Society Design Final

### Names: Dane Erickson dte12, Aaric Han ah496, Nick Strauch njs35, Albert Yuan aly11

## Team Roles and Responsibilities

* Aaric - Frontend (frontend sim parsing (text), language, cell properties display, settings
  sliders/buttons)

* Nick - Frontend (animation, speed sliders/buttons, setting sliders, grids)

* Dane - Backend (backend sim parsing, grid class, cell classes), configuration changes, histogram
  change

* Albert - Backend (model class), simulation changes, histogram change

## Design goals

#### What Features are Easy to Add

* Adding the different game models and game model cells. All the models iterated through the grid,
  retrieved the neighboring cell states, and then changed the cell based on the neighbors
* View Panels because we used the borderpane class

## High-level Design

#### Core Classes

* Controller
* Model
    * Rules
    * EdgePolicies
    * NeighborFinder
* Cell
* Grid
* Parser
* SimControl (play/pause/step animation speed)
* CenterView (display grid/histogram)
* CellProperties (display cell properties as text)
* MainView (connect view bits)
* GameSettings (control game settings from view)
* TopLoadSave (buttons to load/save sim)
* Main

## Assumptions that Affect the Design

* Sim file contents
    * key names, order of the parameters for the simulations and for random configurations, order of
      the states, state numbers (ie fish = 1, shark = 2)
* At start, the user load simfile
* The user knows what each color represents in gridview
* To reset, the user must load a simfile
* The user only speaks English or Spanish
* When there are multiple grids, the user plans on playing all of them

#### Features Affected by Assumptions

* When we parse the file and send the data to the model, we use iterate through each line of the sim
  file. For example, in predator/prey we assume the string comes in like "1,2,3,4" so
  sharkreproduction is 1, fishreproduction is 2, and so on.
* If the user doesn't load a simfile and tries to save, it causes errors
* The Color State Label doesn't exist
* We don't a reset button.
* We only implemented Spanish and English
* The play button plays all the grids' animations

## Significant differences from Original Plan

We didn't drift too far from the original plan except for we added a ton more methods and classes.
View got split up into sections of the screen (top, left, middle, right, bottom), model got some of
its functions put into subclasses like EdgePolicies, NeighborFinder, and Rule. Parser was split up
into ParserCSV and ParserSIM. Controller stayed mostly the same, but with many more methods added
that transferred specific data between the model and view.

Perhaps our biggest deviation from the original plan was to move grid/cell to the controller. This
made it easy for both the view and model to access it, and it took out the need for a model
grid/cell. Instead, the model and view shared one grid object.

## New Features HowTo

#### Easy to Add Features

It was pretty easy to add all the new features. The only hard part was making it. Nick spent a
considerable amount of time figuring out how to do hexagons/triangles, but only because of the math
that it took. For the most part our design made it very easy to add new features. To add new edge
policies and neighborfinders, we just extracted the entire process into a class and used
inheritance/interfaces.

#### Other Features not yet Done

* Correct Hexagon arrangement and neighbor finding
* Choosing which colors represent each state
