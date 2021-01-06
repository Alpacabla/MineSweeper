package Fx;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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
        start(10,10,10);
    }

    public void setMediumStart() {

    }

    public void setHardStart() {

    }

    public void setCustomStart() {

    }

    public void start(int x,int y,int counts) throws Exception {
        GameType.height=x;
        GameType.width=y;
        GameType.counts=counts;
        //new NewWindows().start(new Stage(),"../Fx/Game.fxml");
        ObservableList<Stage> stage = FXRobotHelper.getStages();
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(getClass().getResource("../Fx/Game.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.get(0).setScene(scene);
    }
}
