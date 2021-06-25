package Due_Battle;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GamePane extends Pane {
    private BattleSceneController controller;

    public GamePane() {
        this.setBackground(new Background(new BackgroundFill(Paint.valueOf("white"), null, null)));
    }


    public void setController(BattleSceneController controller) {
        this.controller = controller;
    }

    public BattleSceneController getController() {
        return controller;
    }
}
