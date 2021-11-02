Cell Society
====

This project implements a cellular automata simulator.

Names: Dane Erickson dte12, Aaric Han ah496, Nick Strauch njs35, Albert Yuan aly11

### Timeline

Start Date: 10/13/2021

Finish Date: 11/02/2021

Hours Spent: 100

### Primary Roles

* Front end: Aaric Han and Nick Strauch
* Back end: Dane Erickson and Albert Yuan

### Resources Used

- Google Material UI icons
- Google Material for OpenCSV for parser

### Running the Program

Main class: Main

Data files needed: SIM file with information for the simulation that references a CSV file with
start states for each cell

Features implemented: different neighbor arrangements, different grid edge policies, different grid
shapes, random initial configurations, different simulation styles, different views for the
simulation, change simulation values dynamically

### Notes/Assumptions

Assumptions or Simplifications:
* Sim file contents
    * key names, order of the parameters for the simulations and for random configurations, order of
      the states, state numbers (ie fish = 1, shark = 2)
* At start, the user load simfile
* The user knows what each color represents in gridview
* To reset, the user must load a simfile
* The user only speaks English or Spanish
* When there are multiple grids, the user plans on playing all of them

Interesting data files:
* example.sim in Segregation

Known Bugs:
* Cannot add grids and then switch the grid type for all those grids. Must change the grid type before adding more grids.
* Histogram and CircleTimeline only work for 1 grid.

Noteworthy Features:
* Histogram/CircleTimeline
* Neighbor policies
* Sliders for simulation parameters

### Impressions

