package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionParserTest
{
    @Test
    void chek_f_ToForward()
    {
        //given
        String[] args = {"f"};
        MoveDirection[] expected = {MoveDirection.FORWARD};
        //when & then
        assertArrayEquals(expected, OptionParser.parseMoveDirections(args));
    }
    @Test
    void chek_b_ToBackward()
    {
        //given
        String[] args = {"b"};
        MoveDirection[] expected = {MoveDirection.BACKWARD};
        //when & then
        assertArrayEquals(expected, OptionParser.parseMoveDirections(args));
    }
    @Test
    void chek_l_ToLeft()
    {
        //given
        String[] args = {"l"};
        MoveDirection[] expected = {MoveDirection.LEFT};
        //when & then
        assertArrayEquals(expected, OptionParser.parseMoveDirections(args));
    }
    @Test
    void chek_r_ToRight()
    {
        //given
        String[] args = {"r"};
        MoveDirection[] expected = {MoveDirection.RIGHT};
        //when & then
        assertArrayEquals(expected, OptionParser.parseMoveDirections(args));
    }
    @Test
    void chekMultipleDirections()
    {
        String[] args = {"f","b","l","r"};
        MoveDirection[] expected = {MoveDirection.FORWARD,MoveDirection.BACKWARD,MoveDirection.LEFT,MoveDirection.RIGHT};
        //when & then
        assertArrayEquals(expected, OptionParser.parseMoveDirections(args));

    }
    @Test
    void chekEmptyArgs()
    {
        //given
        String[] args = {};
        MoveDirection[] expected = {};
        //when & then
        assertArrayEquals(expected, OptionParser.parseMoveDirections(args));
    }
    @Test
    void chekUnexpectedChar()
    {
        //given
        String[] args = {"q"};
        MoveDirection[] expected = {};
        //when & then
        assertArrayEquals(expected, OptionParser.parseMoveDirections(args));
    }
    @Test
    void chekUnexpectedCharWithMultipleArgs()
    {
        //given
        String[] args = {"f","q","b"};
        MoveDirection[] expected = {MoveDirection.FORWARD,MoveDirection.BACKWARD};
        //when & then
        assertArrayEquals(expected, OptionParser.parseMoveDirections(args));
    }

}