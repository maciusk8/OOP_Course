package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class OptionParser
{
    public static MoveDirection[] parseMoveDirections(String[] args)
    {
        MoveDirection[] directions = new MoveDirection[args.length];
        int i = 0;
        for (var arg : args) {
            directions[i++] = MoveDirection.initializeFromArgs(arg);
        }
        return directions;
    }
}
