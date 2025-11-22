package agh.ics.oop.model;

import agh.ics.oop.MapDirection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import static agh.ics.oop.World.*;
import static agh.ics.oop.model.Animal.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest
{
    @ParameterizedTest()
    @CsvSource({
            "RIGHT, NORTH, EAST",
            "LEFT, NORTH, WEST",
            "RIGHT, SOUTH, WEST",
            "LEFT, SOUTH, EAST",
            "RIGHT, WEST, NORTH",
            "LEFT, WEST, SOUTH",
            "RIGHT, EAST, SOUTH",
            "LEFT, EAST, NORTH",
    })
    void runMethodShouldChangeOrientationGivenLeftOrRight(MoveDirection direction, MapDirection lookingAt, MapDirection expected)
    {
        var animal = new Animal(DEFAULT_POSITION, lookingAt);
        var map = new RectangularMap(5,5);//Given
        animal.move(direction, map); //When
        assertTrue(animal.isFacing(expected)); //Then
    }

    @ParameterizedTest
    @MethodSource("moveDataFirst")
    void runMethodShouldChangePositionGivenForwardOrBackward(MoveDirection direction, MapDirection lookingAt, Vector2d expected, Vector2d start)
    {
        var animal = new Animal(start, lookingAt);
        var map = new RectangularMap(5,5);//Given
        animal.move(direction, map); //When
        assertTrue(animal.isAt(expected));
    }

    private static Stream<Arguments> moveDataFirst()
    {
        var start = new Vector2d(1, 1);
        return Stream.of(
                Arguments.of(MoveDirection.FORWARD, MapDirection.NORTH, start.add(MapDirection.NORTH.toUnitVector()), start),
                Arguments.of(MoveDirection.BACKWARD, MapDirection.NORTH, start.add(MapDirection.SOUTH.toUnitVector()), start)
        );
    }

    @ParameterizedTest
    @CsvSource({
            "RIGHT",
            "LEFT"
    })
    void runMethodShouldNotChangePositionGivenLeftOrRight(MoveDirection direction)
    {
        var animal = new Animal();;
        var map = new RectangularMap(5,5);

        animal.move(direction, map);

        assertTrue(animal.isAt(DEFAULT_POSITION));

    }

    @ParameterizedTest
    @MethodSource("moveDataSecond")
    void runMethodShouldNotChangePositionBeingOnBorderLookingOutside(MapDirection lookingAt, Vector2d beingAt)
    {
        var animal = new Animal(beingAt, lookingAt);
        var map = new RectangularMap(5,5);
        animal.move(MoveDirection.FORWARD, map);

        assertTrue(animal.isAt(beingAt));
    }

    private static Stream<Arguments> moveDataSecond()
    {
        return Stream.of(
                Arguments.of(MapDirection.WEST, new Vector2d(0, 0)),
                Arguments.of(MapDirection.NORTH, new Vector2d(4, 4)),
                Arguments.of(MapDirection.SOUTH, new Vector2d(0, 0)),
                Arguments.of(MapDirection.EAST, new Vector2d(4, 4))
                );
    }

    @Test
    void runMethodShouldNotChangePositionOutsideBorder()
    {
        var map = new RectangularMap(5,5);//Given
        var vectorOutsideBorder = new Vector2d(5, 5);
        var animal = new Animal(vectorOutsideBorder);
        animal.move(MoveDirection.FORWARD, map);
        assertTrue(animal.isAt(vectorOutsideBorder));
    }
    @ParameterizedTest
    @CsvSource({
            "NORTH, ^",
            "SOUTH, v",
            "EAST, >",
            "WEST, <"
    })
    void toStringFormatChek(MapDirection orientation, String expected)
    {
        var animal = new Animal(DEFAULT_POSITION, orientation);
        assertEquals(expected , animal.toString());
    }

    @Test
    void defaultOrientationCheck()
    {
        var animal = new Animal();
        assertTrue(animal.isFacing(DEFAULT_ORIENTATION));
    }
    @Test
    void defaultPositionCheck()
    {
        var animal = new Animal();
        assertTrue(animal.isAt(DEFAULT_POSITION));
    }

    @Test
    void secondConstructorCheck()
    {
        var startingPosition = new Vector2d(1, 1);
        var animal = new Animal(startingPosition);
        assertTrue(animal.isAt(startingPosition));
        assertTrue(animal.isFacing(DEFAULT_ORIENTATION));
    }
}