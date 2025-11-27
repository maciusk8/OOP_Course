package agh.ics.oop;

import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.random.RandomGenerator;
public class Random2DPositionGenerator  implements Iterable<Vector2d>, Iterator<Vector2d>
{
    private final int mapWidth;
    private final int mapHeight;
    private int attemptsLeft;
    private List<Vector2d> fields;
    private final RandomGenerator randomGenerator = RandomGenerator.getDefault();

    public Random2DPositionGenerator(int mapWidth, int mapHeight, int attempts)
    {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.attemptsLeft = attempts;
        this.fields = initializeFields();
    }

    private List<Vector2d> initializeFields()
    {
        List<Vector2d> positions = new ArrayList<>();
        for (int y = 0; y < mapHeight; y++)
        {
            for (int x = 0; x < mapWidth; x++)
            {
                positions.add(new Vector2d(x, y));
            }
        }
        return positions;
    }

    private Vector2d getRandomPosition()
    {
        int lastIndex = fields.size() - 1;
        int randomIndex = randomGenerator.nextInt(0, lastIndex + 1);
        Vector2d result = fields.get(randomIndex);
        fields.set(randomIndex, fields.get(lastIndex));
        fields.remove(lastIndex);
        return result;
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return attemptsLeft > 0 && !fields.isEmpty();
    }

    @Override
    public Vector2d next()
    {
        if (!hasNext()) {throw new IllegalStateException("No more positions available");}
        attemptsLeft--;
        return getRandomPosition();
    }
}
