package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;

public class World
{
    public static void main(String[] args)
    {
        var mySimulation = new Simulation<String, Number>(List.of("bardzo", "lubie", "majonez", "winiary"), List.of(MoveDirection.LEFT, MoveDirection.LEFT), new TextMap());
        mySimulation.run();
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
