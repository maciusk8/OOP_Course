package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.simulation.OptionParser;
import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.simulation.SimulationEngine;
import com.sun.jdi.connect.Connector;

import java.util.ArrayList;
import java.util.List;

public class World
{
    public static void main(String[] args)
    {
        //f b r l f f r r f f f f f f f f
        try{
            List<MoveDirection> directions = OptionParser.parseMoveDirections(args);
            List<Vector2d> positions = List.of(new Vector2d(5,5), new Vector2d(3,4));
            List<Simulation> sims = new ArrayList<>();
            for (int i = 0; i < 5000; i++)
            {
                //symulacja dla grass field
                WorldMap map1 = new GrassField(10);
                map1.attach(new ConsoleMapDisplay());
                Simulation simulationGrassField = new Simulation(positions, directions, map1);
                //symulacja dla rectangular map;
                WorldMap map2 = new RectangularMap(10,10);
                map2.attach(new ConsoleMapDisplay());
                Simulation simulationRectangularMap = new Simulation(positions, directions, map2);

                sims.add(simulationGrassField);
                sims.add(simulationRectangularMap);
            }
            SimulationEngine simulationEngine = new SimulationEngine(sims);
            simulationEngine.runAsync();
            simulationEngine.awaitSimulationsEnd();
        } catch (IllegalArgumentException | InterruptedException e) {
            IO.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        IO.println("System finished.");
    }

    private static void run(MoveDirection[] directions)
     {
         for (var direction : directions)
         {
             if (direction != null)
             {
                 String message = switch (direction)
                 {
                     case FORWARD -> "forward";
                     case BACKWARD -> "backward";
                     case LEFT -> "left";
                     case RIGHT -> "right";
                 };
                 IO.println(String.format("Animal is moving %s.", message));
             }
         }
     }

}
