package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.*;

public class Controller implements Initializable {
    @FXML public Pane field;

    @FXML public Rectangle player1, player2, bullet1, bullet2;


    LinkedList<Bullet> bullets = new LinkedList<Bullet>();
    private void addBullet(Bullet newBullet) {
        bullets.push(newBullet);
        field.getChildren().add(newBullet.getObj());
    }

    @FXML
    public void handlePressed(KeyEvent keyEvent) {
        System.out.println(keyEvent.getCode());
        switch (keyEvent.getCode()) {
            case W:
                player1.setLayoutY(player1.getLayoutY() - 5);
                break;
            case S:
                player1.setLayoutY(player1.getLayoutY() + 5);
                break;
            case A:
                player1.setLayoutX(player1.getLayoutX() - 5);
                break;
            case D:
                player1.setLayoutX(player1.getLayoutX() + 5);
                break;

            default:
                break;
        }

        switch (keyEvent.getCode()) {
            case UP:
                player2.setLayoutY(player2.getLayoutY() - 5);
                break;
            case DOWN:
                player2.setLayoutY(player2.getLayoutY() + 5);
                break;
            case LEFT:
                player2.setLayoutX(player2.getLayoutX() - 5);
                break;
            case RIGHT:
                player2.setLayoutX(player2.getLayoutX() + 5);
                break;
            default:
                break;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timeline fps = new Timeline(new KeyFrame(Duration.millis(1000 / 3), (e) -> {
            Bullet newBullet1 = new Bullet(bullet1, player1, player2, true);
            Bullet newBullet2 = new Bullet(bullet2, player1, player2, false);
            addBullet(newBullet1);
            addBullet(newBullet2);
            ArrayList<Bullet> tBullets = new ArrayList<Bullet>(bullets);
            for (var tb : tBullets) {
                tb.update();
                if(tb.outOfView(field)) {
                    bullets.remove(tb);
                    field.getChildren().remove(tb.getObj());
                }
            }
        }));
        fps.setCycleCount(Timeline.INDEFINITE);
        fps.play();
    }
}