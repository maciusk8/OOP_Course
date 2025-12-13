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

    @FXML
    private TextField movesInput;

    @FXML
    private void onSimulationStartClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("simulation.fxml"));
        Scene scene = new Scene(loader.load());

        SimulationPresenter presenter = loader.getController();

        WorldMap map = new GrassField(10);
        presenter.setWorldMap(map);
        presenter.startSimulation(
                OptionParser.parseMoveDirections(movesInput.getText().split(" ")),
                List.of(new Vector2d(2, 2), new Vector2d(3, 4))
        );

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Symulacja " +  map.getId());
        stage.show();

        movesInput.requestFocus();
    }
}