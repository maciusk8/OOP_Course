package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap
{
    protected  Map<Vector2d, Animal> animals = new HashMap<>();
    protected final MapVisualizer mapVisualizer = new MapVisualizer(this);
    protected Vector2d upperRightCorner;
    protected Vector2d lowerLeftCorner;
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
        }
    }

    @Override
    public String toString() {return mapVisualizer.draw(lowerLeftCorner, upperRightCorner);}

    @Override
    public List<WorldElement> getElements()
    {
        return new ArrayList<>(animals.values());
    }
}
