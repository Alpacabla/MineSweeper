package Main;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class Utils {

    //private final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

//    static {
//        screenBounds = Screen.getPrimary().getVisualBounds();
//    }

    public void changeStage(Stage stage, String localPath) {
        final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(getClass().getResource(localPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setResizable(false);
        stage.setScene(scene);
        if (stage.isShowing()) {
            stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
        }
    }

    public void newWindow(String title, String localPath) {
        final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Stage stage = new Stage();
        changeStage(stage, localPath);
        stage.setTitle(title);
        stage.getIcons().add(new Image("/ImageSrc/MineSweeperIcon.png"));
        stage.setResizable(false);
        stage.show();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/minesweeper?useSSL=true&characterEncoding=utf-8&user=root&password=root&serverTimezone=UTC");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //    @Test
//    public void connectToSQL() throws Exception {
//        ResultSet rs = null;
//        Connection connection = null;
//        PreparedStatement statement = null;
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/minesweeper?useSSL=true&characterEncoding=utf-8&user=root&password=root&serverTimezone=UTC");
//        System.out.println("创建连接成功");
//
//            String sql = "select * from user";
//            //4.得到statement对象执行sql
//            statement = connection.prepareStatement(sql);
//            //5.得到结果集
//            rs = statement.executeQuery();
//            //6.处理结果集
//            while (rs.next()) {
//                System.out.println(rs.getInt(1));
//                //System.out.println(rs.getString(2));
//                //System.out.println(rs.getString(3));
//            }
//        return ;
//    }
    public boolean showAnConfirm(String title, String info) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(info);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    public void showAnAlert(Alert.AlertType alertType, String title, String info) {
        Alert alert = new Alert(alertType);
        alert.titleProperty().set(title);
        alert.headerTextProperty().set(info);
        alert.showAndWait();
    }

    public boolean existAUser(String name) throws SQLException {
        return doQuery("select name from user where name = ?", new Object[]{name}).next();
    }

    public boolean registeAUser(String name) {
        try {
            if (!existAUser(name) && doUpdate("insert into user values(?)", new Object[]{name}) == 1) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * 执行查询通用方法
     *
     * @param sql    待执行的sql语句，参数使用？作为占位符
     * @param params 参数
     * @return 结果集
     */
    public ResultSet doQuery(String sql, Object[] params) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    /**
     * 执行更新、插入、删除操作通用方法
     *
     * @param sql    待执行的sql语句，参数使用？占位符
     * @param params 参数
     * @return 操作影响行数
     */
    public int doUpdate(String sql, Object[] params) {
        int effect = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            effect = preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return effect;
    }
}
