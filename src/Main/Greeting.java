package Fx;

import Main.EasyTransFromWindow;
import Main.Utils;
import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Greeting implements Initializable {

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
        start(8, 8, 10);
    }

    public void setMediumStart() throws Exception {
        start(16, 16, 40);
    }

    public void setHardStart() throws Exception {
        start(30, 30, 99);
    }

    public void setCustomStart() throws Exception {
        try {
            int x = getLegalNumberFromTextField(XRange, 5, 30);
            int y = getLegalNumberFromTextField(YRange, 5, 30);
            int count = getLegalNumberFromTextField(NumberOfMine, 1, x * y - 1);
            start(x, y, count);
        } catch (Exception e) {
            new Utils().showAnAlert(Alert.AlertType.INFORMATION, "数据范围有误", "请确保输入的数据合法，高宽范围在5到30之间，且雷至少有一个");
        }
    }

    private int getLegalNumberFromTextField(TextField textField, int minNumber, int maxNumber) throws Exception {
        int number = new Integer(textField.getText());
        if (number >= minNumber && number <= maxNumber) {
            return number;
        } else {
            throw new Exception();
        }
    }

    public void start(int x, int y, int counts) throws Exception {
        EasyTransFromWindow.xRange = x;
        EasyTransFromWindow.yRange = y;
        EasyTransFromWindow.counts = counts;
        new Utils().changeStage(FXRobotHelper.getStages().get(0), "../Fx/Game.fxml");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UsernameLabel.setStyle("-fx-font-size:28;-fx-font:bold;");
        UsernameLabel.setText(EasyTransFromWindow.name);

    }
}
