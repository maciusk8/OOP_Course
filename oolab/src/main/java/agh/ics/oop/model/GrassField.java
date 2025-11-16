package agh.ics.oop.model;

import agh.ics.oop.Random2DPositionGenerator;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap
{
    private final int grassCnt;
    private  Map<Vector2d, Grass> grasses = new HashMap<>();

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

        upperRightCorner = new Vector2d(0, 0);
        lowerLeftCorner = upperRightCorner;
    }
    @Override
    public boolean isOccupied(Vector2d position) {return animals.containsKey(position) || grasses.containsKey(position);}

    @Override
    public WorldElement objectAt(Vector2d position) {
        if (isOccupied(position) && animals.containsKey(position)) {
            return animals.get(position);
        }
        return grasses.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position)
    {
        if (objectAt(position) instanceof Animal) return false;
        else return true;
    }

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> elements = super.getElements();
        elements.addAll(new ArrayList<>(grasses.values()));
        return elements;
    }
    @Override
    public String toString()
    {
        updateBounds();
        return super.toString();
    }

    private void updateBounds()
    {
        List<WorldElement> elements = getElements();
        for (WorldElement element : elements)
        {
            lowerLeftCorner = lowerLeftCorner.lowerLeft(element.getPosition());
            upperRightCorner = upperRightCorner.upperRight(element.getPosition());
        }
    }
}
