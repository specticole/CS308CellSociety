# Cell Society Design Plan
### Team Number
12
### Names

Patrick Liu (pyl8)
Bill Guo (wg78)
Cole Spector (cgs26)
Franklin Wei (fw67)

## Introduction
The problem our program is trying to solve is to have the ability to load in and display any 2D CA simulation. The primary design goal is to make our Model and View as flexible as possible, allowing for any set of rules and any cell types in a simulation. Therefore, the classes responsible for simulation, configuration, and display should act as superclasses, closed for modification but open for extension to support new types of simulations.

## Overview

At the highest level, our program will be built around the Model-View-Controller (MVC) design pattern -- the model will hold the state of the simulation, in isolation from either the view or the controller. The model will expose the necessary interface for the view to display a visual representation of the simulation, and for the controller to interact with the model to update its state in a permissible manner.

TODO(Bill): describe each class
make uml picture
TODO(Franklin): describe two ways of implementing main design choices

### class CellularAutomaton:

Variables:

CellGrid myGrid;

Methods:

public CellularAutomaton(CellGrid grid);

public void step();

CellGrid getGrid(); // called by the view


### abstract class CellGrid extends java.lang.Iterable

Variables:

List<Cell> myCells;
int currentTime;

Methods:
public List<Cell> getNeighbors(Cell myCell);

@Override
public Iterator<Cell> iterator();

int getCurrentTime();

#### class RectangularGrid extends CellGrid

This could use a simple dense 2D array, or a List of Lists.

#### class HexGrid extends CellGrid

This class could use a "staggered" List of Lists, and  represent points as the span of two basis vectors.

### class Cell

Variables:

CellGrid parentGrid;
CellStateList myStates;
CellCoordinates myCoordinates;

Methods:
abstract public void step(); // append a state
CellState getState(int time);

### class CellStateList

private int maxStatesToKeep; // 0: keep infinite states

### abstract class CellState
//each concrete CellState will have an Enum for possible cell states.

Methods:
public void updateState(List<Cell> myNeighbors);

public String toString();
public static CellState fromString(String str);

#### class GameOfLifeCellState extends CellState

#### class UserConfiguredCellState extends CellState

### abstract class CellCoordinates

#### class RectangularGridCoordinates extends CellCoordinates

### abstract class CellularAutomatonView:
Variables:

CellularAutomaton myModel;

#### class CellularAutomatonGridView extends CellularAutomatonView

#### class CellularAutomatonPlotView extends CellularAutomatonView


### class CellularAutomatonController:
Variables:
private CellularAutomaton myModel;
private List<CellularAutomatonView> myViews;
private double frameRate;

Methods:
public CellularAutomatonController(CellularAutomaton model, List<CellularAutomatonView> views, String configFileName);

public void playSimulation();

public void pauseSimulation();

public void resetSimulation();

public void setAnimationSpeed(int speed);

public void updateGrid(int time);

## User Interface

![](https://i.imgur.com/IU1hTtW.jpg)
For our user interface we want to model it off of the Wa-Tor design (above) by having a primary view (with the option to add more like the line graph) along with buttons at the bottom of the screen.  In our CA, we hope to have the buttons shown in the image -- speed, step, start, stop, reset -- along with a step-backwards button which allows the user to see previous itterations of the CA.

TODO: expand (Cole)

## Configuration File Format


```xml

<simulation rules="conway">
<title>Conway's Game of Life</title>
<author>Joe Schmoe</author>
<description>Basic Game of Life example on a 20x10 grid</description>
<grid type="rectangular" width="20" height="10">
<gridrow><gridcell>D</gridcell><gridcell>A</gridcell></gridrow><!-- D = dead -->
</grid>
<cellstyle type="D">
<rgb></rgb>
</cellstyle>
</simulation>
```

another example

Config #2 - Franklin

## Design Details

TODO: Franklin, Cole

## Use Cases

TODO: 2 per person in addition to these:

Cole 1:
Cole 2:

Patrick 1:
Patrick 2:

Franklin 1:
Franklin 2:

Bill 1:
Bill 2:

    Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)
    Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)
    Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically - Bill
    Set a simulation parameter: set the value of a global configuration parameter, probCatch, for a simulation, Fire, based on the value given in an XML fire - Patrick
    Switch simulations: load a new simulation from an XML file, stopping the current running simulation, Segregation, and starting the newly loaded simulation, Wa-Tor - Patrick


## Design Considerations
Patrick

## Team Responsibilities

 * Franklin Wei - Model

 * Patrick Liu - Controller

 * Bill Guo - View

 * Cole Spector - Model

Rough timeline: Subteams will work individually until Wednesday, implementing features that can be abstracted and do not rely on other classes. Model subteam will aim to have modules completed by Wednesday, giving View and Controller ample time to test functionality between them.
