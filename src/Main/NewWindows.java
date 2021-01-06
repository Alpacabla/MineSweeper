package Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NewWindows {

    private static final String title;

    static {
        title = "扫雷";
    }

    public void start(Stage primaryStage,String localPath) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(localPath));
        primaryStage.setTitle(title);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
