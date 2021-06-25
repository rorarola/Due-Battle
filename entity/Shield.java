package Due_Battle.entity;

import Due_Battle.Dimensions;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import java.util.Random;

public class Shield extends Circle {
    private double radius = Dimensions.SHIELD_RADIUS;
    private double wait = Dimensions.SHIELD_RADIUS;
    public Shield(double originX, double originY) {
        setCenterX(originX);
        setCenterY(originY);
        setRadius(radius);
        setOpacity(0.2);
        setFill(Paint.valueOf("black"));
    }

    public void updatePosition(double X, double Y) {
        setCenterX(X);
        setCenterY(Y);
    }

    public void updateRadius() {
        Random rand = new Random();
        wait += 1.5 * rand.nextDouble() - 1;
        radius = Math.min(radius, wait);
        if(radius < 0) {
            radius = Dimensions.SHIELD_RADIUS;
            wait = Dimensions.SHIELD_RADIUS;
        }
        setRadius(radius);
    }
}
