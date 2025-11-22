package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;

public class World
{
    public static void main(String[] args)
    {
        //f b r l f f r r f f f f f f f f
        try{
        List<MoveDirection> directions = OptionParser.parseMoveDirections(args);
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
        Simulation simulation = new Simulation(positions, directions, new GrassField(10));
        simulation.run();
        } catch (IllegalArgumentException e) {
            IO.println(e.getMessage());
            e.printStackTrace();
        }

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
