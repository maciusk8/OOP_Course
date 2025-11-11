package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public class TextMap implements WorldMap<String, Integer>
{
    private List<String> map = new LinkedList<>(); //poniewaz czesto modyfikuje elementy
    @Override
    public boolean place(String object)
    {
        if (object == null)
            return false;
        map.add(object);
        return true;
    }

    @Override
    public void move(String object, MoveDirection direction)
    {
        int position = map.indexOf(object);
        if (position == -1)
            return;
        int newPosition = position;
        switch (direction)
        {
            case FORWARD, RIGHT -> newPosition++;
            case BACKWARD, LEFT -> newPosition--;
        }
        if (newPosition < 0 || newPosition >= map.size())
            return;
        String neighbour = map.get(newPosition);
        map.set(newPosition, object);
        map.set(position, neighbour);
    }

    @Override
    public boolean isOccupied(Integer position)
    {
        if (position == null)
            return false;
        return position >= 0 && position < map.size();
    }

    @Override
    public String objectAt(Integer position)
    {
        if (!isOccupied(position))
            return null;
        return map.get(position);
    }

    @Override
    public boolean canMoveTo(Integer position)
    {
        return isOccupied(position);
    }
}
