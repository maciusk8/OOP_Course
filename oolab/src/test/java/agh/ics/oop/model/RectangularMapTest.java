package agh.ics.oop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest
{
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
            "2, 2",
            "0, 0",
            "4, 4",
            "1, 2",
            "2, 1",
            "3, 3",
            "0, 4",
            "4, 0"
    })
    void placeShouldPlaceAnimalOnValidSpot(int x, int y) throws IncorrectPositionException {
        var position = new Vector2d(x, y);
        var animal = new Animal(position);
        map.place(animal);

        assertTrue(map.isOccupied(position));
        Optional<WorldElement> result = map.objectAt(position);
        assertTrue(result.isPresent(), "expected animal but its empty");
        assertEquals(animal, result.get());
    }


    @Test
    void placeShouldNotPlaceAndThrowExceptionAnimalOnOccupiedSpot() throws IncorrectPositionException {
        var animal1 = new Animal(new Vector2d(3, 3));
        var animal2 = new Animal(new Vector2d(3, 3));

        map.place(animal1);
        assertThrows(IncorrectPositionException.class, () -> {
            map.place(animal2);
        });
        assertTrue(map.isOccupied(new Vector2d(3, 3)));
        Optional<WorldElement> result = map.objectAt(new Vector2d(3,3));
        assertTrue(result.isPresent(), "expected animal but its empty");
        assertEquals(animal1, result.get());;
    }
    @Test
    void canMoveToShouldReturnFalseForOccupiedCell() throws IncorrectPositionException {
        var animal = new Animal(new Vector2d(4, 4));
        map.place(animal);
        assertFalse(map.canMoveTo(new Vector2d(4, 4)));
    }

    @Test
    void isOccupied_and_objectAt_ShouldWorkCorrectly() throws IncorrectPositionException {
        var pos = new Vector2d(3, 3);
        var animal = new Animal(pos);

        assertFalse(map.isOccupied(pos));
        assertTrue(map.objectAt(pos).isEmpty());

        map.place(animal);

        assertTrue(map.isOccupied(pos));
        Optional<WorldElement> result = map.objectAt(pos);
        assertTrue(result.isPresent(), "expected animal but its empty");
        assertEquals(animal, result.get());
    }

    @Test
    void moveShouldMoveAnimalToEmptySpot() throws IncorrectPositionException {
        var animal = new Animal(new Vector2d(2, 2)); // Domyślnie NORTH
        map.place(animal);
        map.move(animal, MoveDirection.FORWARD);

        Vector2d newPos = new Vector2d(2, 3);
        assertEquals(newPos, animal.getPosition());
        assertFalse(map.isOccupied(new Vector2d(2, 2)));
        assertTrue(map.objectAt(new Vector2d(2, 2)).isEmpty());
        assertTrue(map.isOccupied(newPos));

        Optional<WorldElement> result = map.objectAt(newPos);
        assertTrue(result.isPresent(), "expected animal but its empty");
        assertEquals(animal, result.get());
    }

    @Test
    void moveShouldNotMoveAnimalOffMap() throws IncorrectPositionException {
        var initialPos = new Vector2d(0, 0);
        var animal = new Animal(initialPos);
        map.place(animal);

        map.move(animal, MoveDirection.LEFT);

        map.move(animal, MoveDirection.FORWARD); // próba ruchu na (-1, 0)

        assertEquals(initialPos, animal.getPosition());
        assertTrue(map.isOccupied(initialPos));
        Optional<WorldElement> result = map.objectAt(initialPos);
        assertTrue(result.isPresent(), "expected animal but its empty");
        assertEquals(animal, result.get());
    }

    @Test
    void move_shouldNotMoveAnimalToOccupiedSpot() throws IncorrectPositionException {
        var animal1 = new Animal(new Vector2d(3, 3)); // zwrócone na NORTH
        var animal2 = new Animal(new Vector2d(3, 4));
        map.place(animal1);
        map.place(animal2);

        var initialPos = new Vector2d(3, 3);
        map.move(animal1, MoveDirection.FORWARD); // Próba ruchu na (3, 4)

        assertEquals(initialPos, animal1.getPosition());
        assertTrue(map.isOccupied(initialPos));
        Optional<WorldElement> result1 = map.objectAt(initialPos);
        assertTrue(result1.isPresent(), "expected animal but its empty");
        assertEquals(animal1, result1.get());

        assertTrue(map.isOccupied(new Vector2d(3, 4)));
        Optional<WorldElement> result2 = map.objectAt(new Vector2d(3,4));
        assertTrue(result2.isPresent(), "expected animal but its empty");
        assertEquals(animal2, result2.get());
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
        Optional<WorldElement> result = map.objectAt(initialPos);
        assertTrue(result.isPresent(), "expected animal but its empty");
        assertEquals(animal, result.get());

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
        var visualizerMap = new RectangularMap(3, 3);
        var animal = new Animal(new Vector2d(1, 1));
        visualizerMap.place(animal);

        String mapString = visualizerMap.toString();
        assertNotNull(mapString);
    }
    private static Stream<Arguments> provideAnimalsAndExpectedOrder() {
        return Stream.of(
                Arguments.of(
                        List.of(new Vector2d(2, 3), new Vector2d(1, 4), new Vector2d(2, 2), new Vector2d(4, 4)), // pozycje
                        List.of(1, 2, 0, 3)
                ),
                Arguments.of(
                        List.of(new Vector2d(0, 0), new Vector2d(1, 0)),
                        List.of(0, 1)
                )
        );
    }
    @ParameterizedTest()
    @MethodSource("provideAnimalsAndExpectedOrder")
    void animalsShouldBeOrderedByPosition(List<Vector2d> positions, List<Integer> expectedOrderIndices) throws IncorrectPositionException {
        // given
        List<Animal> animals = positions.stream()
                .map(Animal::new)
                .collect(Collectors.toList());

        for(Animal a : animals){
            map.place(a);
        }

        // when
        Collection<Animal> orderedAnimals = map.getOrderedAnimals();

        // then
        Animal[] expectedArray = new Animal[expectedOrderIndices.size()];
        for (int i = 0; i < expectedOrderIndices.size(); i++) {
            expectedArray[i] = animals.get(expectedOrderIndices.get(i));
        }

        assertArrayEquals(expectedArray, orderedAnimals.toArray());
    }
}