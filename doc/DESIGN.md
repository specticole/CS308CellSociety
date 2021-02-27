# Cell Society Design Final
### Names
Franklin Wei  
Cole Spector  
Bill Guo  
Patrick Liu  

## Team Roles and Responsibilities

 * Franklin Wei: Model, and interfacing with controller.

 * Cole Spector: Model, and various simulation implementations.

 * Bill Guo: View and styling.

 * Patrick Liu: Controller and XML input.


## Design goals

#### What Features are Easy to Add

- New grids and game rules are extremely easy to add.

- New types of views (e.g. grid and time graphs) are easy to add.

- New preset configurations are easy to generate.

## High-level Design

#### Core Classes

On the model side, the top-level class is a CellularAutomaton, which
consists of a CellGrid (which contains the current and past states of
the automaton) and CellularAutomatonRule, which governs the evolution
of states.

Both CellGrid and CellularAutomatonRule are abstract
classes. Completed implementations of CellGrid include
RectangularCellGrid and HexagonalCellGrid, both of which are
subclasses of the intermediate abstract class Dense2DCellGrid, which
in turn is a specialization of CellGrid.

CellGrids contain a collection of Cells, and each Cell contains a
StateList of CellStates (an abstract class) that keeps track of its
current and past states. The decision to keep CellStates and Cells
separated was motivated by the single-responsibility principle (SRP)
-- a Cell is a combination of both the physical location on a grid,
and the "state" of that location as a function of time. Once we
determined this, we decided that it made sense to separate these two
responsibilities between two classes so as to adhere to the SRP.

Different simulations are implemented by providing a matching
CellState and CellularAutomatonRule -- for example, Game of Life
requires a GameOfLifeRule and a GameOfLifeState. Each
CellularAutomatonRule implements a method to advance the state of a
simulation Cell given the states of its neighboring Cells.

The Model interacts with the View and Controller through a
intentionally limited API that only exposes essential operations, thus
hiding unneccesary functionality from the View and Controller and
maintaining encapsulation.

The Controller has one primary class, CellularAutomatonController, that has an instance
of a CellularAutomaton and CellularAutomatonView object. This class mediates between
the Model and the View, ensuring that they are completely separate from one another. 
In essence, each cycle of a simulation, the Controller calls the Model's step method,
translates the grid of CellStates into a 2D ArrayList of Strings, and then passes the
updated grid to the View. 

Elsewhere in the Controller jurisdiction, there are CellularAutomatonConfiguration and 
CellularAutomatonStyle classes, which primarily hold relevant information read in from 
the configuration and style files, respectively. These classes mainly consist of getters,
but Collections objects are returned as unmodifiable to mitigate breaches of trust from other
classes. These classes separate the Controller from the file format of the configuration files. 

Finally, the XML parsing consists of three classes: XMLGenericParser, XMLConfigurationParser, 
and XMLStyleParser. Configuration and Style inherit basic parsing methods from Generic but 
implement methods that look for specific tags or perform specific tasks, such as creating 
the grid of initial states. 

## Assumptions that Affect the Design

We assumed that all grids have coordinates that can be described with
two integers. Seeing as the specification was to have a
two-dimensional grid, this seems reasonable and did not significantly
impede the implementation of any features.

We also assumed that all grids were finite (i.e. we can iterate over
all Cells in a finite amount of time).

Another assumption we made was that all simulations pause/play at the same time.
This simplification allowed us to only have one Controller for the entire
GridPane, instead of having separate Controller objects for each SimulationView. 

Finally, we made the assumption that the style file must be read in at the same time
as a configuration file, preventing some elements from being "styled" mid-simulation
and simplifying how SimulationViews are constructed.

#### Features Affected by Assumptions

The assumption of a finite grid made it difficult to adapt our program
to support infinite grids.

## Significant differences from Original Plan

No significant deviations in the model design were made from the
original PLAN. The most major difference was the introduction of a
Dense2DCellGrid subclass of CellGrid to ease implementation of
Rectangular and Hexagonal grids, but this wasn't so much a
modification to the PLAN as an addition.

## New Features HowTo

Adding a new dense CellGrid (i.e. triangular cells) is easy: simply
come up with a 2D coordinate system and describe the rule for finding
neighbor cell coordinates from a central cell's coordinate. Then,
program this into a subclass of Dense2DCellGrid and place it in the
cellsociety.model.grids module. Additionally, add it to the
cellsociety.model.grids.Index class so it will be detected by the Controller
when loading XML files.

Adding new simulations requires writing two classes: a CellState
subclass class, and a CellularAutomatonRule subclass. For both of
these, a good template is GameOfLife{Rule,State} -- simply adapt these
classes to suit your new game rule. The most import bit of code to
write is the advanceCellState method of the CellularAutomatonRule
class -- this method needs to know all the details of the particular
simulation, and apply them appropriately to a given central cell of a
Moore neighborhood. In addition, if there are simulation-specific parameters,
those will be read in as Strings from the XML file, so the setGameSpecifics 
method in CellularAutomatonRule is responsible for parsing that String.
Once these classes are written, add them to the
cellsociety.model.rules.Index class and they will be automatically
loaded by the Controller's XML loader.

#### Other Features not yet Done

We have a partial implementation of Langton's Loop that was left
unfinished due to time constraints. Additionally, we did not have
sufficient time to implement tiled triangular grids, although this is
technically very straightforward.

For styling, we implemented the ability to change the language of the
GUI and to turn outlines on or off based on a style file. The other possible
features that can be styled are all in the test style files in the doc folder, 
but have not been implemented in the View yet. However, since these parameters
are all stored in a CellularAutomatonStyle object that the View receives, 
implementing additional styling options solely requires writing additional 
methods in SimulationView that perform the corresponding styling actions. 