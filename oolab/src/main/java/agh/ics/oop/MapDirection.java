package agh.ics.oop;

import java.util.Map;

public enum MapDirection
{
    NORTH,
    EAST,
    SOUTH,
    WEST;
    private final static int DIRECTIONS_CNT = 4;
    @Override
    public String toString()
    {
        return switch (this)
        {
            case NORTH -> "Północ";
            case SOUTH -> "Południe";
            case WEST -> "Zachód";
            case EAST -> "Wschód";
        };
    }
    public MapDirection next()
    {
        return MapDirection.values()[(this.ordinal() + 1) % DIRECTIONS_CNT];
    }

    public MapDirection previous()
    {
        return MapDirection.values()[(this.ordinal() + DIRECTIONS_CNT - 1) % DIRECTIONS_CNT];
    }

    public Vector2d toUnitVector()
    {
        return switch (this)
        {
            case NORTH -> new Vector2d(0, 1);
            case SOUTH -> new Vector2d(0, -1);
            case WEST -> new Vector2d(-1, 0);
            case EAST -> new Vector2d(1, 0);
        };
    }
}
