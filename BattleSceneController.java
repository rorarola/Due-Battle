package Due_Battle;


import Due_Battle.entity.Bullet;
import Due_Battle.entity.Dot;
import Due_Battle.entity.Entity;
import Due_Battle.entity.Player;
import due_battle2.Menu;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BattleSceneController implements Initializable {
    @FXML public BattlePane field;
    @FXML public Rectangle player1HPF, player1HP, player2HPF, player2HP;
    @FXML public Label player1name, player2name;

    //Timelines to fire events at regular intervals
    private Timeline gameLoop;

    //Entities
    private Player player1 = new Player(1, 40, 300, "DODGERBLUE");
    private Player player2 = new Player(2, 800, 300, "#ff1CCC");
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Bullet> projectiles = new ArrayList<>();
    private ArrayList<Dot> dots = new ArrayList<>();

    //We can't add or remove objects while iterating, so make queues
    ArrayList<Entity> entitiesToAdd = new ArrayList<>();
    ArrayList<Shape> entitiesToRemove = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        field.setController(this);
        add(player1);
        add(player2);

        System.out.println(player1);
        player1.addShield();
        player2.addShield();

        setupTimelines();
        //Show two players' name
        player1name.setText("Player1: "+ due_battle2.Menu.Playername1);
        player2name.setText("Player2: "+ Menu.Playername2);

    }

    // player moves
    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {
        KeyCode k = keyEvent.getCode();
        if(k.equals(KeyCode.W)) player1.setGo(0, true);
        if(k.equals(KeyCode.S)) player1.setGo(1, true);
        if(k.equals(KeyCode.A)) player1.setGo(2, true);
        if(k.equals(KeyCode.D)) player1.setGo(3, true);
        if(k.equals(KeyCode.UP)) player2.setGo(0, true);
        if(k.equals(KeyCode.DOWN)) player2.setGo(1, true);
        if(k.equals(KeyCode.LEFT)) player2.setGo(2, true);
        if(k.equals(KeyCode.RIGHT)) player2.setGo(3, true);
    }

    @FXML
    public void handleKeyReleased(KeyEvent keyEvent) {
        KeyCode k = keyEvent.getCode();
        if(k.equals(KeyCode.W)) player1.setGo(0, false);
        if(k.equals(KeyCode.S)) player1.setGo(1, false);
        if(k.equals(KeyCode.A)) player1.setGo(2, false);
        if(k.equals(KeyCode.D)) player1.setGo(3, false);
        if(k.equals(KeyCode.UP)) player2.setGo(0, false);
        if(k.equals(KeyCode.DOWN)) player2.setGo(1, false);
        if(k.equals(KeyCode.LEFT)) player2.setGo(2, false);
        if(k.equals(KeyCode.RIGHT)) player2.setGo(3, false);
    }

    private void setupTimelines() {

        Timeline fireLoop = new Timeline(new KeyFrame(Duration.millis(200), e -> {
            player1.fireBullet(player2.getCenterX(), player2.getCenterY());
            player2.fireBullet(player1.getCenterX(), player1.getCenterY());
        }));
        fireLoop.setCycleCount(Timeline.INDEFINITE);
        fireLoop.play();

        Timeline DotLoop = new Timeline(new KeyFrame(Duration.millis(200), e -> {
            if(dots.size() < 200) {
                Dot dot = new Dot();
                dots.add(dot);
                field.getChildren().add(dot);
            }

        }));
        DotLoop.setCycleCount(Timeline.INDEFINITE);
        DotLoop.play();
        gameLoop = new Timeline(new KeyFrame(Duration.millis(Timing.TICK_LENGTH), e -> {
            for(Dot dot : dots) {
                if(intersect(player1, dot)) {
                    player1.getShield().boost();
                    System.out.println("eat1");
                    queueRemoval(dot);
                } else if(intersect(player2, dot)) {
                    player2.getShield().boost();
                    System.out.println("eat2");
                    queueRemoval(dot);
                }

            }
            for (Entity entity : entities) {
                entity.doTick();


                if (entity instanceof Bullet) {
                    // bullet from player 1
                    if(((Bullet) entity).getPlayerID() == 1) {
                        if(intersect(player2.getShield(), entity)) {
                            queueRemoval(entity);
                        } else if(intersect(player2, entity)) {
                            queueRemoval(entity);
                            player2HP.setWidth(Math.max(0, player2HP.getWidth() - player2HPF.getWidth() * 0.05));
                        }

                    }

                    // bullet from player 2
                    if(((Bullet) entity).getPlayerID() == 2) {
                        if(intersect(player1.getShield(), entity)) {
                            queueRemoval(entity);
                        } else if(intersect(player1, entity)) {
                            queueRemoval(entity);
                            player1HP.setWidth(Math.max(0, player1HP.getWidth() - player1HPF.getWidth() * 0.05));
                        }

                    }
                }
            }


            //Remove Entities queued for removal
            for (Shape entity : entitiesToRemove) {
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

    public void queueAddition(Entity entity) {
        entitiesToAdd.add(entity);
    }

    public void queueRemoval(Shape entity) {
        entitiesToRemove.add(entity);
    }

    private void add(Entity entity) {
        entities.add(entity);
        field.getChildren().add(entity);
    }

    private void remove(Shape entity) {
        if(entity instanceof Rectangle) entities.remove(entity);
        if(entity instanceof Dot) dots.remove(entity);
        field.getChildren().remove(entity);
    }

    private boolean intersect(Shape var1, Shape var2) {
        return Shape.intersect(var1, var2).getBoundsInLocal().getWidth() != -1;
    }
}
