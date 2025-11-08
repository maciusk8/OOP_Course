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
        var animal = new Animal(DEFAULT_POSITION, lookingAt, LOWER_LEFT_CORNER, UPPER_RIGHT_CORNER); //Given
        animal.move(direction); //When
        assertTrue(animal.isFacing(expected)); //Then
    }

    @ParameterizedTest
    @MethodSource("moveDataFirst")
    void runMethodShouldChangePositionGivenForwardOrBackward(MoveDirection direction, MapDirection lookingAt, Vector2d expected)
    {
        var animal = new Animal();

        animal.move(direction);

        assertTrue(animal.isAt(expected));
    }

    private static Stream<Arguments> moveDataFirst()
    {
        return Stream.of(
                Arguments.of(MoveDirection.FORWARD, MapDirection.NORTH, DEFAULT_POSITION.add(MapDirection.NORTH.toUnitVector())),
                Arguments.of(MoveDirection.BACKWARD, MapDirection.NORTH, DEFAULT_POSITION.add(MapDirection.SOUTH.toUnitVector()))
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

        animal.move(direction);

        assertTrue(animal.isAt(DEFAULT_POSITION));

    }

    @ParameterizedTest
    @MethodSource("moveDataSecond")
    void runMethodShouldNotChangePositionBeingOnBorderLookingOutside(MapDirection lookingAt, Vector2d beingAt)
    {
        var animal = new Animal(beingAt, lookingAt, LOWER_LEFT_CORNER, UPPER_RIGHT_CORNER);

        animal.move(MoveDirection.FORWARD);

        assertTrue(animal.isAt(beingAt));
    }

    private static Stream<Arguments> moveDataSecond()
    {
        return Stream.of(
                Arguments.of(MapDirection.WEST, LOWER_LEFT_CORNER),
                Arguments.of(MapDirection.SOUTH, LOWER_LEFT_CORNER),
                Arguments.of(MapDirection.NORTH, UPPER_RIGHT_CORNER),
                Arguments.of(MapDirection.EAST, UPPER_RIGHT_CORNER)
                );
    }

    @Test
    void runMethodShouldNotChangePositionOutsideBorder()
    {
        var vectorOutsideBorder = UPPER_RIGHT_CORNER.add(new Vector2d(1,1));
        var animal = new Animal(vectorOutsideBorder, DEFAULT_ORIENTATION, LOWER_LEFT_CORNER,  UPPER_RIGHT_CORNER);
        animal.move(MoveDirection.FORWARD);
        assertTrue(animal.isAt(vectorOutsideBorder));
    }
    @Test
    void toStringFormatChek()
    {
        var animal = new Animal();
        assertEquals(String.format("na pozycji %s, patrzy na %s", DEFAULT_POSITION, DEFAULT_ORIENTATION) , animal.toString());
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