package Fx;

import Main.Utils;
import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Greeting {

    @FXML
    private Label UsernameLabel;

    @FXML
    private Button EasyStart;

    @FXML
    private Button MediumStart;

    @FXML
    private Button HardStart;

    @FXML
    private Button CustomStart;

    @FXML
    private TextField XRange;

    @FXML
    private TextField YRange;

    @FXML
    private TextField NumberOfMine;

    public void setEasyStart() throws Exception {
        start(8,8,10);
    }

    public void setMediumStart() throws Exception {
        start(16,16,40);
    }

    public void setHardStart() throws Exception {
        start(30,30,99);
    }

    public void setCustomStart() {

    }

    public void start(int x,int y,int counts) throws Exception {
        GameType.xRange = x;
        GameType.yRange = y;
        GameType.counts=counts;
        new Utils().changeStage(FXRobotHelper.getStages().get(0), "../Fx/Game.fxml");
    }
}
