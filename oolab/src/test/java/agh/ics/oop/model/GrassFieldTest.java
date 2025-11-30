
package agh.ics.oop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class GrassFieldTest
{
    private GrassField map;
    private static final int GRASS_CNT = 10;

    @BeforeEach
    void setUp()
    {
        map = new GrassField(GRASS_CNT);
    }

    @Test
    void constructorShouldCreateMapWithCorrectGrassCount()
    {
        assertTrue(map.getElements().size() == GRASS_CNT);
    }

    @Test
    void constructorShouldThrowExceptionForNegativeGrassCount()
    {
        assertThrows(IllegalArgumentException.class, () -> new GrassField(-1));
    }

    @ParameterizedTest()
    @CsvSource({
            "0, 0",
            "100, 100",
            "-50, -50",
            "999, 999",
            "-999, -999"
    })
    void placeShouldPlaceAnimalForAnyValidPosition(int x, int y) throws IncorrectPositionException {
        var position = new Vector2d(x, y);
        var animal = new Animal(position);
        map.place(animal);

        assertTrue(map.isOccupied(position));
        assertEquals(animal, map.objectAt(position));
    }

    @Test
    void placeShouldNotPlaceAnimalOnOccupiedSpotAndThrowException() throws IncorrectPositionException {
        var spot = new Vector2d(3, 3);
        var animal1 = new Animal(spot);
        var animal2 = new Animal(spot);

        map.place(animal1);
        assertThrows(IncorrectPositionException.class, () -> {
            map.place(animal2);
        });

        assertTrue(map.isOccupied(spot));
        assertEquals(animal1, map.objectAt(spot));
    }

    @Test
    void canMoveToShouldReturnTrueForGrassCell()
    {
        var spot = new Vector2d(3, 4);
        map.insertGrass(spot);
        assertTrue(map.canMoveTo(spot));
    }

    @Test
    void canMoveToShouldReturnFalseForGrassCellOccupiedByAnimal() throws IncorrectPositionException {
        var spot = new Vector2d(3, 3);
        map.insertGrass(spot);
        map.place(new Animal(spot));
        assertFalse(map.canMoveTo(spot));
    }

    @Test
    void isOccupied_and_objectAt_ShouldWorkCorrectlyForAnimals() throws IncorrectPositionException {
        var spot = new Vector2d(3, 3);
        var animal = new Animal(spot);

        map.place(animal);

        assertTrue(map.isOccupied(spot));
        assertEquals(animal, map.objectAt(spot));
    }


    @Test
    void moveShouldMoveAnimalToEmptySpot() throws IncorrectPositionException {
        var animal = new Animal(new Vector2d(50, 50));
        map.place(animal);
        map.move(animal, MoveDirection.FORWARD);

        Vector2d newPos = new Vector2d(50, 51);
        assertEquals(newPos, animal.getPosition());
        assertFalse(map.isOccupied(new Vector2d(50, 50)));
        assertTrue(map.isOccupied(newPos));
        assertEquals(animal, map.objectAt(newPos));
    }

    @Test
    void move_shouldNotMoveAnimalToOccupiedSpot() throws IncorrectPositionException {
        var animal1 = new Animal(new Vector2d(3, 3)); // zwrócone na NORTH
        var animal2 = new Animal(new Vector2d(3, 4));
        map.place(animal1);
        map.place(animal2);

        var initialPos = new Vector2d(3, 3);
        map.move(animal1, MoveDirection.FORWARD); // proba ruchu na (3, 4)

        assertEquals(initialPos, animal1.getPosition());
        assertTrue(map.isOccupied(initialPos));
        assertEquals(animal1, map.objectAt(initialPos));
        assertTrue(map.isOccupied(new Vector2d(3, 4)));
        assertEquals(animal2, map.objectAt(new Vector2d(3, 4)));
    }

    @ParameterizedTest
    @CsvSource({
            "RIGHT, EAST",
            "LEFT, WEST"
    })
    void moveShouldOnlyTurnAnimalIfDirectionIsLeftOrRight(MoveDirection direction, MapDirection expected) throws IncorrectPositionException {
        var initialPos = new Vector2d(3, 3);
        var animal = new Animal(initialPos);
        map.place(animal);

        map.move(animal, direction);

        assertTrue(animal.isFacing(expected));

        assertEquals(initialPos, animal.getPosition()); //nie powinno sie ruszyc
        assertTrue(map.isOccupied(initialPos));
        assertEquals(animal, map.objectAt(initialPos));
    }

    @Test
    void moveShouldDoNothingForNullAnimalOrDirection() throws IncorrectPositionException {
        var initialPos = new Vector2d(2, 2);
        var animal = new Animal(initialPos);
        map.place(animal);

        map.move(null, MoveDirection.FORWARD);
        assertEquals(animal, map.objectAt(initialPos));

        map.move(animal, null);
        assertEquals(animal, map.objectAt(initialPos));
        assertEquals(initialPos, animal.getPosition());
    }

    @Test
    void moveShouldDoNothingIfAnimalIsNotOnMap() throws IncorrectPositionException {
        Animal animalOnMap = new Animal(new Vector2d(1, 1));
        Animal animalNotOnMap = new Animal(new Vector2d(2, 2));
        map.place(animalOnMap); // Tylko (1,1) jest zajęte

        map.move(animalNotOnMap, MoveDirection.FORWARD);

        assertEquals(new Vector2d(2, 2), animalNotOnMap.getPosition());
        assertFalse(map.isOccupied(new Vector2d(2, 2)));
        assertFalse(map.isOccupied(new Vector2d(2, 3)));
        assertTrue(map.isOccupied(new Vector2d(1, 1)));
    }

    @Test
    void toStringShouldReturnCorrectStringRepresentation() throws IncorrectPositionException {
        var animal = new Animal(new Vector2d(5, 5));
        map.place(animal);

        String mapString = map.toString();
        assertNotNull(mapString);
        assertTrue(mapString.contains("^")); // Symbol zwierzęcia
        assertTrue(mapString.contains("*")); // Symbol trawy
    }

    @Test
    void toStringShouldUpdateBoundsDynamically() throws IncorrectPositionException {
        var animal1 = new Animal(new Vector2d(0, 0));
        map.place(animal1);

        String mapString1 = map.toString();
        assertNotNull(mapString1);

        // Dodajemy zwierzę dalej
        var animal2 = new Animal(new Vector2d(100, 100));
        map.place(animal2);

        String mapString2 = map.toString();
        assertNotNull(mapString2);

        // Mapa powinna być większa
        assertTrue(mapString2.length() > mapString1.length());
    }
}