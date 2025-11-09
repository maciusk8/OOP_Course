package agh.ics.oop.model;

import agh.ics.oop.MapDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {

    private RectangularMap map;
    private static final int WIDTH = 5;
    private static final int HEIGHT = 5;
    @BeforeEach
    void setUp() {map = new RectangularMap(WIDTH, HEIGHT);}
    @ParameterizedTest()
    @CsvSource({
            "5, 5, 4, 4, true",
            "5, 5, 5, 4, false",
            "5, 5, 4, 5, false",
            "5, 5, -1, 0, false",
            "1, 1, 0, 0, true",
            "1, 1, 1, 0, false",
            "1, 1, 0, 1, false",
            "5, 3, 4, 2, true",
            "5, 3, 5, 2, false",
            "5, 3, 4, 3, false"
    })
    void constructorShouldSetCorrectBoundaries(int width, int height, int testX, int testY, boolean expectedResult)
    {
        var map = new RectangularMap(width, height);
        assertEquals(expectedResult, map.canMoveTo(new Vector2d(testX, testY)));
    }

    @ParameterizedTest()
    @CsvSource({
            "2, 2, true",
            "0, 0, true",
            "4, 4, true",
            "-1, 4, false",
            "4, 5, false",
            "5, 4, false"
    })
    void placeShouldReturnCorrectValue(int x, int y, boolean expectedResult)
    {
        var position = new Vector2d(x, y);
        var animal = new Animal(position);
        assertEquals(expectedResult, map.place(animal));

        if (expectedResult)
        {
            assertTrue(map.isOccupied(position));
            assertEquals(animal, map.objectAt(position));
        }
        else
        {
            assertFalse(map.isOccupied(position));
        }
    }


    @Test
    void placeShouldNotPlaceAnimalOnOccupiedSpot()
    {
        var animal1 = new Animal(new Vector2d(3, 3));
        var animal2 = new Animal(new Vector2d(3, 3));

        map.place(animal1);

        assertFalse(map.place(animal2));
        assertTrue(map.isOccupied(new Vector2d(3, 3)));
        assertEquals(animal1, map.objectAt(new Vector2d(3, 3)));
    }

    @Test
    void placeShouldNotPlaceNull()
    {
        assertFalse(map.place(null));
    }

    @Test
    void canMoveToShouldReturnFalseForOccupiedCell()
    {
        var animal = new Animal(new Vector2d(4, 4));
        map.place(animal);
        assertFalse(map.canMoveTo(new Vector2d(4, 4)));
    }

    @Test
    void isOccupied_and_objectAt_ShouldWorkCorrectly()
    {
        var pos = new Vector2d(3, 3);
        var animal = new Animal(pos);

        assertFalse(map.isOccupied(pos));
        assertNull(map.objectAt(pos));

        map.place(animal);

        assertTrue(map.isOccupied(pos));
        assertEquals(animal, map.objectAt(pos));
    }

    @Test
    void moveShouldMoveAnimalToEmptySpot()
    {
        var animal = new Animal(new Vector2d(2, 2)); // Domyślnie NORTH
        map.place(animal);
        map.move(animal, MoveDirection.FORWARD);

        Vector2d newPos = new Vector2d(2, 3);
        assertEquals(newPos, animal.getPosition());
        assertFalse(map.isOccupied(new Vector2d(2, 2)));
        assertNull(map.objectAt(new Vector2d(2, 2)));
        assertTrue(map.isOccupied(newPos));
        assertEquals(animal, map.objectAt(newPos));
    }

    @Test
    void moveShouldNotMoveAnimalOffMap()
    {
        var initialPos = new Vector2d(0, 0);
        var animal = new Animal(initialPos);
        map.place(animal);

        map.move(animal, MoveDirection.LEFT);

        map.move(animal, MoveDirection.FORWARD); // próba ruchu na (-1, 0)

        assertEquals(initialPos, animal.getPosition());
        assertTrue(map.isOccupied(initialPos));
        assertEquals(animal, map.objectAt(initialPos));
    }

    @Test
    void move_shouldNotMoveAnimalToOccupiedSpot()
    {
        var animal1 = new Animal(new Vector2d(3, 3)); // zwrócone na NORTH
        var animal2 = new Animal(new Vector2d(3, 4));
        map.place(animal1);
        map.place(animal2);

        var initialPos = new Vector2d(3, 3);
        map.move(animal1, MoveDirection.FORWARD); // Próba ruchu na (3, 4)

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
    void moveShouldOnlyTurnAnimalIfDirectionIsLeftOrRight(MoveDirection direction, MapDirection expected)
    {
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
    void moveShouldDoNothingForNullAnimalOrDirection()
    {
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
    void moveShouldDoNothingIfAnimalIsNotOnMap()
    {
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
    void toStringShouldReturnCorrectStringRepresentation()
    {
        var visualizerMap = new RectangularMap(3, 3);
        var animal = new Animal(new Vector2d(1, 1));
        visualizerMap.place(animal);

        String mapString = visualizerMap.toString();
        assertNotNull(mapString);
    }
}