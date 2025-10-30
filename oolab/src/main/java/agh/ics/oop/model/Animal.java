package agh.ics.oop.model;

import agh.ics.oop.MapDirection;
import agh.ics.oop.Vector2d;

import java.util.Objects;

import static agh.ics.oop.World.petName;

public class Animal
{
    private static final MapDirection DEFAULT_ORIENTATION = MapDirection.NORTH;
    private MapDirection orientation;
    private Vector2d position;
    Animal(Vector2d position) {orientation = DEFAULT_ORIENTATION; this.position = position;}
    Animal() { this(new Vector2d(2,2)); }
    boolean isAt(Vector2d position) {return Objects.equals(position, this.position);}

    @Override
    public String toString()
    {
        return String.format("Animal at %s facing %s", position, orientation);
    }
}
