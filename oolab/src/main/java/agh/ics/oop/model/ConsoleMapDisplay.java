package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

public class ConsoleMapDisplay implements MapChangeListener {
    private int changes = 0;
    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        synchronized (IO.class) {
            changes++;
            IO.println(
                    worldMap.getId()
                            + "\n"
                            + "Changes: " + changes
                            + "\n"
                            + worldMap
                            + "\n"
                            + message
                            + "\n"
            );
        }
    }
}
// Nie zauwazylem błedu, o ktorym mowa w poleceniu z tego co czytam to IO.println jest atomowe i nie trzeba synchronizowac
// Chyba jakbym zostawil błedny kod z ostatniego laba w ktorym wypisuje kilka IO.println wystapil by problem i trzebabyloby to dac do bloku synchronized z synchronizowaniem IO
// Dodaje jednak synchronized zeby zworic uwage na krytyczny fragment