package agh.ics.oop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

//import org.junit.jupiter.params.ParameterizedTest;
//
//import org.junit.jupiter.params.provider.CsvSource;

class MapDirectionTest
{
    @ParameterizedTest(name = "{0}.next() zwraca {1}")
    @CsvSource({
            "NORTH, EAST",
            "EAST,  SOUTH",
            "SOUTH, WEST",
            "WEST,  NORTH"
    })
    void testDirectionNext(MapDirection direction, MapDirection expected)
    {
        assertEquals(expected, direction.next());
    }

    @ParameterizedTest(name = "{0}.next() zwraca {1}")
    @CsvSource({
            "EAST, NORTH",
            "SOUTH,  EAST",
            "WEST, SOUTH",
            "NORTH,  WEST"
    })
    void testDirectionPrevious(MapDirection direction, MapDirection expected)
    {
        assertEquals(expected, direction.previous());
    }


//    @Test
//    void directionNext()
//    {
//        assertEquals(MapDirection.NORTH, MapDirection.WEST.next());
//        assertEquals(MapDirection.EAST, MapDirection.NORTH.next());
//        assertEquals(MapDirection.SOUTH, MapDirection.EAST.next());
//        assertEquals(MapDirection.WEST, MapDirection.SOUTH.next());
//    }
//
//    @Test
//    void directionPrevious()
//    {
//        assertEquals(MapDirection.NORTH, MapDirection.EAST.previous());
//        assertEquals(MapDirection.EAST, MapDirection.SOUTH.previous());
//        assertEquals(MapDirection.SOUTH, MapDirection.WEST.previous());
//        assertEquals(MapDirection.WEST, MapDirection.NORTH.previous());
//
//    }
}