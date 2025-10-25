package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//import org.junit.jupiter.params.ParameterizedTest;
//
//import org.junit.jupiter.params.provider.CsvSource;

class MapDirectionTest
{
//    @ParameterizedTest(name = "{0}.next() zwraca {1}")  <------------ W poleceniu jest aby uzyć @Test wiec zostawiam zakomentowany, ale moim zdaniem taki test jest bardziej elegancki i given when then ma wtedy sens bo mowa jest o pojedynczym tescie
//    @CsvSource({
//            "NORTH, EAST",     //Given
//            "EAST,  SOUTH",
//            "SOUTH, WEST",
//            "WEST,  NORTH"
//    })
//    void testDirectionNext(MapDirection direction, MapDirection expected)
//    {
//        assertEquals(expected, direction.next());  //When & Then
//    }


    @Test
    void directionNext()
    {
        assertEquals(MapDirection.NORTH, MapDirection.WEST.next());
        assertEquals(MapDirection.EAST, MapDirection.NORTH.next());
        assertEquals(MapDirection.SOUTH, MapDirection.EAST.next());
        assertEquals(MapDirection.WEST, MapDirection.SOUTH.next());
    }

    @Test
    void directionPrevious()
    {
        assertEquals(MapDirection.NORTH, MapDirection.EAST.previous());
        assertEquals(MapDirection.EAST, MapDirection.SOUTH.previous());
        assertEquals(MapDirection.SOUTH, MapDirection.WEST.previous());
        assertEquals(MapDirection.WEST, MapDirection.NORTH.previous());

    }
}