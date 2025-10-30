package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OptionParser
{
    public static List<MoveDirection> parseMoveDirections(String[] args)
    {
        return Arrays.stream(args)
                .map(MoveDirection::initializeFromArgs)
                .filter(Objects::nonNull)
                .toList();
    }
}
