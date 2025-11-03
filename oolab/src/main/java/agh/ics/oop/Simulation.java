package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;

import java.util.List;

public class Simulation
{
    private final List<MoveDirection> directions;
    private final List<Animal> animals;
    private final int animalCnt;

    public Simulation(List<Vector2d> startingPositions, List<MoveDirection> directions)
    {
        this.directions = directions;
        this.animalCnt = startingPositions.size();
        this.animals = startingPositions.stream()
                .map(Animal::new)
                .toList();
    }

    public void run()
    {
        for (int i = 0; i < directions.size(); i++)
        {
            animals.get(i%animalCnt).move(directions.get(i));
            IO.println(String.format("Zwierzę %d: %s", i%animalCnt, animals.get(i%animalCnt)));
        }
    }

    //Konstruktor na potrzeby testów nie powinno się go uzywac poza nim
    public Simulation(List<Vector2d> startingPositions, List<MoveDirection> directions, Vector2d lowerLeftCorner, Vector2d upperRightCorner)
    {
        this.directions = directions;
        this.animalCnt = startingPositions.size();
        this.animals = startingPositions.stream()
                .map(pos -> new Animal(pos, Animal.DEFAULT_ORIENTATION, lowerLeftCorner, upperRightCorner))
                .toList();
    }
    //Getter na potrzeby testów nie uzywać poza nim
    public List<Animal> getAnimals() {return animals;}
}
