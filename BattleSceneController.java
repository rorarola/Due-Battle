package Due_Battle;

import Due_Battle.entity.Bullet;
import Due_Battle.entity.Player;
import Due_Battle.entity.Shield;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Due_Battle.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class BattleSceneController implements Initializable {
    @FXML GamePane field;
    @FXML public Rectangle player1HPF, player1HP, player2HPF, player2HP;

    //Timelines to fire events at regular intervals
    private Timeline gameLoop;
    private Timeline fireLoop;

    //Entities
    private Player player1 = new Player(1, 20, 20, "DODGERBLUE");
    private Player player2 = new Player(2, 100, 20, "#ff1CCC");
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();

    //We can't add or remove objects while iterating, so make queues
    ArrayList<Entity> entitiesToAdd = new ArrayList<>();
    ArrayList<Entity> entitiesToRemove = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        field.setController(this);
        add(player1);
        add(player2);
        player1.addShield();
        player2.addShield();
        setupTimelines();
    }

    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {
        KeyCode k = keyEvent.getCode();
        if(k.equals(KeyCode.W)) player1.setGoDir(0, true);
        if(k.equals(KeyCode.S)) player1.setGoDir(1, true);
        if(k.equals(KeyCode.A)) player1.setGoDir(2, true);
        if(k.equals(KeyCode.D)) player1.setGoDir(3, true);
        if(k.equals(KeyCode.UP)) player2.setGoDir(0, true);
        if(k.equals(KeyCode.DOWN)) player2.setGoDir(1, true);
        if(k.equals(KeyCode.LEFT)) player2.setGoDir(2, true);
        if(k.equals(KeyCode.RIGHT)) player2.setGoDir(3, true);

    }

    @FXML
    public void handleKeyReleased(KeyEvent keyEvent) {
        KeyCode k = keyEvent.getCode();
        if(k.equals(KeyCode.W)) player1.setGoDir(0, false);
        if(k.equals(KeyCode.S)) player1.setGoDir(1, false);
        if(k.equals(KeyCode.A)) player1.setGoDir(2, false);
        if(k.equals(KeyCode.D)) player1.setGoDir(3, false);
        if(k.equals(KeyCode.UP)) player2.setGoDir(0, false);
        if(k.equals(KeyCode.DOWN)) player2.setGoDir(1, false);
        if(k.equals(KeyCode.LEFT)) player2.setGoDir(2, false);
        if(k.equals(KeyCode.RIGHT)) player2.setGoDir(3, false);
    }

    public void queueAddition(Entity entity) {
        entitiesToAdd.add(entity);
    }
    public void queueRemoval(Entity entity) { entitiesToRemove.add(entity); }


    private void setupTimelines() {
        fireLoop = new Timeline(new KeyFrame(Duration.millis(200), e -> {
            player1.fireBullet(player2.getCenterX(), player2.getCenterY());
            player2.fireBullet(player1.getCenterX(), player1.getCenterY());
        }));

        fireLoop.setCycleCount(Timeline.INDEFINITE);
        fireLoop.play();

        gameLoop = new Timeline(new KeyFrame(Duration.millis(Timing.MOVEMENT_UPDATE_TIME), e -> {
            //Tick for every entity
            for (Entity entity : entities) {
                entity.doTick();
                if (entity instanceof Bullet) {
                    if(((Bullet) entity).getPlayerID() == 1) {
                        if(checkIntersect(entity, player2.getShield())) { queueRemoval(entity); }
                        else if(checkIntersect(entity, player2)) {
                            player2HP.setWidth(Math.max(0, player2HP.getWidth() - player2HPF.getWidth() * 0.05));
                            queueRemoval(entity);
                        }
                    } else if(((Bullet) entity).getPlayerID() == 2) {
                        if(checkIntersect(entity, player1.getShield())) { queueRemoval(entity); }
                        else if(checkIntersect(entity, player1)) {
                            player1HP.setWidth(Math.max(0, player1HP.getWidth() - player1HPF.getWidth() * 0.05));
                            queueRemoval(entity);
                        }
                    }
                }

            }


            for (Entity entity : entitiesToRemove) {
                remove(entity);
            }
            //And clear the list to start fresh next cycle
            entitiesToRemove.clear();
            //Same thing for those queued for addition
            for (Entity entity : entitiesToAdd) {
                add(entity);
            }
            entitiesToAdd.clear();



        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    private boolean checkIntersect(Shape sh1, Shape sh2) {
        return Shape.intersect(sh1, sh2).getBoundsInLocal().getWidth() != -1;
    }


    private void add(Entity entity) {
        entities.add(entity);
        if (entity instanceof Bullet) bullets.add((Bullet) entity);
        field.getChildren().add(entity);
    }

    private void remove(Entity entity) {
        entities.remove(entity);
        //Remove from the other ArraryLists as well
        //No harm done if it isn't in one of these
        field.getChildren().remove(entity);
    }

}
