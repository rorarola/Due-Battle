package Due_Battle.entity;

import Due_Battle.Dimensions;
import Due_Battle.Timing;
import javafx.scene.paint.Paint;

public class Bullet extends Entity {
    private int playerID;
    private double deltaX, deltaY;
    public Bullet(int playerID, double originX, double originY, double toX, double toY) {
        setX(originX);
        setY(originY);

        setWidth(Dimensions.BULLET_WIDTH);
        setHeight(Dimensions.BULLET_HEIGHT);
        setFill(Paint.valueOf("black"));
        this.playerID = playerID;

        deltaX = originX - toX;
        deltaY = originY - toY;
        double dis = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        deltaX /= dis;
        deltaY /= dis;

        setRotate(Math.toDegrees(Math.atan(deltaY / deltaX)));
    }

    public int getPlayerID() {
        return playerID;
    }

    private void doMovement() {
        setX(getX() - deltaX * Timing.BULLET_SPEED);
        setY(getY() - deltaY * Timing.BULLET_SPEED);
    }

    @Override
    public void doTick() {
        doMovement();
    }
}
