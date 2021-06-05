package Due_Battle;

import javafx.animation.AnimationTimer;
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
    @FXML public Rectangle player1HPF, player1HP, player2HPF, player2HP;

    private Boolean[] go1 = {false, false, false, false}; // up down left right
    private Boolean[] go2 = {false, false, false, false};

    private Boolean isInsidePlayer(Rectangle player, double x, double y) {
        if(x >= player.getLayoutX() && x <= player.getLayoutX() + player.getWidth() && y >= player.getLayoutY() && y <= player.getLayoutY() + player.getHeight()) return true;
        else return false;
    }

    LinkedList<Bullet> bullets = new LinkedList<Bullet>();
    private void addBullet(Bullet newBullet) {
        bullets.push(newBullet);
        field.getChildren().add(newBullet.getObj());
    }
    private void update_bullet(Bullet bullet) {
        bullet.update();
    }

    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case W: go1[0] = true; break;
            case S: go1[1] = true; break;
            case A: go1[2] = true; break;
            case D: go1[3] = true; break;
            default: break;
        }

        switch (keyEvent.getCode()) {
            case UP: go2[0] = true; break;
            case DOWN: go2[1] = true; break;
            case LEFT: go2[2] = true; break;
            case RIGHT: go2[3] = true; break;
            default: break;
        }
    }
    @FXML
    public void handleKeyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case W: go1[0] = false; break;
            case S: go1[1] = false; break;
            case A: go1[2] = false; break;
            case D: go1[3] = false; break;
            default: break;
        }
        switch (keyEvent.getCode()) {
            case UP: go2[0] = false; break;
            case DOWN: go2[1] = false; break;
            case LEFT: go2[2] = false; break;
            case RIGHT: go2[3] = false; break;
            default: break;
        }
    }



    // TODO: delete out of view bullets
    public void shoot() {
        Bullet newBullet1 = new Bullet(bullet1, player1, player2, true);
        Bullet newBullet2 = new Bullet(bullet2, player1, player2, false);
        addBullet(newBullet1);
        addBullet(newBullet2);


        Timeline fps = new Timeline(new KeyFrame(Duration.millis(1000 / 30), (e) -> {
            update_bullet(newBullet1);
            if(isInsidePlayer(player2, newBullet1.getObj().getLayoutX(), newBullet1.getObj().getLayoutY())) {
                player2HP.setWidth(Math.max(0, player2HP.getWidth() - player2HPF.getWidth() * 0.05));
            }
        }));
        fps.setCycleCount(Timeline.INDEFINITE);
        fps.play();

        Timeline fps2 = new Timeline(new KeyFrame(Duration.millis(1000 / 30), (e) -> {
            update_bullet(newBullet2);
            if(isInsidePlayer(player1, newBullet2.getObj().getLayoutX(), newBullet2.getObj().getLayoutY())) {
                player1HP.setWidth(Math.max(0, player1HP.getWidth() - player1HPF.getWidth() * 0.05));
            }
        }));
        fps2.setCycleCount(Timeline.INDEFINITE);
        fps2.play();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // TODO: handle player out of view
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(go1[0]) player1.setLayoutY(player1.getLayoutY() - 5);
                if(go1[1]) player1.setLayoutY(player1.getLayoutY() + 5);
                if(go1[2]) player1.setLayoutX(player1.getLayoutX() - 5);
                if(go1[3]) player1.setLayoutX(player1.getLayoutX() + 5);

                if(go2[0]) player2.setLayoutY(player2.getLayoutY() - 5);
                if(go2[1]) player2.setLayoutY(player2.getLayoutY() + 5);
                if(go2[2]) player2.setLayoutX(player2.getLayoutX() - 5);
                if(go2[3]) player2.setLayoutX(player2.getLayoutX() + 5);

            }
        };
        timer.start();


        Timeline fps = new Timeline(new KeyFrame(Duration.millis(2000), (e) -> { shoot(); }));
        fps.setCycleCount(Timeline.INDEFINITE);
        fps.play();


    }
}

