package agh.ics.oop.simulation;

import agh.ics.oop.model.IncorrectPositionException;
import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable
{
    private final List<MoveDirection> directions;
    private final List<Animal> animals;
    private final int animalCnt;
    private final WorldMap map;

    public Simulation(List<Vector2d> startingPositions, List<MoveDirection> directions, WorldMap map)
    {
        this.map = map;
        this.directions = directions;
        this.animalCnt = startingPositions.size();
        this.animals = new ArrayList<>(
                startingPositions.stream()
                        .map(Animal::new)
                        .toList()
        );
        initializeMap();
    }

    // Zakładam, ze moga pojawic sie nieprawidlowe dane skoro tego nie sprecyzowano w poleceniu
    private void initializeMap()
    {
        for (Animal animal : animals)
        {
            try {
                map.place(animal);
            } catch (IncorrectPositionException e) {
                IO.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void run()
    {
        if (animalCnt == 0)
            return;

        try {
            for (int i = 0; i < directions.size(); i++) {
                Thread.sleep(500);
                map.move(animals.get(i % animalCnt), directions.get(i));
            }
        } catch (InterruptedException e) {
         throw new RuntimeException(e);
        }
    }

    //Getter na potrzeby testów nie uzywać poza nim
    public List<Animal> getAnimals() {return List.copyOf(animals);}
}
