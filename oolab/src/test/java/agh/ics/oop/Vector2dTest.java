package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest
{
    @Test
    void chekToStringWorks()
    {
        //given
        Vector2d vector = new Vector2d(1, 2);
        //when & then
        assertEquals("(1, 2)", vector.toString());
    }
    @Test
    void checkEqualsWorks()
    {
        //given
        Vector2d vector1 = new Vector2d(1, 2);
        Vector2d vector2 = new Vector2d(1, 2);
        //when & then
        assertEquals(vector1, vector2); // z tego co rozumiem, to sprawdza równiez czy zachodzi relacja rownowaznosci wiec moze to tak wygladac
    }

    @Test
    void checkSameVectorEqualsWorks()
    {
        //given
        Vector2d vector1 = new Vector2d(1, 2);
        //when & then
        assertEquals(vector1, vector1);
    }
    @Test
    void checkNotEquals()
    {
        //given
        Vector2d vector1 = new Vector2d(1, 2);
        Vector2d vector2 = new Vector2d(1, 3);
        //when & then
        assertNotEquals(vector1, vector2);
    }
    @Test
    void checkOpposite()
    {
        //given
        Vector2d vector1 = new Vector2d(1, 1);
        Vector2d vector2 = new Vector2d(-1, -1);
        Vector2d vectorZero = new Vector2d(0, 0);
        //when & then
        assertEquals(vector1, vector2.opposite());
        assertEquals(vector2, vector1.opposite());
    }
    @Test
    void checkOppositeZeroVector()
    {
        //given
        Vector2d vectorZero = new Vector2d(0, 0);
        //when & then
        assertEquals(vectorZero, vectorZero.opposite());
    }

    @Test
    void checkUpperRight()
    {
        //given
        Vector2d vector1 = new Vector2d(1, 0);
        Vector2d vector2 = new Vector2d(0, 1);
        Vector2d vectorUR = new Vector2d(1, 1);
        //when & then
        assertEquals(vectorUR, vector1.upperRight(vector2));
        assertEquals(vectorUR, vector2.upperRight(vector1));
    }
    @Test
    void checkUpperRightSameVector()
    {
        //given
        Vector2d vector1 = new Vector2d(1, 0);
        //when & then
        assertEquals(vector1, vector1.upperRight(vector1));
    }
    @Test
    void checkLowerLeft()
    {
        //given
        Vector2d vector1 = new Vector2d(1, 0);
        Vector2d vector2 = new Vector2d(0, 1);
        Vector2d vectorLL = new Vector2d(0, 0);
        //when & then
        assertEquals(vectorLL, vector1.lowerLeft(vector2));
        assertEquals(vectorLL, vector2.lowerLeft(vector1));
    }
    @Test
    void checkLowerLeftSameVector()
    {
        //given
        Vector2d vector1 = new Vector2d(1, 0);
        //when & then
        assertEquals(vector1, vector1.lowerLeft(vector1));
    }

    @Test
    void checkAddition()
    {
        //given
        Vector2d vector1 = new Vector2d(1, 1);
        Vector2d vector2 = new Vector2d(2, 2);
        //when & then
        assertEquals(new Vector2d(3,3 ), vector1.add(vector2));

    }

    @Test
    void checkAdditionOppositeVector()
    {
        //given
        Vector2d vector1 = new Vector2d(1, 1);
        Vector2d vector2 = new Vector2d(-1, -1);
        //when & then
        assertEquals(new Vector2d(0,0 ), vector1.add(vector2));

    }

    @Test
    void checkAdditionSameVector()
    {
        //given
        Vector2d vector1 = new Vector2d(1, 1);
        //when & then
        assertEquals(new Vector2d(2,2), vector1.add(vector1));

    }
    @Test
    void checkAdditionVectorZero()
    {
        //given
        Vector2d vectorZero = new Vector2d(0, 0);
        //when & then
        assertEquals(vectorZero, vectorZero.add(vectorZero));

    }
    @Test
    void checkSubtraction()
    {
        //given
        Vector2d vector1 = new Vector2d(3, 3);
        Vector2d vector2 = new Vector2d(2, 1);
        //when & then
        assertEquals(new Vector2d(1, 2), vector1.subtract(vector2));
    }

    @Test
    void checkSubtractionOppositeVector()
    {
        //given
        Vector2d vector1 = new Vector2d(1, 1);
        Vector2d vector2 = new Vector2d(-1, -1);
        //when & then
        assertEquals(new Vector2d(2, 2), vector1.subtract(vector2));
    }

    @Test
    void checkSubtractionSameVector()
    {
        //given
        Vector2d vector1 = new Vector2d(1, 1);
        //when & then
        assertEquals(new Vector2d(0, 0), vector1.subtract(vector1));
    }
    @Test
    void checkSubtractionVectorZero()
    {
        //given
        Vector2d vectorZero = new Vector2d(0, 0);
        //when & then
        assertEquals(vectorZero, vectorZero.subtract(vectorZero));

    }
    @Test
    void checkFollows()
    {
        //given
        Vector2d vector1 = new Vector2d(1, 1);
        Vector2d vector2 = new Vector2d(2, 2);
        //when & then
        assertTrue(vector2.follows(vector1));
    }
    @Test
    void checkFollowsSameVector()
    {
        //given
        Vector2d vector1 = new Vector2d(1, 1);
        //when & then
        assertTrue(vector1.follows(vector1));
    }

    @Test
    void checkPrecedes()
    {
        //given
        Vector2d vector1 = new Vector2d(1, 1);
        Vector2d vector2 = new Vector2d(2, 2);
        //when & then
        assertTrue(vector1.precedes(vector2));
    }
    @Test
    void checkPrecedesSameVector()
    {
        //given
        Vector2d vector1 = new Vector2d(1, 1);
        //when & then
        assertTrue(vector1.precedes(vector1));
    }
}