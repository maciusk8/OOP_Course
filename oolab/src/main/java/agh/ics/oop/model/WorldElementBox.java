package agh.ics.oop.model;

import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.awt.*;

public class WorldElementBox {
    private final Image image;
    private final Label positionLabel;
    private final static int IMG_SIZE = 20;
    private final static int FONT_SIZE = 10;

    public WorldElementBox(WorldElement obj) {
        this.image = new Image(obj.getImageSrc(), IMG_SIZE, IMG_SIZE, false, true);
        this.positionLabel = new Label(obj.getPositionText());
        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(new javafx.scene.image.ImageView(image), positionLabel);
    }

    public void draw(GraphicsContext gc, double x, double y) {
        gc.drawImage(image, x - (double) IMG_SIZE /2, y - (double) IMG_SIZE /2);
        gc.fillText(positionLabel.getText(), x, y + (double) IMG_SIZE /1.5 );
    }
}
