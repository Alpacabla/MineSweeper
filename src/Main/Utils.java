package Main;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Utils {

    private static final Rectangle2D screenBounds;

    static {
        screenBounds = Screen.getPrimary().getVisualBounds();
    }

    public void changeStage(Stage stage, String localPath) {
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(getClass().getResource(localPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        if(stage.isShowing()) {
            stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
        }
    }

    public void newWindow(String title,String localPath) {
        Stage stage = new Stage();
        changeStage(stage,localPath);
        stage.setTitle(title);
        stage.getIcons().add(new Image("/ImageSrc/MineSweeperIcon.png"));
        stage.show();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }
}
