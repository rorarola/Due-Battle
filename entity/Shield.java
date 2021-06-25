package Due_Battle.entity;

import Due_Battle.Dimensions;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Shield extends Circle {

    private double radius = Dimensions.SHIELD_MAX_RADIUS;

    public Shield(double originX, double originY) {
        setCenterX(originX);
        setCenterY(originY);
        setRadius(radius);
        setFill(Paint.valueOf("black"));
        setOpacity(0.25);
    }

    public void updateRadius() {
        Random rand = new Random();
        radius -= Math.max(0, rand.nextDouble() * 2 - 0.8);
        if(radius < 0) radius = Dimensions.SHIELD_MAX_RADIUS;
        setRadius(radius);
    }

    public void updatePosition(double X, double Y) {
        setCenterX(X);
        setCenterY(Y);
    }
}
