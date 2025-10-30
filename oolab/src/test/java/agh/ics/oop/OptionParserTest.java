package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static agh.ics.oop.OptionParser.parseMoveDirections;
import static org.junit.jupiter.api.Assertions.*;

class OptionParserTest
{
    @ParameterizedTest(name = "String[] to Enum")
    @CsvSource({
            "f, FORWARD",
            "b, BACKWARD",
            "r, RIGHT",
            "l, LEFT"
    })
    void givenCharShouldReturnEnum(String move, MoveDirection expected)
    {
        List<MoveDirection> expectedList = List.of(expected);
        String[] args = {move};
        assertEquals(expectedList, parseMoveDirections(args));
    }

    @Test
    void chekMultipleDirections()
    {
        String[] args = {"f","b","l","r"};
        List<MoveDirection> expected = List.of(MoveDirection.FORWARD,MoveDirection.BACKWARD,MoveDirection.LEFT,MoveDirection.RIGHT);
        //when & then
        assertEquals(expected, parseMoveDirections(args));

    }
    @Test
    void chekEmptyArgs()
    {
        //given
        String[] args = {};
        List<MoveDirection> expected = List.of();
        //when & then
        assertEquals(expected, parseMoveDirections(args));
    }
    @Test
    void chekUnexpectedChar()
    {
        //given
        String[] args = {"q"};
        List<MoveDirection> expected = List.of();
        //when & then
        assertEquals(expected, parseMoveDirections(args));
    }
    @Test
    void chekUnexpectedCharWithMultipleArgs()
    {
        //given
        String[] args = {"f","q","b"};
        List<MoveDirection> expected = List.of(MoveDirection.FORWARD,MoveDirection.BACKWARD);
        //when & then
        assertEquals(expected, parseMoveDirections(args));
    }

}