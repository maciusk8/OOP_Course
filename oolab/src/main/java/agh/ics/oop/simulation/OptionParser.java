package agh.ics.oop.simulation;

import agh.ics.oop.model.MoveDirection;

import java.util.*;

public class OptionParser
{
        public static List<MoveDirection> parseMoveDirections (String[]args) throws IllegalArgumentException
        {
            return new ArrayList<>(
                    Arrays.stream(args)
                            .map(MoveDirection::initializeFromArgs)
                            .toList());
        }
}
