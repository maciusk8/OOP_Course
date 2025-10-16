package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class World
{
    public static final String petName = "Big Boss";
    public static void main(String[] args)
    {
        var moveDriection = new MoveDirection[]{};
        IO.println(String.format("System started."));
        run(OptionParser.parseMoveDirections(args));
        IO.println(String.format("System finished."));
    }

     private static void run(MoveDirection[] directions)
     {
         for (var direction : directions)
         {
             switch (direction)
             {
                 case FORWARD -> IO.println(String.format("%s is moving forward.", petName));
                 case BACKWARD -> IO.println(String.format("%s is moving backward.", petName));
                 case LEFT -> IO.println(String.format("%s is turning left.", petName));
                 case RIGHT -> IO.println(String.format("%s is turning right.", petName));
             }
         }
     }

}
