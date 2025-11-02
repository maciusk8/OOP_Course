package agh.ics.oop.model;

import agh.ics.oop.MapDirection;
import agh.ics.oop.Vector2d;

import java.util.Objects;

import static agh.ics.oop.World.*;

public class Animal
{
    public static final MapDirection DEFAULT_ORIENTATION = MapDirection.NORTH;//Uwazam, ze na potrzeby testow te stałe powinny być publiczne.
    public static final Vector2d DEFAULT_POSITION = CENTER;
    private final Vector2d lowerLeftCorner;
    private final Vector2d upperRightCorner;

    private MapDirection orientation;
    private Vector2d position;
    public Animal(Vector2d position)
    {
        this.orientation = DEFAULT_ORIENTATION;
        this.position = position;
        this.lowerLeftCorner = LOWER_LEFT_CORNER;
        this.upperRightCorner = UPPER_RIGHT_CORNER;
    }
    public Animal() { this(DEFAULT_POSITION); }
    public boolean isAt(Vector2d position) {return Objects.equals(position, this.position);}
    public boolean isFacing(MapDirection orientation) {return this.orientation == orientation;}
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
        return position.follows(lowerLeftCorner) && position.precedes(upperRightCorner);
    }

    @Override
    public String toString()
    {
        return String.format("na pozycji %s, patrzy na %s", position, orientation);
    }

    //Konstruktor na potrzeby testu SimulationTest
    public Animal(Vector2d position, MapDirection orientation, Vector2d lowerLeftCorner, Vector2d upperRightCorner) {
        {
            this.position = position;
            this.orientation = orientation;
            this.lowerLeftCorner = lowerLeftCorner;
            this.upperRightCorner = upperRightCorner;
        }
    }
}
