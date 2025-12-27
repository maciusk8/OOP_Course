package agh.ics.oop.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileMapDisplay implements MapChangeListener{
    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        try (FileWriter fileWriter = new FileWriter(String.format("map_%s.log", worldMap.getId()), true)) {
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(
                            worldMap
                            + "\n"
                            + message
                            + "\n");
        } catch (IOException e){
            IO.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
