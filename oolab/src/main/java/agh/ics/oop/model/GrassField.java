package agh.ics.oop.model;

import agh.ics.oop.Random2DPositionGenerator;

import java.util.*;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap {
    private final Map<Vector2d, Grass> grasses;

    public GrassField(int grassCnt) {
        if (grassCnt < 0) {
            throw new IllegalArgumentException("Grass count cannot be negative");
        }
        int size = (int) sqrt(grassCnt * 10);
        grasses = initializeGrasses(size + 1, grassCnt); //size + 1 bo w poleceniu jest losowanie włącznie do pozycji sqrt(10*grassCnt)
        upperRightCorner = new Vector2d(0, 0);
        lowerLeftCorner = upperRightCorner;
    }

    private Map<Vector2d, Grass> initializeGrasses(int size, int grassCnt) {
        Map<Vector2d, Grass> result = new HashMap<>();
        Random2DPositionGenerator randomPositionGenerator = new Random2DPositionGenerator(size, size, grassCnt);
        for (Vector2d grassPosition : randomPositionGenerator) {
            result.put(grassPosition, new Grass(grassPosition));
        }
        return result;
    }

    //W teori mógłbym z poniszych metod wydobyć pewne części wspólne z rectangularMap ale byłyby to pojedyncze linijki i wywoływanie super.metoda() byłoby nieczytelne
    //Jedyne sensowne zastosowanie tego zrobiłem w toStringu
    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position) || grasses.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        if (isOccupied(position) && animals.containsKey(position)) {
            return animals.get(position);
        }
        return grasses.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
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
    public String toString() {
        updateBounds();
        return super.toString();
    }

    private void updateBounds() {
        List<WorldElement> elements = getElements();
        for (WorldElement element : elements) {
            lowerLeftCorner = lowerLeftCorner.lowerLeft(element.getPosition());
            upperRightCorner = upperRightCorner.upperRight(element.getPosition());
        }
    }

    //metoda na potrzeby testów nie uzywac poza nimi
    void insertGrass(Vector2d position)
    {
        grasses.put(position, new Grass(position));
    }
}
