package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

public class ConsoleMapDisplay implements MapChangeListener {
    private int changes = 0;
    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        changes++;
        IO.println("----------");
        IO.println();
        IO.println(message);
        IO.println();
        IO.println(worldMap);
        IO.println("Changes: " + changes);
        IO.println();
    }
}
