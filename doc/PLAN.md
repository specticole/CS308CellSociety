# Cell Society Design Plan
### Team Number
12
### Names

Patrick Liu (pyl8)
Bill Guo (wg78)
Cole Spector (cgs26)
Franklin Wei (fw67)

## Introduction
The problem our program is trying to solve is to have the ability to load in and display any 2D CA simulation. The primary design goal is to make our Model and View as flexible as possible, allowing for any set of rules and any cell types in a simulation. Therefore, the classes responsible for simulation, configuration, and display should be abstract base classes, closed for modification but open for extension to support new types of simulations.

## Overview

At the highest level, our program will be built around the Model-View-Controller (MVC) design pattern -- the model will hold the state of the simulation, in isolation from either the view or the controller. The model will expose the necessary interface for the view to display a visual representation of the simulation, and for the controller to interact with the model to update its state in a permissible manner.

### Major Design Decisions

There are several major design decisions that present themselves:

* **Level of generality in supporting different rule sets** -- should we allow XML descriptors to define new types of game rules, or should we limit ourselves to game rulesets defined in software? The two alternatives here are to 1) define a flexible enough file format (and corresponding software architecture) to support Turing-complete rule sets described in a file (likely through some sort of bytecode virtual machine), and 2) limit ourselves to a fixed set of game rules defined in Java. On this front we opted for a compromise -- our software architecture will be general enough that supporting arbitrary rulesets defined in XML should be possible, but we will not implement this in the first-order design.

* **Grid representation** -- on this front it's important to make things generalizable: we want to make it possible to support not only conventional rectangular grids, but also unforeseen requirements such as hex grids, rhombitrihexagonal tilings, or perhaps even aperiodic Penrose tilings. Such a diverse range of possible "grids" means that our method of representing the abstract notion of a "grid" must be extremely general. This led us to the conclusion that the interface that must be implemented by an abstract grid consists of: 1) iteration over Cells, and 2) retrieval of the neighbors of cells (both immediate neighbors, and perhaps neighbors within a certain "distance metric" -- either Euclidean or Manhattan distance, or perhaps "hops" along an undirected graph). With this in mind, we considered the following two options for grid representation: 1) a simple 2D array representation, and 2) an adjacent-list (graph) representation. The 2D array is self-explanatory, but the adjacency is more generalizable: the CellGrid class would hold a collection of Cells in a Map<CellCoordinates, Cell>, and each Cell would have a list of its own neighbors. Our choice of functionality exposed by CellGrid can be used to implement both the array and graph-based representation. A graph-based representation would need to provide its own implementation of CellCoordinates (which may lose any geometric meaning), as well as CellGrid. Our interface is general enough that both alternatives can be implemented as subclasses.

* **Types of Views** -- We wanted our definition of the View base class to be generalizable to different types of views. To this extent, we considered the following types of views: 1) A 2D grid-based view, and 2) a time-domain plot-based view showing "populations" of each CellState. To implement a 2D grid view, we decided that it would be necessary for us to provide a different implementation of CellularAutomatonGridView for each possible grid layout -- rectangular, hex, or trihexagonal. This was a compromise we reached after trying, and failing, to find a sufficiently general interface for the CellGrid component of the model that could accommodate both rectangular and hexagonal grids.

### Proposed Class Hierarchy

![Diagrammatic representation of the class hierarchy.](https://i.imgur.com/jvItQVE.png)

### class CellularAutomaton:

This class is the top level of the Model. It contains a CellGrid, handles updates and collaborates with the View to show the simulation.

Variables:

CellGrid myGrid;
CellStateRule myGameRule;

Methods:

public CellularAutomaton(CellGrid grid, CellStateRule rule);

public void setStateRule(CellStateRule rule);
public CellStateRule getStateRule()

public void step(); // advance the simulation time of the grid

public CellGrid getGrid(); // called by the view

public void setGrid(CellGrid newGrid); // replace grid with a new grid


### abstract class CellGrid implements java.lang.Iterable

This class contains a collection of Cells that are arranged in some 2D geometric tiling. Derived classes -- a rectangular, hexagonal, or trihexagonal tiling, for example --  will concretely define this tiling by implementing getNeighbors(). This class also has the notion of "time" -- an integer monotonic counter that indicates the number of simulation time steps that have elapsed since initialization. Each Cell will keep a CellStateList of its current and past states to allow both forward and reverse playback. Implementing Iterable allows the parent CellularAutomaton to iterate over the constituent Cells in a clean and extensible fashion, without needing to expose the underlying Collection of Cells.

Variables:

List<Cell> myCells;
int currentTime;

Methods:

public List<Cell> getNeighbors(Cell cell);

@Override
public Iterator<Cell> iterator();

int getCurrentTime();

#### class RectangularGrid extends CellGrid

This could use a simple dense 2D array, or a List of Lists.

#### class HexGrid extends CellGrid

This class could use a "staggered" List of Lists or a 2D array.

#### class TriHexGrid extends CellGrid

This class represents a "grid" that consists of a trihexagonal tiling of the plane.

### class Cell

This class represents a single cell. Each cell has a CellStateList that containes a list of the  states of the cell across time. Cells also have CellCoordinates depending on the grid the cells are housed in. Each cell has a reference to its parent GridCell to find its neighbors and determine its next state, which is added on the end of the CellStateList.

Variables:

CellGrid parentGrid;
CellStateList myStates;
CellCoordinates myCoordinates;

Methods:
abstract public void step(); // append a state
CellState getState(int time);

### class CellStateList

The list of states that a Cell has across time. The number of previous states can be capped to keep the simulation from accumulating memory.

private int maxStatesToKeep; // 0: keep infinite states

public CellState getCellState(int index);

### abstract class CellState
Represents all the states that a cell can take. Each concrete CellState will likely have an Enum for possible cell states. This class is immutable.

Methods:

public String toString();
public static CellState fromString(String str);

#### class GameOfLifeCellState extends CellState

Allowable states are "DEAD" or "ALIVE".

#### class WaTorCellState extends CellState

Allowable states: Empty, Shark, Fish.

### interface CellStateRule

Determines the rules for computing the next state of a cell given a list of its neighbors.

public abstract CellState nextCellState(Cell cell, List<Cell> neighbors);

#### class GameOfLifeRule implements CellStateRule

#### class WaTorWorldRule implements CellStateRule

### abstract class CellCoordinates

Represents the coordinates of a cell, which are used to determine the neighbors of a cell.

#### class RectangularGridCoordinates extends CellCoordinates

### abstract class CellularAutomatonView:

An abstract type representing some graphical interface to the Model. Communicates with the CellularAutomaton Model to receive the state of the grid and display it.

Variables:

CellularAutomaton myModel;

Methods:
public void setDisplayTime(int time);

#### class CellularAutomatonGridView extends CellularAutomatonView

#### class CellularAutomatonPlotView extends CellularAutomatonView


### class CellularAutomatonController:

Manages the GUI, window initialization, and JavaFX boilerplate. Handles user input and relays changes to both the CellularAutomaton and/or the CellularAutomatonView, as appropriate. In charge of loading in the XML file to determine the starting characteristics of the simulation.

A controller will contain one or more views, all of which display the state of the simulation at the same "simulation time." This is an assumption to simplify implementation of the views.

Variables:
private CellularAutomaton myModel;
private List<CellularAutomatonView> myViews;
private double simulationRate; // target ticks/sec

Methods:
public CellularAutomatonController(CellularAutomaton model, List<CellularAutomatonView> views, String configFileName);

public void loadConfigurationFile(String configFileName);

public void playSimulation();

public void pauseSimulation();

public void changeAnimationRate(double rate);

public void step();

public void updateViews(int newTime);

## User Interface

![](https://i.imgur.com/IU1hTtW.jpg)
For our user interface we want to model it off of the Wa-Tor design (above) by having a primary view, with the option to add more like the line graph, along with buttons at the bottom of the screen.  In our CA, we hope to have the image colors customizable from within the xml config file, and for the buttons we hope to include the buttons shown in the image above -- speed, step, start, stop, reset -- along with a step-backwards button which allows the user to see previous itterations of the CA.  Because all of the setup for the CA is done in the config file, within the user interface there is no ability for the user to input invalid commands.

## Configuration File Format


```xml
<simulation rules="conway">
<meta>
<title>Conway's Game of Life</title>
<author>Joe Schmoe</author>
<description>Basic Game of Life example on a 20x10 grid</description>
</meta>
<grid type="rectangular" width="20" height="10" edge="wrap">
<gridrow><gridcell>D</gridcell><gridcell>A</gridcell></gridrow><!-- D = dead, A = alive -->
</grid>
<cellstyle type="D">
<rgb></rgb>
</cellstyle>
</simulation>
```

another example:

```xml
<simulation rules="fire">
<parameters>
<probcatch>0.50</probcatch>
</parameters>
<meta>
<author>Franklin Wei</author>
<title>Spreading of Fire example</title>
<description>More complex example with Spreading of Fire rules</description>
</meta>
<grid type="hex" width="10" height="10" edge="E"
<hexrow><hexcell>E</hexcell><hexcell>B</hexcell><hexcell>T</hexcell></hexrow>
<!--- more rows -->
</grid>
</simulation>
```

## Design Details

Our project's classes are split into three main categories: controller, model, and view.  The controller category revolves around the CellularAutomatonController class, which will get information from the user based off of which button they click or how they reposition the speed slider, and will relay this information to the "model classes."  Along with this, the controller class may send some information to the view class, such as the current time (the number of steps which have been taken), or a change in the speed.  This component is needed in order for user interaction to be possible, while also segregating it from the main interworkings of the project allowing for maximum abstractibility.

The second category, the model, incorporates three abstract classes -- CellGrid, CellState, CellCoordinates -- and two concrete classes -- Cell, CellStateList.  The CellGrid abstract class allows for the implementation of many different types of grids (with RectangularGrid and HexGrid given as examples in our Overview).  The second abstract class, CellState, allows for Cells to get different states (such as EMPTY, TREE, and BURNING in the Spreading Fire CA, and PREDATOR and PREY in the Wa-Tor CA) for different CA models, along with the conditions for which these states change.  The final abstract model class, CellCoordinates, allows for different grid types to refer to the coordinates of its cells in different ways (both rectangular and hex grids could use a simple cartesian coordinate system -- with the hex grid using a "staggered" scheme, but more complex grids might need more complex coordinate schemes).

The first concrete class mentioned, the Cell class, represents each individual cell in the CellGrid, and has its type defined by an enum in CellState.  This way, all possible grid types can support all possible CellStates.  The second concrete, and final model class is the CellStateList, which allows for "time travel" within each cell, along with restrictive memory allocation.  The CellStateList creates a List of the Prior CellStates of a Cell, which allows for them revert to any prior CellState, and also allows for a max number of prior cells to be storred, such that the problem of infinite RAM usage can be solved.
    Each of the model classes will get information from the controller class (such as if to progress or rewind), and will be used by the view class to visually update the presented view.

Finally, the view category has one abstract class: CellularAutomatonView.  This class determines how to present the information in the model classes as a visual representation, and is an abstract class so that this information can be presented in a variety of different ways (such as the CellularAutomatonGridView and CellularAutomatonPlotView examples given in our Overview).  With this abstraction, our project will have the option of whether or not to present multiple different view-types to the user, which may be useful for one CA type, but not in another (i.e. the CellularAutomatonPlotView may be usefull for presenting the Wa-Tor CA, but not for the Spreading of Fire CA).

## Use Cases
1.
    Move to the prior generation: revert all cells in a simulation from their current state to their previous state and display the result graphically. To do this, our Controller will call updateViews() with the new display time. updateViews() will then call setDisplayTime() on all child Views of the Controller, passing the child View the new time which should be displayed. The View then uses this time to retrieve the cell states corresponding to the given time when redrawing the grid (in the case of a PlotView, a thin vertical line, called a "time cursor," may be drawn to indicate the display time).

2. Set simulation speed: The Controller will have a slider which, when augmented, will call changeAnimationRate() on the Controller, which will change the rate at which JavaFX callbacks are made to step().

3. Set a simulation parameter: In the CellularAutomatonController class, loadConfigurationFile() will be called (probably from GUI code) with the name of an XML config file. From this XML file, the value of a certain parameter (such as probcatch) can be retrieved using a conventional DOM parser.

4.
    Switch simulations: the user can enter a filename in a textbox in the UI, which will call loadConfigurationFile() with the new XML filename. This method will stop the current running simulation, clear the View, and start the new simulation.

5.
    Pause the simulation: there will be a button on the UI labeled "pause." When this button is pushed, the CellularAutomatonController.pauseSimulation() method is called, which stops updating the Model and View. If the simulation is already paused, this method call has no effect.

6.
    Resume the simulation: there will be a button on the UI labeled "resume." When this button is pushed, the CellularAutomatonController.resumeSimulation() method is called, which continues updating the Model and View at the current frame rate. If the simulation is already playing, this method call has no effect.

7.
Reset the simulation: pressing "reset" on the UI will call the updateViews() method in the CellularAutomaton class with time = 0 as the parameter to get the state of the simulation at time = 0.

8.
Add a plot: pressing a to-be-created button on the UI will create a new CellularAutomatonView and add it to the Parent so that it will show up on the scene.

9.
Apply the rules to a middle cell: the CellularAutomatonController class will get an Iterator object from the CellGrid in the CellularAutomaton instance. Then, the Controller will iterate through each Cell and call its update method. Within each Cell object, it will call getNeighbors using its CellGrid instance, calculate its new state (dead in this case) based on the rules established in the CellState instance, and then append a new CellState to its List (indexed by time).

10.
Apply the rules to an edge cell: same process as #9. The CellularAutomatonController iterates through the CellGrid, and Cells are responsible for calculating their next state and appending the appropriate CellState to their List of CellStates. The getNeighbors method in the CellGrid class always returns the neighboring cells in a standard order, so edge/corner cells will have nulls in their list of neighbors. The rules will account for the nulls to ensure that the next state is calculated properly.

11.
Move to the next generation: Clicking the step button on the UI will call the step() method from the CellularAutomaton class, which will in turn iterate through all Cells in the CellGrid and call step(). Each Cell then appends a new CellState (determined by calling nextState() on the latest CellState) to its CellStateList. updateViews() will then be called to advance the state of all Views in the Controller to the latest simulation time.

12. Save the simulation state: The user presses a button on the Controller, which prompts the user for a destination file path. A XML representation of the current Model state is then streamed to this file by calling the serialize() function on the CellularAutomaton class.

13. Create a random simulation: The user specifies a type and size of grid, and rule set to the Controller. A new CellGrid is created by the Controller, and is populated with random CellStates. This new CellGrid is then loaded into the Model.

## Design Considerations
One major design decision we grappled with was how to represent cell states over time, and which object should have the responsibility of updating a cell. One alternative we considered was having the CellularAutomaton class store an instance variable of a List of CellGrids, indexed by time. Cell objects would hold a reference to their parent CellGrid, and updating the grid would entail creating a new CellGrid, populating it with Cells that have updated CellStates, and appending it to the list. While this idea may be better for the View, which needs the CellGrid at a given time for visual output, it results in unnecessary copying of data. Since CellStates are the only object that change over time, we decided to have each Cell store a List of CellStates, indexed by time. Hence, there would only be one CellGrid object per simulation, and the View has to check the CellState for each Cell at a given time for visual output.

Another design decision we made was not entirely separating the Model and View. Instead, the Controller is responsible for loading configuration files, updating cell states by calling methods in the Model, and handling user inputs to change the View. However, the View updates its visual output by directly obtaining updated cell states from the Model. We chose this structure since the Model will return a standardized format for the CellGrid, which can be interpreted by the View without an intermediary. However, by doing so we have introduced a dependency between the Model and the View, which may limit the flexibility of these components.

## Team Responsibilities

 * Franklin Wei - Model

 * Patrick Liu - Controller

 * Bill Guo - View

 * Cole Spector - Model

Rough timeline: Subteams will work individually until Wednesday, implementing features that can be abstracted and do not rely on other classes. Model subteam will aim to have modules completed by Wednesday, giving View and Controller ample time to test functionality between them.
