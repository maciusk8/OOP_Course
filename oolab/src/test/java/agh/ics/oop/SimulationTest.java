package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SimulationTest {
    private static Stream<Arguments> simulationDataProvider() {
        return Stream.of(
                //Test z polecenia
                Arguments.of(
                        List.of(new Vector2d(2, 2), new Vector2d(3, 4)),
                        "f b r l f f r r f f f f f f f f".split(" "),
                        new Vector2d(0, 0), new Vector2d(4, 4),
                        List.of(new Vector2d(3, 0), new Vector2d(2, 4)),
                        List.of(MapDirection.SOUTH, MapDirection.NORTH)
                ),
                //Przypadek 2: Zderzenia ze ścianami na małej mapie
                Arguments.of(
                        List.of(new Vector2d(1, 1)),
                        "f f r f f l b b b".split(" "),
                        new Vector2d(0, 0), new Vector2d(2, 2),
                        List.of(new Vector2d(2, 0)),
                        List.of(MapDirection.NORTH)
                ),
                // rzypadek 3: Dwa zwierzaki blokujące się na ścianach
                Arguments.of(
                        List.of(new Vector2d(0, 0), new Vector2d(4, 4)),
                        "l f f r l f f".split(" "),
                        new Vector2d(0, 0), new Vector2d(4, 4),
                        List.of(new Vector2d(0, 0), new Vector2d(4, 4)),
                        List.of(MapDirection.SOUTH, MapDirection.EAST)
                ),
                //Przypadek 4: 3 pokrywające się zwierzaki
                Arguments.of(
                        List.of(new Vector2d(1, 1), new Vector2d(1, 1), new Vector2d(1, 1)),
                        "f f f r r r".split(" "),
                        new Vector2d(0, 0), new Vector2d(4, 4),
                        List.of(new Vector2d(1, 2), new Vector2d(1, 2), new Vector2d(1, 2)),
                        List.of(MapDirection.EAST, MapDirection.EAST, MapDirection.EAST)
                ),

                // Przypadek 5: Zwierzak poruszający się po granicy
                Arguments.of(
                        List.of(new Vector2d(0, 0)),
                        "f f f f r f f f f r f f f f r f f f l".split(" "),
                        new Vector2d(0, 0), new Vector2d(4, 4),
                        List.of(new Vector2d(1, 0)),
                        List.of(MapDirection.SOUTH)
                ),
                //Przypadek 6: test z polecenia z invalid stringami
                Arguments.of(
                        List.of(new Vector2d(2, 2), new Vector2d(3, 4)),
                        "invalid f b invalid r l f invalid f r r f f f f f f f f invalid".split(" "),
                        new Vector2d(0, 0), new Vector2d(4, 4),
                        List.of(new Vector2d(3, 0), new Vector2d(2, 4)),
                        List.of(MapDirection.SOUTH, MapDirection.NORTH)
                ),
                //Przypadek 7: test z pustym stringiem
                Arguments.of(
                        List.of(new Vector2d(2, 2), new Vector2d(3, 4)),
                        "".split(" "),
                        new Vector2d(0, 0), new Vector2d(4, 4),
                        List.of(new Vector2d(2, 2), new Vector2d(3, 4)),
                        List.of(MapDirection.NORTH, MapDirection.NORTH)
                )
        );
    }


    @ParameterizedTest
    @MethodSource("simulationDataProvider")
    void shouldSimulationProduceCorrectFinalPositions(
            List<Vector2d> startingPositions,
            String[] args,
            Vector2d lowerLeftCorner,
            Vector2d upperRightCorner,
            List<Vector2d> expectedPositions, // Ten parametr jest używany
            List<MapDirection> expectedOrientations) { // Ten jest ignorowany

        // Given
        List<MoveDirection> moves = OptionParser.parseMoveDirections(args);
        Simulation simulation = new Simulation(startingPositions, moves, lowerLeftCorner, upperRightCorner);

        // When
        simulation.run();
        List<Animal> animals = simulation.getAnimals();
        // Then
        for(int i = 0; i < animals.size(); i++)
        {
            assertTrue(animals.get(i).isAt(expectedPositions.get(i)));
        }
    }

    @ParameterizedTest
    @MethodSource("simulationDataProvider")
    void shouldSimulationProduceCorrectFinalOrientations(
            List<Vector2d> startingPositions,
            String[] args,
            Vector2d lowerLeftCorner,
            Vector2d upperRightCorner,
            List<Vector2d> expectedPositions, // Ten jest ignorowany
            List<MapDirection> expectedOrientations) { // Ten parametr jest używany

        // Given
        List<MoveDirection> moves = OptionParser.parseMoveDirections(args);
        Simulation simulation = new Simulation(startingPositions, moves, lowerLeftCorner, upperRightCorner);

        // When
        simulation.run();
        List<Animal> animals = simulation.getAnimals();

        // Then
        for(int i = 0; i < animals.size(); i++)
        {
            assertTrue(animals.get(i).isFacing(expectedOrientations.get(i)));
        }
    }
}