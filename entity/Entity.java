package Due_Battle.entity;


import javafx.scene.shape.Rectangle;

public class Entity extends Rectangle {
    public double getCenterX() {
        return getX() + (getWidth() / 2);
    }

    public double getCenterY() {
        return getY() + (getHeight() / 2);
    }

    public void doTick() {};
}
