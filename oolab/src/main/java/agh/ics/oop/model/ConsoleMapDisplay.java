package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

public class ConsoleMapDisplay implements MapChangeListener {
    private int changes = 0;
    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        changes++;
        IO.println("Changes: " + changes
                +  "\n"
                + worldMap
                + "\n"
                + message
        );
    }
}
