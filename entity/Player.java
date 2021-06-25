package Due_Battle.entity;

import Due_Battle.Dimensions;
import Due_Battle.GamePane;
import Due_Battle.Timing;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Player extends Entity {
    private Boolean[] goDir = {false, false, false, false}; // up down left right
    private int id;
    private Shield shield;

    public Player(int id, double originX, double originY, String color) {
        super(originX, originY, Dimensions.PLAYER_WIDTH, Dimensions.PLAYER_HEIGHT, color);
        this.id = id;
    }

    public void addShield() {
        shield = new Shield(getCenterX(), getCenterY());
        ((GamePane)getParent()).getChildren().add(shield);
        Timeline shieldLoop = new Timeline(new KeyFrame(Duration.millis(Timing.MOVEMENT_UPDATE_TIME), e -> {

            shield.updateRadius();

        }));

        shieldLoop.setCycleCount(Timeline.INDEFINITE);
        shieldLoop.play();
    }

    public Shield getShield() {
        return shield;
    }

    public void fireBullet(double toX, double toY) {

        ((GamePane)getParent()).getController().queueAddition(
                new Bullet(id, getCenterX(), getCenterY(), getCenterX() - toX, getCenterY() - toY)
        );
    }

    public void setGoDir(int direction, boolean b) {
        this.goDir[direction] = b;
    }

    public void doMovement() {
        if(goDir[0]) setY(getY() - 5);
        if(goDir[1]) setY(getY() + 5);
        if(goDir[2]) setX(getX() - 5);
        if(goDir[3]) setX(getX() + 5);
        shield.updatePosition(getCenterX(), getCenterY());
    }

    @Override
    public void doTick() {
        doMovement();

    }
}
