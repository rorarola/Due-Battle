package Due_Battle.entity;

import Due_Battle.Dimensions;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Dot extends Circle {
    private String[] colors = {"red", "pink", "green", "yellow"};
    public Dot() {

        setCenterX(Dimensions.SCREEN_WIDTH * Math.random());
        setCenterY(Dimensions.SCREEN_HEIGHT * Math.random());
        setRadius(Dimensions.DOT_RADIUS);
        setOpacity(0.7);
        setFill(Paint.valueOf(colors[(int)(Math.random() * 3)]));
    }
}
