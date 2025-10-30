package agh.ics.oop.model;

import agh.ics.oop.MapDirection;
import agh.ics.oop.Vector2d;

import java.util.Objects;

import static agh.ics.oop.World.LOWER_LEFT_CORNER;
import static agh.ics.oop.World.UPPER_RIGHT_CORNER;

public class Animal
{
    private static final MapDirection DEFAULT_ORIENTATION = MapDirection.NORTH;
    private MapDirection orientation;
    private Vector2d position;
    public Animal(Vector2d position) {orientation = DEFAULT_ORIENTATION; this.position = position;}
    public Animal() { this(new Vector2d(2,2)); }
    public boolean isAt(Vector2d position) {return Objects.equals(position, this.position);}
    public void move(MoveDirection direction)
    {
        Vector2d newPosition = null;

        switch (direction)
        {
            case RIGHT -> orientation = orientation.next();
            case LEFT -> orientation = orientation.previous();
            case FORWARD -> newPosition = position.add(orientation.toUnitVector());
            case BACKWARD -> newPosition = position.subtract(orientation.toUnitVector());
        }

        if (newPosition != null && isWithinBounds(newPosition))
        {
            position = newPosition;
        }
    }
    private boolean isWithinBounds(Vector2d position)
    {
        return position.follows(LOWER_LEFT_CORNER) && position.precedes(UPPER_RIGHT_CORNER);
    }

    @Override
    public String toString()
    {
        return String.format("Zwierzak na pozycji %s, patrzy na %s", position, orientation);
    }
}
