# Simulation Lab Discussion

## Breakout with Inheritance
## Names and NetIDs
Bill Guo
Patrick Liu (pyl8)
Cole Spector (cgs26)
Franklin Wei (fw67)

### Sprite

This superclass's purpose is to represent any object that will appear on the screen during the game:
```java
 public abstract class Sprite {
     Node myNode;
     Point2D myVelocity;
     Game myParent;

     // how the sprite should update given an elapsed time
     public abstract void tick(double dt);

     public abstract void hit(Sprite hitter);
 }
```

#### Subclasses (the Open part)

Brick, Paddle, and Ball would all inherit from Sprite.
In the below example, the Paddle class overrides the tick method in order to respond to keyboard input:
```java
 public class Paddle extends Sprite {
    @Override
    public void tick(double dt) {
        // move in response to keyboard
    }

    public void addRocket(int d) {
        rocketCount += d;
    }
 }
```

#### Affect on Game/Level class (the Closed part)
Having a single Sprite class relieves the Game class of having to keep track of a multitude of different game Actors -- instead, the Game class can keep just one list of Sprites and call tick() each frame.

### Power-up

This superclass's purpose as an abstraction:
```java
 public abstract class PowerUp {
     public abstract void apply();
 }
```

#### Subclasses (the Open part)

This subclass's high-level behavorial differences from the superclass:
```java
 public class SlowBallPowerUp extends PowerUp {
    @Override
    public void apply()
    {
         // apply the powerup to the parent game context
         // find the game ball
         myParent.getBall().makeSlow();
         }
     }
 }
```

#### Affect on Game/Level class (the Closed part)
All powerups would be treated the same in the Game class. Their specific functions would be implemented directly by the Powerup object when it hits the paddle.
