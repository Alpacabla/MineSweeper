package Main;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    private Button RegisteButton;

    @FXML
    private Button LogInButton;

    @FXML
    private TextField Username;

    public void LogIn() {
        //if(Username.getText().equals("123")) {
            ObservableList<Stage> stage = FXRobotHelper.getStages();
            Scene scene = null;
            try {
                scene = new Scene(FXMLLoader.load(getClass().getResource("../Fx/Greeting.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.get(0).setScene(scene);
        //}
    }

}
