package agh.ics.oop.model;

import agh.ics.oop.MapDirection;

import java.util.Objects;

public class Animal
{
    public static final MapDirection DEFAULT_ORIENTATION = MapDirection.NORTH;
    public static final Vector2d DEFAULT_POSITION = new Vector2d(0, 0); //uniwersalna, bo w teorii istnieje dla najmniejszej mozliwej mapy
    private MapDirection orientation;
    private Vector2d position;
    public Animal(Vector2d position)
    {
        this.orientation = DEFAULT_ORIENTATION;
        this.position = position;
    }
    public Animal() { this(DEFAULT_POSITION); }
    public boolean isAt(Vector2d position) {return Objects.equals(position, this.position);}
    public boolean isFacing(MapDirection orientation) {return this.orientation == orientation;}
    public void move(MoveDirection direction, MoveValidator validator)
    {
        if(validator == null || direction == null)
            return;

        Vector2d newPosition = null;
        switch (direction)
        {
            case RIGHT -> orientation = orientation.next();
            case LEFT -> orientation = orientation.previous();
            case FORWARD -> newPosition = position.add(orientation.toUnitVector());
            case BACKWARD -> newPosition = position.subtract(orientation.toUnitVector());
        }
        if (newPosition != null && validator.canMoveTo(newPosition)) {position = newPosition;}
    }
    @Override
    public String toString()
    {
        return switch (orientation)
        {
            case NORTH -> "^";
            case SOUTH -> "v";
            case WEST -> "<";
            case EAST -> ">";
        };
    }

    public Vector2d getPosition() {return position;} //mogę zwrócić referencje, poniewaz nie da sie zmodyfikować tego obiektu
    public MapDirection getOrientation() {return orientation;}

    //Konstruktor na potrzeby testów nie powinno się go uzywac poza nim
    public Animal(Vector2d position, MapDirection orientation)
    {
        this.position = position;
        this.orientation = orientation;
    }
}
