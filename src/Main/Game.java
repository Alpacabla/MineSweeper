package Main;

import Fx.GameType;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class Game implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private FlowPane mineField;

    @FXML
    private Label timer;

    private ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>();
    private ArrayList<Cell> mines = new ArrayList<Cell>();

    private int width;
    private int height;
    private int counts;

    private final static int[][] director4 = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };

    private final static int[][] director8 = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };

    public Game() {
    }
    
    //因为使用了fxml样式，所以初始化要是用这个方法，不然会出现多线程问题
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.width = GameType.width;
        this.height = GameType.height;
        this.counts = GameType.counts;

        borderPane.setPrefWidth(width * 50.0);
        borderPane.setPrefHeight(height * 50.0 + 100.0);

        mineField.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Cell cell = null;
                try {
                    cell = (Cell) event.getTarget();
                    MouseButton mouseButton = event.getButton();
                    int x = cell.getX() , y = cell.getY();
                    switch (mouseButton) {
                        case PRIMARY:       //鼠标左键事件
                            if (!cell.isOpened()) {
                                if (cell.isMine()) {
                                    gameOver(x,y);
                                } else {
                                    cell.sweep();
                                    for (int i = 0; i < 4; i++) {
                                        int xx = x + director4[i][0], yy = y + director4[i][1];
                                        dfsSweep(xx, yy);
                                    }
                                }
                            }
                            break;
                        case SECONDARY:     //鼠标右键事件
//                            if (cell.isOpened()) {
//                                cell.isFlag = true;
//                                cell.setText("旗");
//                            }
                            break;
                    }
                }catch (ClassCastException e) {

                }

            }
        });

        for (int i = 0; i < height; i++) {
            ArrayList<Cell> cellArr = new ArrayList<Cell>();
            for (int j = 0; j < width; j++) {
                cellArr.add(new Cell(i,j));
            }
            cells.add(cellArr);
        }

//        mineField.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                System.out.println("123");
//            }
//
//        });

        for (ArrayList<Cell> cellArr : cells) {
            for (Button cell : cellArr) {
                mineField.getChildren().add(cell);
                cell.setPrefHeight(50);
                cell.setPrefWidth(50);
            }
        }

        Random random = new Random();
        for (int i = 1; i <= counts; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            while (cells.get(x).get(y).isMine()) {
                x = random.nextInt(width);
                y = random.nextInt(height);
            }
            cells.get(x).get(y).setMine(true);
            mines.add(cells.get(x).get(y));
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int cnt = 0;
                for (int k = 0; k < 8; k++) {
                    int x = i + director8[k][0], y = j + director8[k][1];
                    if (judge(x, y) && cells.get(x).get(y).isMine()) {
                        cnt++;
                    }
                }
                cells.get(i).get(j).setCounts(cnt);
            }
        }
    }

    private void gameOver(int x, int y) {
        cells.get(x).get(y).turnChooseBomb();
        for(Cell mine : mines) {
            mine.turnBomb();
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("游戏结束");
        alert.setHeaderText(null);
        alert.setContentText("你想要重新开始吗?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            mineField = new FlowPane();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    private void dfsSweep(int x, int y) {
        if(!judge(x,y)) return ;
        Cell cell = cells.get(x).get(y);
        if(cell.getCounts() != 0 || cell.isMine() || cell.isOpened()) return ;
        cell.sweep();
        for (int i = 0; i < 4; i++) {
            int xx = x + director4[i][0], yy = y + director4[i][1];
            dfsSweep(xx, yy);
        }
        return ;
    }

    private boolean judge(int x, int y) {
        return (x >= 0 && x < this.width) && (y >= 0 && y < this.height);
    }

}

