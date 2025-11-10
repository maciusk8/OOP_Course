package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RectangularMap implements WorldMap<Animal, Vector2d>
{
    private Map<Vector2d, Animal> animals = new HashMap<>();
    private static final Vector2d lowerLeftCorner = new Vector2d(0, 0); //instrukcja nie podaje jak mapa mapa ma być zagnieżdżona w układzie współrzędnych. Najbardziej intuicyjne wydaje mi się umieszczenie jej w I ćwiartce
    private final Vector2d upperRightCorner; //x - definiuje szerokosc, y - definiuje wysokosc
    private final MapVisualizer mapVisualizer = new MapVisualizer(this);

    public RectangularMap(int width, int height) // zakładam, ze dane sa poprawne czyli dodatnie niezerowe, najmniejsza mapa to pojedynczy punkty czyli plansza 1x1
    {
        upperRightCorner = new Vector2d(width - 1, height - 1); // bo numerujemy pola od 0 do n - 1, a nie od 1 do n. tj. plansza 1x1 to pole (0, 0)
    }
    private boolean isWithinBounds(Vector2d position)
    {
        return position.follows(lowerLeftCorner) && position.precedes(upperRightCorner);
    }

    @Override
    public boolean place(Animal animal)
    {
        if (animal == null)
            return false;

        Vector2d position = animal.getPosition();
        boolean canPlace = canMoveTo(position); //Aktualnie canMoveTo i canPlace są jednoznaczne, mysle ze takie oznaczenie jest odpowiednie poniewaz mowi nam ze sa to osobne koncepty, zachowujace sie tak samo na ten moment
        if (canPlace) {animals.put(position, animal);}
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
    public boolean isOccupied(Vector2d position) {return animals.containsKey(position);}

    @Override
    public Animal objectAt(Vector2d position) {return animals.get(position);}

    @Override
    public boolean canMoveTo(Vector2d position) {return (isWithinBounds(position) && !isOccupied(position));}
    @Override
    public String toString() {return mapVisualizer.draw(lowerLeftCorner, upperRightCorner);}
}
