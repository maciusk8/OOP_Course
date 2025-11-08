package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.util.List;

public class World
{
    public static final Vector2d UPPER_RIGHT_CORNER = new Vector2d(4, 4); //Myśle, ze te stale powinny byc w klasie World a nie w klasie Animal, dotycza one wymiaru mapy na kotrej poruszaja sie obiekty Animal
    public static final Vector2d LOWER_LEFT_CORNER = new Vector2d(0, 0);
    public static final Vector2d CENTER = new Vector2d(2, 2);
    public static void main(String[] args)
    {
        var myPet = new Animal();
        IO.println(myPet);
        //f b r l f f r r f f f f f f f f
        List<MoveDirection> directions = OptionParser.parseMoveDirections(args);
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
        Simulation simulation = new Simulation(positions, directions);
        simulation.run();
    }

    private static void run(MoveDirection[] directions)
     {
         for (var direction : directions)
         {
             if (direction != null)
             {
                 String message = switch (direction)
                 {
                     case FORWARD -> "forward";
                     case BACKWARD -> "backward";
                     case LEFT -> "left";
                     case RIGHT -> "right";
                 };
                 IO.println(String.format("Animal is moving %s.", message));
             }
         }
     }

}
