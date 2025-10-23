package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class World
{
    public static final String petName = "Big Boss";
    public static void main(String[] args)
    {
        IO.println(String.format("System started."));
        run(OptionParser.parseMoveDirections(args));
        IO.println(String.format("System finished."));
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
    }

    private static void run(MoveDirection[] directions)
     {
         for (var direction : directions)
         {
             if (direction != null)
             {
                 String message = switch (direction) {
                     case FORWARD -> "moving forward";
                     case BACKWARD -> "moving backward";
                     case LEFT -> "turning left";
                     case RIGHT -> "turning right";
                 };
                 IO.println(String.format("%s is %s.", petName, message));
             }
         }
     }

}
