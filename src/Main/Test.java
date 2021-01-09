package Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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

    @org.junit.Test
    public void writeGradesTest() {
        Random random = new Random();
        for (int i = 1; i <= 100; i++) {
            new Utils().writeAGrade("123", EasyTransFromWindow.GAMETYPE.EASY, Math.abs(random.nextLong()) % 360000);
        }
    }

    public void wirte10000ToDataTable() {
        Random random = new Random();
        ArrayList<String> names = new ArrayList<>();
        int num = 300;
        for (int i = 0; i < num; i++) {
            String name = "";
            int len = Math.abs(random.nextInt(8)) + 1;
            name += (char)('A' + (char)Math.abs(random.nextInt()%26));
            for (int j = 1; j < len; j++) {
                name += (char)('a' + (char)Math.abs(random.nextInt()%26));
            }
            new Utils().registeAUser(name);
            names.add(name);
        }
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 3; k++) {
                    new Utils().writeAGrade(names.get(i), EasyTransFromWindow.GAMETYPES[k], Math.abs(random.nextLong()) % 320000 + 40000 - i * 10);
                }

            }
        }
    }

    @org.junit.Test
    public void gameTest() {
        new Utils().newWindow("test", "../Fx/Greeting.fxml");
    }
}
