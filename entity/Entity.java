package Due_Battle.entity;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Paint;

public class Entity extends Rectangle {
    public Entity(double x, double y, double width, double height, String color) {
        super.setX(x);
        super.setY(y);
        super.setWidth(width);
        super.setHeight(height);
        super.setFill(Paint.valueOf(color));
    }
    public double getCenterX() {
        return getX() + getWidth() / 2;
    }

    public double getCenterY() {
        return getY() + getHeight() / 2;
    }

    public void doTick() {}
}
