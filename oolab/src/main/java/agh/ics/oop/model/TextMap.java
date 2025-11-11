package agh.ics.oop.model;

import java.util.*;

public class TextMap implements WorldNumberPositionMap<String>
{
    private List<String> textList = new LinkedList<>(); //poniewaz czesto modyfikuje elementy
    @Override
    public boolean place(String object)
    {
        if (textList.contains(object) || object == null) //nie jest to sprecyzowane, wiec przyjmuje ze nie moze byc kilka stringow z takim samym napisem, wtedy move str rusza wszystkimi? pierwszym napotkanym? mysle ze lepiej to tak obsluzyc
            return false;
        textList.add(object);
        return true;
    }

    @Override
    public void move(String object, MoveDirection direction)
    {
        int position = textList.indexOf(object);
        if (position == -1)
            return;
        int newPosition = position;
        switch (direction)
        {
            case FORWARD, RIGHT -> newPosition++;
            case BACKWARD, LEFT -> newPosition--;
        }
        if (newPosition < 0 || newPosition >= textList.size())
            return;
        String neighbour = textList.get(newPosition);
        textList.set(newPosition, object);
        textList.set(position, neighbour);
    }

    @Override
    public boolean isOccupied(Number position)
    {
        if (position == null || !(position instanceof Integer))
            return false;
        return position.intValue() >= 0 && position.intValue() < textList.size();
    }

    @Override
    public String objectAt(Number position)
    {
        if (!isOccupied(position))
            return null;
        return textList.get(position.intValue());
    }

    @Override
    public boolean canMoveTo(Number position)
    {
        return isOccupied(position);
    }

    @Override
    public String toString() {
        return String.join(" ", textList);
    }
}
