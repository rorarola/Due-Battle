package Due_Battle.entity;

import Due_Battle.BattlePane;
import Due_Battle.Dimensions;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

public class Player extends Entity {
    private int id;
    private Boolean[] go = {false, false, false, false}; // up down left right
    private Shield shield;

    public Player(int id, double originX, double originY, String color) {
        this.id = id;
        setX(originX);
        setY(originY);
        setWidth(Dimensions.PLAYER_WIDTH);
        setHeight(Dimensions.PLAYER_HEIGHT);
        setFill(Paint.valueOf(color));
    }

    public Shield getShield() {
        return shield;
    }

    public void addShield() {
        shield = new Shield(getCenterX(), getCenterY());
        ((BattlePane)getParent()).getChildren().add(shield);
        Timeline updateLoop = new Timeline(new KeyFrame(Duration.millis(20), e -> {
            shield.updateRadius();
        }));
        updateLoop.setCycleCount(Timeline.INDEFINITE);
        updateLoop.play();

    }

    public void setGo(int direction, boolean b) {
        go[direction] = b;
    }

    public void fireBullet(double toX, double toY) {
        ((BattlePane)getParent()).getController().queueAddition(new Bullet(id, getCenterX(), getCenterY(), toX, toY));
    }

    private void doMovement() {
        if(go[0]) { setY(getY() - 5);}
        if(go[1]) { setY(getY() + 5); }
        if(go[2]) { setX(getX() - 5); }
        if(go[3]) { setX(getX() + 5); }
    }

    private void updateShield() {
        shield.updatePosition(getCenterX(), getCenterY());
    }

    @Override
    public void doTick() {
        doMovement();
        updateShield();
    }
}
