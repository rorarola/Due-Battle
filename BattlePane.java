package Due_Battle;
import javafx.scene.layout.Pane;

public class BattlePane extends Pane{
    BattleSceneController controller;
    public BattleSceneController getController() {
        return controller;
    }

    public void setController(BattleSceneController controller) {
        this.controller = controller;
    }
}
