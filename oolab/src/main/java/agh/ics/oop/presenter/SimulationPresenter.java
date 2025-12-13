package agh.ics.oop.presenter;

import agh.ics.oop.model.*;
import agh.ics.oop.simulation.OptionParser;
import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.simulation.SimulationEngine;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimulationPresenter implements MapChangeListener {

    private static final int CELL_WIDTH = 38;
    private static final int CELL_HEIGHT = 38;

    private WorldMap map;
    private Boundary currentBounds;

    @FXML
    private GridPane mapGrid;
    @FXML
    private Label moveInfoLabel;

    public void setWorldMap(WorldMap map) {
        this.map = map;
        map.attach(this);
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            drawMap();
            moveInfoLabel.setText(message);
        });
    }

    public void startSimulation(List<MoveDirection> directions, List<Vector2d> startPositions)
    {
        drawMap();

        Simulation simulation = new Simulation(
                startPositions,
                directions,
                map
        );

        SimulationEngine engine = new SimulationEngine(List.of(simulation));
        engine.runAsync();
    }

    private void drawMap()
    {
        clearGrid();
        currentBounds = map.getCurrentBounds();
        createAxes();
        addWorldElements();
    }

    private void clearGrid()
    {
        mapGrid.getChildren().clear();
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
        mapGrid.setGridLinesVisible(false);
        mapGrid.setGridLinesVisible(true);
    }

    private void createAxes()
    {
        int minX = currentBounds.lowerLeft().x();
        int maxX = currentBounds.upperRight().x();
        int minY = currentBounds.lowerLeft().y();
        int maxY = currentBounds.upperRight().y();

        // header
        Label xyLabel = new Label("y/x");
        mapGrid.add(centeredLabel(xyLabel), 0, 0);
        mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));

        // kolumny
        for (int i = 0; i <= maxX - minX; i++) {
            Label label = new Label(String.valueOf(minX + i));
            mapGrid.add(centeredLabel(label), i + 1, 0);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }

        // wiersze
        for (int i = 0; i <= maxY - minY; i++) {
            Label label = new Label(String.valueOf(maxY - i));
            mapGrid.add(centeredLabel(label), 0, i + 1);
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }
    }

    private void addWorldElements() {
        //rozwiązanie konieczne do wyświetlania zwierzaka nad trawą
        Set<Vector2d> occupiedPositions = map.getElements().stream()
                .map(WorldElement::getPosition)
                .collect(Collectors.toSet());

        for (Vector2d position : occupiedPositions) {
            Object objectToRender = map.objectAt(position);
            if (objectToRender != null)
            {
                Label elementLabel = new Label(objectToRender.toString());
                int gridX = calculateGridPositionX(position);
                int gridY = calculateGridPositionY(position);
                mapGrid.add(centeredLabel(elementLabel), gridX, gridY);
            }
        }
    }

    private Label centeredLabel(Label label) {
        GridPane.setHalignment(label, HPos.CENTER);
        return label;
    }

    private int calculateGridPositionX(Vector2d worldPosition) {
        return worldPosition.x() - currentBounds.lowerLeft().x() + 1;
    }

    private int calculateGridPositionY(Vector2d worldPosition) {
        return currentBounds.upperRight().y() - worldPosition.y() + 1;
    }
}