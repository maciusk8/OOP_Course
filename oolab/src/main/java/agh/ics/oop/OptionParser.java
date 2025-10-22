package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import java.util.Arrays;
public class OptionParser
{
    public static MoveDirection[] parseMoveDirections(String[] args)
    {
        MoveDirection[] directions = new MoveDirection[args.length];
        int i = 0;
        for (var arg : args)
        {
            if (MoveDirection.initializeFromArgs(arg) != null)
            {
                directions[i++] = MoveDirection.initializeFromArgs(arg);
            }
        }
        return Arrays.copyOf(directions, i+1);
    }
}
