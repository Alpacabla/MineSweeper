package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../Fx/Login.fxml"));
        primaryStage.setTitle("扫雷");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image("/ImageSrc/MineSweeperIcon.png"));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
