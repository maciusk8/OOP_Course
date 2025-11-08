package agh.ics.oop;

import agh.ics.oop.model.Vector2d;

public enum MapDirection
{
    NORTH,
    EAST,
    SOUTH,
    WEST;
    private static final int DIRECTIONS_CNT = MapDirection.values().length;
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
