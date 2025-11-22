package agh.ics.oop.model;

import agh.ics.oop.IncorrectPositionException;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap
{
    protected  Map<Vector2d, Animal> animals = new HashMap<>();
    protected final MapVisualizer mapVisualizer = new MapVisualizer(this);
    protected Boundary bonds;
    protected List<MapChangeListener> observers = new ArrayList<>();
    @Override
    public void attach(MapChangeListener observer) {observers.add(observer);}
    @Override
    public void detach(MapChangeListener observer) {observers.remove(observer);}
    protected void notify(String message)  //Myśle, ze ta metoda nie powinna byc czescią interfejsu WorldMap, nikt nie powinienpoza klasą mieć dostępu do tej metody
    {
        observers.forEach(observer -> observer.mapChanged(this, message));
    }
    @Override
    public void place(Animal animal) throws IncorrectPositionException
    {
        Vector2d position = animal.getPosition();
        boolean canPlace = canMoveTo(position);
        if (!canPlace)
            throw new IncorrectPositionException(position);
        animals.put(position, animal);
        notify("animal placed at" + position);
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
            notify("animal moved to" + newPosition);
        }
    }

    @Override
    public String toString()
    {
        var bounds = getCurrentBounds();
        return mapVisualizer.draw(bounds.lowerLeft(), bounds.upperRight());
    }

    @Override
    public List<WorldElement> getElements()
    {
        return new ArrayList<>(animals.values());
    }

    @Override
    public Boundary getCurrentBounds()
    {
        return bonds;
    }
}
