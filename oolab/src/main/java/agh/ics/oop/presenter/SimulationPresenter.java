package agh.ics.oop.presenter;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.simulation.OptionParser;
import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.simulation.SimulationEngine;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;

public class SimulationPresenter implements MapChangeListener
{
    private WorldMap map;
    @FXML
    private Label infoLabel;
    @FXML
    private Label moveInfoLabel;
    @FXML
    private TextField moveListTextField;

    public void setWorldMap(WorldMap map)
    {
        this.map = map;
        map.attach(this);
    }

    private void drawMap() {if (map != null) {infoLabel.setText(map.toString());}}


    @Override
    public void mapChanged(WorldMap worldMap, String message)
    {
        Platform.runLater(() -> {
            drawMap();
            moveInfoLabel.setText(message);
        });
    }

    public void onSimulationStart() {
        Simulation simulation = new Simulation(
                List.of(new Vector2d(4, 4), new Vector2d(1, 1)),
                OptionParser.parseMoveDirections(moveListTextField.getText().split(" ")),
                map
        );
        SimulationEngine engine = new SimulationEngine(List.of(simulation));
        engine.runAsync();
    }
}

