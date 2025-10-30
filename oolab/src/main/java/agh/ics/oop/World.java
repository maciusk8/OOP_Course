package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;

import java.util.Objects;

public class World
{
    public static void main(String[] args)
    {
        var myPet = new Animal();
        IO.println(myPet);
        run(OptionParser.parseMoveDirections(args));
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
