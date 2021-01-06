package sql;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;

public class abc extends Application {

    @FXML
    private FlowPane flow;

    @FXML
    private Button addButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
        primaryStage.setTitle("扫雷");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public void setAddButton() {
        flow.getChildren().add(new Button());
    }
}
