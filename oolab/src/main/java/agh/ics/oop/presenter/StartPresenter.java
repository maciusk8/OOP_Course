package agh.ics.oop.presenter;

import agh.ics.oop.model.GrassField;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.simulation.OptionParser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class StartPresenter {

    // stałe konfiguracyjne
    private static final String SIMULATION_FXML = "simulation.fxml";
    private static final String WINDOW_TITLE_PREFIX = "Symulacja ";
    private static final String ARGS_SEPARATOR = " ";
    private static final int GRASS_COUNT = 10;
    private static final List<Vector2d> STARTING_POSITIONS = List.of(
            new Vector2d(2, 2),
            new Vector2d(3, 4)
    );

    @FXML
    private TextField movesInput;

    @FXML
    private void onSimulationStartClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(SIMULATION_FXML));
        Scene scene = new Scene(loader.load());

        SimulationPresenter presenter = loader.getController();

        WorldMap map = new GrassField(GRASS_COUNT);
        presenter.setWorldMap(map);

        String[] moves = movesInput.getText().split(ARGS_SEPARATOR);
        presenter.startSimulation(
                OptionParser.parseMoveDirections(moves),
                STARTING_POSITIONS
        );

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(WINDOW_TITLE_PREFIX + map.getId());
        stage.show();

        movesInput.requestFocus();
    }
}