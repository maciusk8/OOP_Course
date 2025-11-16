package agh.ics.oop.model;

import agh.ics.oop.Random2DPositionGenerator;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

import static java.lang.Math.sqrt;

public class GrassField implements WorldMap
{
    private final int grassCnt;
    private  Map<Vector2d, Grass> grasses = new HashMap<>();
    private Map<Vector2d, Animal> animals = new HashMap<>();
    private final MapVisualizer mapVisualizer = new MapVisualizer(this);
    private Vector2d upperRightCorner;
    private Vector2d lowerLeftCorner;


    public GrassField(int grassCnt)
    {
        if (grassCnt < 0) {throw new IllegalArgumentException("Grass count cannot be negative");}
        this.grassCnt = grassCnt;
        int size = (int) sqrt(grassCnt*10);

        Random2DPositionGenerator randomPositionGenerator = new Random2DPositionGenerator(size, size, grassCnt);
        for(Vector2d grassPosition : randomPositionGenerator)
        {
            grasses.put(grassPosition, new Grass(grassPosition));
        }
    }

    private void updateBounds(Vector2d position)
    {
        if (position.precedes(lowerLeftCorner)) {lowerLeftCorner = position;}
        if (position.follows(upperRightCorner)) {upperRightCorner = position;}
    }

    @Override
    public boolean place(Animal animal)
    {
        if (animal == null)
            return false;

        Vector2d position = animal.getPosition();
        boolean canPlace = canMoveTo(position);
        if (canPlace)
        {
            animals.put(position, animal);
            updateBounds(position);
        }
        return canPlace;
    }

    @Override
    public void move(Animal animal, MoveDirection direction)
    {
        if (animal == null || direction == null)
            return;

        Vector2d initialPosition = animal.getPosition();
        if (!Objects.equals(objectAt(initialPosition), animal)) {return;}

        animal.move(direction, this);
        Vector2d newPosition = animal.getPosition();
        if (!Objects.equals(initialPosition, newPosition))
        {
            animals.remove(initialPosition);
            animals.put(newPosition, animal);
            updateBounds(newPosition);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {return animals.containsKey(position);}

    @Override
    public WorldElement objectAt(Vector2d position)
    {
        if (isOccupied(position)) {return animals.get(position);}
        else {return grasses.get(position);}

    }

    @Override
    public boolean canMoveTo(Vector2d position) {return (!isOccupied(position));}
    @Override
    public String toString()
    {
        if (upperRightCorner == null) {return "empty GrassField";}
        return mapVisualizer.draw(lowerLeftCorner, upperRightCorner);
    }
}
