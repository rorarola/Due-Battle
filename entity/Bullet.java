package Due_Battle.entity;

import Due_Battle.Dimensions;

public class Bullet extends Entity {
    private double deltaX = 0.0;
    private double deltaY = 0.0;
    private int playerID = 0;
    public Bullet(int playerID, double originX, double originY, double X, double Y) {
        super(originX, originY, Dimensions.BULLET_WIDTH, Dimensions.BULLET_HEIGHT, "black");
        this.playerID = playerID;

        double dis = Math.sqrt(X * X + Y * Y);
        this.deltaX = X / dis;
        this.deltaY = Y / dis;
        setRotate(Math.toDegrees(Math.atan(this.deltaY / this.deltaX)));
    }

    public void doMovement() {
        setX(getX() - deltaX * Dimensions.BULLET_SPEED_PER_FRAME);
        setY(getY() - deltaY* Dimensions.BULLET_SPEED_PER_FRAME);
    }

    public int getPlayerID() {
        return playerID;
    }

    @Override
    public void doTick() {
        doMovement();
    }
}
