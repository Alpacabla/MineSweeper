package Main;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private Button RegisteButton;

    @FXML
    private Button LogInButton;

    @FXML
    private TextField Username;

    public void LogIn() {
        new Utils().changeStage(FXRobotHelper.getStages().get(0), "../Fx/Greeting.fxml");
    }

}
