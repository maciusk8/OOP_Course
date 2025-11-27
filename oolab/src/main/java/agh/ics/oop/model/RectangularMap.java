package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RectangularMap extends AbstractWorldMap
{
    public RectangularMap(int width, int height) // zakładam, ze dane sa poprawne czyli dodatnie niezerowe, najmniejsza mapa to pojedynczy punkty czyli plansza 1x1
    {
        lowerLeftCorner = new Vector2d(0, 0);
        upperRightCorner = new Vector2d(width - 1, height - 1); // bo numerujemy pola od 0 do n - 1, a nie od 1 do n. tj. plansza 1x1 to pole (0, 0)
    }
    private boolean isWithinBounds(Vector2d position)
    {
        return position.follows(lowerLeftCorner) && position.precedes(upperRightCorner);
    }
    @Override
    public boolean canMoveTo(Vector2d position) {return (isWithinBounds(position) && !isOccupied(position));}
}
