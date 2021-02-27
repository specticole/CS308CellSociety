Cell Society
====

This project implements a cellular automata simulator.

Names:  
Patrick Liu (pyl8)  
Cole Spector (cgs26)  
Bill Guo (wg78)  
Franklin Wei (fw67)  

### Timeline

Start Date: 2/14/2021

Finish Date: 2/22/2021

Hours Spent: 20 per person, per week (total of 160 man-hours)

### Primary Roles

Patrick: controller, view

Cole: model, simulations

Bill: view, controller

Franklin: model, simulations, controller

### Resources Used

* Assorted Java documentation pages (especially those on
reflection).  
* [XML parser documentation](https://mkyong.com/java/how-to-modify-xml-file-in-java-dom-parser/)

### Running the Program

Main class: cellsociety.Main

Data files needed: everything in data/

XML configuration file format: 
* Root node must be called "simulation"
* Metadata (title, author, description) are housed in a "meta" tag
* "grid" tag has attributes for grid type, width, height, number of neighbors, whether edges should wrap, and the distribution
  * Distribution can either be "specified" if listed in the "grid" tag with appropriate "gridrow" and "gridcell" tags, or "randomtotal" if the states are not specified
    * If "randomtotal" is chosen, a "distribution" tag with a "cellstate" tag for each state will generate
  a random assignment of initial states based on the relative distribution numbers given
    * An example of a given distribution is in Fire08.xml
* "cellstyle" tag contains one "cellstyle" tag per state, with the name of the state and RGB values for the desired color
* "config_parameters" tag contains a "parameter" tag for each specified parameter, with a name and value
  * Unspecified parameters do not throw an error but are replaced with a default value

Features implemented:

* Hexagonal, rectangular grids.
* Configurable neighbor count (4 or 8) for rectangular grids.
* Configurable edge behavior: toroidal wrapping, or finite.
* Simulations implemented:
  * Fire.
  * Game of Life (with configurable subrules).
  * Langton's Loop.
  * Percolation.
  * Rock-Paper-Scissors.
  * Segregation.
  * Wa-Tor World.
* Configurable simulation speed.
* Multiple simultaneous simulations.
* Simultaneous grid/graph view.
* Extensive XML error checking.
* Ability to load a style file when loading a configuration file
* Features that can be styled: 
  * Disabling outlines
* Localizable user interface.
  * English
  * Pig Latin

### Notes/Assumptions

Assumptions or Simplifications:

For the UI, we decided to keep all the simulations, including the graph views, in one screen as 
we felt that we could better organize the information pertaining to the simulations without 
having the user do that for themselves. This saves the user time; all they have to do is click 
the buttons that are provided. The control buttons at the top of the view control all 
simulations because if multiple simulations are running, it is convenient to be able to pause all 
of them at the same time in order to compare them. 

Another assumption we made is that style files must be loaded at the same time as the configuration file
for a simulation. We implemented this by asking the user if they would like to select a style file
after loading a configuration file. We made this assumption so that some elements cannot be restyled while
a simulation is running, such as cell dimensions or cell shape.

Another simplification we made is that when you choose to do a random distribution of initial states,
resetting the simulation randomizes the states again instead of returning you to the configuration you started with.
Hence, the user gets a true "random" experience each time they reset the simulation.

No outlines in the style xml means that the outlines are not drawn i.e. they will be filled with 
whatever the background color is. Outlines mean that there will be black outlines.

Errors checked: 
Errors are handled by displaying a message to the user and not loading in the selected configuration file. 
In most cases, the error message is specific to the error type. 
* Missing required values: every tag/attribute in the configuration file must be present except for the "distribution" attribute
in the "grid" tag and simulation-specific parameters 
* Invalid value: if a given value is not supported by the Model/View (i.e. grid type, cell state)
* Incorrect grid size: if cell states are specified, the listed states must match the dimensions of 
the grid exactly
* Empty or badly formatted XML files: will throw an error because required elements will be missing
* Non-XML file: not an error, but file chooser prevents non-XML files from being chosen

Interesting data files:
* GameOfLife06.xml is a hexagonal version of Conway's Game of Life. 

Known Bugs:

 * We took some liberties in our interpretation of the Wa-Tor
specification, so our implementation shows some interesting behavior.
 * Changing the font size or color removes the labels on the speed slider.

Extra credit:

* Auto-generation of XMLs with a shell script (see
  tools/genrandom.sh).

### Impressions

* Very flexible project with simple core, but plenty of cool things to
  build.
  
* The jump from Basic to Complete was tough but manageable, doing a good job of testing 
the strength of our Basic design. 
