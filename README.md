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

Bill: controller, view

Franklin: model, simulations, controller

### Resources Used

Assorted Java documentation pages (especially those on
reflection). XML, SAX parser documentation.

### Running the Program

Main class: cellsociety.Main

Data files needed: everything in data/

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

Interesting data files:

Known Bugs:

We took some liberties in our interpretation of the Wa-Tor
specification, so our implementation shows some interesting behavior.

Extra credit:

* Auto-generation of XMLs with a shell script (see
  tools/genrandom.sh).

### Impressions

* Very flexible project with simple core, but plenty of cool things to
  build.
