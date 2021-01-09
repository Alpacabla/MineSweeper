package Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Test {

    @org.junit.Test
    public void datebaseTest() {
        new Utils().doUpdate("insert into user values(?)", new Object[]{"123"});
    }

    public void main(String[] args) {
        Stage stage = new Stage();
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(getClass().getResource("../Fx/Game.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.getIcons().add(new Image("/ImageSrc/MineSweeperIcon.png"));
        stage.setResizable(false);
        stage.show();
    }


}
