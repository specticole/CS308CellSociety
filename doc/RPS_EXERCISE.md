# Simulation Lab Discussion

## Rock Paper Scissors

## Names and NetIDs

Franklin Wei (fw67)
Bill Guo (wg78)
Patrick Liu (pyl8)
Cole Spector (cgs26)

### High Level Design Ideas

![](https://i.imgur.com/sgWpCHm.png)

We modeled the win-loss function as a 2D antisymmetric matrix with 3 elements -- win, lose, and tie. We noticed that since the matrix is antisymmetric, we only need to keep track of the elements above the diagonal (i.e. the top-right corner).

With this idea, we decided that we could use an abstract "GameMatrix" class to represent the win-loss function of a set of "throws" -- things like rock, paper, or scissors. Then each variant of the game (each with a different set of "throws") could define its own win-loss matrix by inheriting GameMatrix into a concrete subclass such as RPSMatrix for the conventional "rock-paper-scissors" variant.

### CRC Card Classes

This class's purpose or value is to store and return the outcome of any possible throw. In effect, this defines the set of "game rules" that make up a game variant.

```java
 public class GameMatrix {
     enum GameOutcome {
         WIN,
         LOSS,
         TIE
     };
     private ArrayList<ArrayList<GameOutcome>> gameMatrix;
     // "rock" -> 0
     // "paper" -> 1
     // "scissors" -> 2

     private void getMatrixFromFile(String fileName){
         reads (preferrably a csv) file and gets the matrix;
     }
     private Map<String, Integer> indexMap;

     private ArrayList<String> getPossibleThrows();

     public int getTotal (Collection<Integer> data)
     public Value getValue ()

     public void addThrow(String throw, Collection win){
         --adds a row (win loss pairings) to the gameMatrix and adds throw to possibleThrows;
     }
     public int getIndex(String throw){
         -- returns the index of the throw in the game matrix
         }
 }
```

This class's purpose or value is to represent a player and get a throw from that player each round:
```java
 public abstract class Player {

    init(){
        score = 0;
    }
     private int score;
     private String throw;

     public abstract String getThrow ();
        //prompt user for throw and get throw

     public void addScore (int score);
         //adds score to player (this.score += score)
 }
```

This class's purpose or value is to initialize and maintain the set of players in a game:
```java=
public class RPSGame {
    private List<Player> myPlayers;

    protected GameMatrix myRules;

    public RPSGame(GameMatrix rules);

    public void addPlayer(Player p);
    public void playRound();
}
```

### Use Cases

 * A new game is started with five players, their scores are reset to 0.
 ```java
 Player myPlayer = new Player(); // five times
 addPlayer(myPlayer);
 ```

 * A player chooses his RPS "weapon" with which he wants to play for this round.
 ```java
 String myThrow = myPlayer.getThrow();
 ```

 * Given three players' choices, one player wins the round, and their scores are updated.
 ```java
 game.playRound(); // send everyone's throws to GameMatrix, return a list of player outcomes
 ```

 * A new choice is added to an existing game and its relationship to all the other choices is updated.
 ```java
 gameMatrix.addThrow("laser", listOfThingsLaserBeats)
 ```

 * A new game is added to the system, with its own relationships for its all its "weapons".
 ```java
RPSGame myGame = new RPSGame(myRules); // rules can be read in from a stream
 ```
