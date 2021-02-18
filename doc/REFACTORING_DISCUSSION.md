## Refactoring Lab Discussion
### Team
12
### Names

Franklin Wei (fw67)
Cole Spector (cgs26)
Patrick Liu (pyl8)

### Issues in Current Code

The vast majority of design issues detected by SonarQube are trivial single-line issues (unused imports, or use of "\n" instead of "%n" in debugging printf format strings). A handful of issues are then local implementation issues, such as nested if statements and return of boolean values in conditionals.

There were only two "Modularity" issues, and those are again trivial single-line fixes.

Our priorities are to resolve every issue given in the static analysis tool, as no issues require significant redesign to resolve.

#### CellularAutomatonController
 * Design issue
There was a mutable global field given "public" permissions: STEP_SIZES, an array of possible frame rates for the animation.

#### FireRule.java: didCatchFire()

 This method used an if statement for a true or false boolean return, so it was refactored to simply return the result of the boolean expression


### Refactoring Plan

 * What are the code's biggest issues?
There were no overarching issues identified by the static analysis tool: all of the issues were either single-line fixes or unused import statements.

 * Which issues are easy to fix and which are hard?
All of these issues are easy to fix because they are confined to individual lines.

 * What are good ways to implement the changes "in place"?

Use of UNIX command-line tools could easily automate a good portion of the changes.

A small number of the changes may have to be made manually, but since they are just single-line fixes, we'll probably more writing this document than actually making the changes.

### Refactoring Work

 * Issue chosen: Fix and Alternatives
All public static fields were changed to protected so there are no shared global states.

 * Issue chosen: Fix and Alternatives
The didCatchFire() method in the FireRule class was refactored to return the result of the boolean instead of creating an if tree.
