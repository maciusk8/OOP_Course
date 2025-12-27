package agh.ics.oop.presenter;

import agh.ics.oop.model.*;
import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.simulation.SimulationEngine;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SimulationPresenter implements MapChangeListener
{
    private static final int CELL_WIDTH = 40;
    private static final int CELL_HEIGHT = 40;
    private static final double GRID_LINE_WIDTH = 1.0;
    private static final int FONT_SIZE = 14;
    private static final String FONT_NAME = "Arial";
    private static final int AXIS_OFFSET = 1;
    private WorldMap map;
    private Boundary currentBounds;

    @FXML
    private Canvas mapCanvas;

    @FXML
    private Label moveInfoLabel;

    public void setWorldMap(WorldMap map)
    {
        this.map = map;
        map.attach(this);
        map.attach((WorldMap, message) -> {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            IO.println(String.format("%s %s%n", timestamp, message));
        });
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message)
    {
        Platform.runLater(() -> {
            drawMap();
            moveInfoLabel.setText(message);
        });
    }

    public void startSimulation(List<MoveDirection> directions, List<Vector2d> startPositions)
    {
        drawMap();
        Simulation simulation = new Simulation(startPositions, directions, map);
        SimulationEngine engine = new SimulationEngine(List.of(simulation));
        engine.runAsync();
    }

    private void drawMap()
    {
        currentBounds = map.getCurrentBounds();
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();

        int minX = currentBounds.lowerLeft().x();
        int maxX = currentBounds.upperRight().x();
        int minY = currentBounds.lowerLeft().y();
        int maxY = currentBounds.upperRight().y();

        double width = (maxX - minX + 1 + AXIS_OFFSET) * CELL_WIDTH;
        double height = (maxY - minY + 1 + AXIS_OFFSET) * CELL_HEIGHT;

        mapCanvas.setWidth(width);
        mapCanvas.setHeight(height);

        clearGrid(gc);
        drawGridLines(gc, width, height);

        drawAxis(gc, minX, maxX, minY, maxY);
        drawObjects(gc, minX, maxY);
    }

    private void drawAxis(GraphicsContext gc, int minX, int maxX, int minY, int maxY) {
        configureFont(gc, FONT_SIZE, Color.BLACK);

        gc.fillText("y/x", CELL_WIDTH / 2.0, CELL_HEIGHT / 2.0);

        for (int x = minX; x <= maxX; x++) {
            double xPos = (x - minX + AXIS_OFFSET) * CELL_WIDTH + (CELL_WIDTH / 2.0);
            double yPos = CELL_HEIGHT / 2.0;
            gc.fillText(String.valueOf(x), xPos, yPos);
        }

        for (int y = minY; y <= maxY; y++) {
            double xPos = CELL_WIDTH / 2.0;
            double yPos = (maxY - y + AXIS_OFFSET) * CELL_HEIGHT + (CELL_HEIGHT / 2.0);
            gc.fillText(String.valueOf(y), xPos, yPos);
        }
    }

    private void clearGrid(GraphicsContext graphics)
    {
        graphics.setFill(Color.WHITE);
        graphics.fillRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());
    }

    private void drawGridLines(GraphicsContext graphics, double width, double height)
    {
        graphics.setStroke(Color.GRAY);
        graphics.setLineWidth(GRID_LINE_WIDTH);
        for (int x = 0; x <= width; x += CELL_WIDTH) {
            graphics.strokeLine(x, 0, x, height);
        }
        for (int y = 0; y <= height; y += CELL_HEIGHT) {
            graphics.strokeLine(0, y, width, y);
        }
    }

    private void drawObjects(GraphicsContext gc, int minX, int maxY)
    {
        configureFont(gc, FONT_SIZE, Color.RED);

        Set<Vector2d> occupiedPositions = map.getElements().stream()
                .map(WorldElement::getPosition)
                .collect(Collectors.toSet());

        for (Vector2d position : occupiedPositions) {
            Object object = map.objectAt(position).get(); //tutaj mam pewność, ze pozycje sa zajmowane przez animale

            double screenX = (position.x() - minX + AXIS_OFFSET) * CELL_WIDTH;
            double screenY = (maxY - position.y() + AXIS_OFFSET) * CELL_HEIGHT;

            double centerX = screenX + (CELL_WIDTH / 2.0);
            double centerY = screenY + (CELL_HEIGHT / 2.0);

            gc.fillText(object.toString(), centerX, centerY);
        }
    }
    private void configureFont(GraphicsContext graphics, int size, Color color)
    {
        graphics.setTextAlign(TextAlignment.CENTER);
        graphics.setTextBaseline(VPos.CENTER);
        graphics.setFont(new Font(FONT_NAME, size));
        graphics.setFill(color);
    }
}