package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;

public class Simulation<T, P>
{
    private final List<MoveDirection> directions;
    private final List<T> objects;
    private final int objectsCnt;
    private final WorldMap<T, P> map;

    public Simulation(List<T> objects, List<MoveDirection> directions, WorldMap<T,P> map)
    {
        this.map = map;
        this.directions = directions;
        this.objects = objects;
        this.objectsCnt = objects.size();

        for (T obj : this.objects) {
            this.map.place(obj);
        }
    }

    public void run()
    {
        if (objectsCnt == 0)
            return;

        for (int i = 0; i < directions.size(); i++)
        {
            map.move(objects.get(i% objectsCnt), directions.get(i));
            IO.println(map);
        }
    }

    //Getter na potrzeby testów nie uzywać poza nim
    public List<T> getObjects() {return List.copyOf(objects);}
}
