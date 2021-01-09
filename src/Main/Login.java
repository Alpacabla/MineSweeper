package Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Login {

    @FXML
    private TextField LoginUsername;

    @FXML
    private Button LogInButton;

    @FXML
    private TextField RegisteUsername;

    @FXML
    private Button RegisteButton;

    @FXML
    void Login(ActionEvent event) {
        String name = LoginUsername.getText();
        try {
            if (new Utils().existAUser(name)) {
                ((Stage) LogInButton.getScene().getWindow()).close();
                EasyTransFromWindow.name = name;
                new Utils().newWindow("开始", "../Fx/Greeting.fxml");
            } else {
                if (new Utils().showAnConfirm("错误", "用户不存在，是否创建？") == true) {
                    RegisteUsername.setText(name);
                    RegisteButton.fireEvent(event);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void Registe(ActionEvent event) {
        String name = RegisteUsername.getText();
        try {
            if (new Utils().existAUser(name)) {
                if (new Utils().showAnConfirm("错误", "用户已存在，是否登录？") == true) {
                    LoginUsername.setText(name);
                    LogInButton.fireEvent(event);
                }
            } else {
                new Utils().registeAUser(name);
                LoginUsername.setText(name);
                LogInButton.fireEvent(event);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}

//    public void LogIn() {
//        new Utils().changeStage(FXRobotHelper.getStages().get(0), "../Fx/Greeting.fxml");
//    }