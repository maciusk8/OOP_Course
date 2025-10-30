package agh.ics.oop.model;

import agh.ics.oop.MapDirection;
import agh.ics.oop.Vector2d;

import java.util.Objects;
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
        switch (direction)
        {
            case RIGHT -> orientation = orientation.next();
            case LEFT -> orientation = orientation.previous();
            case FORWARD -> position = position.add(orientation.toUnitVector());
            case BACKWARD -> position = position.subtract(orientation.toUnitVector());
        }
    }

    @Override
    public String toString()
    {
        return String.format("Zwierzak na pozycji %s, patrzy na %s", position, orientation);
    }
}
