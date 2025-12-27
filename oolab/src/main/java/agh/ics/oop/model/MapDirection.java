package agh.ics.oop.model;

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

    public String toAnimalString(){
        return switch (this)
        {
            case NORTH -> "^";
            case SOUTH -> "v";
            case WEST -> "<";
            case EAST -> ">";
        };
    }

    public String toAnimalImgSrc(){
        return switch (this)
        {
            case NORTH -> "up.png";
            case SOUTH -> "down.png";
            case WEST -> "left.png";
            case EAST -> "right.png";
        };
    }
}
