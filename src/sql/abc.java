package sql;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.junit.Test;

import java.io.IOException;
import java.sql.*;

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

    @Test
    public void f() {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            //1,加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2.创建连接
            //此处按照实际的数据库名称和账号密码进行修改
            //格式为jdbc:mysql://127.0.0.1:3306/数据库名称?useSSL=true&characterEncoding=utf-8&user=账号名&password=密码
            //connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/MySQL80?useSSL=false&autoReconnect=true&failOverReadOnly=false&serverTimezone=UTC","root","root");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/minesweeper?useSSL=false&serverTimezone=UTC", "root", "root");
            System.out.println("创建连接成功");
            //3.写sql
            //根据数据库实际的表名写SQL语句
//            String sql = "select * from userinfo";
//            //4.得到statement对象执行sql
//            statement = connection.prepareStatement(sql);
//            //5.得到结果集
//            rs = statement.executeQuery();
//            //6.处理结果集
//            while (rs.next()) {
//                System.out.println(rs.getInt(1));
//                System.out.println(rs.getString(2));
//                System.out.println(rs.getString(3));
//            }

        } catch (
                ClassNotFoundException e) {
            e.printStackTrace();
        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {
            //7.关闭
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("关闭成功");
        }
    }
}
